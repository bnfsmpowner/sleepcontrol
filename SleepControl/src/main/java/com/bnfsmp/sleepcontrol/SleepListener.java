package com.bnfsmp.sleepcontrol;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class SleepListener implements Listener {

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();

        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        SleepControl.instance.sleepingPlayers.add(player);

        if (SleepControl.instance.getConfig().getBoolean("messages.sleep.enabled")) {

            int sleeping = SleepControl.instance.sleepingPlayers.size();
            int needed = SleepControl.instance.getRequiredPlayers();

            String text = SleepControl.instance.getConfig()
                    .getString("messages.sleep.text")
                    .replace("%player%", player.getName())
                    .replace("%sleeping%", String.valueOf(sleeping))
                    .replace("%needed%", String.valueOf(needed));

            Component msg = Component.text(text)
                    .clickEvent(ClickEvent.callback(audience -> {

                        if (!(audience instanceof Player)) return;
                        Player clicker = (Player) audience;

                        // Cancel sleep
                        for (Player p : SleepControl.instance.sleepingPlayers) {
                            p.wakeup(true);
                        }

                        SleepControl.instance.sleepingPlayers.clear();

                        // Cancel message with placeholders
                        if (SleepControl.instance.getConfig().getBoolean("messages.cancelled.enabled")) {

                            String cancelMsg = SleepControl.instance.getConfig()
                                    .getString("messages.cancelled.text")
                                    .replace("%player%", clicker.getName()) // canceller
                                    .replace("%canceller%", clicker.getName());

                            Bukkit.broadcastMessage(cancelMsg);
                        }

                    }));

            Bukkit.broadcast(msg);
        }

        SleepControl.instance.checkSleep();
    }

    @EventHandler
    public void onLeave(PlayerBedLeaveEvent event) {
        SleepControl.instance.sleepingPlayers.remove(event.getPlayer());
    }
}