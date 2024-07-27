package mc.blasing.fabrpg.config;

import com.google.gson.*;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.SkillDefinition;
import mc.blasing.fabrpg.skills.actions.Action;
import mc.blasing.fabrpg.skills.abilities.Ability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class SkillsConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("skills.json");

    public Map<String, SkillDefinition> skills;

    public static SkillsConfig load() {
        SkillsConfig config = new SkillsConfig();

        ConfigManager.ensureConfigFile("skills.json", ConfigManager.getDefaultSkillsConfig());

        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                Fabrpg.LOGGER.info("Skills JSON content: {}", json);
                JsonElement jsonElement = ConfigManager.getGson().fromJson(json, JsonElement.class);

                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("skills")) {
                        JsonElement skillsElement = jsonObject.get("skills");
                        if (skillsElement.isJsonObject()) {
                            config.skills = parseSkillsFromJson(skillsElement.getAsJsonObject());
                        } else {
                            Fabrpg.LOGGER.error("Unexpected JSON structure for skills. Using default skills.");
                            config.setDefaults();
                        }
                    } else {
                        Fabrpg.LOGGER.error("No 'skills' object found in JSON. Using default skills.");
                        config.setDefaults();
                    }
                } else {
                    Fabrpg.LOGGER.error("Unexpected JSON structure in skills.json. Using default skills.");
                    config.setDefaults();
                }
            } catch (IOException | JsonSyntaxException e) {
                Fabrpg.LOGGER.error("Error loading skills config file", e);
                config.setDefaults();
            }
        } else {
            config.setDefaults();
        }

        if (config.skills == null || config.skills.isEmpty()) {
            Fabrpg.LOGGER.warn("No skills loaded from config. Using default skills.");
            config.setDefaults();
        }

        config.save();
        return config;
    }

    private static Map<String, SkillDefinition> parseSkillsFromJson(JsonObject skillsJson) {
        Map<String, SkillDefinition> skills = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : skillsJson.entrySet()) {
            String skillId = entry.getKey();
            JsonObject skillJson = entry.getValue().getAsJsonObject();

            String name = skillJson.has("name") ? skillJson.get("name").getAsString() : skillId;
            String description = skillJson.has("description") ? skillJson.get("description").getAsString() : "";

            SkillDefinition skill = new SkillDefinition(skillId, name, description);

            if (skillJson.has("enabled")) {
                skill.setEnabled(skillJson.get("enabled").getAsBoolean());
            }
            if (skillJson.has("maxLevel")) {
                skill.setMaxLevel(skillJson.get("maxLevel").getAsInt());
            }
            if (skillJson.has("xpMultiplier")) {
                skill.setXpMultiplier(skillJson.get("xpMultiplier").getAsDouble());
            }
            if (skillJson.has("xpCap")) {
                skill.setXpCap(skillJson.get("xpCap").getAsInt());
            }
            if (skillJson.has("levelCap")) {
                skill.setLevelCap(skillJson.get("levelCap").getAsInt());
            }

            if (skillJson.has("actions")) {
                JsonArray actionsJson = skillJson.getAsJsonArray("actions");
                List<String> actions = new ArrayList<>();
                for (JsonElement actionElement : actionsJson) {
                    actions.add(actionElement.getAsString());
                }
                skill.setActionIds(actions);
            }

            if (skillJson.has("abilities")) {
                JsonArray abilitiesJson = skillJson.getAsJsonArray("abilities");
                List<String> abilities = new ArrayList<>();
                for (JsonElement abilityElement : abilitiesJson) {
                    abilities.add(abilityElement.getAsString());
                }
                skill.setAbilityIds(abilities);
            }

            if (skillJson.has("unlockConditions")) {
                JsonArray unlockConditionsJson = skillJson.getAsJsonArray("unlockConditions");
                List<String> unlockConditions = new ArrayList<>();
                for (JsonElement conditionElement : unlockConditionsJson) {
                    unlockConditions.add(conditionElement.getAsString());
                }
                skill.setUnlockConditions(unlockConditions);
            }

            skills.put(skillId, skill);
        }
        return skills;
    }

    private void setDefaults() {
        skills = new HashMap<>();
        SkillDefinition mining = new SkillDefinition("mining", "Mining", "Increases mining speed and ore drops");
        mining.getActionIds().add("break_stone");
        mining.getActionIds().add("break_ore");
        mining.getAbilityIds().add("vein_miner");
        mining.getAbilityIds().add("double_drop");
        skills.put("mining", mining);

        SkillDefinition woodcutting = new SkillDefinition("woodcutting", "Woodcutting", "Increases wood cutting speed and log drops");
        skills.put("woodcutting", woodcutting);
    }

    public void save() {
        try {
            JsonObject root = new JsonObject();
            JsonObject skillsObject = new JsonObject();
            for (Map.Entry<String, SkillDefinition> entry : skills.entrySet()) {
                skillsObject.add(entry.getKey(), skillToJson(entry.getValue()));
            }
            root.add("skills", skillsObject);

            String json = ConfigManager.getGson().toJson(root);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Error saving skills config file", e);
        }
    }

    private JsonObject skillToJson(SkillDefinition skill) {
        JsonObject skillJson = new JsonObject();
        skillJson.addProperty("id", skill.getId());
        skillJson.addProperty("name", skill.getName());
        skillJson.addProperty("description", skill.getDescription());
        skillJson.addProperty("enabled", skill.isEnabled());
        skillJson.addProperty("maxLevel", skill.getMaxLevel());
        skillJson.addProperty("xpMultiplier", skill.getXpMultiplier());
        skillJson.addProperty("xpCap", skill.getXpCap());
        skillJson.addProperty("levelCap", skill.getLevelCap());

        JsonArray actionsJson = new JsonArray();
        for (String action : skill.getActionIds()) {
            actionsJson.add(action);
        }
        skillJson.add("actions", actionsJson);

        JsonArray abilitiesJson = new JsonArray();
        for (String ability : skill.getAbilityIds()) {
            abilitiesJson.add(ability);
        }
        skillJson.add("abilities", abilitiesJson);

        JsonArray unlockConditionsJson = new JsonArray();
        for (String condition : skill.getUnlockConditions()) {
            unlockConditionsJson.add(condition);
        }
        skillJson.add("unlockConditions", unlockConditionsJson);

        return skillJson;
    }
}