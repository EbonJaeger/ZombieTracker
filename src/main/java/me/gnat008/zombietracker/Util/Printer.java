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

package me.gnat008.zombietracker.Util;

import me.gnat008.zombietracker.ZTMain;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * This is a utility class for other classes in a plugin to easily send a message to either the console
 * or a specific player that is on the server running the plugin.
 */
public class Printer {

    private ZTMain plugin;
    private String pluginName;

    public Printer(ZTMain plugin) {
        this.plugin = plugin;
        this.pluginName = plugin.getName();
    }

    /**
     * Method to print a message to the server console.
     *
     * @param msg  The message to send to console.
     * @param warn The level of the message.
     */
    public void printToConsole(String msg, boolean warn) {
        if (warn) {
            plugin.getLogger().warning("[" + pluginName + "] " + msg);
        } else {
            plugin.getLogger().info("[" + pluginName + "] " + msg);
        }
    }

    /**
     * Method to print a message to a specific player on the server.
     *
     * @param player The player to send the message to.
     * @param msg    The message to send.
     * @param warn   If the message should be a warning (true for red, false for green).
     */
    public void printToPlayer(Player player, String msg, boolean warn) {
        String message = "";

        if (warn) {
            message += ChatColor.RED;
        } else {
            message += ChatColor.GREEN;
        }

        message += "[" + pluginName + "] " + msg;

        player.sendMessage(message);
    }
}
