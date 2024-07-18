package mc.blasing.fabrpg.skills;

import mc.blasing.fabrpg.config.SkillTreeDefinition;
import java.util.HashMap;
import java.util.Map;

public class SkillTreeManager {
    private static final Map<String, SkillTree> skillTrees = new HashMap<>();

    public static void loadSkillTrees(SkillTreeDefinition[] definitions) {
        for (SkillTreeDefinition definition : definitions) {
            SkillTree skillTree = new SkillTree(definition.getSkillId());
            for (SkillTreeDefinition.NodeDefinition nodeDefinition : definition.getNodes()) {
                SkillTreeNode node = new SkillTreeNode(
                        nodeDefinition.getId(),
                        AbilityManager.getAbility(nodeDefinition.getAbilityId()),
                        nodeDefinition.getX(),
                        nodeDefinition.getY(),
                        nodeDefinition.getPrerequisites()
                );
                skillTree.addNode(node);
            }
            skillTrees.put(definition.getSkillId(), skillTree);
        }
    }

    public static SkillTree getSkillTree(String skillId) {
        return skillTrees.get(skillId);
    }
}