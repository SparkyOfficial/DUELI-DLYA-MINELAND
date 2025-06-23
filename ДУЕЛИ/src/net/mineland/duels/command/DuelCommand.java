package net.mineland.duels.command;

import net.mineland.duels.game.DuelManager;
import net.mineland.duels.queue.DuelQueue;
import net.mineland.duels.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import java.util.*;

public class DuelCommand implements CommandExecutor {
    private final DuelManager duelManager;
    private final DuelQueue duelQueue;
    private final StatsManager statsManager;

    public DuelCommand(DuelManager duelManager, DuelQueue duelQueue, StatsManager statsManager) {
        this.duelManager = duelManager;
        this.duelQueue = duelQueue;
        this.statsManager = statsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Только игрок может использовать эту команду.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "/duel leave, /duel stats, /duel top");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "leave":
                if (duelManager.isInDuel(player)) {
                    // Считаем поражением
                    duelManager.endDuel(duelManager.getDuel(player).getOpponent(player), player);
                    player.sendMessage(ChatColor.RED + "Вы покинули дуэль и засчитано поражение.");
                } else if (duelQueue.isInQueue(player)) {
                    duelQueue.remove(player);
                    player.sendMessage(ChatColor.YELLOW + "Вы покинули очередь на дуэль.");
                } else {
                    player.sendMessage(ChatColor.GRAY + "Вы не в дуэли и не в очереди.");
                }
                break;
            case "stats":
                int wins = statsManager.getWins(player.getUniqueId());
                int losses = statsManager.getLosses(player.getUniqueId());
                int money = statsManager.getMoney(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Ваша статистика дуэлей:");
                player.sendMessage(ChatColor.YELLOW + "Побед: " + wins);
                player.sendMessage(ChatColor.YELLOW + "Поражений: " + losses);
                player.sendMessage(ChatColor.YELLOW + "Баланс дуэлей: " + money);
                break;
            case "top":
                Map<UUID, Integer> winMap = new HashMap<>();
                for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                    int wins = statsManager.getWins(p.getUniqueId());
                    if (wins > 0) winMap.put(p.getUniqueId(), wins);
                }
                List<Map.Entry<UUID, Integer>> top = new ArrayList<>(winMap.entrySet());
                top.sort((a, b) -> b.getValue() - a.getValue());
                player.sendMessage(ChatColor.GOLD + "Топ-10 по победам:");
                for (int i = 0; i < Math.min(10, top.size()); i++) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(top.get(i).getKey());
                    player.sendMessage(ChatColor.YELLOW + String.format("%d. %s - %d", i+1, p.getName(), top.get(i).getValue()));
                }
                break;
            default:
                player.sendMessage(ChatColor.YELLOW + "/duel leave, /duel stats, /duel top");
        }
        return true;
    }
} 