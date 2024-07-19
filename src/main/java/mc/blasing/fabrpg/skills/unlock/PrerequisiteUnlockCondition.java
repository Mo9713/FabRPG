package mc.blasing.fabrpg.skills.unlock;

import mc.blasing.fabrpg.skills.SkillManager;
import net.minecraft.entity.player.PlayerEntity;

public class PrerequisiteUnlockCondition implements UnlockCondition {
    private final String prerequisiteSkillId;

    public PrerequisiteUnlockCondition(String prerequisiteSkillId) {
        this.prerequisiteSkillId = prerequisiteSkillId;
    }

    @Override
    public boolean canUnlock(PlayerEntity player) {
        return SkillManager.isSkillUnlocked(player, prerequisiteSkillId);
    }

    @Override
    public String getType() {
        return "prerequisite";
    }

    public String getPrerequisiteSkillId() {
        return prerequisiteSkillId;
    }
}