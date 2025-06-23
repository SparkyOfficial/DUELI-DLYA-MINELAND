package net.mineland.duels;

import net.mineland.duels.arena.ArenaManager;
import net.mineland.duels.command.DuelCommand;
import net.mineland.duels.economy.EconomyUtil;
import net.mineland.duels.economy.PointsAPIUtil;
import net.mineland.duels.game.DuelManager;
import net.mineland.duels.listener.DuelListener;
import net.mineland.duels.listener.NpcQueueListener;
import net.mineland.duels.listener.CommandBlockListener;
import net.mineland.duels.placeholder.DuelPlaceholderExpansion;
import net.mineland.duels.queue.DuelQueue;
import net.mineland.duels.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DuelPlugin extends JavaPlugin {
    private static DuelPlugin instance;
    private ArenaManager arenaManager;
    private DuelQueue duelQueue;
    private StatsManager statsManager;
    private DuelManager duelManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        // Инициализация модулей
        arenaManager = new ArenaManager();
        duelQueue = new DuelQueue();
        statsManager = new StatsManager();
        duelManager = new DuelManager(statsManager);
        // Экономика
        EconomyUtil.setupEconomy();
        PointsAPIUtil.setupPointsAPI();
        // PlaceholderAPI
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new DuelPlaceholderExpansion(statsManager).register();
        }
        // Регистрация команд и слушателей
        getCommand("duel").setExecutor(new DuelCommand(duelManager, duelQueue, statsManager));
        Bukkit.getPluginManager().registerEvents(new DuelListener(duelManager), this);
        Bukkit.getPluginManager().registerEvents(new NpcQueueListener(duelQueue, duelManager, arenaManager), this);
        Bukkit.getPluginManager().registerEvents(new CommandBlockListener(duelManager), this);
        getLogger().info("Duels plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Duels plugin disabled!");
    }

    public static DuelPlugin getInstance() {
        return instance;
    }

    public ArenaManager getArenaManager() { return arenaManager; }
    public DuelQueue getDuelQueue() { return duelQueue; }
    public StatsManager getStatsManager() { return statsManager; }
    public DuelManager getDuelManager() { return duelManager; }
} 