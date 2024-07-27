package mc.blasing.fabrpg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mc.blasing.fabrpg.Fabrpg;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir().resolve("fabrpg");

    public static FabRPGConfig mainConfig;
    public static SkillsConfig skillsConfig;
    public static ActionsConfig actionsConfig;
    public static AbilitiesConfig abilitiesConfig;

    public static void loadAll() {
        createConfigDirectory();
        ensureConfigFile("fabrpg.json", getDefaultFabRPGConfig());
        ensureConfigFile("skills.json", getDefaultSkillsConfig());
        ensureConfigFile("actions.json", getDefaultActionsConfig());
        ensureConfigFile("abilities.json", getDefaultAbilitiesConfig());

        mainConfig = FabRPGConfig.load();
        skillsConfig = SkillsConfig.load();
        actionsConfig = ActionsConfig.load();
        abilitiesConfig = AbilitiesConfig.load();

        ConfigValidator.validateConfigs();
    }

    public static void saveAll() {
        mainConfig.save();
        skillsConfig.save();
        actionsConfig.save();
        abilitiesConfig.save();
    }

    private static void createConfigDirectory() {
        try {
            Files.createDirectories(CONFIG_DIR);
        } catch (IOException e) {
            Fabrpg.LOGGER.error("Failed to create config directory", e);
        }
    }

    public static void ensureConfigFile(String fileName, String defaultContent) {
        Path filePath = CONFIG_DIR.resolve(fileName);
        if (!Files.exists(filePath)) {
            try {
                Files.writeString(filePath, defaultContent);
                Fabrpg.LOGGER.info("Created default config file: {}", fileName);
            } catch (IOException e) {
                Fabrpg.LOGGER.error("Failed to create config file: {}", fileName, e);
            }
        }
    }

    public static String getDefaultFabRPGConfig() {
        return """
                {
                  "defaultLanguage": "en_us",
                  "forceLanguage": false,
                  "saveStatsTimer": 300,
                  "allowExplosions": true,
                  "allowBuild": true,
                  "allowPvP": true,
                  "allowHurtAnimals": true,
                  "maxLevel": 100,
                  "commands": {
                    "skill": "skill",
                    "skillTree": "skilltree"
                  },
                  "useMinecraftXP": true
                }
                """;
    }

    public static String getDefaultSkillsConfig() {
        return """
            {
              "skills": {
                "mining": {
                  "name": "Mining",
                  "description": "Increases mining speed and ore drops",
                  "actions": ["break_stone", "break_ore"],
                  "abilities": ["vein_miner", "double_drop"]
                },
                "woodcutting": {
                  "name": "Woodcutting",
                  "description": "Increases woodcutting speed and log drops",
                  "actions": ["chop_tree"],
                  "abilities": ["tree_feller"]
                }
              }
            }""";
    }

    public static String getDefaultActionsConfig() {
        return """
            [
              {
                "id": "break_stone",
                "type": "BREAK_BLOCK",
                "blocks": ["STONE", "COBBLESTONE"],
                "experience": 10
              },
              {
                "id": "break_ore",
                "type": "BREAK_BLOCK",
                "blocks": ["IRON_ORE", "GOLD_ORE", "DIAMOND_ORE"],
                "experience": 50
              },
              {
                "id": "touch_entity",
                "type": "ENTITY_TOUCH",
                "entities": ["COW", "SHEEP"],
                "excludedEntities": ["BETTERNETHER_NAGA"],
                "experience": 15,
                "proximityThreshold": 10.0
              }
            ]""";
    }

    public static String getDefaultAbilitiesConfig() {
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
            }""";
    }

    public static Gson getGson() {
        return GSON;
    }

    public static Path getConfigDir() {
        return CONFIG_DIR;
    }
}