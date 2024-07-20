package mc.blasing.fabrpg.skills.types;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.CustomSkill;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.Map;

public class MiningSkill extends CustomSkill {
    public MiningSkill(String id, String name, ServerPlayerEntity player) {
        super(id, name, player);
    }

    // Add any mining-specific methods here
    public void veinMiner(Map<String, Object> context) {
        // Implement vein miner logic
        Fabrpg.LOGGER.info("Vein Miner activated for player {}", getPlayer().getName().getString());
    }
}