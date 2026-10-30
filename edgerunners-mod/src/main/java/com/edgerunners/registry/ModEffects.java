package com.edgerunners.registry;

import com.edgerunners.Edgerunners;
import com.edgerunners.effect.SandevistanEffect;
import com.edgerunners.effect.TimeDilationEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Edgerunners.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> SANDEVISTAN =
            EFFECTS.register("sandevistan", SandevistanEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> TIME_DILATION =
            EFFECTS.register("time_dilation", TimeDilationEffect::new);
}
