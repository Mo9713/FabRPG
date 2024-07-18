package mc.blasing.fabrpg.config;

import java.util.List;

public class SkillTreeDefinition {
    private String skillId;
    private List<NodeDefinition> nodes;

    public String getSkillId() {
        return skillId;
    }

    public List<NodeDefinition> getNodes() {
        return nodes;
    }

    public static class NodeDefinition {
        private String id;
        private String abilityId;
        private int x;
        private int y;
        private List<String> prerequisites;

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

        public List<String> getPrerequisites() {
            return prerequisites;
        }
    }
}