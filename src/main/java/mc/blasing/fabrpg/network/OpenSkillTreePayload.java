package mc.blasing.fabrpg.network;

import mc.blasing.fabrpg.Fabrpg;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpenSkillTreePayload() implements CustomPayload {
    public static final CustomPayload.Id<OpenSkillTreePayload> ID = new CustomPayload.Id<>(new Identifier(Fabrpg.MOD_ID, "open_skill_tree"));
    public static final PacketCodec<PacketByteBuf, OpenSkillTreePayload> CODEC = PacketCodec.unit(OpenSkillTreePayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}

//fix errors