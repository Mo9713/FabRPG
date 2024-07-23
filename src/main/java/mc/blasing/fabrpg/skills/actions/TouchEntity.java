package mc.blasing.fabrpg.skills.actions;

import java.util.List;
import java.util.Map;

public class TouchEntity extends Entity {
    public TouchEntity(String id, String type, List<String> entities, List<String> excludedEntities, int experience, double proximityThreshold) {
        super(id, type, experience, entities, excludedEntities, proximityThreshold);
    }

    @Override
    public boolean matches(Map<String, Object> context) {
        if (!super.matches(context)) {
            return false;
        }

        String entity = (String) context.get("entity");
        if (getExcludedEntities() != null && getExcludedEntities().contains(entity)) {
            return false;
        }

        Double distance = (Double) context.get("distance");
        return distance == null || distance <= getProximityThreshold();
    }
}
