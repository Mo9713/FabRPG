package mc.blasing.fabrpg.events;

import mc.blasing.fabrpg.Fabrpg;
import mc.blasing.fabrpg.skills.CombatSkill;
import mc.blasing.fabrpg.skills.Skill;
import mc.blasing.fabrpg.skills.SkillManager;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class SkillEvents {
    public static void register() {
        registerCombatEvents();
        registerBlockBreakEvent();
        registerEntityKillEvent();
    }

    private static void registerCombatEvents() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player instanceof ServerPlayerEntity && entity instanceof LivingEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                LivingEntity target = (LivingEntity) entity;

                if (target.getHealth() <= 0 || target.isDead()) {
                    CombatSkill combatSkill = (CombatSkill) SkillManager.getOrCreateSkill(serverPlayer, "combat");
                    combatSkill.handleEntityKill(entity.getType().toString());
                }
            }
            return ActionResult.PASS;
        });
    }

    private static void registerBlockBreakEvent() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                Block block = state.getBlock();

                Map<String, Object> context = new HashMap<>();
                context.put("type", "BLOCK_BREAK");
                context.put("block", block.getTranslationKey());

                for (Skill skill : SkillManager.getPlayerSkills(serverPlayer).values()) {
                    skill.handleAction(context);
                }
            }
        });
    }

    private static void registerEntityKillEvent() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player instanceof ServerPlayerEntity && entity instanceof LivingEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                LivingEntity livingEntity = (LivingEntity) entity;

                if (livingEntity.getHealth() <= 0 || livingEntity.isDead()) {
                    Map<String, Object> context = new HashMap<>();
                    context.put("type", "ENTITY_KILL");
                    context.put("entity", entity.getType().toString());

                    for (Skill skill : SkillManager.getPlayerSkills(serverPlayer).values()) {
                        skill.handleAction(context);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}