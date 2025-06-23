package net.mineland.duels.util;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EffectUtil {
    public static void playStartEffect(Player player) {
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 30, 1, 1, 1, 0.2);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }
    public static void playEndEffect(Player player) {
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 30, 1, 1, 1, 0.2);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
    }
} 