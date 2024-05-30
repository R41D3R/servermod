package julian.servermod.entity.client;

import julian.servermod.ServerMod;
import julian.servermod.entity.custom.LootBalloonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.model.GeoModel;
import net.minecraft.util.Identifier;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LootBalloonRenderer extends GeoEntityRenderer<LootBalloonEntity> {
    public LootBalloonRenderer(EntityRendererFactory.Context rendererManager) {
        super(rendererManager, new LootBalloonModel());
    }

    @Override
    public Identifier getTextureLocation(LootBalloonEntity entity) {
        return new Identifier(ServerMod.MOD_ID, "textures/entity/balloon_1.png");
    }

    @Override
    public void render(LootBalloonEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        // here you can downnscale baby animals
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
