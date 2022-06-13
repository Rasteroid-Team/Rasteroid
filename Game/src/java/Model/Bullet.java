package Model;

import Controller.GameControl;
import View.Objects.ObjectModels.ObjectModel;

import java.util.List;

public class Bullet extends GameObject {
    protected String ownerName;
    protected float damage;
    protected float angle;
    protected int offset_x;
    protected int offset_y;

    /*--------------------
        Constructor
     --------------------*/

    //Basic constructor
    public Bullet(Body body, String ownerName) {
        super(body);
        this.ownerName = null;
        this.damage = 5;
    }

    // ★ Pruebas Josel ★

    //TODO: Ajustar las nuevas implementaciones junto a las comunicaciones
    public Bullet(Player owner, ObjectModel model, int off_x, int off_y, int bullet_damage)
    {
        super(null, true, 1, true, model);
        damage = 0;
        offset_x = off_x;
        offset_y = off_y;
        ownerName = owner.getName();
        angle = owner.getBody().getAngle();
        BulletBody body = new BulletBody(owner);
        setBody(body);
    }

    @Override
    public void update(List<GameObject> objects) {
        super.update(objects);
        body.update(objects);
    }

    public class BulletBody extends DynamicBody
    {
        private Player player_owner;

        protected BulletBody(Player owner)
        {
            player_owner = owner;

            setRadius(20);
            int[] offset = lengthdir_xy(offset_x, offset_y, angle);
            setPosX(owner.getBody().getPosX() + offset[0]);
            setPosY(owner.getBody().getPosY() + offset[1]);
            {
                // conserve relative speed moment at shoot
                DynamicBody owner_body = (DynamicBody) owner.getBody();
                setSpeedX(owner_body.getSpeedY());
                setSpeedY(owner_body.getSpeedY());

            }
            accelerando = true;
            setAngle(owner.getBody().getAngle());
            anguloFuerza = (int) angle;
            potencia_aceleracion = 0;
            speedLimit = 300;
            frictionCofficient = 0;

            move(anguloFuerza, 1500);
        }

        public int[] lengthdir_xy(int offset_x, int offset_y, double angle)
        {
            int lenX = (int) ((offset_x) * Math.cos(Math.toRadians(angle)) - (offset_y) * Math.sin(Math.toRadians(angle)));
            int lenY = (int) ((offset_x) * Math.sin(Math.toRadians(angle)) + (offset_y) * Math.cos(Math.toRadians(angle)));

            return new int[]{lenX, lenY};
        }

        @Override
        public int update(List<GameObject> objects) {
            return super.update(objects);
        }

        @Override
        public void collision(GameObject object) {
            super.collision(object);
            if (object instanceof Map)
            {
                GameControl.remove_object(Bullet.this);
            }
            if (object instanceof Player && object != player_owner)
            {
                object.take_damage(damage);
                GameControl.remove_object(Bullet.this);
            }
        }
    }

    /*--------------------
        Getters/Setters
     --------------------*/


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
