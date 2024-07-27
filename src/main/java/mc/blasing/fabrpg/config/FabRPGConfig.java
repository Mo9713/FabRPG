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
    private String defaultLanguage = "en_us";
    private boolean forceLanguage = false;
    private boolean useMinecraftXP = true;
    private int saveStatsTimer = 300;
    private boolean allowExplosions = true;
    private boolean allowBuild = true;
    private boolean allowPvP = true;
    private boolean allowHurtAnimals = true;
    private int maxLevel = 100;
    private Map<String, String> commands = new HashMap<>();

    // Getters and setters
    public String getDefaultLanguage() { return defaultLanguage; }
    public void setDefaultLanguage(String defaultLanguage) { this.defaultLanguage = defaultLanguage; }

    public boolean isForceLanguage() { return forceLanguage; }
    public void setForceLanguage(boolean forceLanguage) { this.forceLanguage = forceLanguage; }

    public boolean isUseMinecraftXP() { return useMinecraftXP; }
    public void setUseMinecraftXP(boolean useMinecraftXP) { this.useMinecraftXP = useMinecraftXP; }

    public int getSaveStatsTimer() { return saveStatsTimer; }
    public void setSaveStatsTimer(int saveStatsTimer) { this.saveStatsTimer = saveStatsTimer; }

    public boolean isAllowExplosions() { return allowExplosions; }
    public void setAllowExplosions(boolean allowExplosions) { this.allowExplosions = allowExplosions; }

    public boolean isAllowBuild() { return allowBuild; }
    public void setAllowBuild(boolean allowBuild) { this.allowBuild = allowBuild; }

    public boolean isAllowPvP() { return allowPvP; }
    public void setAllowPvP(boolean allowPvP) { this.allowPvP = allowPvP; }

    public boolean isAllowHurtAnimals() { return allowHurtAnimals; }
    public void setAllowHurtAnimals(boolean allowHurtAnimals) { this.allowHurtAnimals = allowHurtAnimals; }

    public int getMaxLevel() { return maxLevel; }
    public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel; }

    public Map<String, String> getCommands() { return commands; }
    public void setCommands(Map<String, String> commands) { this.commands = commands; }

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