package mc.blasing.fabrpg.skills;

import mc.blasing.fabrpg.skills.abilities.Ability;
import mc.blasing.fabrpg.skills.actions.Action;
import mc.blasing.fabrpg.skills.experience.DefaultExperienceCalculator;
import mc.blasing.fabrpg.skills.experience.ExperienceCalculator;
import mc.blasing.fabrpg.config.ConfigManager;
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
        if (ConfigManager.mainConfig.isUseMinecraftXP()) {
            player.addExperience(amount);
            checkLevelUp();
        } else {
            experience += amount;
            while (experience >= expCalculator.getExperienceForNextLevel(level)) {
                levelUp();
            }
        }
    }

    private void checkLevelUp() {
        int newLevel = player.experienceLevel + 1;
        if (newLevel > level) {
            int levelsGained = newLevel - level;
            for (int i = 0; i < levelsGained; i++) {
                levelUp();
            }
        }
    }

    private void levelUp() {
        level++;
        if (!ConfigManager.mainConfig.isUseMinecraftXP()) {
            experience -= expCalculator.getExperienceForNextLevel(level - 1);
        }
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

    public boolean spendSkillPoint() {
        if (ConfigManager.mainConfig.isUseMinecraftXP()) {
            if (player.experienceLevel > 0) {
                player.addExperienceLevels(-1);
                return true;
            }
            return false;
        } else {
            if (skillTokens > 0) {
                skillTokens--;
                return true;
            }
            return false;
        }
    }

    // Getters and setters
    public Identifier getId() { return id; }
    public String getName() { return name; }
    public int getLevel() {
        return ConfigManager.mainConfig.isUseMinecraftXP() ? player.experienceLevel : level;
    }
    public int getExperience() {
        return ConfigManager.mainConfig.isUseMinecraftXP() ? (int) player.experienceProgress : experience;
    }
    public int getPassiveTokens() { return passiveTokens; }
    public int getSkillTokens() { return skillTokens; }
    public ServerPlayerEntity getPlayer() { return player; }

    public void setExpCalculator(ExperienceCalculator calculator) {
        this.expCalculator = calculator;
    }
}