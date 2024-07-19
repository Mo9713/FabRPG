package mc.blasing.fabrpg.events;

import mc.blasing.fabrpg.skills.Skill;
import mc.blasing.fabrpg.skills.SkillManager;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class SkillEventListener {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                Block block = state.getBlock();

                Map<String, Object> context = new HashMap<>();
                context.put("block", block);

                if (block == Blocks.STONE || block == Blocks.COAL_ORE || block == Blocks.IRON_ORE) {
                    Skill miningSkill = SkillManager.getOrCreateSkill(serverPlayer, "mining");
                    miningSkill.handleAction(context);
                }
                // Add more conditions for other skills and blocks as needed
            }
        });
    }
}