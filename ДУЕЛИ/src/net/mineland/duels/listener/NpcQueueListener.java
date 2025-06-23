import net.mineland.duels.gui.ModeSelectGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.mineland.duels.gui.ModeSelectGui.ModeSelectEvent;
import net.mineland.duels.queue.ModeQueueManager;
import net.mineland.duels.arena.WorldInstanceManager;
import net.mineland.duels.arena.ArenaManager;
import net.mineland.duels.arena.Arena;
import net.mineland.duels.DuelPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class NpcQueueListener implements Listener {

    private final ModeQueueManager modeQueueManager;
    private final WorldInstanceManager worldInstanceManager;
    private final ArenaManager arenaManager;

    public NpcQueueListener(ModeQueueManager modeQueueManager, WorldInstanceManager worldInstanceManager, ArenaManager arenaManager) {
        this.modeQueueManager = modeQueueManager;
        this.worldInstanceManager = worldInstanceManager;
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onNpcRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        String npcName = event.getNPC().getName();
        String configName = DuelPlugin.getInstance().getConfig().getString("npc.name", "Дуэли");
        if (!npcName.equalsIgnoreCase(configName)) return;
        ModeSelectGui.open(player);
    }

    @EventHandler
    public void onModeSelect(ModeSelectEvent event) {
        Player player = event.getPlayer();
        String mode = event.getMode();
        if (modeQueueManager.isInQueue(mode, player)) {
            player.sendMessage("§eВы уже в очереди на этот режим!");
            return;
        }
        modeQueueManager.addToQueue(mode, player);
        player.sendMessage("§aВы встали в очередь на дуэль (режим: " + mode + ")!");
        // Если есть пара — стартуем дуэль
        if (modeQueueManager.hasPair(mode)) {
            Player[] pair = modeQueueManager.pollPair(mode);
            // Выбираем арену с нужным шаблоном
            Arena arena = arenaManager.getFreeArena();
            if (arena == null) {
                pair[0].sendMessage("§cНет свободных арен!");
                pair[1].sendMessage("§cНет свободных арен!");
                modeQueueManager.addToQueue(mode, pair[0]);
                modeQueueManager.addToQueue(mode, pair[1]);
                return;
            }
            // Клонируем мир для дуэли
            String templateWorld = arena.getWorld().getName();
            String instanceWorld = worldInstanceManager.createInstance(templateWorld);
            World bukkitWorld = Bukkit.getWorld(instanceWorld);
            Arena instancedArena = Arena.withWorld(arena, bukkitWorld);
            // Запускаем дуэль с нужным режимом
            DuelPlugin.getInstance().getDuelManager().startDuel(pair[0], pair[1], instancedArena, mode);
            // TODO: после дуэли вызвать worldInstanceManager.deleteInstance(instanceWorld)
        }
    }
} 