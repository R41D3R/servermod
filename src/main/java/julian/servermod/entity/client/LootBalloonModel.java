package julian.servermod.entity.client;

import julian.servermod.ServerMod;
import julian.servermod.entity.custom.LootBalloonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class LootBalloonModel extends GeoModel<LootBalloonEntity> {
    @Override
    public Identifier getModelResource(LootBalloonEntity animatable) {
        return new Identifier(ServerMod.MOD_ID, "geo/loot_balloon.geo.json");
    }

    @Override
    public Identifier getTextureResource(LootBalloonEntity animatable) {
        return new Identifier(ServerMod.MOD_ID, "textures/entity/balloon_1.png");
    }

    @Override
    public Identifier getAnimationResource(LootBalloonEntity animatable) {
        return new Identifier(ServerMod.MOD_ID, "animations/empty.animation.json");
    }
}
