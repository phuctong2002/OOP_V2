package hust.crawler;

import hust.util.JsonHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public abstract class Crawler {
    protected JSONArray data = new JSONArray();
    public JSONObject findObject(String name){
        for( int i = 0; i < data.size(); ++i){
            JSONObject obj = (JSONObject) data.get(i);
            if( ((String) obj.get("tên")).toLowerCase().equals( name.toLowerCase())){
                return obj;
            }
        }
        return null;
    }
    protected void saveData( String fileName){
        JsonHandler.writeFile( fileName, data);
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
    public void setData(String fileName){
        setData(JsonHandler.readFile(fileName));
    }

    public JSONArray getData() {
        return data;
    }
    public abstract void get();
}
