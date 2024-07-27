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

        if (config.getMaxLevel() < -1 || config.getMaxLevel() == 0) {
            errors.add("Max level must be -1 (for no cap) or greater than 0. Current value: " + config.getMaxLevel());
        }
        if (config.getSaveStatsTimer() < 0) {
            errors.add("Save stats timer cannot be negative. Current value: " + config.getSaveStatsTimer());
        }
        if (config.getDefaultLanguage() == null || config.getDefaultLanguage().isEmpty()) {
            errors.add("Default language cannot be empty.");
        }
        if (config.getCommands().isEmpty()) {
            errors.add("No commands defined in the config.");
        } else {
            if (!config.getCommands().containsKey("skill")) {
                errors.add("Skill command is not defined in the config.");
            }
            if (!config.getCommands().containsKey("skillTree")) {
                errors.add("Skill tree command is not defined in the config.");
            }
        }

        logErrors("FabRPG Config", errors);
    }

    private static void validateSkillsConfig() {
        Map<String, SkillDefinition> skills = ConfigManager.skillsConfig.skills;
        List<String> errors = new ArrayList<>();

        for (Map.Entry<String, SkillDefinition> entry : skills.entrySet()) {
            String skillId = entry.getKey();
            SkillDefinition skill = entry.getValue();

            if (!skillId.equals(skill.getId())) {
                errors.add("Skill ID mismatch for " + skillId + ". ID in map: " + skillId + ", ID in skill: " + skill.getId());
            }
            if (skill.getName() == null || skill.getName().isEmpty()) {
                errors.add("Skill name is missing for " + skillId);
            }
            if (skill.getDescription() == null || skill.getDescription().isEmpty()) {
                errors.add("Skill description is missing for " + skillId);
            }
            if (skill.getMaxLevel() <= 0) {
                errors.add("Max level must be greater than 0 for skill " + skillId + ". Current value: " + skill.getMaxLevel());
            }
            if (skill.getXpMultiplier() <= 0) {
                errors.add("XP multiplier must be greater than 0 for skill " + skillId + ". Current value: " + skill.getXpMultiplier());
            }
            if (skill.getActionIds().isEmpty()) {
                errors.add("No actions defined for skill " + skillId);
            }
            if (skill.getAbilityIds().isEmpty()) {
                errors.add("No abilities defined for skill " + skillId);
            }
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
            if (action.getExperience() < 0) {
                errors.add("Experience cannot be negative for action " + action.getId() + ". Current value: " + action.getExperience());
            }
            if (action.getType().equals("BREAK_BLOCK") && (action.getBlocks() == null || action.getBlocks().isEmpty())) {
                errors.add("No blocks defined for BREAK_BLOCK action " + action.getId());
            }
            if (action.getType().equals("ENTITY_TOUCH") && (action.getEntities() == null || action.getEntities().isEmpty())) {
                errors.add("No entities defined for ENTITY_TOUCH action " + action.getId());
            }
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
            if (ability.getDescription() == null || ability.getDescription().isEmpty()) {
                errors.add("Ability description is missing for " + ability.getId());
            }
            if (ability.getRequiredLevel() < 0) {
                errors.add("Required level cannot be negative for " + ability.getId() + ". Current value: " + ability.getRequiredLevel());
            }
            if (!ability.isPassive() && ability.getCooldown() <= 0) {
                errors.add("Non-passive ability " + ability.getId() + " must have a cooldown greater than 0. Current value: " + ability.getCooldown());
            }
            if (ability.getActivationType() == null) {
                errors.add("Activation type is missing for ability " + ability.getId());
            }
            if (ability.getTriggerCondition() == null) {
                errors.add("Trigger condition is missing for ability " + ability.getId());
            }
        }

        logErrors("Abilities Config", errors);
    }

    private static void logErrors(String configName, List<String> errors) {
        if (!errors.isEmpty()) {
            hasErrors = true;
            Fabrpg.LOGGER.error("Validation errors found in {}:", configName);
            for (String error : errors) {
                Fabrpg.LOGGER.error("  - {}", error);
            }
        }
    }
}