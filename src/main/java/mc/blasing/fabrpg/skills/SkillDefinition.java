package mc.blasing.fabrpg.skills;

import java.util.ArrayList;
import java.util.List;

public class SkillDefinition {
    private String id;
    private String name;
    private List<Action> actions;
    private List<Ability> abilities;

    public SkillDefinition(String id, String name) {
        this.id = id;
        this.name = name;
        this.actions = new ArrayList<>();
        this.abilities = new ArrayList<>();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Action> getActions() { return actions; }
    public List<Ability> getAbilities() { return abilities; }
}