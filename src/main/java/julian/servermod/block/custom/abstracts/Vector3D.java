package julian.servermod.block.custom.abstracts;

public class Vector3D {
    private double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    public void rotateX(int degrees) {
        double radians = Math.toRadians(degrees);
        double newY = y * Math.cos(radians) - z * Math.sin(radians);
        double newZ = y * Math.sin(radians) + z * Math.cos(radians);
        y = newY;
        z = newZ;
    }

    public void rotateY(int degrees) {
        double radians = Math.toRadians(degrees);
        double newX = x * Math.cos(radians) + z * Math.sin(radians);
        double newZ = -x * Math.sin(radians) + z * Math.cos(radians);
        x = newX;
        z = newZ;
    }

    public void rotateZ(int degrees) {
        double radians = Math.toRadians(degrees);
        double newX = x * Math.cos(radians) - y * Math.sin(radians);
        double newY = x * Math.sin(radians) + y * Math.cos(radians);
        x = newX;
        y = newY;
    }

    public void rotate(int degrees, char axis) {
        switch (axis) {
            case 'X':
            case 'x':
                rotateX(degrees);
                break;
            case 'Y':
            case 'y':
                rotateY(degrees);
                break;
            case 'Z':
            case 'z':
                rotateZ(degrees);
                break;
            default:
                throw new IllegalArgumentException("Invalid axis. Use 'X', 'Y', or 'Z'.");
        }
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }
}