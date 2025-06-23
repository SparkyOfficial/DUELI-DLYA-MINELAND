package net.mineland.duels.arena;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldInstanceManager {
    private final Map<UUID, String> duelWorlds = new HashMap<>();
    private int worldCounter = 0;

    /**
     * Клонирует шаблонный мир для дуэли, возвращает имя нового мира
     */
    public String createInstance(String templateWorld) {
        String newWorld = templateWorld + "_duel_" + (++worldCounter);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv clone " + templateWorld + " " + newWorld);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv load " + newWorld);
        return newWorld;
    }

    /**
     * Удаляет временный мир после дуэли
     */
    public void deleteInstance(String worldName) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv unload " + worldName);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv delete " + worldName);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv confirm");
    }

    public static void deleteInstanceStatic(String worldName) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv unload " + worldName);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv delete " + worldName);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv confirm");
    }

    public void registerDuelWorld(UUID duelId, String worldName) {
        duelWorlds.put(duelId, worldName);
    }
    public String getDuelWorld(UUID duelId) {
        return duelWorlds.get(duelId);
    }
    public void unregisterDuelWorld(UUID duelId) {
        duelWorlds.remove(duelId);
    }
} 