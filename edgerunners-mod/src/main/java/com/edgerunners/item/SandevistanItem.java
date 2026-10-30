package com.edgerunners.item;

import com.edgerunners.cyber.CyberSlot;
import com.edgerunners.cyber.Cyberware;
import com.edgerunners.registry.ModEffects;
import com.edgerunners.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;

public class SandevistanItem extends CyberwareItem {
    public static final int DURATION = 400;

    public SandevistanItem(Properties props) { super(props, CyberSlot.OPERATING_SYSTEM, 20); }

    @Override
    public void onRemoved(ServerPlayer p) { p.removeEffect(ModEffects.SANDEVISTAN); }

    public static void toggle(ServerPlayer p) {
        if (!Cyberware.has(p, ModItems.SANDEVISTAN.get())) {
            p.displayClientMessage(Component.translatable("msg.edgerunners.need_implant"), true);
            return;
        }
        if (p.hasEffect(ModEffects.SANDEVISTAN)) {
            p.removeEffect(ModEffects.SANDEVISTAN);
            p.level().playSound(null, p.blockPosition(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1f, 2f);
        } else {
            p.addEffect(new MobEffectInstance(ModEffects.SANDEVISTAN, DURATION, 0, false, true, true));
            p.level().playSound(null, p.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1f, 2f);
        }
    }
}
