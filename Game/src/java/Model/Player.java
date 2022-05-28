package Model;

import Controller.GameControl;
import Testing.InputAdapter;
import View.Objects.ObjectModels.ObjectModel;
import View.Objects.ObjectModels.Players.HR75;
import View.Resources;

import java.util.List;

public class Player extends GameObject {
    protected String name;
    protected float bulletDamage;
    protected float bulletSpeed;
    protected int ammo;
    protected int killCount;

    protected double fire_interval_seconds;
    protected long last_fire;
    protected InputAdapter input;
    GameControl control;

    /*--------------------
        Constructor
     --------------------*/

    //Basic constructor
    public Player(GameControl g_control, InputAdapter adapter, ObjectModel model) {
        super(null, true, 100, false, model);
        input = adapter;
        setBody(new PlayerBody());
        control = g_control;
        this.bulletDamage = 5;
        this.bulletSpeed = 1;
        this.ammo = 10000;
        this.killCount = 0;
        this.fire_interval_seconds = 0.3;
        this.last_fire = 0;
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

    public void set_adapter(InputAdapter adapter) { input = adapter; }

    /*--------------------
            Methods
     --------------------*/

    @Override
    public void update(List<GameObject> objects) {
        super.update(objects);

        //IDLE
        currentState = 0;

        //MOVE
        if (input.get_active_keys()[0])
        {
            currentState = 1;
        }

        //SHOOT
        if (input.get_active_keys()[3])
        {
            currentState = 2;
            shoot();
        }

        //MOVE-SHOOT
        if (input.get_active_keys()[0] && input.get_active_keys()[3])
        {
            currentState = 3;
            shoot();
        }

        stateList.get(currentState).get_animation().update();
        body.update(objects);
    }

    public class PlayerBody extends DynamicBody
    {

        public PlayerBody() {
            potencia_aceleracion = 80;
            speedLimit = 5;
        }

        @Override
        public void update(List<GameObject> objects) {
            super.update(objects);

            accelerando = input.get_active_keys()[0];
            if (input.get_active_keys()[1]) {
                anguloFuerza -= 5;
            }
            if (input.get_active_keys()[2]) {
                anguloFuerza += 5;
            }
            setAngle(anguloFuerza);
        }

    }

    public void shoot() {
        if ((System.currentTimeMillis()-last_fire)/1000.0 >= fire_interval_seconds)
        {
            Bullet bullet = new Bullet(this, Resources.BULLET_YELLOW());
            GameControl.add_object(bullet);
            last_fire = System.currentTimeMillis();
        }
    }
}
