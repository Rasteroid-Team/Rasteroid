package Model;

import Testing.InputAdapter;
import View.Objects.MachineState;
import View.Objects.ObjectModels.Players.HR75;
import View.Objects.ObjectModels.ObjectModel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class GameObject {
    protected Body body;
    protected boolean visible;
    protected float health;
    protected boolean invencible;
    protected int currentState = 0;
    protected List<MachineState> stateList;

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

    public void update(List<GameObject> objects) {

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
            System.out.println("damaged");
            health -= damage;
            if (health < 0) {health = 0;}
            System.out.println("HEALTH = "+health);
            show_life_bar();
        }
    }


    //ui methods
    private double life_bar_screen_time = 1.5;
    private long life_bar_time = 0;

    private void draw_life_bar(Graphics2D graphics)
    {
        if (System.currentTimeMillis() - life_bar_time < life_bar_screen_time*1000)
        {
            int offsetY = 30;
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
                graphics.fillRoundRect((int) (getBody().getPosX()-(width/2)), (int) (getBody().getPosY()+offsetY), width, height, radius, radius);
                graphics.setColor(Color.RED);
                graphics.fillRoundRect((int) (getBody().getPosX()-(width/2)), (int) (getBody().getPosY()+offsetY), last_life_percent, height, radius, radius);
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
}
