package hust.model;

import org.json.simple.JSONObject;

public abstract class Entity {
    private String name;
    public abstract void loadField(JSONObject jsonObject);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
