package mc.blasing.fabrpg;

import mc.blasing.fabrpg.commands.SkillTreeCommand;
import mc.blasing.fabrpg.config.ConfigManager;
import mc.blasing.fabrpg.skills.SkillManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fabrpg implements ModInitializer {
    public static final String MOD_ID = "fabrpg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private MinecraftServer server;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing FabRPG");

        // Load configuration
        ConfigManager.loadConfig();

        // Initialize SkillManager
        SkillManager.initialize();

        // Register server lifecycle events
        registerServerEvents();

        // Register commands
        registerCommands();

        LOGGER.info("FabRPG initialization complete");
    }

    private void registerServerEvents() {
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
    }

    private void onServerStarting(MinecraftServer server) {
        this.server = server;
        // Perform any necessary actions when the server is starting
    }

    private void onServerStopping(MinecraftServer server) {
        // Perform any necessary cleanup when the server is stopping
        ConfigManager.loadConfig(); // This will save the config if needed
    }

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SkillTreeCommand.register(dispatcher);
        });
    }
}