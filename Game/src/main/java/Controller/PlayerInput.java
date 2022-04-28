package Controller;
public record PlayerInput() {
    public static float rotation;
    public static float propulsion;
    public static boolean shooting;

    public static float getRotation() {
        return rotation;
    }

    public static void setRotation(float rotation) {
        PlayerInput.rotation = rotation;
    }

    public static float getPropulsion() {
        return propulsion;
    }

    public static void setPropulsion(float propulsion) {
        PlayerInput.propulsion = propulsion;
    }

    public static boolean isShooting() {
        return shooting;
    }

    public static void setShooting(boolean shooting) {
        PlayerInput.shooting = shooting;
    }
}
