package net.mineland.duels.queue;

import org.bukkit.entity.Player;
import java.util.*;

public class ModeQueueManager {
    private final Map<String, DuelQueue> modeQueues = new HashMap<>();

    public DuelQueue getQueue(String mode) {
        return modeQueues.computeIfAbsent(mode, k -> new DuelQueue());
    }

    public void addToQueue(String mode, Player player) {
        getQueue(mode).add(player);
    }

    public void removeFromQueue(String mode, Player player) {
        getQueue(mode).remove(player);
    }

    public boolean isInQueue(String mode, Player player) {
        return getQueue(mode).isInQueue(player);
    }

    public boolean hasPair(String mode) {
        return getQueue(mode).hasPair();
    }

    public Player[] pollPair(String mode) {
        return getQueue(mode).pollPair();
    }
} 