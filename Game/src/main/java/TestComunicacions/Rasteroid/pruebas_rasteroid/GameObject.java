package pruebas_rasteroid;

public class GameObject {
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
