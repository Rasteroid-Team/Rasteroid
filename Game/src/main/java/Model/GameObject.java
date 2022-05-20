package Model;

import View.Animation;

import java.util.HashMap;

public class GameObject {
    private Body body;
    private boolean visible;
    private float health;
    private boolean invencible;
    private HashMap<String, Animation> animationMap;
    private Animation currentAnimation;

    public GameObject(Body body, boolean visible, float health, boolean invencible, HashMap<String, Animation> animationMap, Animation currentAnimation) {
        this.body = body;
        this.visible = visible;
        this.health = health;
        this.invencible = invencible;
        this.animationMap = animationMap;
        this.currentAnimation = currentAnimation;
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
}
