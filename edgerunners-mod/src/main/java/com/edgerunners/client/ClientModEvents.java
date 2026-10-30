package com.edgerunners.client;

import com.edgerunners.Edgerunners;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Edgerunners.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    public static KeyMapping OPEN_CYBERWARE;
    public static KeyMapping ACTIVATE_SANDEVISTAN;

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        OPEN_CYBERWARE = new KeyMapping(
                "key.edgerunners.open_cyberware",
                GLFW.GLFW_KEY_B,
                "key.categories.edgerunners"
        );
        ACTIVATE_SANDEVISTAN = new KeyMapping(
                "key.edgerunners.activate_sandevistan",
                GLFW.GLFW_KEY_V,
                "key.categories.edgerunners"
        );
        event.register(OPEN_CYBERWARE);
        event.register(ACTIVATE_SANDEVISTAN);
    }
}
