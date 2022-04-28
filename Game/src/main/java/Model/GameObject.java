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
            Methods
     --------------------*/

    public void damage(float damage) {
    }

    public void destroy() {
    }

    public void setAnimationState(String animationState) {
    }

    public void drawTo(Graphics graphics) {
    }
}
