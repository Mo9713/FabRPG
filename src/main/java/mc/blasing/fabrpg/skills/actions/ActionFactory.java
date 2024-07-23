package mc.blasing.fabrpg.skills.actions;

import mc.blasing.fabrpg.Fabrpg;
import java.util.ArrayList;
import java.util.List;

public class ActionFactory {
    public static Action createAction(ActionDefinition definition) {
        return switch (definition.getType().toLowerCase()) {
            case "break_block" -> new BreakBlock(definition.getId(), definition.getExperience());
            case "craft_item" -> new CraftItem(definition.getId(), definition.getExperience());
            case "touch_entity" ->
                    new TouchEntity(definition.getId(), definition.getType(), definition.getEntities(), definition.getExcludedEntities(), definition.getExperience(), definition.getProximityThreshold());
            default -> {
                Fabrpg.LOGGER.warn("Unknown action type: {}", definition.getType());
                yield null;
            }
        };
    }

    public static List<Action> createActions(List<ActionDefinition> definitions) {
        List<Action> actions = new ArrayList<>();
        for (ActionDefinition definition : definitions) {
            Action action = createAction(definition);
            if (action != null) {
                actions.add(action);
            }
        }
        return actions;
    }
}
