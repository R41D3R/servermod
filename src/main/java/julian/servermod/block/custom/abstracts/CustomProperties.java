package julian.servermod.block.custom.abstracts;

import julian.servermod.block.custom.abstracts.properties.TripleBlockHalf;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.EnumProperty;

public class CustomProperties {

    public static final EnumProperty<TripleBlockHalf> TRIPLE_BLOCK_HALF = EnumProperty.of("half", TripleBlockHalf.class);

}
