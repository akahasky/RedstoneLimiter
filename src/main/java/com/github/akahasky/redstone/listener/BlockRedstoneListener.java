package com.github.akahasky.redstone.listener;

import com.github.akahasky.redstone.controller.RedstoneController;
import com.github.akahasky.redstone.model.ChunkTicks;
import com.google.common.collect.ImmutableSet;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.logging.Level;

public class BlockRedstoneListener implements Listener {

    private final ImmutableSet<Material> redstoneMaterials = ImmutableSet.<Material>builder().add(

            Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON,
            Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON

    ).build();

    @EventHandler(priority = EventPriority.LOWEST)
    void on(BlockRedstoneEvent event) {

        Chunk chunk = event.getBlock().getChunk();
        ChunkTicks chunkTicks = RedstoneController.get(chunk.getX(), chunk.getZ());

        if (redstoneMaterials.contains(event.getBlock().getType()))
            chunkTicks.setTicks(chunkTicks.getTicks() + 15);

        else chunkTicks.setTicks(chunkTicks.getTicks() + 1);

        if (chunkTicks.getTicks() < RedstoneController.getMaxTicks()) return;

        event.setNewCurrent(0);
        chunkTicks.setTicks(0);

        event.getBlock().breakNaturally();

        if (chunkTicks.isWarned()) return;

        chunkTicks.setWarned(true);

        for (Entity entity : chunk.getEntities()) {

            if (!(entity instanceof Player)) continue;

            entity.sendMessage("§cMany redstone circuits in the same region.");

        }

        Location location = event.getBlock().getLocation();

        Bukkit.getLogger().log(Level.WARNING, String.format(

                "A very large redstone circuit has been blocked at %s,%s, %s!",
                location.getBlockX(), location.getBlockY(), location.getBlockZ()

        ));

    }

}
