package mc.blasing.fabrpg.config;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.SkillDefinition;
import mc.blasing.fabrpg.skills.SkillTreeManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SkillsConfig {
    private static final Path CONFIG_PATH = ConfigManager.CONFIG_DIR.resolve("skills.json");
    private static final Path SKILL_TREES_PATH = ConfigManager.CONFIG_DIR.resolve("skill_trees.json");

    public List<SkillDefinition> skills = new ArrayList<>();

    public static SkillsConfig load() {
        SkillsConfig config;

        ConfigManager.ensureConfigFile("skills.json", "data/skills.json");
        ConfigManager.ensureConfigFile("skill_trees.json", "data/skill_trees.json");

        if (Files.exists(CONFIG_PATH)) {
            try {
                config = ConfigManager.GSON.fromJson(Files.newBufferedReader(CONFIG_PATH), SkillsConfig.class);
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Error loading skills config file", e);
                config = new SkillsConfig();
            }
        } else {
            config = new SkillsConfig();
            config.setDefaults();
        }

        config.loadSkillTrees();
        config.save();
        return config;
    }

    private void setDefaults() {
        skills.add(new SkillDefinition("mining", "Mining", 100, "Increases mining speed and ore drops"));
        skills.add(new SkillDefinition("woodcutting", "Woodcutting", 100, "Increases wood cutting speed and log drops"));
    }

    public void save() {
        try {
            Files.writeString(CONFIG_PATH, ConfigManager.GSON.toJson(this));
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Error saving skills config file", e);
        }
    }

    private void loadSkillTrees() {
        try {
            String json = Files.readString(SKILL_TREES_PATH);
            SkillTreeDefinition[] definitions = ConfigManager.GSON.fromJson(json, SkillTreeDefinition[].class);
            SkillTreeManager.loadSkillTrees(definitions);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Failed to load skill trees", e);
        }
    }
}