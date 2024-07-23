package mc.blasing.fabrpg.skills.actions;

import java.util.List;

public class ActionDefinition {
    private String id;
    private String type;
    private List<String> blocks;
    private int experience;
    private List<String> entities;
    private List<String> excludedEntities;
    private double proximityThreshold;

    public ActionDefinition(String id, String type, List<String> blocks, int experience, List<String> entities, List<String> excludedEntities, double proximityThreshold) {
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
}
