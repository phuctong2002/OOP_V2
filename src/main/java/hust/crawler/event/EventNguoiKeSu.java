package hust.crawler.event;

import hust.crawler.Crawler;
import hust.model.Event;
import hust.util.JsonHandler;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventNguoiKeSu extends Crawler {

    public EventNguoiKeSu(){
        setData(JsonHandler.readFile("Event.json"));
        get();
    }
    @Override
    public void get() {
        String url = "https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=start=0&start=";
        for(int i = 0; i <= 70; i = i + 5){
            try {
                Document document = Jsoup.connect(url + Integer.toString(i)).get();
                Elements item = document.select(".readmore");
                for(Element e : item){
                    try {
                        String link = e.select("a").attr("href");

                        Document doc = Jsoup.connect("https://nguoikesu.com/" + link).get();
                        String time = getTime(doc.select("table table table tr >td:contains(Thời gian)").first());
                        if(time != null){
                            handleNewData(time,doc);
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        JsonHandler.writeFile("Event.json",data);
    }

    private String getTime(Element element) {
        if (element == null) return null;
        element =element.nextElementSibling();
        String time = element.text().replace("\u2013", "-");
        if (time.toLowerCase().contains("năm"))
            time = time.toLowerCase();
        else
            time = "năm " + time;
        return time;
    }
    private String getName(Element element){
        if (element == null) return null;
        return element.text();
    }

    public String getResult(Element element) {
        if (element == null) return null;
        element =element.nextElementSibling();
        return element.text();
    }

    public String getSummary(Element element) {
        if (element == null) return null;
        return element.text();
    }

    public List<String> getRelatedInformation(Element element) {
        if (element == null) return null;
        List<String> relatedInformation = new ArrayList<>();

        Elements a = element.select("a");
        for (Element i : a)
            relatedInformation.add(i.text());

        return relatedInformation;
    }

    @Override
    public JSONObject findObject(String time) {
        for( int i = 0; i < data.size(); ++i){
            JSONObject obj = (JSONObject) data.get(i);
            if( ((String) obj.get("thời gian")).toLowerCase().equals(time.toLowerCase())){
                return obj;
            }
        }
        return null;
    }

    public void handleNewData(String time, Element element){
        JSONObject tmp = findObject(time);
        if( tmp == null){
            // khong ton tai o day nhe
            JSONObject jsonObject = new JSONObject();
            Event event = new Event();
            event.setTime(time);
            event.setName(getName(element.select("table table table tr td").first()));
            event.setSummary(getSummary(element.select("p").first()));
            event.setResult(getResult(element.select("table table table tr >td:contains(Kết quả)").first()));
            event.setRelatedInfo(getRelatedInformation(element.select("p").first()));
            event.loadField(jsonObject);
//            System.out.println(jsonObject);
            data.add(jsonObject);
        }else{
            Event event = new Event(tmp);
            if (event.getRelatedInfo() == null)
                event.setRelatedInfo(getRelatedInformation(element.select("p").first()));
            if (event.getSummary() == null)
                event.setSummary(getSummary(element.select("p").first()));
            if (event.getResult() == null)
                event.setResult(getResult(element.select("table table table tr >td:contains(Kết quả)").first()));
            System.out.println(tmp);
            event.loadField(tmp);
        }
    }
}
