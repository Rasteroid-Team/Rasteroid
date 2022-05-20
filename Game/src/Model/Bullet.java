package Model;

public class Bullet extends GameObject {
    protected String ownerName;
    protected float damage;

    /*--------------------
        Constructor
     --------------------*/

    //Basic constructor
    public Bullet(Body body, String ownerName) {
        super(body);
        this.ownerName = null;
        this.damage = 5;
    }

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
