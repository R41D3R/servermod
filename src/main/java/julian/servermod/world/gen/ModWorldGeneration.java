package julian.servermod.world.gen;

public class ModWorldGeneration {

    public static void generateModWorldGen() {
        ModOreGeneration.generateOres();
        ModLootBoxGeneration.generateLootVases();
    }
}
