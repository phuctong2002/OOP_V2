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

    @Override
    public JSONObject findObject(String time) {
        for( int i = 0; i < data.size(); ++i){
            JSONObject obj = (JSONObject) data.get(i);
            if(((String) obj.get("thời gian")).toLowerCase().equals(time.toLowerCase())){
                return obj;
            }
        }
        return null;
    }

    public void handleNewData(JSONObject newObj){
        JSONObject oldObj = findObject(newObj.get("thời gian").toString());
        if(oldObj == null)
            data.add(newObj);
        else{
            Iterator<?> keys = newObj.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (checkField(key, oldObj) && checkField(key, newObj))
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
        System.out.println(obj);
        return obj;
    }

}
