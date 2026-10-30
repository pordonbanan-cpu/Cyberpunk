package com.edgerunners.network;

import com.edgerunners.Edgerunners;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record CyberwareSyncPayload(int entityId, List<String> ids) implements CustomPacketPayload {
    public static final Type<CyberwareSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Edgerunners.MODID, "cyberware_sync"));
    public static final StreamCodec<ByteBuf, CyberwareSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CyberwareSyncPayload::entityId,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), CyberwareSyncPayload::ids,
            CyberwareSyncPayload::new);

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
