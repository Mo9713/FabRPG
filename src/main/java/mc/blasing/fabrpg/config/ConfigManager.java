package mc.blasing.fabrpg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.SkillTreeManager;
import net.fabricmc.loader.api.FabricLoader;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("fabrpg.json");
    private static final Path SKILL_TREES_PATH = FabricLoader.getInstance().getConfigDir().resolve("skill_trees.json");

    public static void loadConfig() {
        // Load main configuration
    }

    public static void loadSkillTrees() {
        try {
            Path skillTreesPath = FabricLoader.getInstance().getConfigDir().resolve("skill_trees.json");
            if (!Files.exists(skillTreesPath)) {
                // Copy default skill_trees.json from resources to config directory
                InputStream inputStream = Fabrpg.class.getClassLoader().getResourceAsStream("data/skill_trees.json");
                Files.copy(inputStream, skillTreesPath);
            }
            String json = Files.readString(skillTreesPath);
            SkillTreeDefinition[] definitions = GSON.fromJson(json, SkillTreeDefinition[].class);
            SkillTreeManager.loadSkillTrees(definitions);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Failed to load skill trees", e);
        }
    }

    // Other methods...
}