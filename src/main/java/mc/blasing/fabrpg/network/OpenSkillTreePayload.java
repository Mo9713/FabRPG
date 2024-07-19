package mc.blasing.fabrpg.network;

import mc.blasing.fabrpg.Fabrpg;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpenSkillTreePayload() implements CustomPayload {
    public static final CustomPayload.Id<OpenSkillTreePayload> ID = new CustomPayload.Id<>(Identifier.of(Fabrpg.MOD_ID, "open_skill_tree"));

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}

//fix errors