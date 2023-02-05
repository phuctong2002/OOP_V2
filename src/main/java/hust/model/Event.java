package hust.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Event extends Entity {
    private String time;
    private List<String> relatedInfo;
    private String summary;
    private String result;


    public Event() {
        setName(null);
        this.time = null;
        this.result = null;
        this.summary = null;
        this.relatedInfo = null;
    }

    public Event(JSONObject jsonObject) {
        setName((String) jsonObject.get("tên"));
        setTime((String) jsonObject.get("thời gian"));
        setSummary((String) jsonObject.get("tóm tắt"));
        setResult((String) jsonObject.get("kết quả"));

        JSONArray arr = (JSONArray) jsonObject.get("thông tin liên quan");
        if (arr != null) {
            relatedInfo = new ArrayList<String>();
            for (int i = 0; i < arr.size(); ++i) {
                relatedInfo.add((String) arr.get(i));
            }
        }
    }

    @Override
    public void loadField(JSONObject jsonObject) {
        JSONParser parser = new JSONParser();
        String strRelatedInfo = JSONArray.toJSONString(relatedInfo);
        JSONArray jsonRelatedInfo = new JSONArray();
        try {
            jsonRelatedInfo = (JSONArray) parser.parse(strRelatedInfo);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        jsonObject.put("tên", getName());
        jsonObject.put("thời gian", time);
        jsonObject.put("thông tin liên quan", jsonRelatedInfo);
        jsonObject.put("tóm tắt", summary);
        jsonObject.put("kết quả", result);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setRelatedInfo(List<String> relatedInfo) {
        this.relatedInfo = relatedInfo;
    }

    public String getTime() {
        return time;
    }

    public String getResult() {
        return result;
    }

    public String getSummary() {
        return summary;
    }

    public List<String> getRelatedInfo() {
        return relatedInfo;
    }
}
