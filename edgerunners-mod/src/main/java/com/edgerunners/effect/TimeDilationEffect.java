package com.edgerunners.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TimeDilationEffect extends MobEffect {
    public TimeDilationEffect() {
        super(MobEffectCategory.HARMFUL, 0x5533AA);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, SandevistanEffect.rl("time_dilation_slow"), -0.85, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override public boolean shouldApplyEffectTickThisTick(int duration, int amp) { return true; }

    @Override
    public boolean applyEffectTick(LivingEntity e, int amp) {
        e.setDeltaMovement(e.getDeltaMovement().multiply(0.3, 0.9, 0.3));
        return true;
    }
}
