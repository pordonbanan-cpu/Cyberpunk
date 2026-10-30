package com.edgerunners;

import com.edgerunners.cyber.Cyberware;
import com.edgerunners.item.SandevistanItem;
import com.edgerunners.network.ActivatePayload;
import com.edgerunners.network.CyberActionPayload;
import com.edgerunners.network.CyberwareSyncPayload;
import com.edgerunners.registry.ModAttachments;
import com.edgerunners.registry.ModEffects;
import com.edgerunners.registry.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(Edgerunners.MODID)
public class Edgerunners {
    public static final String MODID = "edgerunners";

    public Edgerunners(IEventBus modBus) {
        ModEffects.EFFECTS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModItems.TABS.register(modBus);
        ModAttachments.ATTACHMENTS.register(modBus);
        modBus.addListener(Edgerunners::payloads);
    }

    private static void payloads(RegisterPayloadHandlersEvent e) {
        e.registrar("1")
                .playToServer(ActivatePayload.TYPE, ActivatePayload.CODEC,
                        (p, ctx) -> ctx.enqueueWork(() -> {
                            if (ctx.player() instanceof ServerPlayer sp) SandevistanItem.toggle(sp);
                        }))
                .playToServer(CyberActionPayload.TYPE, CyberActionPayload.CODEC,
                        (p, ctx) -> ctx.enqueueWork(() -> {
                            if (!(ctx.player() instanceof ServerPlayer sp)) return;
                            if (p.install()) Cyberware.installFromGui(sp, p.index(), p.itemId());
                            else Cyberware.remove(sp, p.index());
                        }))
                .playToClient(CyberwareSyncPayload.TYPE, CyberwareSyncPayload.CODEC,
                        (p, ctx) -> ctx.enqueueWork(() -> {
                            Player me = ctx.player();
                            Entity target = p.entityId() == me.getId() ? me : me.level().getEntity(p.entityId());
                            if (target instanceof Player tp) Cyberware.get(tp).replaceWith(p.ids());
                        }));
    }
}
