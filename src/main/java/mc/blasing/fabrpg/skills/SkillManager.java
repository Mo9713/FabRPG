package mc.blasing.fabrpg.skills;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillManager {
    private static final Gson GSON = new Gson();
    private static final Map<String, SkillDefinition> skillDefinitions = new HashMap<>();
    private static final Map<ServerPlayerEntity, Map<String, Skill>> playerSkills = new HashMap<>();

    public static void initialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Identifier.of(Fabrpg.MOD_ID, "skills");
            }

            @Override
            public void reload(ResourceManager manager) {
                skillDefinitions.clear();
                try {
                    for (Resource resource : manager.getAllResources(Identifier.of(Fabrpg.MOD_ID, "data/fabrpg/skills.json"))) {
                        try (InputStream inputStream = resource.getInputStream()) {
                            JsonObject jsonObject = GSON.fromJson(new InputStreamReader(inputStream), JsonObject.class);
                            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                                String skillId = entry.getKey();
                                JsonObject skillObject = entry.getValue().getAsJsonObject();
                                SkillDefinition skillDefinition = GSON.fromJson(skillObject, SkillDefinition.class);
                                skillDefinitions.put(skillId, skillDefinition);
                            }
                        }
                    }
                    Fabrpg.LOGGER.info("Loaded {} skill definitions", skillDefinitions.size());
                } catch (Exception e) {
                    Fabrpg.LOGGER.error("Failed to load skill definitions", e);
                }
            }
        });
    }

    public static Skill getOrCreateSkill(ServerPlayerEntity player, String skillId) {
        return playerSkills
                .computeIfAbsent(player, k -> new HashMap<>())
                .computeIfAbsent(skillId, id -> createSkill(player, id));
    }

    private static Skill createSkill(ServerPlayerEntity player, String skillId) {
        SkillDefinition def = skillDefinitions.get(skillId);
        if (def == null) {
            throw new IllegalArgumentException("Unknown skill: " + skillId);
        }

        Skill skill;
        switch (skillId) {
            case "mining":
                skill = new MiningSkill(def.getId(), def.getName(), player);
                break;
            // Add cases for other specialized skills here
            default:
                skill = new CustomSkill(def.getId(), def.getName(), player);
        }

        for (Action action : def.getActions()) {
            ((CustomSkill) skill).addAction(action);
        }
        for (Ability ability : def.getAbilities()) {
            ((CustomSkill) skill).addAbility(ability);
        }

        return skill;
    }

    public static void loadSkillDefinitions(List<SkillDefinition> definitions) {
        for (SkillDefinition definition : definitions) {
            skillDefinitions.put(definition.getId(), definition);
        }
        Fabrpg.LOGGER.info("Loaded {} skill definitions", skillDefinitions.size());
    }

    public static Skill createSkillForPlayer(String skillId, ServerPlayerEntity player) {
        SkillDefinition def = skillDefinitions.get(skillId);
        if (def == null) {
            throw new IllegalArgumentException("Unknown skill: " + skillId);
        }
        Skill skill = new Skill(def.getId(), def.getName(), player);
        for (Ability ability : def.getAbilities()) {
            skill.addAbility(ability.getId(), ability);
        }
        for (Action action : def.getActions()) {
            skill.addAction(action.getId(), action);
        }
        return skill;
    }

    public static Map<String, Skill> getPlayerSkills(ServerPlayerEntity player) {
        return new HashMap<>(playerSkills.getOrDefault(player, new HashMap<>()));
    }

    public static boolean isSkillUnlocked(PlayerEntity player, String skillId) {
        // Implement logic to check if the skill is unlocked
        return false; // Placeholder
    }
}

