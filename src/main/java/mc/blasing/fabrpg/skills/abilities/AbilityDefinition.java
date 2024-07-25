package mc.blasing.fabrpg.skills.abilities;

public class AbilityDefinition {
    public String id;
    public String name;
    public String description;
    public int requiredLevel;
    public int x;
    public int y;

    public AbilityDefinition(String id, String name, String description, int requiredLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredLevel = requiredLevel;
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
}
