package Model;

import Controller.GameControl;
import Testing.InputAdapter;
import View.Objects.ObjectModels.ObjectModel;
import View.Objects.ObjectModels.Players.HR75;
import View.Objects.ObjectModels.Players.PlayerModel;
import View.Resources;
import View.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
    PlayerModel model;
    protected Color color;

    /*--------------------
        Constructor
     --------------------*/

    //Basic constructor
    public Player(GameControl g_control, InputAdapter adapter, PlayerModel player_model, Color player_color) {
        super(null, true, 100, false, player_model);
        color = player_color;
        model = player_model;
        model.set_aura_color(color);
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

    @Override
    public void render(Graphics2D graphics) {
        BufferedImage aura = model.get_aura().get_image();
        float pos_x = body.getPosX();
        float pos_y = body.getPosY();
        float orientation = body.getAngle();

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate( pos_x, pos_y );
        affineTransform.rotate( Math.toRadians( orientation ) );
        affineTransform.translate(-aura.getWidth()/2.0, -aura.getWidth()/2.0);
        affineTransform.scale(1,1);

        graphics.drawImage(aura, affineTransform, null);
        super.render(graphics);
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

    @Override
    public void die() {
        super.die();
        GameControl.add_object(new ParticleFx(Resources.PARTICLE_EXPLOSION(), (int) (getBody().getPosX()-50), (int) (getBody().getPosY()-50)));
        GameControl.remove_object(this);
    }
}
