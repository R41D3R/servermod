package julian.servermod.world.biome;

import julian.servermod.ServerMod;
import julian.servermod.world.biome.surface.ModMaterialRules;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerrablenderAPI implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegion(new Identifier(ServerMod.MOD_ID, "overworld"), 4));

        //SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, ServerMod.MOD_ID, ModMaterialRules.materialRules());
    }
}
