package Model;

import Testing.InputAdapter;
import Testing.Test;

public class Player extends GameObject {
    protected String name;
    protected float bulletDamage;
    protected float bulletSpeed;
    protected int ammo;
    protected int killCount;

    /*--------------------
        Constructor
     --------------------*/

    //Basic constructor
    public Player(DynamicBody dynamicBody) {
        super(dynamicBody);
        this.bulletDamage = 5;
        this.bulletSpeed = 1;
        this.ammo = 10000;
        this.killCount = 0;
    }

    /*--------------------
        Getters/Setters
     --------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(float bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    /*--------------------
            Methods
     --------------------*/

    public void update(InputAdapter input) {
        this.getBody().update(input);
        if (input.get_active_keys()[3])  {
            shoot();
        }
    }

    public void shoot() {
        DynamicBody dynBody = (DynamicBody) this.getBody();
        Bullet bullet = new Bullet(new DynamicBody(this.getBody().getPosX(), this.getBody().getPosY(),
                this.getBody().getAngle(), 1, dynBody.getSpeedX(), dynBody.getSpeedY(),
                0.01f, dynBody.getSpeedLimit()*3), this.name);
        Test.game_control.addQueue.add(bullet);
    }
}
