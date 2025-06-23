package net.mineland.duels.game;

import net.mineland.duels.arena.Arena;
import net.mineland.duels.stats.StatsManager;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.mineland.duels.arena.WorldInstanceManager;

public class DuelManager {
    private final Map<UUID, Duel> activeDuels = new HashMap<>();
    private final Map<UUID, String> duelWorlds = new HashMap<>(); // duelId -> instanceWorld
    private final StatsManager statsManager;

    public DuelManager(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    public void startDuel(Player p1, Player p2, Arena arena, String mode) {
        Duel duel = new Duel(p1, p2, arena, statsManager, mode);
        activeDuels.put(p1.getUniqueId(), duel);
        activeDuels.put(p2.getUniqueId(), duel);
        duel.start();
        // Сохраняем instanceWorld для удаления после дуэли
        if (arena.getWorld() != null) {
            duelWorlds.put(p1.getUniqueId(), arena.getWorld().getName());
        }
    }

    public void endDuel(Player winner, Player loser) {
        Duel duel = activeDuels.get(winner != null ? winner.getUniqueId() : loser.getUniqueId());
        if (duel != null) {
            duel.end(winner, loser);
            // Удаляем временный мир после дуэли
            String worldName = duelWorlds.get(duel.getPlayer1().getUniqueId());
            if (worldName != null) {
                WorldInstanceManager.deleteInstanceStatic(worldName);
            }
            activeDuels.remove(duel.getPlayer1().getUniqueId());
            activeDuels.remove(duel.getPlayer2().getUniqueId());
            duelWorlds.remove(duel.getPlayer1().getUniqueId());
        }
    }

    public Duel getDuel(Player player) {
        return activeDuels.get(player.getUniqueId());
    }

    public boolean isInDuel(Player player) {
        return activeDuels.containsKey(player.getUniqueId());
    }
} 