package com.edgerunners.client;

import com.edgerunners.Edgerunners;
import com.edgerunners.cyber.Cyberware;
import com.edgerunners.registry.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/** Рисует Сэндевистан на спине (привязан к кости body), если он установлен в киберварях. */
public class SandevistanLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(Edgerunners.MODID, "textures/entity/sandevistan.png");

    public SandevistanLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) { super(parent); }

    @Override
    public void render(PoseStack ps, MultiBufferSource buf, int light, AbstractClientPlayer pl, float limbSwing,
                       float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (pl.isInvisible() || !Cyberware.has(pl, ModItems.SANDEVISTAN.get())) return;
        VertexConsumer vc = buf.getBuffer(RenderType.entityCutoutNoCull(TEX));
        ps.pushPose();
        getParentModel().body.translateAndRotate(ps);
        PoseStack.Pose pose = ps.last();
        float[] d = SandevistanMesh.D;
        for (int f = 0; f + 23 < d.length; f += 24) {
            int l = d[f + 23] > 0.5f ? LightTexture.FULL_BRIGHT : light;
            for (int v = 0; v < 4; v++) {
                int o = f + v * 5;
                vc.addVertex(pose, d[o] / 16f, d[o + 1] / 16f, d[o + 2] / 16f)
                        .setColor(255, 255, 255, 255).setUv(d[o + 3], d[o + 4])
                        .setOverlay(OverlayTexture.NO_OVERLAY).setLight(l)
                        .setNormal(pose, d[f + 20], d[f + 21], d[f + 22]);
            }
        }
        ps.popPose();
    }
}
