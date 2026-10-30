package com.edgerunners.cyber;

import com.edgerunners.item.CyberwareItem;
import com.edgerunners.network.CyberwareSyncPayload;
import com.edgerunners.registry.ModAttachments;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public final class Cyberware {
    private Cyberware() {}

    public static final int MAX_CAPACITY = 56;

    public enum Result {
        OK("installed"), WRONG_SLOT("wrong_slot"), OCCUPIED("slot_full"),
        NO_CAPACITY("no_capacity"), DUPLICATE("already_installed"), MISSING("missing_item");

        public final String msgKey;
        Result(String k) { this.msgKey = k; }
    }

    public static CyberwareData get(Player p) { return p.getData(ModAttachments.CYBERWARE); }

    public static boolean has(Player p, Item item) { return get(p).has(item); }

    public static Result install(ServerPlayer sp, int idx, CyberwareItem ci, ItemStack source) {
        CyberwareData d = get(sp);
        if (idx < 0 || idx >= CyberwareData.SIZE || CyberSlot.fromIndex(idx) != ci.slot()) return Result.WRONG_SLOT;
        if (!d.isEmpty(idx)) return Result.OCCUPIED;
        if (d.has(ci)) return Result.DUPLICATE;
        if (d.used() + ci.cost() > MAX_CAPACITY) return Result.NO_CAPACITY;

        ItemStack src = source != null ? source : findInInventory(sp, ci);
        if (src == null || src.isEmpty()) return Result.MISSING;

        src.shrink(1);
        d.set(idx, BuiltInRegistries.ITEM.getKey(ci).toString());
        sp.level().playSound(null, sp.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1f, 1.4f);
        sync(sp);
        return Result.OK;
    }

    public static void quickInstall(ServerPlayer sp, ItemStack stack, CyberwareItem ci) {
        CyberwareData d = get(sp);
        int idx = -1;
        for (int i = ci.slot().offset(); i < ci.slot().offset() + ci.slot().count; i++)
            if (d.isEmpty(i)) { idx = i; break; }
        Result r = idx < 0 ? Result.OCCUPIED : install(sp, idx, ci, stack);
        message(sp, r, ci);
    }

    public static void installFromGui(ServerPlayer sp, int idx, String itemId) {
        net.minecraft.resources.ResourceLocation rl = net.minecraft.resources.ResourceLocation.tryParse(itemId);
        if (rl == null) return;
        Item it = BuiltInRegistries.ITEM.getOptional(rl).orElse(null);
        if (it instanceof CyberwareItem ci) message(sp, install(sp, idx, ci, null), ci);
    }

    public static void remove(ServerPlayer sp, int idx) {
        CyberwareData d = get(sp);
        if (idx < 0 || idx >= CyberwareData.SIZE) return;
        Item it = d.item(idx);
        if (it == null) return;
        d.set(idx, "");
        if (it instanceof CyberwareItem ci) ci.onRemoved(sp);
        ItemStack back = new ItemStack(it);
        if (!sp.getInventory().add(back)) sp.drop(back, false);
        sp.level().playSound(null, sp.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.7f, 1.6f);
        sync(sp);
    }

    public static void tick(ServerPlayer sp) {
        CyberwareData d = get(sp);
        for (int i = 0; i < CyberwareData.SIZE; i++) {
            CyberwareItem ci = d.implant(i);
            if (ci != null) ci.onTick(sp);
        }
    }

    public static CyberwareSyncPayload payloadFor(Player p) {
        return new CyberwareSyncPayload(p.getId(), get(p).toList());
    }

    public static void sync(ServerPlayer sp) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(sp, payloadFor(sp));
    }

    private static ItemStack findInInventory(ServerPlayer sp, Item item) {
        Inventory inv = sp.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (s.is(item)) return s;
        }
        return null;
    }

    private static void message(ServerPlayer sp, Result r, CyberwareItem ci) {
        Component c = r == Result.OK
                ? Component.translatable("msg.edgerunners.installed", ci.getDescription())
                : Component.translatable("msg.edgerunners." + r.msgKey);
        sp.displayClientMessage(c, true);
    }
}
