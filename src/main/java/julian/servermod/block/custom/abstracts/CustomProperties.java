package julian.servermod.block.custom.abstracts;

import julian.servermod.block.custom.abstracts.properties.TripleBlockHalf;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;

public class CustomProperties {

    public static final EnumProperty<TripleBlockHalf> TRIPLE_BLOCK_HALF = EnumProperty.of("half", TripleBlockHalf.class);
    public static final EnumProperty<DoubleBlockHalf> DOUBLE_BLOCK_HALF = EnumProperty.of("half", DoubleBlockHalf.class);
    public static final IntProperty TRIPLE_COL_BLOCK = IntProperty.of("column", 1, 3);
}
