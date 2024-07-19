package mc.blasing.fabrpg.skills.unlock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface UnlockCondition {
    boolean canUnlock(PlayerEntity player);
    String getType();
}