package mc.blasing.fabrpg.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AbilitiesConfig {
    private static final Path CONFIG_PATH = ConfigManager.getConfigDir().resolve("abilities.json");
    public List<AbilityDefinition> abilities = new ArrayList<>();

    public static AbilitiesConfig load() {
        AbilitiesConfig config = new AbilitiesConfig();

        ConfigManager.ensureConfigFile("abilities.json", ConfigManager.getDefaultAbilitiesConfig());

        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                JsonObject jsonObject = ConfigManager.getGson().fromJson(json, JsonObject.class);

                if (jsonObject.has("abilities")) {
                    JsonArray abilitiesArray = jsonObject.getAsJsonArray("abilities");

                    for (JsonElement element : abilitiesArray) {
                        JsonObject abilityObject = element.getAsJsonObject();
                        AbilityDefinition ability = parseAbilityDefinition(abilityObject);
                        config.abilities.add(ability);
                    }
                } else {
                    Fabrpg.LOGGER.error("No 'abilities' object found in JSON. Using default abilities.");
                    config.setDefaults();
                }
            } catch (JsonSyntaxException e) {
                Fabrpg.LOGGER.error("Error parsing abilities config: ", e);
                config.setDefaults();
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Error loading abilities config", e);
                config.setDefaults();
            }
        } else {
            config.setDefaults();
        }

        return config;
    }

    private static AbilityDefinition parseAbilityDefinition(JsonObject jsonObject) {
        String id = jsonObject.get("id").getAsString();
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        List<String> skills = ConfigManager.getGson().fromJson(jsonObject.get("skills"), new TypeToken<List<String>>(){}.getType());
        int requiredLevel = jsonObject.get("requiredLevel").getAsInt();
        boolean passive = jsonObject.has("passive") && jsonObject.get("passive").getAsBoolean();
        AbilityDefinition.ActivationType activationType = AbilityDefinition.ActivationType.valueOf(jsonObject.get("activationType").getAsString());

        AbilityDefinition ability = new AbilityDefinition(id, name, description, skills, requiredLevel, passive, activationType);

        // Set other fields if they exist in the JSON
        if (jsonObject.has("allowedTools")) {
            ability.setAllowedTools(ConfigManager.getGson().fromJson(jsonObject.get("allowedTools"), new TypeToken<List<String>>(){}.getType()));
        }
        if (jsonObject.has("cooldown")) {
            ability.setCooldown(jsonObject.get("cooldown").getAsInt());
        }
        if (jsonObject.has("duration")) {
            ability.setDuration(jsonObject.get("duration").getAsInt());
        }
        if (jsonObject.has("chanceFormula")) {
            ability.setChanceFormula(jsonObject.get("chanceFormula").getAsString());
        }
        if (jsonObject.has("messages")) {
            ability.setMessages(ConfigManager.getGson().fromJson(jsonObject.get("messages"), AbilityDefinition.Messages.class));
        }
        if (jsonObject.has("triggerCondition")) {
            ability.setTriggerCondition(ConfigManager.getGson().fromJson(jsonObject.get("triggerCondition"), AbilityDefinition.TriggerCondition.class));
        }
        if (jsonObject.has("order")) {
            ability.setOrder(jsonObject.get("order").getAsInt());
        }
        if (jsonObject.has("maxBlocksBroken")) {
            ability.setMaxBlocksBroken(jsonObject.get("maxBlocksBroken").getAsInt());
        }
        if (jsonObject.has("stackable")) {
            ability.setStackable(jsonObject.has("stackable") && jsonObject.get("stackable").getAsBoolean());
        }
        if (jsonObject.has("cancelable")) {
            ability.setCancelable(jsonObject.has("cancelable") && jsonObject.get("cancelable").getAsBoolean());
        }
        if (jsonObject.has("consumeItem")) {
            ability.setConsumeItem(jsonObject.get("consumeItem").getAsString());
        }
        if (jsonObject.has("energyCost")) {
            ability.setEnergyCost(jsonObject.get("energyCost").getAsInt());
        }

        return ability;
    }

    public List<Ability> getAbilitiesForSkill(String skillId) {
        return abilities.stream()
                .filter(abilityDef -> abilityDef.getActivationType() != null &&
                        abilityDef.getActivationType().name().equals("BREAK_BLOCK") &&
                        (skillId.equals("mining") || skillId.equals("woodcutting")))
                .map(AbilityFactory::createAbility)
                .collect(Collectors.toList());
    }

    private void setDefaults() {
        abilities.add(new AbilityDefinition("double_drop", "Double Drop", "Doubles the drops from mining or woodcutting",
                Arrays.asList("mining", "woodcutting"), 1, true, AbilityDefinition.ActivationType.PASSIVE));
        abilities.add(new AbilityDefinition("instant_mine", "Instant Mine", "Instantly mines a block with a single hit",
                Arrays.asList("mining"), 1, false, AbilityDefinition.ActivationType.RIGHT_CLICK));
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

    private static class AbilitiesWrapper {
        List<AbilityDefinition> abilities;
    }
}
