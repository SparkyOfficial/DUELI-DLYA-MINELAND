package net.mineland.duels.stats;

import net.mineland.duels.DuelPlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StatsManager {
    private final File file;
    private final YamlConfiguration config;

    public StatsManager() {
        file = new File(DuelPlugin.getInstance().getDataFolder(), "stats.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public int getWins(UUID uuid) {
        return config.getInt(uuid + ".wins", 0);
    }
    public int getLosses(UUID uuid) {
        return config.getInt(uuid + ".losses", 0);
    }
    public int getMoney(UUID uuid) {
        return config.getInt(uuid + ".money", 0);
    }
    public void addWin(UUID uuid) {
        config.set(uuid + ".wins", getWins(uuid) + 1);
        save();
    }
    public void addLoss(UUID uuid) {
        config.set(uuid + ".losses", getLosses(uuid) + 1);
        save();
    }
    public void addMoney(UUID uuid, int amount) {
        config.set(uuid + ".money", getMoney(uuid) + amount);
        save();
    }
    public void removeMoney(UUID uuid, int amount) {
        config.set(uuid + ".money", Math.max(0, getMoney(uuid) - amount));
        save();
    }
    private void save() {
        try { config.save(file); } catch (IOException ignored) {}
    }
} 