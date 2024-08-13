package julian.servermod;

public class Packets {
    public record CrateScreenPacket(int crateKeyItem, int rewardItem, int rewardItemCount) {}

}
