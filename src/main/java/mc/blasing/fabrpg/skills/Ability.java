package mc.blasing.fabrpg.skills;

import java.util.Map;

public class Ability {
    private String id;
    private String name;
    private String description;
    private int requiredLevel;
    private int x;
    private int y;

    public Ability(String id, String name, String description, int requiredLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredLevel = requiredLevel;
    }

    public boolean canActivate(Map<String, Object> context) {
        // Implement activation logic here
        return true; // Placeholder
    }

    public void activate(Skill skill, Map<String, Object> context) {
        // Implement ability activation here
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getRequiredLevel() { return requiredLevel; }
}