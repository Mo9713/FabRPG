package mc.blasing.fabrpg.config;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.SkillDefinition;
import mc.blasing.fabrpg.skills.abilities.AbilityDefinition;
import mc.blasing.fabrpg.skills.actions.ActionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigValidator {

    private static boolean hasErrors = false;
        public static boolean hasErrors() {
        return hasErrors;
    }

    public static void validateConfigs() {
        validateFabRPGConfig();
        validateSkillsConfig();
        validateActionsConfig();
        validateAbilitiesConfig();
    }

    private static void validateFabRPGConfig() {
        FabRPGConfig config = ConfigManager.mainConfig;
        List<String> errors = new ArrayList<>();

        if (config.getMaxLevel() <= 0) {
            errors.add("Max level must be greater than 0");
        }
        if (config.getSaveStatsTimer() < 0) {
            errors.add("Save stats timer cannot be negative");
        }
        // Add more validations as needed

        logErrors("FabRPG Config", errors);
    }

    private static void validateSkillsConfig() {
        Map<String, SkillDefinition> skills = ConfigManager.skillsConfig.skills;
        List<String> errors = new ArrayList<>();

        for (Map.Entry<String, SkillDefinition> entry : skills.entrySet()) {
            String skillId = entry.getKey();
            SkillDefinition skill = entry.getValue();

            if (!skillId.equals(skill.getId())) {
                errors.add("Skill ID mismatch for " + skillId);
            }
            if (skill.getName() == null || skill.getName().isEmpty()) {
                errors.add("Skill name is missing for " + skillId);
            }
            // Add more skill-specific validations
        }

        logErrors("Skills Config", errors);
    }

    private static void validateActionsConfig() {
        List<ActionDefinition> actions = ConfigManager.actionsConfig.actions;
        List<String> errors = new ArrayList<>();

        for (ActionDefinition action : actions) {
            if (action.getId() == null || action.getId().isEmpty()) {
                errors.add("Action ID is missing");
            }
            if (action.getType() == null) {
                errors.add("Action type is missing for " + action.getId());
            }
            // Add more action-specific validations
        }

        logErrors("Actions Config", errors);
    }

    private static void validateAbilitiesConfig() {
        List<AbilityDefinition> abilities = ConfigManager.abilitiesConfig.abilities;
        List<String> errors = new ArrayList<>();

        for (AbilityDefinition ability : abilities) {
            if (ability.getId() == null || ability.getId().isEmpty()) {
                errors.add("Ability ID is missing");
            }
            if (ability.getName() == null || ability.getName().isEmpty()) {
                errors.add("Ability name is missing for " + ability.getId());
            }
            if (ability.getRequiredLevel() < 0) {
                errors.add("Required level cannot be negative for " + ability.getId());
            }
            // Add more ability-specific validations
        }

        logErrors("Abilities Config", errors);
    }

    private static void logErrors(String configName, List<String> errors) {
        if (!errors.isEmpty()) {
            hasErrors = true;
            Fabrpg.LOGGER.error("{}:", "Validation errors found in " + configName);
            for (String error : errors) {
                Fabrpg.LOGGER.error("  - {}", error);
            }
        }
    }
}