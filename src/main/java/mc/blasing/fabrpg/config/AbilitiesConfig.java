package mc.blasing.fabrpg.config;

import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.abilities.AbilityDefinition;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AbilitiesConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("abilities.json");

    public List<AbilityDefinition> abilities = new ArrayList<>();

    public static AbilitiesConfig load() {
        AbilitiesConfig config = new AbilitiesConfig();

        ConfigManager.ensureConfigFile("abilities.json", "[]");

        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                Type type = new TypeToken<List<AbilityDefinition>>() {}.getType();
                config.abilities = ConfigManager.getGson().fromJson(json, type);
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Error loading abilities config file", e);
                config.setDefaults();
            }
        } else {
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
            String json = ConfigManager.getGson().toJson(abilities);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Error saving abilities config file", e);
        }
    }
}
