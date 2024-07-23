package mc.blasing.fabrpg.config;

import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.actions.ActionDefinition;
import mc.blasing.fabrpg.skills.actions.ActionManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ActionsConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("actions.json");
    private List<ActionDefinition> actions;

    public ActionsConfig() {
        this.actions = new ArrayList<>();
    }

    public static ActionsConfig load() {
        ActionsConfig config = new ActionsConfig();

        ConfigManager.ensureConfigFile("actions.json", getDefaultActionsConfig());

        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                Type listType = new TypeToken<ArrayList<ActionDefinition>>() {}.getType();
                config.actions = ConfigManager.getGson().fromJson(json, listType);
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Error loading actions config file", e);
                config.setDefaults();
            }
        } else {
            config.setDefaults();
        }

        // Initialize ActionManager here
        ActionManager.initialize(config.actions);

        return config;
    }

    private void setDefaults() {
        actions.clear();
        actions.add(new ActionDefinition("break_stone", "BLOCK_BREAK", List.of("STONE", "COBBLESTONE"), 10, List.of(), List.of(), 0.0));
        actions.add(new ActionDefinition("break_ore", "BLOCK_BREAK", List.of("IRON_ORE", "GOLD_ORE", "DIAMOND_ORE"), 50, List.of(), List.of(), 0.0));
        actions.add(new ActionDefinition("touch_entity", "ENTITY_TOUCH", List.of(), 15, List.of("COW", "SHEEP"), List.of("BETTERNETHER_NAGA"), 10.0));
        // Add more default actions as needed
    }

    public void save() {
        try {
            String json = ConfigManager.getGson().toJson(actions);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Error saving actions config file", e);
        }
    }

    public List<ActionDefinition> getActions() {
        return new ArrayList<>(actions);
    }

    private static String getDefaultActionsConfig() {
        return "[\n  {\n    \"id\": \"break_stone\",\n    \"type\": \"BLOCK_BREAK\",\n    \"blocks\": [\"STONE\", \"COBBLESTONE\"],\n    \"experience\": 10\n  },\n  {\n    \"id\": \"break_ore\",\n    \"type\": \"BLOCK_BREAK\",\n    \"blocks\": [\"IRON_ORE\", \"GOLD_ORE\", \"DIAMOND_ORE\"],\n    \"experience\": 50\n  },\n  {\n    \"id\": \"touch_entity\",\n    \"type\": \"ENTITY_TOUCH\",\n    \"entities\": [\"COW\", \"SHEEP\"],\n    \"excludedEntities\": [\"BETTERNETHER_NAGA\"],\n    \"experience\": 15,\n    \"proximityThreshold\": 10.0\n  }\n]";
    }
}
