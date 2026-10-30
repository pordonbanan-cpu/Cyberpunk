package com.edgerunners.registry;

import com.edgerunners.Edgerunners;
import com.edgerunners.cyber.CyberSlot;
import com.edgerunners.item.AttributeImplantItem;
import com.edgerunners.item.AttributeImplantItem.Mod;
import com.edgerunners.item.SandevistanItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Edgerunners.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Edgerunners.MODID);

    private static Item.Properties implant(Rarity r) { return new Item.Properties().stacksTo(1).rarity(r); }

    public static final DeferredHolder<Item, SandevistanItem> SANDEVISTAN =
            ITEMS.register("sandevistan", () -> new SandevistanItem(implant(Rarity.EPIC)));

    public static final DeferredHolder<Item, AttributeImplantItem> REINFORCED_TENDONS =
            ITEMS.register("reinforced_tendons", () -> new AttributeImplantItem(implant(Rarity.RARE), CyberSlot.LEGS, 10,
                    new Mod(Attributes.JUMP_STRENGTH, "tendons_jump", 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                    new Mod(Attributes.SAFE_FALL_DISTANCE, "tendons_fall", 4.0, AttributeModifier.Operation.ADD_VALUE)));

    public static final DeferredHolder<Item, AttributeImplantItem> SUBDERMAL_ARMOR =
            ITEMS.register("subdermal_armor", () -> new AttributeImplantItem(implant(Rarity.RARE), CyberSlot.INTEGUMENTARY_SYSTEM, 12,
                    new Mod(Attributes.ARMOR, "subdermal_armor", 4.0, AttributeModifier.Operation.ADD_VALUE)));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.edgerunners"))
                    .icon(() -> SANDEVISTAN.get().getDefaultInstance())
                    .displayItems((p, out) -> {
                        out.accept(SANDEVISTAN.get());
                        out.accept(REINFORCED_TENDONS.get());
                        out.accept(SUBDERMAL_ARMOR.get());
                    })
                    .build());
}
