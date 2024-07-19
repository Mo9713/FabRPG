package mc.blasing.fabrpg.commands;

import com.mojang.brigadier.CommandDispatcher;
import mc.blasing.fabrpg.client.FabrpgClient;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import static net.minecraft.server.command.CommandManager.literal;

public class SkillTreeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("skilltree")
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    if (player != null) {
                        ServerPlayNetworking.send(player, FabrpgClient.OpenSkillTreePayload.INSTANCE);
                        return 1;
                    }
                    return 0;
                })
        );
    }
}