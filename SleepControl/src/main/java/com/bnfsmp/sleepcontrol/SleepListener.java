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

            String text = SleepControl.instance.getConfig()
                    .getString("messages.sleep.text")
                    .replace("%player%", player.getName());

            Component msg = Component.text(text)
                    .clickEvent(ClickEvent.runCommand("/trigger sleepcancel set 1"));

            Bukkit.broadcast(msg);
        }

        SleepControl.instance.checkSleep();
    }

    @EventHandler
    public void onLeave(PlayerBedLeaveEvent event) {
        SleepControl.instance.sleepingPlayers.remove(event.getPlayer());
    }
}