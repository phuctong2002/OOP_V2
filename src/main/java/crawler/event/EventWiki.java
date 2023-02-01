package crawler.event;

import crawler.Crawler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.JsonHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static util.JsonHandler.writeFile;

public class EventWiki extends EventCrawler implements IEvent {


    public EventWiki(){
        setData("Event.json");
        get();
        saveData("Event.json");
    }

    @Override
    public void get() {
        try {
            Document document = Jsoup.connect("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam").get();
            Elements elements = document.select(".mw-parser-output > p");
            System.out.println( elements.size());
            for (int j = 1; j < elements.size(); ++j) {
                Element element = elements.get(j);
                time = getTime(element);
                name = getName(element);
                if (name.equals(time)) {
                    Element dl = element.nextElementSibling();
                    Elements dd = dl.select("dd");
                    getListHistoricEvent(dd);
                } else {
                    relatedInformation = getRelatedInformation(element);
                    summary = getSummary(element);
                    handleNewData(getHistoricEvent());
                }
                System.out.println(j);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTime(Element element) {
        return element.select("b").text();
    }

    @Override
    public String getName(Element element) {
        return element.text().replace(getTime(element) + " ", "");
    }

    @Override
    public String getSummary(Element element) {
        StringBuilder summary = new StringBuilder();
        try {
            Elements a = element.select("a");
            if (a.size() > 1) return null;
            String url = a.attr("href");
            Document document = Jsoup.connect("https://vi.wikipedia.org" + url).get();
            Element p = document.select(".mw-parser-output > p").first();
            while (p.tagName().equals("p")) {
                summary.append(p.text());
                p = p.nextElementSibling();
            }

        } catch (Exception e) {

        }
        return summary.toString();
    }

    @Override
    public List<String> getRelatedInformation(Element element) {
        List<String> relatedInformation = new ArrayList<>();
        Elements a = element.select("a");
        for (Element i : a) {
            if (!(i.text().equals(name)))
                relatedInformation.add(i.text());
        }
        return relatedInformation;
    }

    public void getListHistoricEvent(Elements elements) {
        for (Element element : elements) {
            time = getTime(element) + " năm " + time;
            name = getName(element);
            relatedInformation = getRelatedInformation(element);
            summary = getSummary(element);
            handleNewData(getHistoricEvent());
        }
    }


}
