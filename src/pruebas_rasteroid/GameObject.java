/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_rasteroid;

/**
 *
 * @author alfon
 */
public class GameObject {
    private boolean visible;
    private float health;
    private boolean invencible;
    private Body staticBody;
    private DynamicBody dynamicBody;
    
    public GameObject(DynamicBody dynamicBody){
        this.visible = true;
        this.health = 100;
        this.invencible = false;
        this.dynamicBody = dynamicBody;
    }
    
    public GameObject(Body staticBody){
        this.visible = true;
        this.health = 100;
        this.invencible = false;
        this.staticBody = staticBody;
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

    public Body getStaticBody() {
        return staticBody;
    }

    public void setStaticBody(Body staticBody) {
        this.staticBody = staticBody;
    }

    public DynamicBody getDynamicBody() {
        return dynamicBody;
    }

    public void setDynamicBody(DynamicBody dynamicBody) {
        this.dynamicBody = dynamicBody;
    }
    
    
    
}
