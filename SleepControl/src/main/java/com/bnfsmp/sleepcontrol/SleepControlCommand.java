package com.bnfsmp.sleepcontrol;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SleepControlCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§eUsage: /sleepcontrol reload");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {

            if (!sender.hasPermission("sleepcontrol.reload")) {
                sender.sendMessage("§cNo permission.");
                return true;
            }

            SleepControl.instance.reloadConfig();
            sender.sendMessage("§aSleepControl config reloaded.");
            return true;
        }

        sender.sendMessage("§cUnknown subcommand.");
        return true;
    }
}