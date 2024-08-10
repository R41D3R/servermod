package julian.servermod.block.custom.abstracts.properties;

// Coordinate3D.java

public class Coordinate3D implements Comparable<Coordinate3D> {
    private final int x;
    private final int y;
    private final int z;

    public Coordinate3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }

    @Override
    public int compareTo(Coordinate3D other) {
        if (this.x != other.x) return Integer.compare(this.x, other.x);
        if (this.y != other.y) return Integer.compare(this.y, other.y);
        return Integer.compare(this.z, other.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate3D)) return false;
        Coordinate3D that = (Coordinate3D) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}