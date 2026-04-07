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

        Component msg = Component.text("§e" + player.getName() + " went to sleep ")
                .append(Component.text("§c[Cancel]")
                        .clickEvent(ClickEvent.runCommand("/sleepcancel " + player.getName())));

        Bukkit.broadcast(msg);

        SleepControl.instance.checkSleep();
    }

    @EventHandler
    public void onLeave(PlayerBedLeaveEvent event) {
        SleepControl.instance.sleepingPlayers.remove(event.getPlayer());
    }
}