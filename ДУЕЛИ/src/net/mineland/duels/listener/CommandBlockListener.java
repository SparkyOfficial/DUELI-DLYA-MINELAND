package net.mineland.duels.listener;

import net.mineland.duels.DuelPlugin;
import net.mineland.duels.game.DuelManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.List;

public class CommandBlockListener implements Listener {
    private final DuelManager duelManager;
    public CommandBlockListener(DuelManager duelManager) {
        this.duelManager = duelManager;
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!duelManager.isInDuel(player)) return;
        String cmd = event.getMessage().split(" ")[0].toLowerCase();
        if (cmd.equals("/duel") && event.getMessage().toLowerCase().contains("leave")) return;
        List<String> blocked = DuelPlugin.getInstance().getConfig().getStringList("blocked-commands");
        if (blocked.contains(cmd)) {
            event.setCancelled(true);
            player.sendMessage("§cЭта команда запрещена во время дуэли!");
        }
    }
} 