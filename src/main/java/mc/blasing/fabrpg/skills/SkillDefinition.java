package mc.blasing.fabrpg.skills;

import java.util.ArrayList;
import java.util.List;

public class SkillDefinition {
    private String id;
    private String name;
    private String description;
    private boolean enabled;
    private int maxLevel;
    private double xpMultiplier;
    private List<String> actionIds;
    private List<String> abilityIds;
    private int xpCap;
    private int levelCap;
    private List<String> unlockConditions;

    public SkillDefinition(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.actionIds = new ArrayList<>();
        this.abilityIds = new ArrayList<>();
        this.unlockConditions = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public int getMaxLevel() { return maxLevel; }
    public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel; }

    public double getXpMultiplier() { return xpMultiplier; }
    public void setXpMultiplier(double xpMultiplier) { this.xpMultiplier = xpMultiplier; }

    public List<String> getActionIds() { return actionIds; }
    public void setActionIds(List<String> actionIds) { this.actionIds = actionIds; }
    public void addActionId(String actionId) { this.actionIds.add(actionId); }

    public List<String> getAbilityIds() { return abilityIds; }
    public void setAbilityIds(List<String> abilityIds) { this.abilityIds = abilityIds; }
    public void addAbilityId(String abilityId) { this.abilityIds.add(abilityId); }

    public int getXpCap() { return xpCap; }
    public void setXpCap(int xpCap) { this.xpCap = xpCap; }

    public int getLevelCap() { return levelCap; }
    public void setLevelCap(int levelCap) { this.levelCap = levelCap; }

    public List<String> getUnlockConditions() { return unlockConditions; }
    public void setUnlockConditions(List<String> unlockConditions) { this.unlockConditions = unlockConditions; }
}