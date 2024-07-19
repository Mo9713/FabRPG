package mc.blasing.fabrpg.skills;

import mc.blasing.fabrpg.skills.unlock.UnlockCondition;

import java.util.ArrayList;
import java.util.List;

public class SkillDefinition {
    private String id;
    private String name;
    private List<Action> actions;
    private List<Ability> abilities;
    private List<UnlockCondition> unlockConditions;

    public SkillDefinition(String id, String name) {
        this.id = id;
        this.name = name;
        this.actions = new ArrayList<>();
        this.abilities = new ArrayList<>();
        this.unlockConditions = new ArrayList<>();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void addUnlockCondition(UnlockCondition condition) {
        unlockConditions.add(condition);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Action> getActions() { return new ArrayList<>(actions); }
    public List<Ability> getAbilities() { return new ArrayList<>(abilities); }
    public List<UnlockCondition> getUnlockConditions() { return new ArrayList<>(unlockConditions); }
}