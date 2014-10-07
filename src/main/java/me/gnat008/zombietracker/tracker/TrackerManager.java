/*
 * Copyright (c) 2014 Gnat008
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.gnat008.zombietracker.tracker;

import me.gnat008.zombietracker.ZTMain;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class TrackerManager {

    private static TrackerManager instance;

    private HashMap<UUID, Tracker> activeTrackers = new HashMap<UUID, Tracker>();
    private final long DELAY = 200L;

    private ZTMain plugin;

    private TrackerManager(ZTMain plugin) {
        this.plugin = plugin;
    }

    public static TrackerManager getInstance(ZTMain plugin) {
        if (instance == null) {
            instance = new TrackerManager(plugin);
            return instance;
        }

        return instance;
    }

    public HashMap<UUID, Tracker> getActiveTrackers() {
        return this.activeTrackers;
    }

    public void createTracker(Player player) {
        Wool wool = new Wool(DyeColor.BLACK);
        ItemStack item = wool.toItemStack(1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Zombie Scanning Device");
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
        activeTrackers.put(player.getUniqueId(), new Tracker(DyeColor.BLACK));
        startCheckingTask(player);
    }

    public void removeTracker(Player player) {
        Iterator<ItemStack> itr = player.getInventory().iterator();
        while (itr.hasNext()) {
            if (itr.next().hasItemMeta() &&
                    itr.next().getItemMeta().hasDisplayName() &&
                    itr.next().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Zombie Scanning Device")) {
                itr.remove();
                break;
            }
        }

        activeTrackers.remove(player.getUniqueId());
        player.updateInventory();
    }

    private void startCheckingTask(final Player player) {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (!(activeTrackers.containsKey(player.getUniqueId()))) {
                    return;
                }

                boolean found = false;
                while (!found) {
                    for (Entity entity : player.getNearbyEntities(50D, 50D, 50D)) {
                        if (entity instanceof Zombie) {
                            Location entityLocation = entity.getLocation();
                            Location playerLocation = player.getLocation();

                            if (entityLocation.distance(playerLocation) < 10) {
                                updateTracker(DyeColor.WHITE, player);
                            } else if (entityLocation.distance(playerLocation) < 20) {
                                updateTracker(DyeColor.LIME, player);
                            } else if (entityLocation.distance(playerLocation) < 30) {
                                updateTracker(DyeColor.GREEN, player);
                            } else if (entityLocation.distance(playerLocation) < 40) {
                                updateTracker(DyeColor.YELLOW, player);
                            } else if (entityLocation.distance(playerLocation) < 50) {
                                updateTracker(DyeColor.ORANGE, player);
                            } else {
                                updateTracker(DyeColor.RED, player);
                            }

                            found = true;
                        }
                    }
                }
            }
        }, DELAY, DELAY);
    }

    private void updateTracker(DyeColor newColor, Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta() &&
                    item.getItemMeta().hasDisplayName() &&
                    item.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Zombie Scanning Device")) {
                item.setDurability((short) newColor.getDyeData());
                player.updateInventory();
            }
        }
    }
}
