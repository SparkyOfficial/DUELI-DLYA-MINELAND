package net.mineland.duels.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PointsAPIUtil {
    private static Plugin pointsAPI;

    public static void setupPointsAPI() {
        if (pointsAPI == null) {
            pointsAPI = Bukkit.getPluginManager().getPlugin("PointsAPI");
        }
    }

    public static boolean isEnabled() {
        return pointsAPI != null && pointsAPI.isEnabled();
    }

    public static void givePoints(Player player, int amount) {
        if (isEnabled()) {
            pointsAPI.getServer().dispatchCommand(Bukkit.getConsoleSender(), "points give " + player.getName() + " " + amount);
        }
    }
} 