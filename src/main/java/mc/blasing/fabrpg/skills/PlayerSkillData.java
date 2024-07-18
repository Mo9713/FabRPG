package mc.blasing.fabrpg.skills;

import net.minecraft.server.network.ServerPlayerEntity;
import java.util.HashMap;
import java.util.Map;

public class PlayerSkillData {
    private final ServerPlayerEntity player;
    private final Map<String, SkillTree> unlockedNodes;
    private int skillPoints;

    public PlayerSkillData(ServerPlayerEntity player) {
        this.player = player;
        this.unlockedNodes = new HashMap<>();
        this.skillPoints = 0;
    }

    public void unlockNode(String skillId, String nodeId) {
        SkillTree skillTree = unlockedNodes.computeIfAbsent(skillId, k -> new SkillTree(skillId));
        SkillTreeNode node = skillTree.getNode(nodeId);
        if (node != null) {
            node.setUnlocked(true);
        }
    }

    public boolean isNodeUnlocked(String skillId, String nodeId) {
        SkillTree skillTree = unlockedNodes.get(skillId);
        if (skillTree == null) return false;
        SkillTreeNode node = skillTree.getNode(nodeId);
        return node != null && node.isUnlocked();
    }

    public void addSkillPoints(int points) {
        this.skillPoints += points;
    }

    public boolean useSkillPoints(int points) {
        if (this.skillPoints >= points) {
            this.skillPoints -= points;
            return true;
        }
        return false;
    }

    public int getSkillPoints() {
        return skillPoints;
    }
}