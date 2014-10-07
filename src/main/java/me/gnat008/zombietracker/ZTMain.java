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

package me.gnat008.zombietracker;

import me.gnat008.zombietracker.commands.ZTCommand;
import me.gnat008.zombietracker.listeners.PlayerQuitListener;
import me.gnat008.zombietracker.util.Printer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.WeakHashMap;

public class ZTMain extends JavaPlugin {

    private WeakHashMap<Player, Boolean> players = new WeakHashMap<Player, Boolean>();

    private Printer printer;

    @Override
    public void onEnable() {
        this.printer = new Printer(this);

        getCommand("zombietracker").setExecutor(new ZTCommand(this));

        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }

    public WeakHashMap<Player, Boolean> getPlayers() {
        return this.players;
    }

    public Printer getPrinter() {
        return this.printer;
    }

    public void addPlayer(Player player) {
        this.players.put(player, true);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }
}
