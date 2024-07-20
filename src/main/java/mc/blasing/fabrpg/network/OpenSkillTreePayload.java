package mc.blasing.fabrpg.network;

import mc.blasing.fabrpg.Fabrpg;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public record OpenSkillTreePayload() {
    public static final Identifier ID = Identifier.of(Fabrpg.MOD_ID, "open_skill_tree");

    public static OpenSkillTreePayload read(PacketByteBuf buf) {
        return new OpenSkillTreePayload();
    }

    public static void write(PacketByteBuf buf, OpenSkillTreePayload payload) {
        // No data to write
    }
}