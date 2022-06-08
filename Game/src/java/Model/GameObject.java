package Model;

import Controller.ConnectionController;
import Controller.ScreenConnectionController;
import Testing.InputAdapter;
import View.Objects.MachineState;
import View.Objects.ObjectModels.Players.HR75;
import View.Objects.ObjectModels.ObjectModel;
import View.Resources;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.Serializable;
import java.util.List;

public class GameObject implements Serializable {
    protected Body body;
    protected boolean visible;
    protected float health;
    protected boolean invencible;
    protected int currentState = 0;
    protected List<MachineState> stateList;
    protected String transferingTo;
    protected int transferingSide;
    protected int transfer = -1;

    public GameObject(Body body, boolean visible, float health, boolean invencible, ObjectModel model) {
        this.body = body;
        this.visible = visible;
        this.health = health;
        this.invencible = invencible;
        this.stateList = model.get_machine_states();
    }

    public GameObject() {
        this.body = new DynamicBody();
        this.visible = true;
        this.health = 100;
        this.invencible = false;
        this.stateList = new HR75().get_machine_states();
    }

    public GameObject(Body body){
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body staticBody) {
        this.body = staticBody;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isInvencible() {
        return invencible;
    }

    public void setInvencible(boolean invencible) {
        this.invencible = invencible;
    }


    public void setStateList(List<MachineState> stateList) {
        this.stateList = stateList;
    }

    public String getTransferingTo() {
        return transferingTo;
    }

    public int getTransferingSide() {
        return transferingSide;
    }

    public void update(List<GameObject> objects) {
        this.checkObjectTransfer();
    }

    public void render(Graphics2D graphics)
    {

        BufferedImage output_image;

        BufferedImage image = stateList.get(currentState).get_animation().get_current_sprite().get_image();
        int width = image.getWidth();
        int height = image.getHeight();

        output_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = output_image.createGraphics();
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();

        float pos_x = body.getPosX();
        float pos_y = body.getPosY();
        float orientation = body.getAngle();

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate( pos_x, pos_y );
        affineTransform.rotate( Math.toRadians( orientation ) );
        affineTransform.translate(-width/2.0, -height/2.0);
        affineTransform.scale(1,1);

        graphics.drawImage(output_image, affineTransform, null);
        //graphics.drawOval((int)(body.getPosX()-body.getRadius()), (int)(body.getPosY()-body.getRadius()), (int)body.getRadius()*2, (int)body.getRadius()*2);
        //draw ui
        draw_life_bar(graphics);
    }

    public void take_damage(float damage)
    {
        if (!invencible)
        {
            health -= damage;
            if (health < 0) {health = 0;}
            show_life_bar();
        }
        if (health <= 0)
        {
            die();
        }
    }

    public void die() {}

    //ui methods
    private double life_bar_screen_time = 1.5;
    private long life_bar_time = 0;

    private void draw_life_bar(Graphics2D graphics)
    {
        if (System.currentTimeMillis() - life_bar_time < life_bar_screen_time*1000)
        {
            int offsetY = 40;
            int width = 140;
            int height = 10;
            int radius = 10;
            int last_life_percent = (int) (health*width/100);
            //draw method
            {
                graphics.setRenderingHint
                (
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );
                graphics.setColor(Color.darkGray);
                graphics.drawImage(Resources.UI_LIFE_BAR.get_image(), (int) (getBody().getPosX()-(width/2)), (int) (getBody().getPosY()+offsetY), null);
                graphics.setColor(Color.RED);
                graphics.fillRoundRect((int) (getBody().getPosX()-(width/2)+3), (int) (getBody().getPosY()+offsetY+12), last_life_percent-3, height-4, radius, radius);
                graphics.setRenderingHint
                (
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF
                );
            }
        }
    }

    private void show_life_bar() {
        life_bar_time = System.currentTimeMillis();
    }

    //up 0, right 1, down 2, left 3
    private void checkObjectTransfer(){
        if (this.transfer != -1){
            switch (this.transfer){
                case 0:
                    this.transferingTo = ScreenConnectionController.getConnections()[0];
                    this.transferingSide = 0;
                    this.transfer = -1;
                    ConnectionController.addTransferingObject(this);
                    break;

                case 1:
                    this.transferingTo = ScreenConnectionController.getConnections()[1];
                    this.transferingSide = 1;
                    this.transfer = -1;
                    ConnectionController.addTransferingObject(this);
                    break;

                case 2:
                    this.transferingTo = ScreenConnectionController.getConnections()[2];
                    this.transferingSide = 2;
                    this.transfer = -1;
                    ConnectionController.addTransferingObject(this);
                    break;

                case 3:
                    this.transferingTo = ScreenConnectionController.getConnections()[3];
                    this.transferingSide = 3;
                    this.transfer = -1;
                    ConnectionController.addTransferingObject(this);
                    break;
            }
        }
    }
}
