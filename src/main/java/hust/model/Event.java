package hust.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class Event extends Entity{
    private String time;
    private List<String> relatedInfo;
    private String result;
    @Override
    public void loadField(JSONObject jsonObject) {
        JSONParser parser = new JSONParser();
        String strRelatedInfo = JSONArray.toJSONString( relatedInfo);
        JSONArray jsonRelatedInfo = new JSONArray();
        try{
            jsonRelatedInfo = (JSONArray) parser.parse( strRelatedInfo);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        jsonObject.put("tên", getName());
        jsonObject.put("thời gian", time);
        jsonObject.put("thông tin liên quan", jsonRelatedInfo);
        jsonObject.put("kết quả", result);
    }
}
