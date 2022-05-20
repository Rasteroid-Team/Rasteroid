package PruebasRasteroidFisicas.pruebas_rasteroid;


import Model.Player;
import PruebasRasteroidFisicas.pruebas_rasteroid.Objects.MachineState;
import PruebasRasteroidFisicas.pruebas_rasteroid.Objects.PlayerModels.PlayerModel;

import java.awt.*;
import java.util.List;

public abstract class Body {

    private float posX, posY;
    private int angle;
    private float radius = 50;
    
    //ESTO ES UN OBJETO ESTATICO
    //MAP SIZE --> 1000, 700
    public Body() {
        posX = 100f + (int) (Math.random() * 400f);
        posY = 50f + (int) (Math.random() * 400f);
        angle = 0;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
    
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }


    //GRAPHICS

    public Body(PlayerModel model)
    {
        posX = 100f + (int) (Math.random() * 400f);
        posY = 50f + (int) (Math.random() * 400f);
        angle = 0;
        machine_states = model.get_machine_states();
        current_state = model.get_machine_states().get(0);
    }

    //machine states and animations
    protected List<MachineState> machine_states;
    protected MachineState current_state;


    //update and render

    public void render(Graphics2D graphics) {}


    public void update(InputAdapter input) {
        current_state.get_animation().update();
    }
}
