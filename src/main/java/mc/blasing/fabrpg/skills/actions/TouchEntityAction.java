package mc.blasing.fabrpg.skills.actions;

import java.util.List;
import java.util.Map;

public class TouchEntityAction extends Action {
    private List<String> entities;
    public List<String> getEntities() { return entities; }

    private List<String> excludedEntities;
    public List<String> getExcludedEntities() { return excludedEntities; }

    private double proximityThreshold;
    public double getProximityThreshold() { return proximityThreshold; }

    public TouchEntityAction(String id, String type, List<String> entities, List<String> excludedEntities, int experience, double proximityThreshold) {
        super(id, type, null, experience); // Pass null for blocks as it's not used in this action
        this.entities = entities;
        this.excludedEntities = excludedEntities; // Initialize the new field
        this.proximityThreshold = proximityThreshold;
    }

    @Override
    public boolean matches(Map<String, Object> context) {
        if (!super.matches(context)) {
            return false;
        }

        // Check if the entity is in the excluded list
        String entity = (String) context.get("entity");
        if (excludedEntities != null && excludedEntities.contains(entity)) {
            return false;
        }

        // Additional logic to handle entity proximity and other checks
        // ...

        return true;
    }
}
