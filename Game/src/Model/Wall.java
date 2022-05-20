package Model;

import static Model.GameConstants.*;

public class Wall {

    public boolean active;
    public float health;
    public boolean unbreakable;
    public boolean corner;
    public boolean orientation;

    /*--------------------
        Constructor
     --------------------*/

    //Basic constructor
    public Wall(int pos) {
        switch (pos) {
            case UP, DOWN -> this.orientation = HORIZONTAL;
            case RIGHT, LEFT -> this.orientation = VERTICAL;
        }
        this.active = false;
        this.health = 100;
        this.corner = false;
        this.unbreakable = true;
    }

}
