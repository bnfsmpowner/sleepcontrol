package com.bnfsmp.sleepcontrol;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class SleepControl extends JavaPlugin implements Listener {

    public static SleepControl instance;
    public Set<Player> sleepingPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new SleepListener(), this);
        Bukkit.getPluginManager().registerEvents(new TriggerListener(), this);

        // Register command
        getCommand("sleepcontrol").setExecutor(new SleepControlCommand());

        // Create trigger (safe if already exists)
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard objectives add sleepcancel trigger");
    }

    public int getRequiredPlayers() {
        return getConfig().getInt("players-needed", 1);
    }

    public void checkSleep() {
        if (sleepingPlayers.size() >= getRequiredPlayers()) {

            int delaySeconds = getConfig().getInt("delay-seconds", 3);
            long delay = delaySeconds * 20L;

            if (getConfig().getBoolean("messages.countdown.enabled")) {
                String msg = getConfig().getString("messages.countdown.text")
                        .replace("%seconds%", String.valueOf(delaySeconds));
                Bukkit.broadcastMessage(msg);
            }

            Bukkit.getScheduler().runTaskLater(this, () -> {

                if (sleepingPlayers.size() >= getRequiredPlayers()) {

                    for (World world : Bukkit.getWorlds()) {
                        if (world.getEnvironment() == World.Environment.NORMAL) {
                            world.setTime(0);
                            world.setStorm(false);
                        }
                    }

                    if (getConfig().getBoolean("messages.skipped.enabled")) {
                        Bukkit.broadcastMessage(getConfig().getString("messages.skipped.text"));
                    }

                    sleepingPlayers.clear();
                }

            }, delay);
        }
    }
}