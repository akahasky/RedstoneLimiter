package com.github.akahasky.redstone;

import com.github.akahasky.redstone.controller.RedstoneController;
import com.github.akahasky.redstone.listener.BlockRedstoneListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class RedstonePlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        RedstoneController.init();
        Bukkit.getPluginManager().registerEvents(new BlockRedstoneListener(), this);
        Bukkit.getLogger().log(Level.INFO, "Plugin started successfully!");

    }

    @Override
    public void onDisable() {

        HandlerList.unregisterAll(this);
        Bukkit.getLogger().log(Level.INFO, "The plugin has been successfully shut down!");

    }

}
