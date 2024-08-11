package julian.servermod.mixin;

import julian.servermod.ServerMod;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModelToBake(ModelIdentifier modelId, UnbakedModel unbakedModel);

    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);
    @Shadow
    private UnbakedModel missingModel;



    @Shadow
    protected abstract JsonUnbakedModel loadModelFromJson(Identifier id);

    @Inject(method = "<init>", at = @At("RETURN"))
    public void addDiggingMittModel(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels,
                                    Map<Identifier, List<BlockStatesLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "models/item/wooden_watering_can_3d"), "inventory"),
                jsonUnbakedModels.get(Identifier.of(ServerMod.MOD_ID, "models/item/wooden_watering_can_3d.json")));

        this.loadItemModel(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "wooden_watering_can_full"), "inventory"));

        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "models/item/wooden_watering_can"), "inventory"),
                jsonUnbakedModels.get(Identifier.of(ServerMod.MOD_ID, "models/item/wooden_watering_can.json")));

        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "models/item/wooden_watering_can_3d_full"), "inventory"),
                jsonUnbakedModels.get(Identifier.of(ServerMod.MOD_ID, "models/item/wooden_watering_can_3d_full.json")));

        this.loadItemModel(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "snail"), "inventory"));
        this.loadItemModel(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "snail_3d"), "inventory"));
    }


//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModelToBake(Lnet/minecraft/client/util/ModelIdentifier;Lnet/minecraft/client/render/model/UnbakedModel;)V", ordinal = 0, shift = At.Shift.AFTER))
//    public void addWateringCan(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<BlockStatesLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
//        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "wooden_watering_can_3d"), "inventory"), this.missingModel);
//        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "wooden_watering_can"), "inventory"), this.missingModel);
//        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "wooden_watering_can_full"), "inventory"), this.missingModel);
//        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "wooden_watering_can_3d_full"), "inventory"), this.missingModel);
//        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "capture_net"), "inventory"), this.missingModel);
//        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "capture_net_closed"), "inventory"), this.missingModel);
//        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "snail"), "inventory"), this.missingModel);
//        this.addModelToBake(new ModelIdentifier(Identifier.of(ServerMod.MOD_ID, "snail_3d"), "inventory"), this.missingModel);
//    }
}