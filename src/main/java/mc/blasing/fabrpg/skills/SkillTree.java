package mc.blasing.fabrpg.skills;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SkillTree {
    private String skillId;
    private Map<String, SkillTreeNode> nodes;

    public SkillTree(String skillId) {
        this.skillId = skillId;
        this.nodes = new HashMap<>();
    }

    public void addNode(SkillTreeNode node) {
        nodes.put(node.getId(), node);
    }

    public SkillTreeNode getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public Collection<SkillTreeNode> getNodes() {
        return nodes.values();
    }

    public String getSkillId() {
        return skillId;
    }

    // Add methods for traversing the tree, checking prerequisites, etc.
}