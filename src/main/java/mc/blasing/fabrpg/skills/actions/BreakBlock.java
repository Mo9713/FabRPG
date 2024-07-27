package mc.blasing.fabrpg.skills.actions;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.SkillManager;
import mc.blasing.fabrpg.skills.CustomSkill;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.ItemEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BreakBlock extends Action {
    private final List<String> validBlocks;

    public BreakBlock(String id, List<String> blocks, int experience) {
        super(id, "break_block", blocks, experience, null, null, 0.0);
        this.validBlocks = blocks;
    }

    @Override
    public boolean matches(Map<String, Object> context) {
        if (!super.matches(context)) return false;
        Block block = (Block) context.get("block");
        String blockId = Registries.BLOCK.getId(block).toString();
        return validBlocks.contains(blockId);
    }

    public void execute(ServerPlayerEntity player, Map<String, Object> context) {
        Block block = (Block) context.get("block");
        BlockPos pos = (BlockPos) context.get("pos");

        grantExperience(player);
        checkAbilities(player, block, pos);
    }

    private void grantExperience(ServerPlayerEntity player) {
        CustomSkill skill = SkillManager.getOrCreateSkill(player, "mining");
        skill.addExperience(getExperience());
        Fabrpg.LOGGER.info("Player {} gained {} mining experience", player.getName().getString(), getExperience());
    }

    private void checkAbilities(ServerPlayerEntity player, Block block, BlockPos pos) {
        CustomSkill skill = SkillManager.getOrCreateSkill(player, "mining");

        // Check for Double Drop ability
        if (skill.hasAbility("double_drop") && Math.random() < 0.1) { // 10% chance for example
            dropExtraItem(player, block, pos);
        }

        // Check for Vein Miner ability
        if (skill.hasAbility("vein_miner") && isOreBlock(block)) {
            activateVeinMiner(player, block, pos);
        }
    }

    private void dropExtraItem(ServerPlayerEntity player, Block block, BlockPos pos) {
        ItemStack dropStack = new ItemStack(block.asItem());
        ItemEntity itemEntity = new ItemEntity(
                player.getWorld(),
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                dropStack
        );
        player.getWorld().spawnEntity(itemEntity);
        Fabrpg.LOGGER.info("Double Drop activated for player {}", player.getName().getString());
    }

    private boolean isOreBlock(Block block) {
        String blockId = Registries.BLOCK.getId(block).toString();
        return blockId.contains("_ore");
    }

    private void activateVeinMiner(ServerPlayerEntity player, Block startBlock, BlockPos startPos) {
        int maxBlocks = 10; // Limit the number of blocks to mine
        int blocksMined = 0;

        for (BlockPos pos : BlockPos.iterate(startPos.add(-1, -1, -1), startPos.add(1, 1, 1))) {
            if (blocksMined >= maxBlocks) break;

            Block block = player.getWorld().getBlockState(pos).getBlock();
            if (block == startBlock) {
                player.getWorld().breakBlock(pos, true, player);
                blocksMined++;
            }
        }

        Fabrpg.LOGGER.info("Vein Miner activated for player {}, mined {} blocks", player.getName().getString(), blocksMined);
    }

    // Static factory methods for creating common BreakBlock instances
    public static BreakBlock createStoneBreakBlock() {
        return new BreakBlock("break_stone", Arrays.asList("minecraft:stone", "minecraft:cobblestone"), 10);
    }

    public static BreakBlock createOreBreakBlock() {
        return new BreakBlock("break_ore", Arrays.asList("minecraft:iron_ore", "minecraft:gold_ore", "minecraft:diamond_ore"), 50);
    }

    public static BreakBlock createLogBreakBlock() {
        return new BreakBlock("break_log", Arrays.asList(
                "minecraft:oak_log", "minecraft:birch_log", "minecraft:spruce_log",
                "minecraft:jungle_log", "minecraft:acacia_log", "minecraft:dark_oak_log"
        ), 20);
    }
}