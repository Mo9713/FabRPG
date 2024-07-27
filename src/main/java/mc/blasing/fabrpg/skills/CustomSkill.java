package mc.blasing.fabrpg.skills;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.abilities.Ability;
import mc.blasing.fabrpg.skills.actions.Action;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.Map;
import java.util.HashMap;

public class CustomSkill extends Skill {
    private Map<String, Action> actions;
    private Map<String, Ability> abilities;

    public CustomSkill(String id, String name, ServerPlayerEntity player) {
        super(id, name, player);
        this.actions = new HashMap<>();
        this.abilities = new HashMap<>();
    }

    public void addAction(Action action) {
        actions.put(action.getId(), action);
    }

    public void addAbility(Ability ability) {
        abilities.put(ability.getId(), ability);
    }

    public boolean hasAbility(String abilityId) {
        return abilities.containsKey(abilityId);
    }

    @Override
    public void handleAction(Map<String, Object> context) {
        String actionType = (String) context.get("type");
        for (Action action : actions.values()) {
            if (action.matches(context)) {
                addExperience(action.getExperience());
                Fabrpg.LOGGER.info("Player {} gained {} experience in {} from action {}",
                        getPlayer().getName().getString(), action.getExperience(), getName(), actionType);

                for (Ability ability : abilities.values()) {
                    if (ability.canActivate(context)) {
                        ability.activate(this, context);
                        Fabrpg.LOGGER.info("Player {} activated ability {} in skill {}",
                                getPlayer().getName().getString(), ability.getName(), getName());
                    }
                }
                break;
            }
        }
    }
}