package mc.blasing.fabrpg.skills.actions;

import java.util.List;

public class ActionFactory {
    public static Action createAction(ActionDefinition definition) {
        switch (definition.getType()) {
            case "break_block":
                return new BreakBlockAction(definition.getId(), definition.getExperience());
            // Add more cases for other action types
            default:
                throw new IllegalArgumentException("Unknown action type: " + definition.getType());
        }
    }
}
