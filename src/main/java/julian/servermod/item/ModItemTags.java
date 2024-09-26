package julian.servermod.item;

import julian.servermod.ServerMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class ModItemTags {

    public final static List<Item> HAS_POCKETS = List.of(Items.LEATHER_LEGGINGS);
    public final static List<Item> POCKETS_BLACKLIST = List.of(Items.LEATHER_LEGGINGS, Items.SHULKER_BOX);

}
