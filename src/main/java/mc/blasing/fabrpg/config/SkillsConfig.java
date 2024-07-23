package mc.blasing.fabrpg.config;

import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.SkillDefinition;
import mc.blasing.fabrpg.skills.SkillTreeDefinition;
import mc.blasing.fabrpg.skills.SkillTreeManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class SkillsConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("skills.json");
    private static final Path SKILL_TREES_PATH = ConfigManager.getConfigDir().resolve("skill_trees.json");

    private Map<String, SkillDefinition> skills;

    public static SkillsConfig load() {
        SkillsConfig config = new SkillsConfig();

        ConfigManager.ensureConfigFile("skills.json", "{ \"mining\": { \"name\": \"Mining\", \"actions\": [], \"abilities\": [] } }");
        ConfigManager.ensureConfigFile("skill_trees.json", "[]");

        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                Type type = new TypeToken<Map<String, SkillDefinition>>() {}.getType();
                config.skills = ConfigManager.getGson().fromJson(json, type);
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Error loading skills config file", e);
                config.setDefaults();
            }
        } else {
            config.setDefaults();
        }

        config.loadSkillTrees();
        config.save();
        return config;
    }

    private void setDefaults() {
        // Define default skills here
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
        try {
            String json = Files.readString(SKILL_TREES_PATH);
            SkillTreeDefinition[] definitions = ConfigManager.getGson().fromJson(json, SkillTreeDefinition[].class);
            SkillTreeManager.loadSkillTrees(definitions);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Failed to load skill trees", e);
        }
    }
}
