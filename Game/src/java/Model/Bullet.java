package Model;

import Controller.GameControl;
import Testing.InputAdapter;
import View.Objects.ObjectModels.ObjectModel;

import java.util.List;

public class Bullet extends GameObject {
    protected String ownerName;
    protected float damage;
    protected float angle;

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

    public Bullet(Player owner, ObjectModel model)
    {
        super(null, true, 1, true, model);
        damage = 5;
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
            setPosX((float) (owner.getBody().getPosX() + 50*Math.cos(Math.toRadians(angle-90))));
            setPosY((float) (owner.getBody().getPosY() + 50*Math.sin(Math.toRadians(angle-90))));
            {
                // conserve relative speed moment at shoot
                DynamicBody owner_body = (DynamicBody) owner.getBody();
                setSpeedX(owner_body.getSpeedY());
                setSpeedY(owner_body.getSpeedY());

            }
            accelerando = true;
            setAngle(owner.getBody().getAngle());
            anguloFuerza = (int) angle;
            potencia_aceleracion = 300;
            speedLimit = 300;
        }

        @Override
        public void update(List<GameObject> objects) {
            super.update(objects);
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
