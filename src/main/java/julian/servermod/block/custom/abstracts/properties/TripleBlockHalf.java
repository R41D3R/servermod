package julian.servermod.block.custom.abstracts.properties;


import net.minecraft.util.StringIdentifiable;

public enum TripleBlockHalf implements StringIdentifiable
{
    UPPER,
    MIDDLE,
    LOWER;


    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        switch (this) {
            case UPPER:
                return "upper";
            case LOWER:
                return "lower";
            default:
                return "middle";
        }
    }
}

