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
        getCommand("sleepcancel").setExecutor(new SleepCancelCommand());
    }

    public int getRequiredPlayers() {
        return getConfig().getInt("players-needed", 1);
    }

    public void checkSleep() {
        if (sleepingPlayers.size() >= getRequiredPlayers()) {
            for (World world : Bukkit.getWorlds()) {
                if (world.getEnvironment() == World.Environment.NORMAL) {
                    world.setTime(0);
                    world.setStorm(false);
                }
            }

            Bukkit.broadcastMessage("§aNight skipped!");
            sleepingPlayers.clear();
        }
    }
}