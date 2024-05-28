package julian.servermod.block.entity;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<PhoenixBlockEntity> PHOENIX_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ServerMod.MOD_ID, "phoenix_block_be"),
                    FabricBlockEntityTypeBuilder.create(PhoenixBlockEntity::new,
                            ModBlocks.PHOENIX_BLOCK).build());

    public static final BlockEntityType<StylingTableMineEntity> STYLING_TABLE_MINE_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ServerMod.MOD_ID, "styling_table_mine_be"),
                    FabricBlockEntityTypeBuilder.create(StylingTableMineEntity::new,
                            ModBlocks.STYLING_TABLE_MINE).build());

    public static final BlockEntityType<BoulderBlockEntity> BOULDER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ServerMod.MOD_ID, "boulder_block_be"),
                    FabricBlockEntityTypeBuilder.create(BoulderBlockEntity::new,
                            ModBlocks.BOULDER_BLOCK).build());

    public static final BlockEntityType<BadgerTaskBlockEntity> BADGER_TASK_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ServerMod.MOD_ID, "badger_task_block_be"),
                    FabricBlockEntityTypeBuilder.create(BadgerTaskBlockEntity::new,
                            ModBlocks.BADGER_TASK_BLOCK).build());

    public static void registerBLockEntities() {
        ServerMod.LOGGER.info("Registering Block Entities for " + ServerMod.MOD_ID);
    }
}
