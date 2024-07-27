package mc.blasing.fabrpg.skills.abilities;

import java.util.ArrayList;
import java.util.List;

public class AbilityDefinition {
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    private List<String> skills;
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    private int requiredLevel;
    public int getRequiredLevel() { return requiredLevel; }
    public void setRequiredLevel(int requiredLevel) { this.requiredLevel = requiredLevel; }

    private boolean passive;
    public boolean isPassive() { return passive; }
    public void setPassive(boolean passive) { this.passive = passive; }

    private ActivationType activationType;
    public ActivationType getActivationType() { return activationType; }
    public void setActivationType(ActivationType activationType) { this.activationType = activationType; }

    private List<String> allowedTools;
    public List<String> getAllowedTools() { return allowedTools; }
    public void setAllowedTools(List<String> allowedTools) { this.allowedTools = allowedTools; }

    private int cooldown;
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown; }

    private int duration;
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    private String chanceFormula;
    public String getChanceFormula() { return chanceFormula; }
    public void setChanceFormula(String chanceFormula) { this.chanceFormula = chanceFormula; }

    private Messages messages;
    public Messages getMessages() { return messages; }
    public void setMessages(Messages messages) { this.messages = messages; }

    private TriggerCondition triggerCondition;
    public TriggerCondition getTriggerCondition() { return triggerCondition; }
    public void setTriggerCondition(TriggerCondition triggerCondition) { this.triggerCondition = triggerCondition; }

    private int order;
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    private int maxBlocksBroken;
    public int getMaxBlocksBroken() { return maxBlocksBroken; }
    public void setMaxBlocksBroken(int maxBlocksBroken) { this.maxBlocksBroken = maxBlocksBroken; }

    private boolean stackable;
    public boolean isStackable() { return stackable; }
    public void setStackable(boolean stackable) { this.stackable = stackable; }

    private boolean cancelable;
    public boolean isCancelable() { return cancelable; }
    public void setCancelable(boolean cancelable) { this.cancelable = cancelable; }

    private String consumeItem;
    public String getConsumeItem() { return consumeItem; }
    public void setConsumeItem(String consumeItem) { this.consumeItem = consumeItem; }

    private int energyCost;
    public int getEnergyCost() { return energyCost; }
    public void setEnergyCost(int energyCost) { this.energyCost = energyCost; }

    public AbilityDefinition(String id, String name, String description, List<String> skills,
                             int requiredLevel, boolean passive, ActivationType activationType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.skills = skills;
        this.requiredLevel = requiredLevel;
        this.passive = passive;
        this.activationType = activationType;
    }

    public enum ActivationType {
        KEY_HELD,
        RIGHT_CLICK,
        PASSIVE
    }

    public static class Messages {
        private String preparation;
        public String getPreparation() { return preparation; }
        public void setPreparation(String preparation) { this.preparation = preparation; }

        private String activation;
        public String getActivation() { return activation; }
        public void setActivation(String activation) { this.activation = activation; }

        private String deactivation;
        public String getDeactivation() { return deactivation; }
        public void setDeactivation(String deactivation) { this.deactivation = deactivation; }

        private String onCooldown;
        public String getOnCooldown() { return onCooldown; }
        public void setOnCooldown(String onCooldown) { this.onCooldown = onCooldown; }
    }

    public static class TriggerCondition {
        private String type;
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        private List<String> blocks;
        public List<String> getBlocks() { return blocks; }
        public void setBlocks(List<String> blocks) { this.blocks = blocks; }

        private List<String> entities;
        public List<String> getEntities() { return entities; }
        public void setEntities(List<String> entities) { this.entities = entities; }

        private List<String> toolTypes;
        public List<String> getToolTypes() { return toolTypes; }
        public void setToolTypes(List<String> toolTypes) { this.toolTypes = toolTypes; }
    }
}