package julian.servermod.screen.data;



import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.math.BlockPos;

public class BadgerTaskBlockData {
    public static final PacketCodec<RegistryByteBuf, BadgerTaskBlockData> PACKET_CODEC = PacketCodec.of(
            BadgerTaskBlockData::write,
            BadgerTaskBlockData::new
    );

    private final BlockPos pos;

    public BadgerTaskBlockData(PacketByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public BlockEntity pos(PlayerInventory inventory) {
        return inventory.player.getWorld().getBlockEntity(this.pos);
    }

    // Add other necessary methods and fields
}