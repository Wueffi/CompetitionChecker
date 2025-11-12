package wueffi.checker.client.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import wueffi.checker.BlockSelectListener;
import wueffi.checker.Verifier;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class checkCommand {

    public static void setup() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            var selectCommand = CommandManager.literal("selectbot")
                    .then(CommandManager.argument("name", StringArgumentType.string())
                        .executes(wueffi.checker.client.commands.checkCommand::executeSelectBot)
                    );

            var verifyCommand = CommandManager.literal("verify")
                    .then(CommandManager.argument("name", StringArgumentType.string())
                        .then(CommandManager.literal("seed")
                            .then(CommandManager.argument("seed", LongArgumentType.longArg())
                                .executes(wueffi.checker.client.commands.checkCommand::executeSeededTest)
                            )
                        )
                        .then(CommandManager.literal("random")
                            .then(CommandManager.argument("count", IntegerArgumentType.integer())
                                .executes(wueffi.checker.client.commands.checkCommand::executeRandomTests)
                            )
                        )
                    );

            dispatcher.register(selectCommand);
            dispatcher.register(verifyCommand);
        });
    }

    private static int executeSelectBot(CommandContext<ServerCommandSource> context) {
        String name = StringArgumentType.getString(context, "name");
        context.getSource().sendFeedback(() -> Text.literal("Left click on the bottom north west corner to select."), false);
        BlockSelectListener.awaitSelection(Objects.requireNonNull(context.getSource().getPlayer()), name);
        return 1;
    }

    private static int executeSeededTest(CommandContext<ServerCommandSource> context) {
        String name = StringArgumentType.getString(context, "name");
        long seed = LongArgumentType.getLong(context, "seed");
        context.getSource().sendFeedback(() -> Text.literal("Verifying " + name + " with seed " + seed), false);

        PlayerEntity player = context.getSource().getPlayer();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> Verifier.verify(player, name, 1, true, seed), 50, TimeUnit.MILLISECONDS);

        return 1;
    }

    private static int executeRandomTests(CommandContext<ServerCommandSource> context) {
        String name = StringArgumentType.getString(context, "name");
        int testCount = IntegerArgumentType.getInteger(context, "count");
        context.getSource().sendFeedback(() -> Text.literal("Checking " + name + " with " + testCount + " random board positions"), false);

        PlayerEntity player = context.getSource().getPlayer();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> Verifier.verify(player, name, testCount, false, 0), 50, TimeUnit.MILLISECONDS);

        return 1;
    }
}
