package hust.crawler.event;

import hust.crawler.Crawler;
import hust.model.Event;
import hust.model.King;
import hust.util.JsonHandler;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventWiki extends Crawler {
    public EventWiki(){
//        setData(JsonHandler.readFile("Event.json"));
        get();
    }
    @Override
    public void get() {
        try {
            Document document = Jsoup.connect("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam").get();
            Elements elements = document.select(".mw-parser-output > p");
            System.out.println( elements.size());
            for (int j = 1; j < elements.size(); ++j) {
                Element element = elements.get(j);
                String time = getTime(element);
                String name = getName(element);
                System.out.println(time + "   :  " + name);
                if (name.equals(time)) {

                    Element dl = element.nextElementSibling();
                    Elements dd = dl.select("dd");
//                    getListHistoricEvent(dd);
                    for(Element e : dd){
                        time = "ngày " + getTime(e) + " năm " + time;
                        getNewData(time,e);
                    }

                } else {
                    Event event = new Event();
//                    relatedInformation = getRelatedInformation(element);
//                    summary = getSummary(element);
//                    handleNewData(getHistoricEvent());
                    getNewData("năm " + time,element);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonHandler.writeFile("Event.json",data);
    }

    private String getTime(Element element) {
        return element.select("b").text().replace("\u2013", "-");
    }
    private String getName(Element element){
        String name = element.text().replace("\u2013", "-");
        name = name.replace(getTime(element) + " ", "");
        if(name.contains(","))
            return name.split(",")[0];
        return name;
    }

    public String getResult(Element element) {
        return null;
    }

    public String getSummary(Element element) {
        StringBuilder summary = new StringBuilder();
        try {
            Element a = element.select("a").first();
            String url = a.attr("href");
            Document document = Jsoup.connect("https://vi.wikipedia.org" + url).get();
            Element p = document.select(".mw-parser-output > p").first();
            while (p.tagName().equals("p")) {
                summary.append(p.text());
                p = p.nextElementSibling();
            }

        } catch (Exception e) {

        }
        return summary.toString().replace("\u2013", "-");
    }

    public List<String> getRelatedInformation(Element element) {
        List<String> relatedInformation = new ArrayList<>();
        Elements a = element.select("a");
        for (Element i : a) {
            if (!(i.text().equals(getName(element))))
                relatedInformation.add(i.text());
        }
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

    public void getNewData(String time, Element element){
        JSONObject tmp = null;
        if( tmp == null){
            // khong ton tai o day nhe
            JSONObject jsonObject = new JSONObject();
            Event event = new Event();
            event.setTime(time);
            event.setName(getName(element));
            event.setSummary(getSummary(element));
            event.setResult(getResult(element));
            event.setRelatedInfo(getRelatedInformation(element));
            event.loadField(jsonObject);
            data.add(jsonObject);
        }else{
            Event event = new Event(tmp);
            if (event.getRelatedInfo() == null) event.setRelatedInfo(getRelatedInformation(element));
            if (event.getSummary() == null) event.setSummary(getSummary(element));
            if (event.getResult() == null) event.setResult(getResult(element));
            event.loadField(tmp);
        }
    }

}
