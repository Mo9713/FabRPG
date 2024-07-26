package mc.blasing.fabrpg.skills.abilities;

import mc.blasing.fabrpg.Fabrpg;

public class AbilityFactory {
    public static Ability createAbility(AbilityDefinition definition) {
        return new Ability(definition.getId(), definition.getName(), definition.getDescription(), definition.getRequiredLevel());
    }
}