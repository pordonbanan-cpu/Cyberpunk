package com.edgerunners.network;

import com.edgerunners.Edgerunners;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ActivatePayload() implements CustomPacketPayload {
    public static final Type<ActivatePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Edgerunners.MODID, "activate"));
    public static final StreamCodec<ByteBuf, ActivatePayload> CODEC = StreamCodec.unit(new ActivatePayload());

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
