package julian.servermod;

import com.mojang.serialization.Codec;
import julian.servermod.utils.pockets.PocketContentsComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final ComponentType<Integer> POCKET_COUNT = register("pocket_count",
            ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build());

    public static final ComponentType<Boolean> HAS_POCKETS = register("has_pockets",
            ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build());

    public static final ComponentType<PocketContentsComponent> BUNDLE_CONTENTS = register(
            "bundle_contents", builder -> builder.codec(PocketContentsComponent.CODEC).packetCodec(PocketContentsComponent.PACKET_CODEC).cache());

    private static <T> ComponentType<T> register(String name, ComponentType<T> componentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(ServerMod.MOD_ID, name), componentType);
    }

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE,
                Identifier.of(ServerMod.MOD_ID, id),
                builderOperator.apply(ComponentType.<T>builder()).build());
    }

    public static void register() {
        ServerMod.LOGGER.info("Registering Mod Data Components for " + ServerMod.MOD_ID);
    }
}