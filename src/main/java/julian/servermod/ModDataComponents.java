package julian.servermod;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModDataComponents {
    public static final ComponentType<Integer> CUSTOM_NAME = register("design_name",
            ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build());

    private static <T> ComponentType<T> register(String name, ComponentType<T> builder) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ServerMod.MOD_ID, builder);
    }

    public static void register() {
        ServerMod.LOGGER.info("Registering Mod Data Components for " + ServerMod.MOD_ID);
    }
}