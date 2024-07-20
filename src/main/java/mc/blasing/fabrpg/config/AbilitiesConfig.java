package mc.blasing.fabrpg.config;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.abilities.AbilityDefinition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AbilitiesConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("abilities.json");

    public List<AbilityDefinition> abilities = new ArrayList<>();

    public static AbilitiesConfig load() {
        AbilitiesConfig config;

        ConfigManager.ensureConfigFile("abilities.json", "data/abilities.json");

        if (Files.exists(CONFIG_PATH)) {
            try {
                config = ConfigManager.getGson().fromJson(Files.newBufferedReader(CONFIG_PATH), AbilitiesConfig.class);
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Error loading abilities config file", e);
                config = new AbilitiesConfig();
            }
        } else {
            config = new AbilitiesConfig();
            config.setDefaults();
        }

        config.save();
        return config;
    }

    private void setDefaults() {
        abilities.add(new AbilityDefinition("double_drop", "Double Drop", "Doubles the drops from mining or woodcutting", 1));
        abilities.add(new AbilityDefinition("instant_mine", "Instant Mine", "Instantly mines a block with a single hit", 1));
    }

    public void save() {
        try {
            Files.writeString(CONFIG_PATH, ConfigManager.getGson().toJson(this));
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Error saving abilities config file", e);
        }
    }
}
