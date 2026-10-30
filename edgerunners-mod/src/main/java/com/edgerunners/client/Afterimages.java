package com.edgerunners.client;

import com.edgerunners.registry.ModEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EventBusSubscriber(modid = "edgerunners", value = Dist.CLIENT)
public class Afterimages {

    private static final List<Ghost> GHOSTS = new ArrayList<>();
    private static final int MAX_GHOSTS = 8;
    private static final int GHOST_LIFE = 12; // ticks

    private static class Ghost {
        final double x, y, z;
        final float yRot, xRot;
        final AbstractClientPlayer player;
        int age;

        Ghost(AbstractClientPlayer p) {
            this.player = p;
            this.x = p.getX();
            this.y = p.getY();
            this.z = p.getZ();
            this.yRot = p.getYRot();
            this.xRot = p.getXRot();
            this.age = 0;
        }
    }

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            GHOSTS.clear();
            return;
        }
        AbstractClientPlayer player = mc.player;
        MobEffectInstance eff = player.getEffect(ModEffects.SANDEVISTAN);
        if (eff != null) {
            if (GHOSTS.size() < MAX_GHOSTS) {
                GHOSTS.add(new Ghost(player));
            } else {
                // rotate
                GHOSTS.remove(0);
                GHOSTS.add(new Ghost(player));
            }
        }
        Iterator<Ghost> it = GHOSTS.iterator();
        while (it.hasNext()) {
            Ghost g = it.next();
            g.age++;
            if (g.age > GHOST_LIFE) it.remove();
        }
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        if (!(event.getEntity() instanceof AbstractClientPlayer player)) return;
        if (player != Minecraft.getInstance().player) return;
        if (GHOSTS.isEmpty()) return;

        PlayerRenderer renderer = (PlayerRenderer) event.getRenderer();
        PoseStack pose = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();
        float partial = event.getPartialTick();

        for (Ghost g : GHOSTS) {
            float alpha = 1.0f - (g.age + partial) / (float) GHOST_LIFE;
            if (alpha <= 0) continue;
            pose.pushPose();
            // translate relative to current player position
            double dx = g.x - player.getX();
            double dy = g.y - player.getY();
            double dz = g.z - player.getZ();
            pose.translate(dx, dy, dz);
            // simple tint via alpha on the model is hard without custom layer;
            // we still draw the model at ghost pose for trail effect
            renderer.render(player, g.yRot, partial, pose, buffer, event.getPackedLight());
            pose.popPose();
        }
    }
}
