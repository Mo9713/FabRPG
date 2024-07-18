package mc.blasing.fabrpg.skills;

import net.minecraft.util.Identifier;
import net.minecraft.server.network.ServerPlayerEntity;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.events.SkillLevelUpCallback;

import java.util.HashMap;
import java.util.Map;

public class Skill {
    private final Identifier id;
    private final String name;
    private int level;
    private int experience;
    private int passiveTokens;
    private int skillTokens;
    private ExperienceCalculator expCalculator;
    private final ServerPlayerEntity player;
    private Map<String, Ability> abilities;
    private Map<String, Action> actions;

    public Skill(String id, String name, ServerPlayerEntity player) {
        this.id = Identifier.of(Fabrpg.MOD_ID, id);
        this.name = name;
        this.player = player;
        this.level = 1;
        this.experience = 0;
        this.passiveTokens = 0;
        this.skillTokens = 0;
        this.expCalculator = new DefaultExperienceCalculator();
        this.abilities = new HashMap<>();
        this.actions = new HashMap<>();
    }

    public void addExperience(int amount) {
        experience += amount;
        while (experience >= expCalculator.getExperienceForNextLevel(level)) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        experience -= expCalculator.getExperienceForNextLevel(level - 1);
        passiveTokens++;
        if (level % 100 == 0) {
            skillTokens++;
        }
        SkillLevelUpCallback.EVENT.invoker().onSkillLevelUp(player, this, level);
    }

    public void addAbility(String abilityId, Ability ability) {
        abilities.put(abilityId, ability);
    }

    public Ability getAbility(String abilityId) {
        return abilities.get(abilityId);
    }

    public void addAction(String actionId, Action action) {
        actions.put(actionId, action);
    }

    public void handleAction(Map<String, Object> context) {
        for (Action action : actions.values()) {
            if (action.matches(context)) {
                addExperience(action.getExperience());
                // Trigger any abilities that might activate on this action
                for (Ability ability : abilities.values()) {
                    if (ability.canActivate(context)) {
                        ability.activate(this, context);
                    }
                }
                break;
            }
        }
    }

    // Getters and setters
    public Identifier getId() { return id; }
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getPassiveTokens() { return passiveTokens; }
    public int getSkillTokens() { return skillTokens; }
    public ServerPlayerEntity getPlayer() { return player; }

    public void setExpCalculator(ExperienceCalculator calculator) {
        this.expCalculator = calculator;
    }
}