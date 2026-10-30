package com.edgerunners.item;

import com.edgerunners.cyber.CyberSlot;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Простой имплант, дающий один AttributeModifier пока установлен. */
public class AttributeImplantItem extends CyberwareItem {
    private final Holder<Attribute> attribute;
    private final ResourceLocation modifierId;
    private final double amount;
    private final AttributeModifier.Operation operation;

    public AttributeImplantItem(Properties props, CyberSlot slot, int capacityCost,
                                Holder<Attribute> attribute, ResourceLocation modifierId,
                                double amount, AttributeModifier.Operation operation) {
        super(props, slot, capacityCost);
        this.attribute = attribute;
        this.modifierId = modifierId;
        this.amount = amount;
        this.operation = operation;
    }

    @Override
    public void onInstalled(Player player, ItemStack stack) {
        var inst = player.getAttribute(attribute);
        if (inst != null && !inst.hasModifier(modifierId)) {
            inst.addTransientModifier(new AttributeModifier(modifierId, amount, operation));
        }
    }

    @Override
    public void onRemoved(Player player, ItemStack stack) {
        var inst = player.getAttribute(attribute);
        if (inst != null) inst.removeModifier(modifierId);
    }
}
