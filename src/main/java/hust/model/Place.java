package hust.model;

import org.json.simple.JSONObject;


public class Place extends Entity{
    private String location;
    private String brief;
    @Override
    public void loadField(JSONObject jsonObject) {
        jsonObject.put("tên", getName());
        jsonObject.put("địa điểm", location);
        jsonObject.put("tóm tắt", brief);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }


    public Place( JSONObject jsonObject){
        if( jsonObject.get("tên") != null)
            setName( (String) jsonObject.get("tên"));
        else setName(null);
        if( jsonObject.get( "location") != null)
            setLocation( (String) jsonObject.get("địa điểm"));
        else setLocation(null);
        if( jsonObject.get("tóm tắt") != null)
            setBrief((String) jsonObject.get("tóm tắt"));
        else setBrief( null);
    }

    public Place( ){
        setName(null);
        location = null;
        brief = null;
    }

    public String getLocation() {
        return location;
    }

    public String getBrief() {
        return brief;
    }

}
