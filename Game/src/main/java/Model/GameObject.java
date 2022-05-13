package Model;

import View.Animation;

import java.awt.*;
import java.util.HashMap;

public class GameObject {
    protected boolean visible;
    protected float health;
    protected boolean invincible;
    protected HashMap<String, Animation> animationMap;
    protected Animation currentAnimation;
    protected Body body;

    /*--------------------
        Getters/Setters
     --------------------*/

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

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public HashMap<String, Animation> getAnimationMap() {
        return animationMap;
    }

    public void setAnimationMap(HashMap<String, Animation> animationMap) {
        this.animationMap = animationMap;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    /*--------------------
            Constructor
     --------------------*/

    //Basic gameObject
    public GameObject(Body body) {
        this.visible = true;
        this.health = 100;
        this.invincible = false;
        this.body = body;
        //needs animations
    }

    /*--------------------
            Methods
     --------------------*/

    public void damage(float damage) {
        this.health -= damage;
        if (this.health <= 0) {     //Correct?
            destroy();
        }
    }

    public void destroy() {
        //this.visible = false;     Correct?
        GameScreen.removeGameObject(this);
    }

    public void setAnimationState(String animationState) {
    }

    public void drawTo(Graphics graphics) {
        //Waiting for physics and graphics
    }
}
