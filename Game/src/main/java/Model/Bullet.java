package Model;

public class Bullet extends GameObject {
    protected String ownerName;
    protected float damage;

    /*--------------------
        Getters/Setters
     --------------------*/

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
