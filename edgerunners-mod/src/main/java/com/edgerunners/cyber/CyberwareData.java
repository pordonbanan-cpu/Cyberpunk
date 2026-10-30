package com.edgerunners.cyber;

import com.edgerunners.item.CyberwareItem;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CyberwareData {
    public static final int SIZE = CyberSlot.TOTAL;
    public static final Codec<CyberwareData> CODEC =
            Codec.STRING.listOf().xmap(CyberwareData::fromList, CyberwareData::toList);

    private final String[] ids = new String[SIZE];
    private final Item[] items = new Item[SIZE];

    public CyberwareData() { Arrays.fill(ids, ""); }

    public static CyberwareData fromList(List<String> list) {
        CyberwareData d = new CyberwareData();
        d.replaceWith(list);
        return d;
    }

    public List<String> toList() { return new ArrayList<>(Arrays.asList(ids)); }

    public void replaceWith(List<String> list) {
        for (int i = 0; i < SIZE; i++) set(i, i < list.size() ? list.get(i) : "");
    }

    public void set(int i, String id) {
        ids[i] = id == null ? "" : id;
        items[i] = resolve(ids[i]);
    }

    private static Item resolve(String id) {
        if (id.isEmpty()) return null;
        ResourceLocation rl = ResourceLocation.tryParse(id);
        if (rl == null) return null;
        return BuiltInRegistries.ITEM.getOptional(rl).orElse(null);
    }

    public boolean isEmpty(int i) { return ids[i].isEmpty(); }

    public Item item(int i) { return items[i]; }

    public CyberwareItem implant(int i) {
        return items[i] instanceof CyberwareItem c ? c : null;
    }

    public boolean has(Item item) {
        for (int i = 0; i < SIZE; i++) if (items[i] == item && item != null) return true;
        return false;
    }

    public int used() {
        int sum = 0;
        for (int i = 0; i < SIZE; i++) if (items[i] instanceof CyberwareItem c) sum += c.cost();
        return sum;
    }
}
