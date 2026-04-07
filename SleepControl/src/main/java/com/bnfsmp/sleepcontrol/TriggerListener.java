package com.bnfsmp.sleepcontrol;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class TriggerListener implements Listener {

    @EventHandler
    public void onTrigger(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage();

        if (!msg.startsWith("/trigger sleepcancel")) return;

        event.setCancelled(true);

        for (Player p : SleepControl.instance.sleepingPlayers) {
            p.wakeup(true);
        }

        SleepControl.instance.sleepingPlayers.clear();

        if (SleepControl.instance.getConfig().getBoolean("messages.cancelled.enabled")) {
            Bukkit.broadcastMessage(
                SleepControl.instance.getConfig().getString("messages.cancelled.text")
            );
        }
    }
}