package mc.blasing.fabrpg.skills.actions;

import java.util.List;

public class ActionDefinition {
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String type;
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    private List<String> blocks;
    public List<String> getBlocks() { return blocks; }
    public void setBlocks(List<String> blocks) { this.blocks = blocks; }

    private int experience;
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    private List<String> entities;
    public List<String> getEntities() { return entities; }
    public void setEntities(List<String> entities) { this.entities = entities; }

    private List<String> excludedEntities;
    public List<String> getExcludedEntities() { return excludedEntities; }
    public void setExcludedEntities(List<String> excludedEntities) { this.excludedEntities = excludedEntities; }

    private double proximityThreshold;
    public double getProximityThreshold() { return proximityThreshold; }
    public void setProximityThreshold(double proximityThreshold) { this.proximityThreshold = proximityThreshold; }

    // New fields
    private String xpFormula;
    public String getXpFormula() { return xpFormula; }
    public void setXpFormula(String xpFormula) { this.xpFormula = xpFormula; }

    private List<String> conditions;
    public List<String> getConditions() { return conditions; }
    public void setConditions(List<String> conditions) { this.conditions = conditions; }

    public ActionDefinition(String id, String type, int experience) {
        this.id = id;
        this.type = type;
        this.experience = experience;
    }
}