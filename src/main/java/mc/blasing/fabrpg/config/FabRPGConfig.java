package mc.blasing.fabrpg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FabRPGConfig {
    private static final Logger LOGGER = LogManager.getLogger(FabRPGConfig.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("fabrpg");

    // General settings
    public String defaultLanguage = "en_us";
    public boolean forceLanguage = false;
    public boolean useMinecraftXP = true;

    public int saveStatsTimer = 300;
        public int getSaveStatsTimer() {
        return saveStatsTimer;
    }
    public boolean allowExplosions = true;
    public boolean allowBuild = true;
    public boolean allowPvP = true;
    public boolean allowHurtAnimals = true;

    public int maxLevel = 100;
        public int getMaxLevel() {
        return maxLevel;
    }

    // Command customization

    public Map<String, String> commands = new HashMap<>();

    public boolean isUseMinecraftXP() {
        return useMinecraftXP;
    }

    public void setUseMinecraftXP(boolean useMinecraftXP) {
        this.useMinecraftXP = useMinecraftXP;
    }

    public static FabRPGConfig load() {
        FabRPGConfig config;
        Path mainConfigPath = CONFIG_DIR.resolve("fabrpg.json");

        if (Files.exists(mainConfigPath)) {
            try {
                config = GSON.fromJson(Files.newBufferedReader(mainConfigPath), FabRPGConfig.class);
            } catch (IOException e) {
                LOGGER.error("Error loading main config file", e);
                config = new FabRPGConfig();
            }
        } else {
            config = new FabRPGConfig();
            config.setDefaults();
        }

        config.save();
        return config;
    }

    private void setDefaults() {
        commands.put("skill", "skill");
        commands.put("skillTree", "skilltree");
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_DIR);
            Files.writeString(CONFIG_DIR.resolve("fabrpg.json"), GSON.toJson(this));
        } catch (IOException e) {
            LOGGER.error("Error saving main config file", e);
        }
    }
}