package julian.servermod.entity.client;

import julian.servermod.ServerMod;
import julian.servermod.entity.custom.LootBalloonEntity;
import julian.servermod.entity.custom.SnailEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SnailRenderer extends GeoEntityRenderer<SnailEntity> {
    public SnailRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SnailModel());
    }

    @Override
    public Identifier getTextureLocation(SnailEntity entity) {
        return Identifier.of(ServerMod.MOD_ID, "textures/entity/snail.png");
    }

    @Override
    public void render(SnailEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.3F, 0.3F, 0.3F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
