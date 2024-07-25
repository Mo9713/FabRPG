package mc.blasing.fabrpg.skills.abilities;

import mc.blasing.fabrpg.skills.Skill;

import java.util.Map;

public class Ability {
    public String id; public String getId() { return id; }
    private String name; public String getName() { return name; }
    private String description; public String getDescription() { return description; }
    private int requiredLevel; public int getRequiredLevel() { return requiredLevel; }
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
}
