package com.github.arboriginal.CoralBlocks;

import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class CB extends JavaPlugin implements Listener {
    private final MetadataValue v = new FixedMetadataValue(this, true);
    private final String        k = getClass().getCanonicalName();
    private Set<Material>       c;

    @Override
    public void onEnable() {
        super.onEnable();
        c = Tag.CORAL_BLOCKS.getValues();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onBlockPlace(BlockPlaceEvent e) {
        if (c.contains(e.getBlock().getType()) && e.getPlayer().isSneaking()) e.getBlock().setMetadata(k, v);
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockFade(BlockFadeEvent e) {
        Block b = e.getBlock();
        if (b.hasMetadata(k)) {
            b.removeMetadata(k, this);
            e.setCancelled(true);
        }
    }
}
