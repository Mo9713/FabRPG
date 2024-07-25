package mc.blasing.fabrpg.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.SkillDefinition;
import mc.blasing.fabrpg.skills.SkillTreeDefinition;
import mc.blasing.fabrpg.skills.SkillTreeManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SkillsConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("skills.json");
    //private static final Path SKILL_TREES_PATH = ConfigManager.getConfigDir().resolve("skill_trees.json");

    private Map<String, SkillDefinition> skills;

    public static SkillsConfig load() {
        SkillsConfig config = new SkillsConfig();

        ConfigManager.ensureConfigFile("skills.json", ConfigManager.getDefaultSkillsConfig());
        ConfigManager.ensureConfigFile("skill_trees.json", "[]");

        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                Fabrpg.LOGGER.info("Skills JSON content: {}", json); // Log the content for debugging
                JsonElement jsonElement = ConfigManager.getGson().fromJson(json, JsonElement.class);

                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("skills")) {
                        JsonElement skillsElement = jsonObject.get("skills");
                        if (skillsElement.isJsonObject()) {
                            Type type = new TypeToken<Map<String, SkillDefinition>>() {}.getType();
                            config.skills = ConfigManager.getGson().fromJson(skillsElement, type);
                        } else if (skillsElement.isJsonArray()) {
                            Fabrpg.LOGGER.warn("Skills JSON contains an empty array. Using default skills.");
                            config.setDefaults();
                        } else {
                            Fabrpg.LOGGER.error("Unexpected JSON structure for skills. Using default skills.");
                            config.setDefaults();
                        }
                    } else {
                        Type type = new TypeToken<Map<String, SkillDefinition>>() {}.getType();
                        config.skills = ConfigManager.getGson().fromJson(jsonObject, type);
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

        config.loadSkillTrees();
        config.save();
        return config;
    }

    private void setDefaults() {
        skills = new HashMap<>();
        skills.put("mining", new SkillDefinition("mining", "Mining", 100, "Increases mining speed and ore drops"));
        skills.put("woodcutting", new SkillDefinition("woodcutting", "Woodcutting", 100, "Increases wood cutting speed and log drops"));
    }

    public void save() {
        try {
            String json = ConfigManager.getGson().toJson(skills);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Error saving skills config file", e);
        }
    }

    private void loadSkillTrees() {
        Fabrpg.LOGGER.info("Skill trees not implemented yet. Skipping loading.");
    }
}
