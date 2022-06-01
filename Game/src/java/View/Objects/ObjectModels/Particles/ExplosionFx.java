package View.Objects.ObjectModels.Particles;

import View.Objects.Animation;
import View.Objects.MachineState;
import View.Objects.ObjectModels.ObjectModel;

import java.util.ArrayList;

public class ExplosionFx extends ObjectModel {

    private String res_path = "Game/src/Resources/misc/particles/explosionfx/";

    @Override
    public void load_states() {
        machine_states = new ArrayList<>();

        //MAP STATE
        MachineState explosion = new MachineState("explosionfx");
        {
            Animation explosion_animation = new Animation();
            explosion_animation.set_delay(2);
            explosion_animation.set_loop(false);
            load_sprite_in_range(explosion_animation, res_path + "explosionfx_", 10, 100, 100);
            explosion.set_animation(explosion_animation);
        }
        machine_states.add(explosion);
    }

}
