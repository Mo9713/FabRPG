package mc.blasing.fabrpg.skills;

import net.minecraft.server.network.ServerPlayerEntity;

public class CombatSkill extends CustomSkill {
    public CombatSkill(String id, String name, ServerPlayerEntity player) {
        super(id, name, player);
    }

    // Add combat-specific methods here
    public void handleEntityKill(String entityType) {
        // Implement combat exp gain logic
    }
}