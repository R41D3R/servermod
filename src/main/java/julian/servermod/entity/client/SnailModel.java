package julian.servermod.entity.client;

import julian.servermod.ServerMod;
import julian.servermod.entity.custom.SnailEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SnailModel extends GeoModel<SnailEntity> {
    @Override
    public Identifier getModelResource(SnailEntity animatable) {
        return Identifier.of(ServerMod.MOD_ID, "geo/snail.geo.json");
    }

    @Override
    public Identifier getTextureResource(SnailEntity animatable) {
        return Identifier.of(ServerMod.MOD_ID, "textures/entity/snail.png");
    }

    @Override
    public Identifier getAnimationResource(SnailEntity animatable) {
        return Identifier.of(ServerMod.MOD_ID, "animations/snail.animation.json");
    }
}
