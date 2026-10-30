package com.edgerunners.registry;

import com.edgerunners.Edgerunners;
import com.edgerunners.cyber.CyberwareData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Edgerunners.MODID);

    public static final Supplier<AttachmentType<CyberwareData>> CYBERWARE = ATTACHMENTS.register("cyberware",
            () -> AttachmentType.builder(CyberwareData::new)
                    .serialize(CyberwareData.CODEC)
                    .copyOnDeath()
                    .build());
}
