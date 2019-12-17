package com.github.arboriginal.CoralBlocks;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class CB extends JavaPlugin implements Listener {
    private final String         k = getClass().getCanonicalName();
    private final MetadataValue  f = new FixedMetadataValue(this, true);
    private final List<Material> c = Arrays.asList(Material.BUBBLE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK,
            Material.FIRE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK, Material.TUBE_CORAL_BLOCK);

    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockBreak(BlockBreakEvent e) {
        Block    b = e.getBlock();
        Material t = b.getType();
        if (!c.contains(t) || !e.isDropItems()) return;
        b.removeMetadata(k, this);
        b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(t));
        e.setDropItems(false);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onBlockPlace(BlockPlaceEvent e) {
        if (c.contains(e.getBlock().getType()) && e.getPlayer().isSneaking()) e.getBlock().setMetadata(k, f);
    }

    @EventHandler(ignoreCancelled = true)
    private void onBlockFade(BlockFadeEvent e) {
        if (e.getBlock().hasMetadata(k)) e.setCancelled(true);
    }
}
