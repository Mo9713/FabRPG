package mc.blasing.fabrpg.skills.actions;

import java.util.List;
import java.util.Map;

public abstract class Entity extends Action {
    private final List<String> entities;
    private final List<String> excludedEntities;
    private final double proximityThreshold;

    public Entity(String id, String type, int experience, List<String> entities, List<String> excludedEntities, double proximityThreshold) {
        super(id, type, null, experience, entities, excludedEntities, proximityThreshold);
        this.entities = entities;
        this.excludedEntities = excludedEntities;
        this.proximityThreshold = proximityThreshold;
    }

    public List<String> getEntities() { return entities; }
    public List<String> getExcludedEntities() { return excludedEntities; }
    public double getProximityThreshold() { return proximityThreshold; }

    @Override
    public boolean matches(Map<String, Object> context) {
        if (!super.matches(context)) {
            return false;
        }

        String entity = (String) context.get("entity");
        if (excludedEntities != null && excludedEntities.contains(entity)) {
            return false;
        }

        Double distance = (Double) context.get("distance");
        return distance == null || !(distance > proximityThreshold);
    }
}