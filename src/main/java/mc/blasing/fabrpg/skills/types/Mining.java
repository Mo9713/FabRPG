package mc.blasing.fabrpg.skills.types;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.config.ConfigManager;
import mc.blasing.fabrpg.skills.CustomSkill;
import mc.blasing.fabrpg.skills.abilities.AbilityDefinition;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Mining extends CustomSkill {
    private Map<Block, Integer> blockXpValues;
    private static final Random random = new Random();

    public Mining(String id, String name, ServerPlayerEntity player) {
        super(id, name, player);
        initializeBlockXpValues();
    }

    private void initializeBlockXpValues() {
        blockXpValues = new HashMap<>();
        blockXpValues.put(Blocks.STONE, 1);
        blockXpValues.put(Blocks.COAL_ORE, 2);
        blockXpValues.put(Blocks.IRON_ORE, 3);
        blockXpValues.put(Blocks.GOLD_ORE, 4);
        blockXpValues.put(Blocks.DIAMOND_ORE, 5);
    }

    public void onBlockMined(Block block, ItemStack tool) {
        if (tool.getItem() instanceof PickaxeItem && blockXpValues.containsKey(block)) {
            int xpGained = calculateXpGain(block, tool);

            if (ConfigManager.mainConfig.isUseMinecraftXP()) {
                getPlayer().addExperience(xpGained);
            } else {
                addExperience(xpGained);
            }

            Fabrpg.LOGGER.info("Player {} gained {} mining XP from breaking {}",
                    getPlayer().getName().getString(), xpGained, block.getTranslationKey());

            processDrops(block);
            checkVeinMiner(block);
        }
    }

    private int calculateXpGain(Block block, ItemStack tool) {
        return blockXpValues.getOrDefault(block, 0);
    }

    private void processDrops(Block block) {
        if (canDoubleDrop(block)) {
            doubleDrops(block);
        }
    }

    public boolean canDoubleDrop(Block block) {
        AbilityDefinition doubleDropAbility = findAbilityById("double_drop");
        if (doubleDropAbility == null) return false;

        if (getLevel() < doubleDropAbility.requiredLevel) return false;

        // For now, we'll use a simple chance calculation. You might want to adjust this.
        double chance = 0.01 * getLevel(); // 1% per level
        return random.nextDouble() < chance;
    }

    private void doubleDrops(Block block) {
        // Implement logic to drop an extra item
        Fabrpg.LOGGER.info("Player {} triggered double drop for {}",
                getPlayer().getName().getString(), block.getTranslationKey());
    }

    public void checkVeinMiner(Block block) {
        AbilityDefinition veinMinerAbility = findAbilityById("vein_miner");
        if (veinMinerAbility == null) return;

        if (getLevel() < veinMinerAbility.requiredLevel) return;

        // For simplicity, we'll assume vein miner works on all ores.
        // You might want to add more specific logic here.
        if (block.getTranslationKey().contains("_ore")) {
            activateVeinMiner(block);
        }
    }

    private void activateVeinMiner(Block startBlock) {
        World world = getPlayer().getWorld();
        BlockPos startPos = getPlayer().getBlockPos();
        int maxBlocks = 50; // You might want to make this configurable
        int blocksMinedCount = 0;

        for (BlockPos pos : BlockPos.iterate(startPos.add(-2, -2, -2), startPos.add(2, 2, 2))) {
            if (blocksMinedCount >= maxBlocks) break;

            Block block = world.getBlockState(pos).getBlock();
            if (block == startBlock) {
                onBlockMined(block, getPlayer().getMainHandStack());
                world.breakBlock(pos, false, getPlayer());
                blocksMinedCount++;
            }
        }

        Fabrpg.LOGGER.info("Vein Miner activated for player {}, mined {} blocks",
                getPlayer().getName().getString(), blocksMinedCount);
    }

    @Override
    public void handleAction(Map<String, Object> context) {
        super.handleAction(context);
        if (context.get("type").equals("BLOCK_BREAK") && context.get("block") instanceof Block) {
            onBlockMined((Block) context.get("block"), getPlayer().getMainHandStack());
        }
    }

    private AbilityDefinition findAbilityById(String abilityId) {
        for (AbilityDefinition ability : ConfigManager.abilitiesConfig.abilities) {
            if (ability.id.equals(abilityId)) {
                return ability;
            }
        }
        return null;
    }
}

    // SuperBreaker methods are commented out for now until i figure out enchantments
    /*
    public void activateSuperBreaker() {
        // Implementation
    }

    public void deactivateSuperBreaker() {
        // Implementation
    }

    private void addEnchantmentLevel(ItemStack itemStack, String enchantmentId, int levelsToAdd) {
        // Implementation
    }

    private void removeEnchantmentLevel(ItemStack itemStack, String enchantmentId, int levelsToRemove) {
        // Implementation
    }
    */
