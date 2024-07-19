package mc.blasing.fabrpg.client;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.gui.SkillTreeScreen;
import mc.blasing.fabrpg.skills.SkillTreeManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class FabrpgClient implements ClientModInitializer {
    private static final Identifier OPEN_SKILL_TREE_PACKET_ID = Identifier.of(Fabrpg.MOD_ID, "open_skill_tree");

    @Override
    public void onInitializeClient() {
        // Register client-side packet handler
        ClientPlayNetworking.registerGlobalReceiver(OPEN_SKILL_TREE_PACKET_ID, (client, handler, buf, responseSender) -> {
            // Read packet data on the network thread
            String skillTreeId = buf.readString(); // WHY DOESNT THIS WORK??!! :(

            // Schedule the task to run on the main client thread
            MinecraftClient.getInstance().execute(() -> {
                // This lambda is run on the render thread
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().setScreen(new SkillTreeScreen(MinecraftClient.getInstance().currentScreen, SkillTreeManager.getSkillTree(skillTreeId)));
                }
            });
        });
    }
}
