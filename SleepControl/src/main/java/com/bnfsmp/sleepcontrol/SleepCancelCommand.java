package com.bnfsmp.sleepcontrol;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepCancelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage("§cUsage: /sleepcancel <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }

        if (SleepControl.instance.sleepingPlayers.remove(target)) {
            target.wakeup(true); // ✅ FIX HERE
            Bukkit.broadcastMessage("§c" + target.getName() + "'s sleep was cancelled.");
        } else {
            sender.sendMessage("§cThat player is not sleeping.");
        }

        return true;
    }
}