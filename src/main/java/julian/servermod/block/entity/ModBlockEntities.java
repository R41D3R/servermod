package julian.servermod.block.entity;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
//    public static final BlockEntityType<PhoenixBlockEntity> PHOENIX_BLOCK_ENTITY =
//            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ServerMod.MOD_ID, "phoenix_block_be"),
//                    FabricBlockEntityTypeBuilder.create(PhoenixBlockEntity::new,
//                            ModBlocks.PHOENIX_BLOCK).build());
//
//
//    public static final BlockEntityType<BadgerTaskBlockEntity> BADGER_TASK_BLOCK_ENTITY =
//            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ServerMod.MOD_ID, "badger_task_block_be"),
//                    FabricBlockEntityTypeBuilder.create(BadgerTaskBlockEntity::new,
//                            ModBlocks.BADGER_TASK_BLOCK).build());

    public static final BlockEntityType<CoordinateEntity> COORDINATE_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(ServerMod.MOD_ID, "coordinate_entity"),
            BlockEntityType.Builder.create(CoordinateEntity::new, ModBlocks.CUSTOM_BARRIER, ModBlocks.LOG_BENCH).build()
    );

    public static void registerBLockEntities() {
        ServerMod.LOGGER.info("Registering Block Entities for " + ServerMod.MOD_ID);
    }
}
