package julian.servermod.item;

import julian.servermod.ServerMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.NbtComponent;

import java.util.function.UnaryOperator;

public class ModComponents {
    // Create a registry key for your mod's component types
    public static final ComponentType<NbtComponent> CAPTURED_ENTITY_DATA = register("captured_entity_data", (builder) -> {
        return builder.codec(NbtComponent.CODEC_WITH_ID).packetCodec(NbtComponent.PACKET_CODEC);
    });
    public ModComponents() {
    }
    // Registration method
    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return (ComponentType)Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(ServerMod.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    // Call this method in your mod initializer
    public static void registerComponents() {
        // This line isn't strictly necessary for registration, but it can help ensure your components are loaded
        ServerMod.LOGGER.info("Registering Mod Components for " + ServerMod.MOD_ID);
    }
}