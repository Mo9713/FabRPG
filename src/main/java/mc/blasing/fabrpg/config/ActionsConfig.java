package mc.blasing.fabrpg.config;

import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.actions.ActionDefinition;
import mc.blasing.fabrpg.skills.actions.ActionFactory;
import mc.blasing.fabrpg.skills.actions.Action;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ActionsConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("actions.json");
    public List<ActionDefinition> actions;
    private Map<String, List<String>> skillToActionMap;

    public ActionsConfig() {
        this.actions = new ArrayList<>();
        this.skillToActionMap = new HashMap<>();
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

        config.initializeSkillToActionMap();

        return config;
    }

    private void setDefaults() {
        actions.clear();
        actions.add(new ActionDefinition("break_stone", "BREAK_BLOCK", List.of("STONE", "COBBLESTONE"), 10, List.of(), List.of(), 0.0));
        actions.add(new ActionDefinition("break_ore", "BREAK_BLOCK", List.of("IRON_ORE", "GOLD_ORE", "DIAMOND_ORE"), 50, List.of(), List.of(), 0.0));
        actions.add(new ActionDefinition("touch_entity", "ENTITY_TOUCH", List.of(), 15, List.of("COW", "SHEEP"), List.of("BETTERNETHER_NAGA"), 10.0));
    }

    private void initializeSkillToActionMap() {
        // Load this from a config file or generate based on your game's logic
        skillToActionMap.put("mining", List.of("break_stone", "break_ore"));
        skillToActionMap.put("combat", List.of("touch_entity"));
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

    public List<Action> getActionsForSkill(String skillId) {
        List<String> actionIds = skillToActionMap.getOrDefault(skillId, new ArrayList<>());
        return actions.stream()
                .filter(actionDef -> actionIds.contains(actionDef.getId()))
                .map(ActionFactory::createAction)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static String getDefaultActionsConfig() {
        return """
            [
              {
                "id": "break_stone",
                "type": "BREAK_BLOCK",
                "blocks": ["STONE", "COBBLESTONE"],
                "experience": 10
              },
              {
                "id": "break_ore",
                "type": "BREAK_BLOCK",
                "blocks": ["IRON_ORE", "GOLD_ORE", "DIAMOND_ORE"],
                "experience": 50
              },
              {
                "id": "touch_entity",
                "type": "ENTITY_TOUCH",
                "entities": ["COW", "SHEEP"],
                "excludedEntities": ["BETTERNETHER_NAGA"],
                "experience": 15,
                "proximityThreshold": 10.0
              }
            ]""";
    }
}