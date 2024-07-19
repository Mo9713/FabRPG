package mc.blasing.fabrpg.network;

import mc.blasing.fabrpg.Fabrpg;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.network.packet.CustomPayload;

public record OpenSkillTreePacket() implements CustomPayload {
    public static final Id<OpenSkillTreePacket> ID = new Id<>(Identifier.of(Fabrpg.MOD_ID, "open_skill_tree"));

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new OpenSkillTreePacket());
    }
}