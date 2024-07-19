package mc.blasing.fabrpg.network;

import mc.blasing.fabrpg.Fabrpg;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public record OpenSkillTreePacket() implements CustomPayload {
    public static final Id<OpenSkillTreePacket> ID = new Id<>(Identifier.of(Fabrpg.MOD_ID, "open_skill_tree"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new OpenSkillTreePacket());
    }
}