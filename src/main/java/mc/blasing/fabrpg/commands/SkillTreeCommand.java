package mc.blasing.fabrpg.commands;

import com.mojang.brigadier.CommandDispatcher;
import mc.blasing.fabrpg.network.OpenSkillTreePacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class SkillTreeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("skilltree")
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    if (player != null) {
                        OpenSkillTreePacket.send(player);
                        return 1;
                    }
                    return 0;
                })
        );
    }
}