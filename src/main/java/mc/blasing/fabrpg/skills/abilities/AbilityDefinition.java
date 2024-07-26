package mc.blasing.fabrpg.skills.abilities;

import java.util.ArrayList;
import java.util.List;

public class AbilityDefinition {
    public String id;
    public String name;
    public String description;
    public int requiredLevel;
    public int x;
    public int y;
    public List<String> skills;
    public String chanceFormula;
    public Activation activation;

    public AbilityDefinition(String id, String name, String description, int requiredLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredLevel = requiredLevel;
        this.skills = new ArrayList<>();
    }

    public Activation getActivation() {
        return activation;
    }

    public static class Activation {
        public String type;
        public List<String> blocks;
        public List<String> toolTypes;

        public String getType() {
            return type;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

}
