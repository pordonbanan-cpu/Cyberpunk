package com.edgerunners.client;

import com.edgerunners.Edgerunners;
import com.edgerunners.registry.ModEffects;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = Edgerunners.MODID, value = Dist.CLIENT)
public class ClientGameEvents {
    @SubscribeEvent
    public static void tick(ClientTickEvent.Post e) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.hasEffect(ModEffects.SANDEVISTAN))
            Afterimages.capture(mc.player);
        else
            Afterimages.clear();
    }
}
