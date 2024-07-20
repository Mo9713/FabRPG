package mc.blasing.fabrpg.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import mc.blasing.fabrpg.skills.Skill;
import mc.blasing.fabrpg.skills.SkillManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SkillCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("skill")
                .then(literal("view")
                        .executes(context -> viewSkills(context.getSource().getPlayer())))
                .then(literal("addexp")
                        .then(argument("skill", StringArgumentType.word())
                                .then(argument("amount", IntegerArgumentType.integer(0))
                                        .executes(context -> addExperience(
                                                context.getSource().getPlayer(),
                                                StringArgumentType.getString(context, "skill"),
                                                IntegerArgumentType.getInteger(context, "amount")
                                        ))))));
    }

    private static int viewSkills(ServerPlayerEntity player) {
        if (player == null) return 0;

        player.sendMessage(Text.literal("Your skills:"));
        for (Skill skill : SkillManager.getPlayerSkills(player).values()) {
            player.sendMessage(Text.literal(String.format("- %s: Level %d (Exp: %d)",
                    skill.getName(), skill.getLevel(), skill.getExperience())));
        }
        return 1;
    }

    private static int addExperience(ServerPlayerEntity player, String skillName, int amount) {
        if (player == null) return 0;

        // TODO: Implement adding experience to a skill
        player.sendMessage(Text.literal(String.format("Added %d exp to %s skill (not implemented yet)", amount, skillName)));
        return 1;
    }
}