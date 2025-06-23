package net.mineland.duels.arena;

import net.mineland.duels.DuelPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArenaManager {
    private final List<Arena> arenas = new ArrayList<>();

    public ArenaManager() {
        loadArenas();
    }

    private void loadArenas() {
        arenas.clear();
        List<Map<?, ?>> arenaList = DuelPlugin.getInstance().getConfig().getMapList("arenas");
        for (Map<?, ?> map : arenaList) {
            String name = (String) map.get("name");
            String worldName = (String) map.get("world");
            World world = Bukkit.getWorld(worldName);
            if (world == null) continue;
            String[] s1 = ((String) map.get("spawn1")).split(",");
            String[] s2 = ((String) map.get("spawn2")).split(",");
            Location spawn1 = new Location(world, Double.parseDouble(s1[0]), Double.parseDouble(s1[1]), Double.parseDouble(s1[2]), Float.parseFloat(s1[3]), Float.parseFloat(s1[4]));
            Location spawn2 = new Location(world, Double.parseDouble(s2[0]), Double.parseDouble(s2[1]), Double.parseDouble(s2[2]), Float.parseFloat(s2[3]), Float.parseFloat(s2[4]));
            arenas.add(new Arena(name, world, spawn1, spawn2));
        }
    }

    public Arena getFreeArena() {
        for (Arena arena : arenas) {
            if (!arena.isOccupied()) {
                arena.setOccupied(true);
                return arena;
            }
        }
        return null;
    }

    public void releaseArena(Arena arena) {
        arena.setOccupied(false);
    }

    public List<Arena> getArenas() {
        return arenas;
    }
} 