package Model;

public class GameRules {

    //valores default a gamerules
    //valores se sobreescriben con fichero de configuracion(?)
    public static int numPlayers = 0;
    private float baseBulletDamage = 10.0f;
    private float baseBulletSpreen = 3.0f;
    private float baseBulletLifeSpan = 10.0f;
    private float basePlayerHealth = 100.0f;
    private float basePlayerShootCoolDown = 1.5f;
    private boolean breakableWalls = false;
    private float environmentFriction = 0.3f;
    private int goal = 10;
    private float maxPlayerSpeed = 5.0f;
    private boolean peaceful = false;
    private int startBreaktimeDuration = 4;

    public float getBaseBulletDamage() {
        return baseBulletDamage;
    }

    public void setBaseBulletDamage(float baseBulletDamage) {
        this.baseBulletDamage = baseBulletDamage;
    }

    public float getBaseBulletSpreen() {
        return baseBulletSpreen;
    }

    public void setBaseBulletSpreen(float baseBulletSpreen) {
        this.baseBulletSpreen = baseBulletSpreen;
    }

    public float getBaseBulletLifeSpan() {
        return baseBulletLifeSpan;
    }

    public void setBaseBulletLifeSpan(float baseBulletLifeSpan) {
        this.baseBulletLifeSpan = baseBulletLifeSpan;
    }

    public float getBasePlayerHealth() {
        return basePlayerHealth;
    }

    public void setBasePlayerHealth(float basePlayerHealth) {
        this.basePlayerHealth = basePlayerHealth;
    }

    public float getBasePlayerShootCoolDown() {
        return basePlayerShootCoolDown;
    }

    public void setBasePlayerShootCoolDown(float basePlayerShootCoolDown) {
        this.basePlayerShootCoolDown = basePlayerShootCoolDown;
    }

    public boolean isBreakableWalls() {
        return breakableWalls;
    }

    public void setBreakableWalls(boolean breakableWalls) {
        this.breakableWalls = breakableWalls;
    }

    public float getEnvironmentFriction() {
        return environmentFriction;
    }

    public void setEnvironmentFriction(float environmentFriction) {
        this.environmentFriction = environmentFriction;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public float getMaxPlayerSpeed() {
        return maxPlayerSpeed;
    }

    public void setMaxPlayerSpeed(float maxPlayerSpeed) {
        this.maxPlayerSpeed = maxPlayerSpeed;
    }

    public boolean getPeaceful() {
        return peaceful;
    }

    public void setPeaceful(boolean peaceful) {
        this.peaceful = peaceful;
    }

    public int getStartBreaktimeDuration() {
        return startBreaktimeDuration;
    }

    public void setStartBreaktimeDuration(int startBreaktimeDuration) {
        this.startBreaktimeDuration = startBreaktimeDuration;
    }
}
