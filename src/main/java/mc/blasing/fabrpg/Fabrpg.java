package mc.blasing.fabrpg;

import mc.blasing.fabrpg.commands.SkillTreeCommand;
import mc.blasing.fabrpg.commands.SkillCommand;
import mc.blasing.fabrpg.config.ConfigManager;
import mc.blasing.fabrpg.skills.SkillManager;
import mc.blasing.fabrpg.events.SkillEventListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fabrpg implements ModInitializer {
    public static final String MOD_ID = "fabrpg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing FabRPG");

        // Load configuration
        ConfigManager.loadAll();

        // Initialize SkillManager
        SkillManager.initialize();

        // Register commands
        registerCommands();

        // Register server lifecycle events
        registerServerEvents();

        // Register skill event listeners
        registerSkillEvents();

        LOGGER.info("FabRPG initialization complete");
    }

    private void registerServerEvents() {
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
    }

    private void onServerStarting(MinecraftServer server) {
        LOGGER.info("FabRPG is starting");
        // Add any server start logic here if needed
    }

    private void onServerStopping(MinecraftServer server) {
        LOGGER.info("FabRPG: Saving Data...");

        // Save all configurations
        ConfigManager.saveAll();

        // Add any additional cleanup or saving logic here if needed

        LOGGER.info("FabRPG: Save Complete");
    }

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SkillTreeCommand.register(dispatcher);
            SkillCommand.register(dispatcher);
        });
    }

    private void registerSkillEvents() {
        SkillEventListener.register();
    }
}