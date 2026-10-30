package com.edgerunners.item;

import com.edgerunners.cyber.CyberSlot;
import com.edgerunners.cyber.Cyberware;
import com.edgerunners.cyber.CyberwareData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/** Базовый класс любого импланта: слот + стоимость ёмкости + хуки. */
public class CyberwareItem extends Item {
    private final CyberSlot slot;
    private final int capacityCost;

    public CyberwareItem(Properties props, CyberSlot slot, int capacityCost) {
        super(props);
        this.slot = slot;
        this.capacityCost = capacityCost;
    }

    public CyberSlot getSlot() { return slot; }
    public int getCapacityCost() { return capacityCost; }

    /** Вызывается каждый тик, пока имплант установлен. */
    public void onTick(Player player, ItemStack stack) {}

    /** Вызывается при установке. */
    public void onInstalled(Player player, ItemStack stack) {}

    /** Вызывается при снятии. */
    public void onRemoved(Player player, ItemStack stack) {}

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResultHolder.success(stack);
        CyberwareData data = Cyberware.get(player);
        if (data == null) return InteractionResultHolder.fail(stack);
        if (data.tryInstall(player, stack.copy())) {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            return InteractionResultHolder.success(stack);
        }
        player.displayClientMessage(Component.translatable("message.edgerunners.install_failed"), true);
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> tip, TooltipFlag flag) {
        tip.add(Component.translatable("tooltip.edgerunners.slot", Component.translatable("cyber.slot." + slot.name().toLowerCase())));
        tip.add(Component.translatable("tooltip.edgerunners.capacity", capacityCost));
    }
}
