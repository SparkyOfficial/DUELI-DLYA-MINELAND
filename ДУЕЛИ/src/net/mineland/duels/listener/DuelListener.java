package net.mineland.duels.listener;

import net.mineland.duels.game.DuelManager;
import net.mineland.duels.game.Duel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DuelListener implements Listener {
    private final DuelManager duelManager;

    public DuelListener(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (duelManager.isInDuel(player)) {
            Duel duel = duelManager.getDuel(player);
            Player winner = duel.getOpponent(player);
            duelManager.endDuel(winner, player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (duelManager.isInDuel(player)) {
            Duel duel = duelManager.getDuel(player);
            Player winner = duel.getOpponent(player);
            duelManager.endDuel(winner, player);
        }
    }
} 