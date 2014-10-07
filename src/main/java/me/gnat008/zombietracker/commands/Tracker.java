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

package me.gnat008.zombietracker.commands;

import me.gnat008.zombietracker.ZTMain;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class Tracker {

    private enum ValidArgs {ON, OFF}

    private Player player;
    private String[] args;
    private ZTMain plugin;

    private final long DELAY = 200L; // 10 seconds

    public Tracker(ZTMain plugin, Player player, String... args) {
        this.plugin = plugin;
        this.player = player;
        this.args = args;

        doCommand();
    }

    private void doCommand() {
        if (args.length == 1) {
            if (plugin.getPlayers().containsKey(player)) {
                plugin.removePlayer(player);
                plugin.getPrinter().printToPlayer(player, "Tracker disabled!", false);
            } else {
                plugin.addPlayer(player);
                plugin.getPrinter().printToPlayer(player, "Tracker enabled!", false);
                runCheckingTask();
            }
        } else if (args.length == 2) {
            ValidArgs vargs;
            try {
                vargs = ValidArgs.valueOf(args[1].toUpperCase());
            } catch (Exception notEnum) {
                plugin.getPrinter().printToPlayer(player, "Invalid usage! Use /zt tracker [on|off]", true);
                return;
            }

            switch (vargs) {
                case ON:
                    plugin.addPlayer(player);
                    plugin.getPrinter().printToPlayer(player, "Tracker enabled!", false);
                    runCheckingTask();

                case OFF:
                    plugin.removePlayer(player);
                    plugin.getPrinter().printToPlayer(player, "Tracker disabled!", false);
            }
        } else {
            plugin.getPrinter().printToPlayer(player, "Invalid usage! Use /zt tracker [on|off]", true);
        }
    }

    private void runCheckingTask() {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (!(plugin.getPlayers().containsKey(player))) {
                    return;
                }

                boolean found = false;
                while (!found) {
                    for (Entity entity : player.getNearbyEntities(50D, 50D, 50D)) {
                        if (entity instanceof Zombie) {
                            Location entityLocation = entity.getLocation();
                            Location playerLocation = player.getLocation();

                            if (entityLocation.distance(playerLocation) < 10) {
                                //
                            } else if (entityLocation.distance(playerLocation) < 20) {
                                //
                            } else if (entityLocation.distance(playerLocation) < 30) {
                                //
                            } else if (entityLocation.distance(playerLocation) < 40) {
                                //
                            } else if (entityLocation.distance(playerLocation) < 50) {
                                //
                            } else {
                                //
                            }

                            found = true;
                        }
                    }
                }
            }
        }, DELAY, DELAY);
    }
}
