package mc.blasing.fabrpg.skills.actions;

import java.util.List;
import java.util.Map;

public class Action {
    public String id;
    private String type;
    private List<String> blocks;
    private int experience;
    private List<String> entities;           // Entities to include
    private List<String> excludedEntities;   // Entities to exclude
    private double proximityThreshold;       // For proximity-based actions

    // Constructor with additional parameters
    public Action(String id, String type, List<String> blocks, int experience, List<String> entities, List<String> excludedEntities, double proximityThreshold) {
        this.id = id;
        this.type = type;
        this.blocks = blocks;
        this.experience = experience;
        this.entities = entities;
        this.excludedEntities = excludedEntities;
        this.proximityThreshold = proximityThreshold;
    }

    // Getter methods
    public String getId() { return id; }
    public String getType() { return type; }
    public List<String> getBlocks() { return blocks; }
    public int getExperience() { return experience; }
    public List<String> getEntities() { return entities; }
    public List<String> getExcludedEntities() { return excludedEntities; }
    public double getProximityThreshold() { return proximityThreshold; }

    // Check if an action matches the provided context
    public boolean matches(Map<String, Object> context) {
        if (!type.equals(context.get("type"))) {
            return false;
        }

        // Check for blocks if applicable
        Object block = context.get("block");
        if (blocks != null && block != null && !blocks.contains(block.toString())) {
            return false;
        }

        // Check for entities if applicable
        String entity = (String) context.get("entity");
        if (entities != null && !entities.isEmpty()) {
            if (entities.contains("*")) {
                // If wildcard is present, all entities are included unless explicitly excluded
                return excludedEntities == null || !excludedEntities.contains(entity);
            }
            if (!entities.contains(entity)) {
                return false;
            }
        }

        // Check for excluded entities
        return excludedEntities == null || !excludedEntities.contains(entity);
    }
}
