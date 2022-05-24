package TestComunicacions.Rasteroid.pruebas_rasteroid;

import java.io.Serializable;

public class GameObject implements Serializable {
    private Body staticBody;
    private DynamicBody dynamicBody;
    
    public GameObject(DynamicBody dynamicBody){
        this.dynamicBody = dynamicBody;
    }
    
    public GameObject(Body staticBody){
        this.staticBody = staticBody;
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
