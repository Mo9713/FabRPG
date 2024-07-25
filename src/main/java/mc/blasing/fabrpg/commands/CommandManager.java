package mc.blasing.fabrpg.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandManager {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SkillCommand.register(dispatcher);
        });
    }
}