package mc.blasing.fabrpg.client;

import mc.blasing.fabrpg.client.gui.SkillTreeScreen;
import mc.blasing.fabrpg.skills.SkillManager;
import mc.blasing.fabrpg.network.OpenSkillTreePacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class FabrpgClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(OpenSkillTreePacket.ID,
                (packet, context) -> {
                    context.client().execute(this::openSkillTreeScreen);
                }
        );
    }

    private void openSkillTreeScreen() {
        MinecraftClient client = MinecraftClient.getInstance();
        client.setScreen(new SkillTreeScreen(client.currentScreen, SkillManager.getClientSkillTree()));
    }
}