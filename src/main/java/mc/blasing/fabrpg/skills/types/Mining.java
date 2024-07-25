package mc.blasing.fabrpg.skills.types;

import mc.blasing.fabrpg.skills.CustomSkill;
import mc.blasing.fabrpg.Fabrpg;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class Mining extends CustomSkill {
    private Map<Block, Integer> blockXpValues;

    public Mining(String id, String name, ServerPlayerEntity player) {
        super(id, name, player);
        initializeBlockXpValues();
    }

    private void initializeBlockXpValues() {
        blockXpValues = new HashMap<>();
        blockXpValues.put(Blocks.STONE, 1);
        blockXpValues.put(Blocks.COAL_ORE, 2);
        blockXpValues.put(Blocks.IRON_ORE, 3);
        // Add more blocks and XP values as needed
    }

    public void onBlockMined(Block block, ItemStack tool) {
        if (tool.getItem() instanceof PickaxeItem && blockXpValues.containsKey(block)) {
            int xpGained = blockXpValues.get(block);
            addExperience(xpGained);
            Fabrpg.LOGGER.info("Player {} gained {} mining XP from breaking {}",
                    getPlayer().getName().getString(), xpGained, block.getTranslationKey());
        }
    }

    public void veinMiner(Map<String, Object> context) {
        // Implement vein miner logic
        Fabrpg.LOGGER.info("Vein Miner activated for player {}", getPlayer().getName().getString());
    }

    @Override
    public void handleAction(Map<String, Object> context) {
        super.handleAction(context);
        // Add any mining-specific action handling here
    }
}