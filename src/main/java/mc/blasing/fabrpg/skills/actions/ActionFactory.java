package mc.blasing.fabrpg.skills.actions;

import mc.blasing.fabrpg.Fabrpg;
import java.util.ArrayList;
import java.util.List;

public class ActionFactory {
    public static Action createAction(ActionDefinition definition) {
        return switch (definition.getType().toLowerCase()) {
            case "break_block" -> new BreakBlock(definition.getId(), definition.getBlocks(), definition.getExperience());
            case "craft_item" -> new CraftItem(definition.getId(), definition.getExperience());
            case "touch_entity" -> new TouchEntity(definition.getId(), definition.getType(), definition.getEntities(), definition.getExcludedEntities(), definition.getExperience(), definition.getProximityThreshold());
            default -> {
                Fabrpg.LOGGER.warn("Unknown action type: {}", definition.getType());
                yield null;
            }
        };
    }
}