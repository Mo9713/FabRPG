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
            } catch (IOException e) {
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

            String name = skillJson.get("name").getAsString();
            String description = skillJson.get("description").getAsString();

            SkillDefinition skill = new SkillDefinition(skillId, name, ConfigManager.mainConfig.getMaxLevel(), description);

            if (skillJson.has("actions")) {
                JsonArray actionsJson = skillJson.getAsJsonArray("actions");
                for (JsonElement actionElement : actionsJson) {
                    skill.addAction(new Action(actionElement.getAsString(), "placeholder", new ArrayList<>(), 0, new ArrayList<>(), new ArrayList<>(), 0.0));
                }
            }

            if (skillJson.has("abilities")) {
                JsonArray abilitiesJson = skillJson.getAsJsonArray("abilities");
                for (JsonElement abilityElement : abilitiesJson) {
                    skill.addAbility(new Ability(abilityElement.getAsString(), "placeholder", "placeholder", 0));
                }
            }

            skills.put(skillId, skill);
        }
        return skills;
    }

    private void setDefaults() {
        skills = new HashMap<>();
        skills.put("mining", new SkillDefinition("mining", "Mining", ConfigManager.mainConfig.getMaxLevel(), "Increases mining speed and ore drops"));
        skills.put("woodcutting", new SkillDefinition("woodcutting", "Woodcutting", ConfigManager.mainConfig.getMaxLevel(), "Increases wood cutting speed and log drops"));
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
        skillJson.addProperty("id", skill.id);
        skillJson.addProperty("name", skill.name);
        skillJson.addProperty("description", skill.description);

        JsonArray actionsJson = new JsonArray();
        for (Action action : skill.actions) {
            actionsJson.add(action.id);
        }
        skillJson.add("actions", actionsJson);

        JsonArray abilitiesJson = new JsonArray();
        for (Ability ability : skill.abilities) {
            abilitiesJson.add(ability.id);
        }
        skillJson.add("abilities", abilitiesJson);

        return skillJson;
    }
}