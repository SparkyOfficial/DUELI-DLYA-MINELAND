package net.mineland.duels.util;

import net.mineland.duels.DuelPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitUtil {
    public static void giveKit(Player player, String mode) {
        FileConfiguration config = DuelPlugin.getInstance().getConfig();
        ConfigurationSection modeSection = config.getConfigurationSection("modes." + mode + ".items");
        if (modeSection == null) modeSection = config.getConfigurationSection("items");
        player.getInventory().clear();
        // Меч
        if (modeSection.contains("sword"))
            player.getInventory().setItem(0, new ItemStack(Material.valueOf(modeSection.getString("sword", "DIAMOND_SWORD"))));
        // Лук
        if (modeSection.contains("bow"))
            player.getInventory().setItem(1, new ItemStack(Material.valueOf(modeSection.getString("bow"))));
        // Стрелы
        if (modeSection.contains("arrows"))
            player.getInventory().setItem(8, new ItemStack(Material.ARROW, modeSection.getInt("arrows", 0)));
        // Броня
        if (modeSection.contains("armor.helmet"))
            player.getInventory().setHelmet(new ItemStack(Material.valueOf(modeSection.getString("armor.helmet"))));
        if (modeSection.contains("armor.chestplate"))
            player.getInventory().setChestplate(new ItemStack(Material.valueOf(modeSection.getString("armor.chestplate"))));
        if (modeSection.contains("armor.leggings"))
            player.getInventory().setLeggings(new ItemStack(Material.valueOf(modeSection.getString("armor.leggings"))));
        if (modeSection.contains("armor.boots"))
            player.getInventory().setBoots(new ItemStack(Material.valueOf(modeSection.getString("armor.boots"))));
        // Зелья
        if (modeSection.contains("potions")) {
            for (String potion : modeSection.getStringList("potions")) {
                PotionEffectType type = PotionEffectType.getByName(potion);
                if (type != null) {
                    player.addPotionEffect(new PotionEffect(type, 20*60, 0));
                }
            }
        }
        // Золотые яблоки
        if (modeSection.contains("golden_apples"))
            player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, modeSection.getInt("golden_apples", 3)));
    }
} 