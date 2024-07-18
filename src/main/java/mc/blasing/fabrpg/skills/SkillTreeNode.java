package mc.blasing.fabrpg.skills;

import java.util.List;

public class SkillTreeNode {
    private String id;
    private Ability ability;
    private int x;
    private int y;
    private List<String> prerequisites;
    private boolean unlocked;

    public SkillTreeNode(String id, Ability ability, int x, int y, List<String> prerequisites) {
        this.id = id;
        this.ability = ability;
        this.x = x;
        this.y = y;
        this.prerequisites = prerequisites;
    }

    public String getId() {
        return id;
    }

    public Ability getAbility() {
        return ability;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}