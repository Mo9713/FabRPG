package mc.blasing.fabrpg.config;

import mc.blasing.fabrpg.skills.unlock.UnlockCondition;

import java.util.List;
import java.util.ArrayList;

public class SkillTreeDefinition {
    private String skillId;
    private List<NodeDefinition> nodes;

    public String getSkillId() {
        return skillId;
    }

    public List<NodeDefinition> getNodes() {
        return new ArrayList<>(nodes);
    }

    public static class NodeDefinition {
        private String id;
        private String abilityId;
        private int x;
        private int y;
        private List<UnlockCondition> unlockConditions;

        public String getId() {
            return id;
        }

        public String getAbilityId() {
            return abilityId;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public List<UnlockCondition> getUnlockConditions() {
            return new ArrayList<>(unlockConditions);
        }
    }
}