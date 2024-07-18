package mc.blasing.fabrpg.events;

import mc.blasing.fabrpg.skills.Skill;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface SkillLevelUpCallback {
    Event<SkillLevelUpCallback> EVENT = EventFactory.createArrayBacked(SkillLevelUpCallback.class,
            (listeners) -> (player, skill, newLevel) -> {
                for (SkillLevelUpCallback listener : listeners) {
                    listener.onSkillLevelUp(player, skill, newLevel);
                }
            });

    void onSkillLevelUp(ServerPlayerEntity player, Skill skill, int newLevel);
}