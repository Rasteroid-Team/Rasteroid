package Model;

import Controller.ConnectionController;
import Controller.GameControl;
import Controller.GameEngine;
import Controller.ScreenConnectionController;
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
        super(null, true, player_model.get_meta().health_points, false, player_model);
        color = player_color;
        model = player_model;
        model.set_aura_color(color);
        setBody(new PlayerBody());
        this.bulletDamage = model.get_meta().damage_per_bullet;
        this.bulletSpeed = 1;
        this.ammo = 10000;
        this.killCount = 0;
        this.fire_interval_seconds = model.get_meta().shoot_interval;
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

        /*
            Debug para el spawn de las balas
        */
        //graphics.drawRect((int) (body.getPosX()-5), (int) (body.getPosY()-5), 5, 5);
        ////bullets_spawn_points
        //graphics.setColor(Color.RED);
        //for (int[] bullet_spawn_xy:model.get_meta().bullet_offset_x_y_list) {
        //    //formula
        //    int x = (int) (getPlayerBody().getPosX() + (bullet_spawn_xy[0]) * Math.cos(Math.toRadians(getPlayerBody().getAngle())) - (bullet_spawn_xy[1]) * Math.sin(Math.toRadians(getPlayerBody().getAngle())));
        //    int y = (int) (getPlayerBody().getPosY() + (bullet_spawn_xy[0]) * Math.sin(Math.toRadians(getPlayerBody().getAngle())) + (bullet_spawn_xy[1]) * Math.cos(Math.toRadians(getPlayerBody().getAngle())));
        //    //drawing
        //    graphics.drawRect(x-5, y-5, 5, 5);
        //}
    }


    public class PlayerBody extends DynamicBody implements Serializable
    {

        public PlayerBody() {
            speedLimit = Player.this.model.get_meta().velocity;
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

        @Override
        public void collision(GameObject object) {

            if(object instanceof Player){
               // System.out.println(getAngle());

                Player player = (Player)object;
                if(!player.equals(Player.this)){

                    DynamicBody obj2 = (DynamicBody) object.getBody();
                    float spFinalX;
                    float spFinalY;
                    //Get mass
                    float m1 = Player.this.getBody().getWeight();
                    float m2 = player.getBody().getWeight();
                    //Get current X speed
                    float v1X = this.getSpeedX();
                    float v2X = obj2.getSpeedX();

                    //Get current Y speed
                    float v1Y = this.getSpeedY();
                    float v2Y = obj2.getSpeedY();
                    //Calcule X final speed

                    //Formula para calcular velociadad final
                    float ejeX = m1 * v1X + m2 * v2X;
                    float ejeY = m1 * v1Y + m2 * v2Y;
                    float velFinal = (float) Math.atan(ejeX/ejeY);

                    //Calculo de velociadades en cada eje
                    spFinalX = (m1 * v1X + m2 * v2X) / ((m1+m2) * velFinal);
                    spFinalY= (m1 * v1Y + m2 * v2Y) / ((m1 + m2) * velFinal);
                    System.out.println("vel final: " + velFinal);
                    //Set velocidades


                    //spFinalX *= -spFinalX *0.8;
                    this.setSpeedX(spFinalX);
                    this.setSpeedY(spFinalY);

                    System.out.println("vel X: "+  spFinalX);
                    System.out.println("vel Y: "+  spFinalY);
                    System.out.println("\n");

                }
            }else if(object instanceof Bullet){
                System.out.println("Choque bala");
                //System.out.println(getHealth());

            }
        }
    }

    public void shoot() {
        if ((System.currentTimeMillis()-last_fire)/1000.0 >= fire_interval_seconds)
        {
            for (int[] bullet_off_x_y : model.get_meta().bullet_offset_x_y_list) {
                Bullet bullet = new Bullet(this, Resources.BULLET_YELLOW(), bullet_off_x_y[0], bullet_off_x_y[1], model.get_meta().damage_per_bullet);
                GameControl.add_object(bullet);
                last_fire = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void die() {
        super.die();
        GameControl.add_object(new ParticleFx(Resources.PARTICLE_EXPLOSION(), (int) (getBody().getPosX()-50), (int) (getBody().getPosY()-50)));
        GameControl.remove_object(this);
    }

    //up 0, right 1, down 2, left 3
    private void checkPlayerTransfer(int transfer){
        if (transfer != -1){
            switch (transfer){
                case 0:
                    this.transferingTo = ScreenConnectionController.getConnections()[0];
                    this.transferingSide = 0;
                    ConnectionController.addTransferingObject(this);
                    break;

                case 1:
                    this.transferingTo = ScreenConnectionController.getConnections()[1];
                    this.transferingSide = 1;
                    ConnectionController.addTransferingObject(this);
                    break;

                case 2:
                    this.transferingTo = ScreenConnectionController.getConnections()[2];
                    this.transferingSide = 2;
                    ConnectionController.addTransferingObject(this);
                    break;

                case 3:
                    this.transferingTo = ScreenConnectionController.getConnections()[3];
                    this.transferingSide = 3;
                    ConnectionController.addTransferingObject(this);
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
