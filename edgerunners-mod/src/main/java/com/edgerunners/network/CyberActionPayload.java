package com.edgerunners.network;

import com.edgerunners.Edgerunners;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CyberActionPayload(boolean install, int index, String itemId) implements CustomPacketPayload {
    public static final Type<CyberActionPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Edgerunners.MODID, "cyber_action"));
    public static final StreamCodec<ByteBuf, CyberActionPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, CyberActionPayload::install,
            ByteBufCodecs.VAR_INT, CyberActionPayload::index,
            ByteBufCodecs.STRING_UTF8, CyberActionPayload::itemId,
            CyberActionPayload::new);

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
