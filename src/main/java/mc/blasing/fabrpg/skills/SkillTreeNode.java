package mc.blasing.fabrpg.skills;

import mc.blasing.fabrpg.skills.abilities.Ability;
import mc.blasing.fabrpg.skills.unlock.UnlockCondition;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.ArrayList;

public class SkillTreeNode {
    private final String id;
    private final Ability ability;
    private int x;
    private int y;
    private final List<UnlockCondition> unlockConditions;
    private boolean unlocked;

    public SkillTreeNode(String id, Ability ability, int x, int y, List<UnlockCondition> unlockConditions) {
        this.id = id;
        this.ability = ability;
        this.x = x;
        this.y = y;
        this.unlockConditions = new ArrayList<>(unlockConditions);
        this.unlocked = false;
    }

    public boolean canUnlock(PlayerEntity player) {
        return unlockConditions.stream().allMatch(condition -> condition.canUnlock(player));
    }

    public void unlock() {
        this.unlocked = true;
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<UnlockCondition> getUnlockConditions() {
        return new ArrayList<>(unlockConditions);
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void addUnlockCondition(UnlockCondition condition) {
        unlockConditions.add(condition);
    }

    @Override
    public String toString() {
        return "SkillTreeNode{" +
                "id='" + id + '\'' +
                ", ability=" + ability +
                ", x=" + x +
                ", y=" + y +
                ", unlocked=" + unlocked +
                '}';
    }
}