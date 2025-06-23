package net.mineland.duels.game;

import net.mineland.duels.arena.Arena;
import net.mineland.duels.DuelPlugin;
import net.mineland.duels.stats.StatsManager;
import net.mineland.duels.util.KitUtil;
import net.mineland.duels.economy.EconomyUtil;
import net.mineland.duels.util.EffectUtil;
import net.mineland.duels.economy.PointsAPIUtil;
import net.mineland.duels.util.LobbyUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.ChatColor;
import java.util.UUID;

public class Duel {
    private final Player player1;
    private final Player player2;
    private final Arena arena;
    private boolean started = false;
    private final StatsManager statsManager;
    private BukkitRunnable duelTimer;
    private boolean draw = false;
    private final String mode;

    public Duel(Player player1, Player player2, Arena arena, StatsManager statsManager, String mode) {
        this.player1 = player1;
        this.player2 = player2;
        this.arena = arena;
        this.statsManager = statsManager;
        this.mode = mode;
    }

    public void start() {
        started = true;
        // Телепортируем игроков
        player1.teleport(arena.getSpawn1());
        player2.teleport(arena.getSpawn2());
        // Выдаём предметы
        KitUtil.giveKit(player1, mode);
        KitUtil.giveKit(player2, mode);
        EffectUtil.playStartEffect(player1);
        EffectUtil.playStartEffect(player2);
        int timeLimit = DuelPlugin.getInstance().getConfig().getInt("duel-time-limit", 180);
        duelTimer = new BukkitRunnable() {
            int count = timeLimit;
            @Override
            public void run() {
                if (!started) { cancel(); return; }
                if (count <= 0) {
                    draw = true;
                    end(null, null);
                    cancel();
                    return;
                }
                count--;
            }
        };
        duelTimer.runTaskTimer(DuelPlugin.getInstance(), 20, 20);
        // Визуальный отсчёт
        new BukkitRunnable() {
            int count = 3;
            @Override
            public void run() {
                if (count > 0) {
                    player1.sendTitle("§e" + count, "", 0, 20, 0);
                    player2.sendTitle("§e" + count, "", 0, 20, 0);
                    count--;
                } else {
                    player1.sendTitle("§aБой!", "", 0, 20, 0);
                    player2.sendTitle("§aБой!", "", 0, 20, 0);
                    player1.setInvulnerable(false);
                    player2.setInvulnerable(false);
                    cancel();
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("Duels"), 0, 20);
        player1.setInvulnerable(true);
        player2.setInvulnerable(true);
    }

    public void end(Player winner, Player loser) {
        started = false;
        if (duelTimer != null) duelTimer.cancel();
        // Возврат в лобби, награда, очистка инвентаря
        player1.getInventory().clear();
        player2.getInventory().clear();
        EffectUtil.playEndEffect(player1);
        EffectUtil.playEndEffect(player2);
        if (draw) {
            player1.sendTitle("§cВремя дуэли истекло!", "", 10, 60, 10);
            player2.sendTitle("§cВремя дуэли истекло!", "", 10, 60, 10);
            player1.sendMessage("§cВремя дуэли истекло! Бой завершён ничьей.");
            player2.sendMessage("§cВремя дуэли истекло! Бой завершён ничьей.");
            LobbyUtil.teleportToLobby(player1);
            LobbyUtil.teleportToLobby(player2);
            return;
        }
        // Статистика
        statsManager.addWin(winner.getUniqueId());
        statsManager.addLoss(loser.getUniqueId());
        // Награды
        int money = DuelPlugin.getInstance().getConfig().getInt("rewards.money", 100);
        int exp = DuelPlugin.getInstance().getConfig().getInt("rewards.exp", 50);
        String msg = ChatColor.translateAlternateColorCodes('&', DuelPlugin.getInstance().getConfig().getString("rewards.message", "&aВы победили в дуэли!"));
        statsManager.addMoney(winner.getUniqueId(), money);
        winner.giveExp(exp);
        winner.sendMessage(msg);
        // Vault/PointsAPI
        if (EconomyUtil.isEnabled()) {
            EconomyUtil.giveMoney(winner, money);
        } else if (PointsAPIUtil.isEnabled()) {
            PointsAPIUtil.givePoints(winner, money);
        }
        // Телепорт в лобби
        LobbyUtil.teleportToLobby(winner);
        LobbyUtil.teleportToLobby(loser);
    }

    public Player getOpponent(Player player) {
        if (player.getUniqueId().equals(player1.getUniqueId())) return player2;
        if (player.getUniqueId().equals(player2.getUniqueId())) return player1;
        return null;
    }

    public boolean isStarted() { return started; }
    public Arena getArena() { return arena; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
} 