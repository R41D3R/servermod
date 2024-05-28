package julian.servermod.mixin;

import julian.servermod.ServerMod;
import julian.servermod.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useRubyStaffModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode,
                                        boolean leftHanded, MatrixStack matrices,
                                        VertexConsumerProvider vertexConsumers, int light, int overlay) {

        // item is not in GUI (it is in hand or on the floor) render the 3d model
        if (stack.isOf(ModItems.WateringCan) && renderMode != ModelTransformationMode.GUI) {
            if (stack.getDamage() < 5) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(ServerMod.MOD_ID,
                        "wooden_watering_can_3d_full", "inventory"));
            }
            return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(ServerMod.MOD_ID,
                    "wooden_watering_can_3d", "inventory"));
        }


        // if it is in GUI render flat model
        if (stack.isOf(ModItems.WateringCan) && renderMode == ModelTransformationMode.GUI) {
            if (stack.getDamage() < 5) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(ServerMod.MOD_ID,
                        "wooden_watering_can_full", "inventory"));
            }

            return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(ServerMod.MOD_ID,
                    "wooden_watering_can", "inventory"));
        }

        return value;
    }
}