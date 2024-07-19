package mc.blasing.fabrpg.skills;

import java.util.HashMap;
import java.util.Map;

public class AbilityManager {
    private static final Map<String, Ability> abilities = new HashMap<>();

    public static void registerAbility(Ability ability) {
        abilities.put(ability.getId(), ability);
    }

    public static Ability getAbility(String id) {
        return abilities.get(id);
    }
}