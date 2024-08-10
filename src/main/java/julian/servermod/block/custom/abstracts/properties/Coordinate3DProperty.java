package julian.servermod.block.custom.abstracts.properties;

import com.google.common.collect.ImmutableSet;
import net.minecraft.state.property.Property;

import java.util.Collection;
import java.util.Optional;

public class Coordinate3DProperty extends Property<Coordinate3D> {
    private final ImmutableSet<Coordinate3D> values;
    private final int minX, maxX, minY, maxY, minZ, maxZ;

    protected Coordinate3DProperty(String name, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(name, Coordinate3D.class);
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;

        ImmutableSet.Builder<Coordinate3D> builder = ImmutableSet.builder();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    builder.add(new Coordinate3D(x, y, z));
                }
            }
        }
        this.values = builder.build();
    }

    @Override
    public Collection<Coordinate3D> getValues() {
        return this.values;
    }

    @Override
    public String name(Coordinate3D value) {
        return value.toString();
    }

    @Override
    public Optional<Coordinate3D> parse(String name) {
        String[] parts = name.split(",");
        if (parts.length != 3) return Optional.empty();
        try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            if (x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ) {
                return Optional.of(new Coordinate3D(x, y, z));
            }
        } catch (NumberFormatException e) {
            // Parsing failed, return empty
        }
        return Optional.empty();
    }

    public static Coordinate3DProperty of(String name, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        return new Coordinate3DProperty(name, minX, maxX, minY, maxY, minZ, maxZ);
    }
}