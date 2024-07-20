package mc.blasing.fabrpg.skills.actions;

import java.util.List;

public class ActionDefinition {
    private String id;
    private String type;
    private List<String> blocks;
    private int experience;

    public ActionDefinition(String id, String type, List<String> blocks, int experience) {
        this.id = id;
        this.type = type;
        this.blocks = blocks;
        this.experience = experience;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public List<String> getBlocks() { return blocks; }
    public int getExperience() { return experience; }
}
