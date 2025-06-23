package net.mineland.duels.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.mineland.duels.DuelPlugin;
import net.mineland.duels.stats.StatsManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class DuelPlaceholderExpansion extends PlaceholderExpansion {
    private final StatsManager statsManager;

    public DuelPlaceholderExpansion(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "duel";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MinelandTeam";
    }

    @Override
    public @NotNull String getVersion() {
        return DuelPlugin.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(OfflinePlayer player, String params) {
        if (player == null || !player.hasPlayedBefore()) return "0";
        switch (params.toLowerCase()) {
            case "wins":
                return String.valueOf(statsManager.getWins(player.getUniqueId()));
            case "losses":
                return String.valueOf(statsManager.getLosses(player.getUniqueId()));
            case "money":
                return String.valueOf(statsManager.getMoney(player.getUniqueId()));
            default:
                return null;
        }
    }
} 