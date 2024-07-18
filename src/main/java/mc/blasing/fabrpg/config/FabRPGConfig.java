package mc.blasing.fabrpg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FabRPGConfig {
    private static final Logger LOGGER = LogManager.getLogger(FabRPGConfig.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("fabrpg.json");

    // Configuration fields
    public String defaultLanguage = "en_us";
    public boolean forceLanguage = false;
    public int saveStatsTimer = 300;
    public boolean allowExplosions = true;
    public boolean allowBuild = true;
    public boolean allowPvP = true;
    public boolean allowHurtAnimals = true;
    public int maxLevel = 100;

    // Add more configuration fields as needed

    public static FabRPGConfig load() {
        FabRPGConfig config;

        if (Files.exists(CONFIG_PATH)) {
            try {
                config = GSON.fromJson(Files.newBufferedReader(CONFIG_PATH), FabRPGConfig.class);
            } catch (IOException e) {
                LOGGER.error("Error loading config file", e);
                config = new FabRPGConfig();
            }
        } else {
            config = new FabRPGConfig();
        }

        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(this));
        } catch (IOException e) {
            LOGGER.error("Error saving config file", e);
        }
    }
}