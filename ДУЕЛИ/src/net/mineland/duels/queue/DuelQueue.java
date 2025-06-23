package net.mineland.duels.queue;

import org.bukkit.entity.Player;
import java.util.LinkedList;
import java.util.Queue;

public class DuelQueue {
    private final Queue<Player> queue = new LinkedList<>();

    public void add(Player player) {
        if (!queue.contains(player)) {
            queue.add(player);
        }
    }

    public void remove(Player player) {
        queue.remove(player);
    }

    public boolean hasPair() {
        return queue.size() >= 2;
    }

    public Player[] pollPair() {
        if (hasPair()) {
            return new Player[] { queue.poll(), queue.poll() };
        }
        return null;
    }

    public boolean isInQueue(Player player) {
        return queue.contains(player);
    }

    public int size() {
        return queue.size();
    }
} 