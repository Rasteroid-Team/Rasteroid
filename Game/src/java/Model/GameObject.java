package Model;

import Testing.InputAdapter;
import View.Objects.MachineState;
import View.Objects.PlayerModels.HR75;
import View.Objects.PlayerModels.PlayerModel;
import View.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class GameObject {
    private Body body;
    private boolean visible;
    private float health;
    private boolean invencible;
    private int currentState = 0;
    private List<MachineState> stateList;

    public GameObject(Body body, boolean visible, float health, boolean invencible, PlayerModel model) {
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

    public void update(InputAdapter input) {
        if (input.get_active_keys()[0])
        {
            currentState = 1;
        }
        else
        {
            currentState = 0;
        }
        stateList.get(currentState).get_animation().update();
        body.update(input);
    }

    public void render(Graphics2D graphics)
    {

        BufferedImage output_image;

        output_image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = output_image.createGraphics();
        g2.drawImage(stateList.get(currentState).get_animation().get_current_sprite().get_image(), 0, 0, 100, 100, null);
        g2.dispose();

        float pos_x = body.getPosX();
        float pos_y = body.getPosY();
        float orientation = body.getAngle();

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate( pos_x, pos_y );
        affineTransform.rotate( Math.toRadians( orientation ) );
        affineTransform.translate(-50, -50);
        affineTransform.scale(1,1);

        graphics.drawImage(output_image, affineTransform, null);
        //graphics.drawOval((int)(body.getPosX()-body.getRadius()), (int)(body.getPosY()-body.getRadius()), (int)body.getRadius()*2, (int)body.getRadius()*2);
    }

}
