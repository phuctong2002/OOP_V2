package hust.crawler.event;

import hust.crawler.Crawler;
import hust.util.JsonHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class EventWiki extends Crawler {
    public EventWiki(){
        setData(JsonHandler.readFile("Event.json"));
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

//                    Element dl = element.nextElementSibling();
//                    Elements dd = dl.select("dd");
//                    getListHistoricEvent(dd);
                } else {
//                    relatedInformation = getRelatedInformation(element);
//                    summary = getSummary(element);
//                    handleNewData(getHistoricEvent());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTime(Element element) {
        return element.select("b").text();
    }
    private String getName(Element element){
        String name = element.text().replace(getTime(element) + " ", "");
        if(name.contains(","))
            return name.split(",")[0];
        return name;
    }
}
