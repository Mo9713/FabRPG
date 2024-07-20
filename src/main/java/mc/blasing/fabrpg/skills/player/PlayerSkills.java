package mc.blasing.fabrpg.skills.player;

import mc.blasing.fabrpg.skills.Skill;
import mc.blasing.fabrpg.skills.SkillManager;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerSkills {
    private static final Map<UUID, PlayerSkills> playerSkillsMap = new HashMap<>();

    private final Map<String, Skill> skills = new HashMap<>();
    private final ServerPlayerEntity player;

    private PlayerSkills(ServerPlayerEntity player) {
        this.player = player;
    }

    public static PlayerSkills getPlayerSkills(ServerPlayerEntity player) {
        return playerSkillsMap.computeIfAbsent(player.getUuid(), k -> new PlayerSkills(player));
    }

    public Skill getOrCreateSkill(String skillId) {
        return skills.computeIfAbsent(skillId, id -> SkillManager.createSkillForPlayer(id, player));
    }
}