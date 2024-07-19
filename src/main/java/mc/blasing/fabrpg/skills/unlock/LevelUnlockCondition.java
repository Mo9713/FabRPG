package mc.blasing.fabrpg.skills.unlock;

import net.minecraft.entity.player.PlayerEntity;

public class LevelUnlockCondition implements UnlockCondition {
    private final int requiredLevel;

    public LevelUnlockCondition(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public boolean canUnlock(PlayerEntity player) {
        return player.experienceLevel >= requiredLevel;
    }

    @Override
    public String getType() {
        return "level";
    }
}