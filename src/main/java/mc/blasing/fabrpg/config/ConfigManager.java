package mc.blasing.fabrpg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mc.blasing.fabrpg.Fabrpg;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("fabrpg");

    public static FabRPGConfig mainConfig;
    public static SkillsConfig skillsConfig;
    public static ActionsConfig actionsConfig;
    public static AbilitiesConfig abilitiesConfig;

    public static void loadAll() {
        createConfigDirectory();
        ensureConfigFile("main_config.json", getDefaultMainConfig());
        ensureConfigFile("skills.json", getDefaultSkillsConfig());
        ensureConfigFile("actions.json", getDefaultActionsConfig());
        ensureConfigFile("abilities.json", getDefaultAbilitiesConfig());

        mainConfig = FabRPGConfig.load();
        skillsConfig = SkillsConfig.load();
        actionsConfig = ActionsConfig.load();
        abilitiesConfig = AbilitiesConfig.load();
    }

    public static void saveAll() {
        mainConfig.save();
        skillsConfig.save();
        actionsConfig.save();
        abilitiesConfig.save();
    }

    private static void createConfigDirectory() {
        try {
            Files.createDirectories(CONFIG_DIR);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Failed to create config directory", e);
        }
    }

    public static void ensureConfigFile(String fileName, String defaultContent) {
        Path filePath = CONFIG_DIR.resolve(fileName);
        if (!Files.exists(filePath)) {
            try {
                Files.writeString(filePath, defaultContent);
                Fabrpg.LOGGER.info("Created default config file: {}", fileName);
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Failed to create config file: {}", fileName, e);
            }
        }
    }

    private static String getDefaultMainConfig() {
        return "{\n  \"defaultLanguage\": \"en_us\",\n  \"maxLevel\": 100\n}";
    }

    public static String getDefaultSkillsConfig() {
        return "{\n" +
                "  \"skills\": {\n" +
                "    \"mining\": {\n" +
                "      \"id\": \"mining\",\n" +
                "      \"name\": \"Mining\",\n" +
                "      \"maxLevel\": 100,\n" +
                "      \"description\": \"Increases mining speed and ore drops\",\n" +
                "      \"actions\": [],\n" +
                "      \"abilities\": []\n" +
                "    },\n" +
                "    \"woodcutting\": {\n" +
                "      \"id\": \"woodcutting\",\n" +
                "      \"name\": \"Woodcutting\",\n" +
                "      \"maxLevel\": 100,\n" +
                "      \"description\": \"Increases wood cutting speed and log drops\",\n" +
                "      \"actions\": [],\n" +
                "      \"abilities\": []\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private static String getDefaultActionsConfig() {
        return "[\n  {\n    \"id\": \"break_stone\",\n    \"type\": \"BLOCK_BREAK\",\n    \"blocks\": [\"STONE\"],\n    \"experience\": 10\n  }\n]";
    }

    private static String getDefaultAbilitiesConfig() {
        return "[\n  {\n    \"id\": \"double_ore\",\n    \"name\": \"Double Ore\",\n    \"description\": \"Chance to double ore drops\"\n  }\n]";
    }

    public static Gson getGson() {
        return GSON;
    }

    public static Path getConfigDir() {
        return CONFIG_DIR;
    }
}
