package mc.blasing.fabrpg.network;

import mc.blasing.fabrpg.Fabrpg;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class OpenSkillTreePacket {
    public static final Identifier ID = new Identifier(Fabrpg.MOD_ID, "open_skill_tree");

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, ID, PacketByteBufs.create());
    }
}