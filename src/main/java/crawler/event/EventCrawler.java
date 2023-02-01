package crawler.event;

import crawler.Crawler;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class EventCrawler extends Crawler {
    protected String time;
    protected String name;
    protected List<String> relatedInformation = new ArrayList<>();
    protected String summary;
    protected String result;


    public JSONObject findObject(JSONObject newObj) {
        for( int i = 0; i < data.size(); ++i){
            JSONObject obj = (JSONObject) data.get(i);
            if(isMatchData(obj,newObj)){
                return obj;
            }
        }
        return null;
    }

    public void handleNewData(JSONObject newObj){
        JSONObject oldObj = findObject(newObj);
        if(oldObj == null)
            data.add(newObj);
        else{
            Iterator<?> keys = newObj.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (!checkField(key, oldObj) && checkField(key, newObj))
                    oldObj.put(key, newObj.get(key));
            }
        }
    }

    public JSONObject getHistoricEvent() {
        JSONObject obj = new JSONObject();
        obj.put("tên", name);
        obj.put("thời gian", time);
        obj.put("thông tin liên quan", relatedInformation);
        obj.put("tóm tắt", summary);
        obj.put("kết quả", result);
        System.out.println(obj);
        return obj;
    }

    public boolean isMatchData(JSONObject obj1, JSONObject obj2){
        if (obj1.get("thời gian").toString().equals(obj2.get("thời gian").toString()))
            return true;
        else if (obj1.get("thời gian").toString().contains(obj2.get("thời gian").toString()))
            return true;
        else if (obj2.get("thời gian").toString().contains(obj1.get("thời gian").toString()))
            return true;
        return false;
    }

}
