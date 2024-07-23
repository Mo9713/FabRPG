package mc.blasing.fabrpg.skills.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionManager {
    private static final Map<String, Action> actions = new HashMap<>();

    public static void initialize(List<ActionDefinition> definitions) {
        for (ActionDefinition definition : definitions) {
            Action action = ActionFactory.createAction(definition);
            if (action != null) {
                actions.put(action.getId(), action);
            }
        }
    }

    public static Action getAction(String id) {
        return actions.get(id);
    }

    public static List<Action> getAllActions() {
        return List.copyOf(actions.values());
    }

    // Add more methods here for managing actions
}