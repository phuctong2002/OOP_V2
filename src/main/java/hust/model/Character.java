package hust.model;

import org.json.simple.JSONObject;

public class Character extends Person{
    private String job;

    @Override
    public void loadField(JSONObject jsonObject) {
        jsonObject.put("tên", getName());
        jsonObject.put("sinh", getBirth());
        jsonObject.put("mất", getDeath());
        jsonObject.put("công việc", job);
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    public Character( JSONObject jsonObject){
        if( jsonObject.get("tên") != null){
            setName( (String) jsonObject.get("tên"));
        }else setName(null);
        if( jsonObject.get("sinh") == null){
            setBirth((String) jsonObject.get("sinh"));
        }else setBirth(null);
        if( jsonObject.get("mất") == null){
            setDeath( (String) jsonObject.get("mất"));
        }else setDeath( null);
        if( jsonObject.get("công việc") == null){
            job = (String) jsonObject.get("công việc");
        }else job = null;
    }

    public Character(){
        setName(null);
        setBirth(null);
        setDeath(null);
        job = null;
    }


}
