package hust.crawler.cultural;

import hust.crawler.Crawler;
import hust.model.Cultural;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CulturalWiki extends Crawler {
    public CulturalWiki() {
        setData("Cultural.json");
//        setData(JsonHandler.readFile("Cultural.json"));
    }
    @Override
    public void get() {
        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#";
        try {
            Document document = Jsoup.connect(url).get();
            Elements table = document.getElementsByClass("prettytable wikitable");
            Elements elements = Objects.requireNonNull(table.first()).getElementsByTag("tr");
            for (int j = 1; j < elements.size(); j++) {
                try {
                    Elements d = elements.get(j).select("td");
                    String name = getName(d.get(2));
//                    JSONObject obj = new JSONObject();
                    JSONObject tmp = findObject(name);
                    if (tmp == null) {
                        JSONObject jsonObject = new JSONObject();
                        Cultural ctr = new Cultural();
                        ctr.setName(name);
                        ctr.setTime(getTime(d.get(0)));
                        ctr.setLocation(getLocation(d.get(1)));
                        ctr.setFirstTime(getFirstTime(d.get(3)));
                        ctr.setRelatedCharacter(getRelatedCharacter(d.get(4)));
                        ctr.loadField(jsonObject);
                        getData().add(jsonObject);
                    } else {
                        Cultural ctr = new Cultural(tmp);
                        if (ctr.getTime() == null) ctr.setTime(getTime(d.get(0)));
                        if (ctr.getLocation() == null) ctr.setLocation(getLocation(d.get(1)));
                        if (ctr.getFirstTime() == null) ctr.setFirstTime(getFirstTime(d.get(3)));
                        if (ctr.getRelatedCharacter() == null) ctr.setRelatedCharacter(getRelatedCharacter(d.get(4)));
                        ctr.loadField(tmp);
                    }
                } catch (Exception e) {
                    System.out.println("Error getData in CulturalWiki 1");
                }
            }
        } catch (IOException e) {
            System.out.println("Error getData in DynastyWiki 2");
        }
        saveData("Cultural.json");
//        JsonHandler.writeFile("Cultural.json", getData());
    }
    private List<String> getRelatedCharacter(Element element) {
        List<String> relatedCharacter = new ArrayList<>();
        String a = element.select("td").text();
        Collections.addAll(relatedCharacter, a.split(", ", 0));
        return relatedCharacter;
    }


    private String getTime(Element element) {
        return element.text();
    }

    private String getFirstTime(Element element) {
        return element.text();
    }

    public static String getLocation(Element element) {
        return element.text();
    }

    private String getName(Element element) {
        return element.text();
    }
}
