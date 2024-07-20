package mc.blasing.fabrpg.skills.actions;

import java.util.List;
import java.util.Map;

public class Action {
    private String id;
    private String type;
    private List<String> blocks;
    private int experience;

    public Action(String id, String type, List<String> blocks, int experience) {
        this.id = id;
        this.type = type;
        this.blocks = blocks;
        this.experience = experience;
    }

    public boolean matches(Map<String, Object> context) {
        if (!type.equals(context.get("type"))) {
            return false;
        }
        if (blocks != null && !blocks.contains(context.get("block"))) {
            return false;
        }
        return true;
    }

    public String getId() { return id; }
    public int getExperience() { return experience; }
}