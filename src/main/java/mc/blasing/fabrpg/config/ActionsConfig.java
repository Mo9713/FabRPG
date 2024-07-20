package mc.blasing.fabrpg.config;

import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.actions.Action;
import mc.blasing.fabrpg.skills.actions.ActionDefinition;
import mc.blasing.fabrpg.skills.actions.ActionFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ActionsConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("actions.json");
    public List<Action> actions = new ArrayList<>();

    public static ActionsConfig load() {
        ActionsConfig config;

        ConfigManager.ensureConfigFile("actions.json", "data/actions.json");

        if (Files.exists(CONFIG_PATH)) {
            try {
                Type actionListType = new TypeToken<ArrayList<ActionDefinition>>(){}.getType();
                List<ActionDefinition> definitions = ConfigManager.getGson().fromJson(Files.newBufferedReader(CONFIG_PATH), actionListType);

                config = new ActionsConfig();
                for (ActionDefinition def : definitions) {
                    config.actions.add(ActionFactory.createAction(def));
                }
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Error loading actions config file", e);
                config = new ActionsConfig();
            }
        } else {
            config = new ActionsConfig();
            config.setDefaults();
        }

        return config;
    }

    private void setDefaults() {
        // Add default actions if needed
    }

    public void save() {
        try {
            List<ActionDefinition> definitions = new ArrayList<>();
            for (Action action : actions) {
                definitions.add(new ActionDefinition(action.getId(), action.getType(), action.getBlocks(), action.getExperience()));
            }
            Files.writeString(CONFIG_PATH, ConfigManager.getGson().toJson(definitions));
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Error saving actions config file", e);
        }
    }
}
