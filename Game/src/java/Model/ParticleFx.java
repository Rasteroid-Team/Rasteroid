package Model;

import Controller.GameControl;
import View.Objects.MachineState;
import View.Objects.ObjectModels.ObjectModel;

import java.awt.*;
import java.util.List;

public class ParticleFx extends GameObject {

    private MachineState particle_state;
    private int x, y;

    public ParticleFx(ObjectModel model, int spawn_x, int spawn_y)
    {
        invencible = true;
        particle_state = model.get_machine_states().get(0);
        x = spawn_x;
        y = spawn_y;
    }

    @Override
    public void update(List<GameObject> objects) {
        if (!particle_state.get_animation().has_finished())
        {
            particle_state.get_animation().update();
        }
        else
        {
            GameControl.remove_object(this);
        }
    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.drawImage(particle_state.get_animation().get_current_sprite().get_image(), x, y, null);
    }

    @Override
    public void take_damage(float damage) {}
}
