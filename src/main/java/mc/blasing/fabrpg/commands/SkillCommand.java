package mc.blasing.fabrpg.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mc.blasing.fabrpg.skills.Skill;
import mc.blasing.fabrpg.skills.SkillManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Map;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SkillCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("skill")
                .then(literal("view")
                        .executes(SkillCommand::viewSkills))
                .then(literal("addexp")
                        .then(argument("skillId", StringArgumentType.string())
                                .then(argument("amount", IntegerArgumentType.integer(0))
                                        .executes(SkillCommand::addExperience)))));
    }

    private static int viewSkills(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        Map<String, Skill> skills = SkillManager.getPlayerSkills(player);

        context.getSource().sendFeedback(() -> Text.literal("Your skills:"), false);
        for (Skill skill : skills.values()) {
            context.getSource().sendFeedback(() -> Text.literal(
                    String.format("%s: Level %d (Exp: %d)", skill.getName(), skill.getLevel(), skill.getExperience())
            ), false);
        }

        return 1;
    }

    private static int addExperience(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        String skillId = StringArgumentType.getString(context, "skillId");
        int amount = IntegerArgumentType.getInteger(context, "amount");

        Skill skill = SkillManager.getOrCreateSkill(player, skillId);
        skill.addExperience(amount);

        context.getSource().sendFeedback(() -> Text.literal(
                String.format("Added %d experience to %s. New level: %d", amount, skill.getName(), skill.getLevel())
        ), false);

        return 1;
    }
}