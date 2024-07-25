package mc.blasing.fabrpg.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import mc.blasing.fabrpg.skills.Skill;
import mc.blasing.fabrpg.skills.SkillManager;
import mc.blasing.fabrpg.skills.types.Mining;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

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
                                        )))))
                .then(literal("mining")
                        .executes(context -> testMining(context.getSource().getPlayer()))));
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

        Skill skill = SkillManager.getOrCreateSkill(player, skillName);
        if (skill != null) {
            skill.addExperience(amount);
            player.sendMessage(Text.literal(String.format("Added %d exp to %s skill", amount, skillName)));
        } else {
            player.sendMessage(Text.literal(String.format("Skill %s not found", skillName)));
        }
        return 1;
    }

    private static int testMining(ServerPlayerEntity player) {
        if (player == null) return 0;

        Mining miningSkill = (Mining) SkillManager.getOrCreateSkill(player, "mining");
        int level = miningSkill.getLevel();

        player.sendMessage(Text.literal("Mining skill level: " + level));

        // Test Double Drop
        boolean doubleDropTriggered = miningSkill.canDoubleDrop(null);  // Passing null for simplicity, adjust as needed
        player.sendMessage(Text.literal("Double Drop triggered: " + doubleDropTriggered));

        // Test Vein Miner
        BlockPos playerPos = player.getBlockPos();
        miningSkill.checkVeinMiner(player.getWorld().getBlockState(playerPos.down()).getBlock());
        player.sendMessage(Text.literal("Vein Miner check completed. Check logs for details."));

        return 1;
    }
}