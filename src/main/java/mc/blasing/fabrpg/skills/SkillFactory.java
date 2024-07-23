package mc.blasing.fabrpg.skills;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.types.*;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class SkillFactory {
    public static Skill createSkill(SkillDefinition definition, ServerPlayerEntity player) {
        return switch (definition.getId().toLowerCase()) {
            case "mining" -> new Mining(definition.getId(), definition.getName(), player);
            case "combat" -> new Combat(definition.getId(), definition.getName(), player);
            // Add other specialized skills here
            default -> new CustomSkill(definition.getId(), definition.getName(), player);
        };
    }

    public static List<Skill> createSkills(List<SkillDefinition> definitions, ServerPlayerEntity player) {
        List<Skill> skills = new ArrayList<>();
        for (SkillDefinition definition : definitions) {
            Skill skill = createSkill(definition, player);
            if (skill != null) {
                skills.add(skill);
            } else {
                Fabrpg.LOGGER.warn("Failed to create skill: {}", definition.getId());
            }
        }
        return skills;
    }
}