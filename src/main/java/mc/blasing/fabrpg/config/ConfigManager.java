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
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Failed to create config file: " + fileName, e);
            }
        }
    }

    public static Gson getGson() {
        return GSON;
    }

    public static Path getConfigDir() {
        return CONFIG_DIR;
    }
}
