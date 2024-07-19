package mc.blasing.fabrpg.client;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.client.gui.SkillTreeScreen;
import mc.blasing.fabrpg.skills.SkillTree;
import mc.blasing.fabrpg.skills.SkillManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodec;

public class FabrpgClient implements ClientModInitializer {
    public static final CustomPayload.Id<OpenSkillTreePayload> OPEN_SKILL_TREE_PACKET_ID = new CustomPayload.Id<>(Identifier.of(Fabrpg.MOD_ID, "open_skill_tree"));

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(OPEN_SKILL_TREE_PACKET_ID, (payload, context) ->
                context.client().execute(this::openSkillTreeScreen)
        );
    }

    private void openSkillTreeScreen() {
        MinecraftClient client = MinecraftClient.getInstance();
        SkillTree skillTree = SkillManager.getClientSkillTree();
        client.setScreen(new SkillTreeScreen(client.currentScreen, skillTree));
    }

    public static class OpenSkillTreePayload implements CustomPayload {
        public static final OpenSkillTreePayload INSTANCE = new OpenSkillTreePayload();

        private OpenSkillTreePayload() {}

        @SuppressWarnings("unused")
        public static final PacketCodec<PacketByteBuf, OpenSkillTreePayload> CODEC = PacketCodec.unit(() -> INSTANCE);

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return OPEN_SKILL_TREE_PACKET_ID;
        }
    }
}