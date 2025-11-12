package wueffi.checker;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockSelectListener {
    private static final Map<UUID, String> waitingPlayers = new HashMap<>();
    private static final Map<String, BlockPos> positions = new HashMap<>();

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (waitingPlayers.containsKey(player.getUuid())) {
                String name = waitingPlayers.remove(player.getUuid());
                saveCorner(name, pos);
                player.sendMessage(Text.literal("§7[Competition Checker] §fCreated Selection '§6" + name + "§f' with corner at §6" + pos.toShortString() +"§f."), false);

                return false;
            }
            return true;
        });
    }

    public static void awaitSelection(ServerPlayerEntity player, String name) {
        waitingPlayers.put(player.getUuid(), name);
    }

    private static void saveCorner(String name, BlockPos pos) {
        positions.put(name, pos);
    }

    public static Map<String, BlockPos> getPositions() {
        return positions;
    }
}
