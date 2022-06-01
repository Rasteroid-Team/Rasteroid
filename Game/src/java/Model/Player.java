package Model;

import Controller.GameControl;
import Controller.GameEngine;
import Testing.InputAdapter;
import View.Objects.ObjectModels.ObjectModel;
import View.Objects.ObjectModels.Players.HR75;
import View.Objects.ObjectModels.Players.PlayerModel;
import View.Resources;
import View.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public class Player extends GameObject implements Serializable {
    protected String name;
    protected float bulletDamage;
    protected float bulletSpeed;
    protected int ammo;
    protected int killCount;

    protected double fire_interval_seconds;
    protected long last_fire;
    PlayerModel model;
    protected Color color;
    private String associatedMac;
    private boolean shooting;

    /*--------------------
        Constructor
     --------------------*/

    //Basic constructor
    public Player(PlayerModel player_model, Color player_color, String associatedMac) {
        super(null, true, 100, false, player_model);
        color = player_color;
        model = player_model;
        model.set_aura_color(color);
        setBody(new PlayerBody());
        this.bulletDamage = 5;
        this.bulletSpeed = 1;
        this.ammo = 10000;
        this.killCount = 0;
        this.fire_interval_seconds = 0.3;
        this.last_fire = 0;
        this.associatedMac = associatedMac;
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

    public void setModel(PlayerModel model) {
        this.model = model;
    }

    public PlayerModel getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public String getAssociatedMac() {
        return associatedMac;
    }

    public PlayerBody getPlayerBody(){
        return (PlayerBody)this.body;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    /*--------------------
            Methods
     --------------------*/

    @Override
    public void update(List<GameObject> objects) {
        super.update(objects);

        //IDLE
        currentState = 0;

        //MOVE
        if (((PlayerBody)body).is_accelerating())
        {
            currentState = 1;
        }

        //SHOOT
        if (shooting)
        {
            currentState = 2;
            shoot();
        }

        //MOVE-SHOOT
        if (((PlayerBody)body).is_accelerating() && shooting)
        {
            currentState = 3;
            shoot();
        }

        shooting = false;
        stateList.get(currentState).get_animation().update();
        int transfer = body.update(objects);
        this.checkPlayerTransfer(transfer);
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

    public class PlayerBody extends DynamicBody implements Serializable
    {

        public PlayerBody() {
            speedLimit = 5;
        }

        public void setAccelerando(boolean accelerando) {
            this.accelerando = accelerando;
        }

        public void setPotenciaAcceleracion(int strength) {
            this.potencia_aceleracion = strength;
        }

        @Override
        public int update(List<GameObject> objects) {
            int transfer = super.update(objects);
            return transfer;
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

    //up 0, right 1, down 2, left 3
    private void checkPlayerTransfer(int transfer){
        if (transfer != -1){
            switch (transfer){
                case 0:
                    this.transferingTo = GameEngine.getConnections()[0];
                    this.transferingSide = 0;
                    GameEngine.addTransferingObject(this);
                    break;

                case 1:
                    this.transferingTo = GameEngine.getConnections()[1];
                    this.transferingSide = 1;
                    GameEngine.addTransferingObject(this);
                    break;

                case 2:
                    this.transferingTo = GameEngine.getConnections()[2];
                    this.transferingSide = 2;
                    GameEngine.addTransferingObject(this);
                    break;

                case 3:
                    this.transferingTo = GameEngine.getConnections()[3];
                    this.transferingSide = 3;
                    GameEngine.addTransferingObject(this);
                    break;
            }
        }
    }

    public void repositionBeforeTransfer() {
        switch (transferingSide) {
            case 0:
            case 2:
                float x = this.body.getPosX();
                int totalWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
                this.body.setPosX((x * 100) / totalWidth);
                break;

            case 1:
            case 3:
                float y = this.body.getPosY();
                int totalHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
                this.body.setPosY((y * 100) / totalHeight);
                break;
        }
    }

    public void repositionAfterTransfer() {
        switch (transferingSide) {
            case 0:
                this.body.setPosY(Toolkit.getDefaultToolkit().getScreenSize().height-70);
                this.body.setPosX((this.body.getPosX() * Toolkit.getDefaultToolkit().getScreenSize().width) / 100);
                break;

            case 1:
                this.body.setPosX(70);
                this.body.setPosY((this.body.getPosY() * Toolkit.getDefaultToolkit().getScreenSize().height) / 100);
                break;

            case 2:
                this.body.setPosY(70);
                this.body.setPosX((this.body.getPosX() * Toolkit.getDefaultToolkit().getScreenSize().width) / 100);
                break;

            case 3:
                this.body.setPosX(Toolkit.getDefaultToolkit().getScreenSize().width-70);
                this.body.setPosY((this.body.getPosY() * Toolkit.getDefaultToolkit().getScreenSize().height) / 100);
                break;
        }
    }

}
