package mc.blasing.fabrpg.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class SkillTreeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("skilltree")
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    if (player != null) {
                        // For now, just send a message to the player
                        player.sendMessage(Text.literal("Skill tree command executed!"), false);
                        // We'll implement the actual skill tree opening later
                        return 1;
                    }
                    return 0;
                })
        );
    }
}