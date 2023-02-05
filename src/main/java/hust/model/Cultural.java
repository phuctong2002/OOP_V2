package hust.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Cultural extends Entity {
    private String location;
    private String time;
    private String firstTime;
    private List<String> relatedCharacter = new ArrayList<>();

    @Override
    public void loadField(JSONObject jsonObject) {
        JSONParser parser = new JSONParser();
        String strrelatedCharacter = JSONArray.toJSONString(relatedCharacter);
        JSONArray jsonRelatedCharacter = new JSONArray();
        try {
            jsonRelatedCharacter = (JSONArray) parser.parse(strrelatedCharacter);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        jsonObject.put("tên", getName());
        jsonObject.put("địa điểm", location);
        jsonObject.put("thời gian", time);
        jsonObject.put("tổ chức lần đầu", firstTime);
        jsonObject.put("nhân vật liên quan", jsonRelatedCharacter);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public List<String> getRelatedCharacter() {
        return relatedCharacter;
    }

    public void setRelatedCharacter(List<String> relatedCharacter) {
        this.relatedCharacter = relatedCharacter;
    }

    public Cultural(JSONObject jsonObj) {
        if (jsonObj.get("tên") != null)
            setName((String) jsonObj.get("tên"));
        else setName(null);
        if (jsonObj.get("địa điểm") != null)
            location = (String) jsonObj.get("địa điểm");
        else location = null;
        if (jsonObj.get("thời gian") != null)
            time = (String) jsonObj.get("thời gian");
        else time = null;
        if (jsonObj.get("tổ chức lần đầu") != null)
            firstTime = (String) jsonObj.get("tổ chức lần đầu");
        else firstTime = null;
        if (jsonObj.get("nhân vật liên quan") != null) {
            JSONArray arr = (JSONArray) jsonObj.get("nhân vật liên quan");
            if (arr != null) {
                relatedCharacter = new ArrayList<String>();
                for (Object o : arr) {
                    relatedCharacter.add((String) o);
                }
            }
        } else relatedCharacter = null;

    }

    public Cultural() {
        setName(null);
        this.location = null;
        this.time = null;
        this.firstTime = null;
        this.relatedCharacter = null;
    }
}
