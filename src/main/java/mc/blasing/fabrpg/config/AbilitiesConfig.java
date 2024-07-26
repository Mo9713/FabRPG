package mc.blasing.fabrpg.config;

import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.abilities.Ability;
import mc.blasing.fabrpg.skills.abilities.AbilityDefinition;
import mc.blasing.fabrpg.skills.abilities.AbilityFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AbilitiesConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("abilities.json");

    public List<AbilityDefinition> abilities = new ArrayList<>();

    public static AbilitiesConfig load() {
        AbilitiesConfig config = new AbilitiesConfig();

        ConfigManager.ensureConfigFile("abilities.json", getDefaultAbilitiesConfig());

        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                Type type = new TypeToken<AbilitiesWrapper>() {}.getType();
                AbilitiesWrapper wrapper = ConfigManager.getGson().fromJson(json, type);
                config.abilities = wrapper.abilities;

                // Normalize activation types
                for (AbilityDefinition ability : config.abilities) {
                    if (ability.getActivation() != null) {
                        String activationType = ability.getActivation().getType();
                        if (activationType != null && activationType.equalsIgnoreCase("BLOCK_BREAK")) {
                            ability.getActivation().type = "BREAK_BLOCK";
                            Fabrpg.LOGGER.info("Normalized activation type for ability '{}' from '{}' to 'BREAK_BLOCK'",
                                    ability.getId(), activationType);
                        }
                    }
                }
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Error loading abilities config file", e);
                config.setDefaults();
            }
        } else {
            config.setDefaults();
        }

        config.save();
        return config;
    }

    public List<Ability> getAbilitiesForSkill(String skillId) {
        return abilities.stream()
                .filter(abilityDef -> abilityDef.getActivation() != null &&
                        "BREAK_BLOCK".equalsIgnoreCase(abilityDef.getActivation().getType()) &&
                        (skillId.equals("mining") || skillId.equals("woodcutting")))
                .map(AbilityFactory::createAbility)
                .collect(Collectors.toList());
    }

    private void setDefaults() {
        abilities.add(new AbilityDefinition("double_drop", "Double Drop", "Doubles the drops from mining or woodcutting", 1));
        abilities.add(new AbilityDefinition("instant_mine", "Instant Mine", "Instantly mines a block with a single hit", 1));
    }

    public void save() {
        try {
            AbilitiesWrapper wrapper = new AbilitiesWrapper();
            wrapper.abilities = this.abilities;
            String json = ConfigManager.getGson().toJson(wrapper);
            Files.writeString(CONFIG_PATH, json);
            Fabrpg.LOGGER.info("Saved abilities config with normalized activation types");
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Error saving abilities config file", e);
        }
    }

    private static String getDefaultAbilitiesConfig() {
        return """
                {
                   "abilities": [
                     {
                       "id": "vein_miner",
                       "name": "Vein Miner",
                       "description": "Mine entire veins of ore at once",
                       "requiredLevel": 10,
                       "cooldown": 60,
                       "activation": {
                         "type": "BREAK_BLOCK",
                         "blocks": ["IRON_ORE", "GOLD_ORE", "DIAMOND_ORE"],
                         "toolTypes": ["PICKAXE"]
                       }
                     },
                     {
                       "id": "double_drop",
                       "name": "Double Drop",
                       "description": "Chance to get double drops when mining ores",
                       "requiredLevel": 15,
                       "passive": true,
                       "chanceFormula": "0.01 * level",
                       "activation": {
                         "type": "BREAK_BLOCK",
                         "blocks": ["IRON_ORE", "GOLD_ORE", "DIAMOND_ORE"]
                       }
                     },
                     {
                       "id": "tree_feller",
                       "name": "Tree Feller",
                       "description": "Cut down entire trees at once",
                       "requiredLevel": 15,
                       "cooldown": 120,
                       "activation": {
                         "type": "BREAK_BLOCK",
                         "blocks": ["OAK_LOG", "BIRCH_LOG"],
                         "toolTypes": ["AXE"]
                       }
                     }
                   ]
                 }
                """;
    }

    private static class AbilitiesWrapper {
        List<AbilityDefinition> abilities;
    }
}