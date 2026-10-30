package com.edgerunners.effect;

import com.edgerunners.Edgerunners;
import com.edgerunners.registry.ModEffects;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.joml.Vector3f;

/** Эффект на игроке: скорость, скорость атаки, урон + замедление времени вокруг. */
public class SandevistanEffect extends MobEffect {
    public SandevistanEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00F0FF);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, rl("sandevistan_speed"), 0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, rl("sandevistan_atkspd"), 1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, rl("sandevistan_dmg"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    static ResourceLocation rl(String p) { return ResourceLocation.fromNamespaceAndPath(Edgerunners.MODID, p); }

    @Override public boolean shouldApplyEffectTickThisTick(int duration, int amp) { return true; }

    @Override
    public boolean applyEffectTick(LivingEntity e, int amp) {
        if (e.level() instanceof ServerLevel sl) {
            for (LivingEntity t : sl.getEntitiesOfClass(LivingEntity.class, e.getBoundingBox().inflate(20), x -> x != e && x.isAlive()))
                t.addEffect(new MobEffectInstance(ModEffects.TIME_DILATION, 4, 0, false, false));
            // голубой «след» (афтеимидж-частицы)
            sl.sendParticles(new DustParticleOptions(new Vector3f(0f, 0.94f, 1f), 1.6f),
                    e.getX(), e.getY() + 1, e.getZ(), 6, 0.3, 0.6, 0.3, 0);
        }
        return true;
    }
}
