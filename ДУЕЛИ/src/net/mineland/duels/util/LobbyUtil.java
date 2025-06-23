package net.mineland.duels.util;

import net.mineland.duels.DuelPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LobbyUtil {
    public static void teleportToLobby(Player player) {
        var config = DuelPlugin.getInstance().getConfig();
        String worldName = config.getString("lobby.world", "world");
        double x = config.getDouble("lobby.x", 0);
        double y = config.getDouble("lobby.y", 64);
        double z = config.getDouble("lobby.z", 0);
        float yaw = (float) config.getDouble("lobby.yaw", 0);
        float pitch = (float) config.getDouble("lobby.pitch", 0);
        World world = Bukkit.getWorld(worldName);
        if (world == null) return;
        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
    }
} 