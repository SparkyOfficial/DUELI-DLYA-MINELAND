package net.mineland.duels.gui;

import net.mineland.duels.DuelPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class ModeSelectGui implements Listener {
    public static final String GUI_TITLE = "Выбор режима дуэли";

    public static void open(Player player) {
        var config = DuelPlugin.getInstance().getConfig();
        ConfigurationSection modes = config.getConfigurationSection("modes");
        if (modes == null) return;
        int size = Math.max(9, ((modes.getKeys(false).size() + 8) / 9) * 9);
        Inventory inv = Bukkit.createInventory(null, size, GUI_TITLE);
        int slot = 0;
        for (String key : modes.getKeys(false)) {
            ConfigurationSection mode = modes.getConfigurationSection(key);
            String name = mode.getString("name", key);
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§a" + name);
            List<String> lore = new ArrayList<>();
            lore.add("§7Режим: " + key);
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(slot++, item);
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(GUI_TITLE)) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            Player player = (Player) event.getWhoClicked();
            String mode = null;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            var config = DuelPlugin.getInstance().getConfig();
            ConfigurationSection modes = config.getConfigurationSection("modes");
            for (String key : modes.getKeys(false)) {
                if (name.contains(modes.getConfigurationSection(key).getString("name", key))) {
                    mode = key;
                    break;
                }
            }
            if (mode != null) {
                player.closeInventory();
                Bukkit.getPluginManager().callEvent(new ModeSelectEvent(player, mode));
            }
        }
    }

    public static class ModeSelectEvent extends org.bukkit.event.Event {
        private static final HandlerList handlers = new HandlerList();
        private final Player player;
        private final String mode;
        public ModeSelectEvent(Player player, String mode) {
            this.player = player;
            this.mode = mode;
        }
        public Player getPlayer() { return player; }
        public String getMode() { return mode; }
        @Override public HandlerList getHandlers() { return handlers; }
        public static HandlerList getHandlerList() { return handlers; }
    }
} 