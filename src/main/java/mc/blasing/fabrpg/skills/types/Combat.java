package mc.blasing.fabrpg.skills.types;

import mc.blasing.fabrpg.skills.CustomSkill;
import net.minecraft.server.network.ServerPlayerEntity;

public class Combat extends CustomSkill {
    public Combat(String id, String name, ServerPlayerEntity player) {
        super(id, name, player);
    }

    // Add combat-specific methods here
    public void handleEntityKill(String entityType) {
        // Implement combat exp gain logic
    }
}