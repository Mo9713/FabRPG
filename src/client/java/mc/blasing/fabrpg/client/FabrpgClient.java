package mc.blasing.fabrpg.client;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.gui.SkillTreeScreen;
import mc.blasing.fabrpg.skills.SkillTreeManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.networking.v1.ClientPlayNetworking.PlayChannelHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class FabrpgClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register client-side packet handler
        ClientPlayNetworking.registerGlobalReceiver(Identifier.of(Fabrpg.MOD_ID, "open_skill_tree"),
                (MinecraftClient client, ClientPlayNetworking.PlayChannelHandler.Context context, net.minecraft.network.PacketByteBuf buf, net.fabricmc.fabric.api.networking.v1.PacketSender responseSender) -> {
                    String skillTreeId = buf.readString();
                    client.execute(() ->
                            client.setScreen(new SkillTreeScreen(client.currentScreen, SkillTreeManager.getSkillTree(skillTreeId)))
                    );
                }
        );
    }
}