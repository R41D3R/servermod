package julian.servermod.entity;

import julian.servermod.ServerMod;
import julian.servermod.entity.custom.LootBalloonEntity;
import julian.servermod.entity.custom.SnailEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import javax.swing.text.html.parser.Entity;

public class ModEntities {
    public static final EntityType<LootBalloonEntity> LOOT_BALLOON = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(ServerMod.MOD_ID, "loot_balloon"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, LootBalloonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 3f))
                    .build()
    );

    public static final EntityType<SnailEntity> SNAIL = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(ServerMod.MOD_ID, "snail"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SnailEntity::new)
                    .dimensions(EntityDimensions.fixed(0.2f, 0.5f))
                    .build()
    );
}
