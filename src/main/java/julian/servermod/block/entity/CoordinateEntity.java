package julian.servermod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class CoordinateEntity extends BlockEntity{
    public BlockPos mainBlockPos;

    public CoordinateEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COORDINATE_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        if (this.mainBlockPos != null) {
            nbt.putLong("MainBlockPos", mainBlockPos.asLong());
        }
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("MainBlockPos", 8)) {
            this.mainBlockPos = BlockPos.fromLong(nbt.getLong("MainBlockPos"));
        }
    }
}
