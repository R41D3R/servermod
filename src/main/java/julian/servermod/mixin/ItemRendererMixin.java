package julian.servermod.mixin;

import julian.servermod.ServerMod;
import julian.servermod.item.ModItems;
// import julian.servermod.item.custom.CaptureNet;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {


    @ModifyVariable(method = "renderItem", at = @At("HEAD"), argsOnly = true)
    private BakedModel useRubyStaffModel(BakedModel model, ItemStack stack, ModelTransformationMode renderMode,
                                         boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                         int light, int overlay) {
        if (stack.isOf(ModItems.WATERING_CAN)) {
            boolean isFull = stack.getDamage() < 5;
            boolean isGUI =renderMode == ModelTransformationMode.GUI;

            ModelIdentifier modelId;
            if (isGUI) {
                if (isFull) {
                    return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID,
                            "wooden_watering_can_full"), "inventory"));
                }
                modelId = new ModelIdentifier(IdentifierAccessor.callConstructor(ServerMod.MOD_ID,
                        isFull ? "wooden_watering_can_full" : "models/item/wooden_watering_can"), "inventory");
            } else {
                modelId = new ModelIdentifier(IdentifierAccessor.callConstructor(ServerMod.MOD_ID,
                        isFull ? "models/item/wooden_watering_can_3d_full" : "models/item/wooden_watering_can_3d"), "inventory");
            }

            BakedModel newModel = ((ItemRendererAccessor) this).getModels().getModelManager().getModel(modelId);

            // Debug output
            System.out.println("Watering Can Debug:");
            System.out.println("Is Full: " + isFull);
            System.out.println("Is GUI: " + isGUI);
            System.out.println("Model ID: " + modelId);
            System.out.println("New Model: " + (newModel != null ? "Loaded" : "Null"));

            return newModel != null ? newModel : model;
        }

        return model;
    }

//    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
//    public BakedModel changeCaptureNetModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode,
//                                        boolean leftHanded, MatrixStack matrices,
//                                        VertexConsumerProvider vertexConsumers, int light, int overlay) {
//
//        // item is not in GUI (it is in hand or on the floor) render the 3d model
//        if (stack.isOf(ModItems.CAPTURE_NET) && CaptureNet.hasEntity(stack)) {
//            return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID,
//                    "capture_net_closed"), "inventory"));
//        }
//        if (stack.isOf(ModItems.CAPTURE_NET) && !CaptureNet.hasEntity(stack)) {
//            return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID,
//                    "capture_net"), "inventory"));
//        }
//        return value;
//    }

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel snailItem(BakedModel value, ItemStack stack, ModelTransformationMode renderMode,
                                        boolean leftHanded, MatrixStack matrices,
                                        VertexConsumerProvider vertexConsumers, int light, int overlay) {

        // item is not in GUI (it is in hand or on the floor) render the 3d model
        if (stack.isOf(ModItems.SNAIL) && renderMode != ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID,
                    "snail_3d"), "inventory"));
        }


        // if it is in GUI render flat model
        if (stack.isOf(ModItems.SNAIL) && renderMode == ModelTransformationMode.GUI) {
            return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID,
                    "snail"), "inventory"));
        }

        return value;
    }
}