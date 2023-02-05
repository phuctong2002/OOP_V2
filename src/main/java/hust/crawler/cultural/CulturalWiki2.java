package hust.crawler.cultural;

import hust.crawler.Crawler;
import hust.model.Cultural;
import hust.util.JsonHandler;
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

public class CulturalWiki2 extends Crawler {
    public CulturalWiki2() {
        setData("Cultural.json");
//                (JsonHandler.readFile("Cultural.json"));

    }

    @Override
    public void get() {
        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#";
        try {
            Document document = Jsoup.connect(url).get();
            Elements table2 = document.getElementsByClass("mw-parser-output");
            Elements tds = Objects.requireNonNull(table2.first()).getElementsByTag("ul");
            Elements td = tds.get(10).select("li");
            for (Element element : td) {
                //tach dia diem, le hoi
                List<String> list = new ArrayList<>();
                String a = element.select("li").text();
                Collections.addAll(list, a.split(": ", 0));
                String location = list.get(0);
                // tach le hoi
                List<String> listFes = new ArrayList<>();
                String tmp = list.get(1).replaceAll("\\(.*?\\)", "");
                Collections.addAll(listFes, tmp.split("[,\\;]", 0));
                // tach ngay
                for (int k = 0; k < listFes.size(); k++) {
                    JSONObject obj = new JSONObject();
                    String str = listFes.get(k).replaceAll("[^0-9/-]", " ");
                    List<String> m = new ArrayList<>();
                    Collections.addAll(m, str.split(" ", 0));
                    String name = listFes.get(k);
                    if (name == null) break;
                    JSONObject tp = findObject(name);
                    if (tp == null) {
                        JSONObject jsonObject = new JSONObject();
                        Cultural ctr = new Cultural();
                        ctr.setName(name);
                        if (m.size() != 0) {
                            ctr.setTime(m.get(m.size() - 1));
                        } else {
                            ctr.setTime(null);
                        }
                        ctr.setLocation(location);
                        ctr.loadField(jsonObject);
                        getData().add(jsonObject);
                    } else {
                        Cultural ctr = new Cultural(tp);
                        if (ctr.getTime() == null) {
                            if (m.size() != 0) {
                                ctr.setTime(m.get(m.size() - 1));
                            } else {
                                ctr.setTime(null);
                            }
                        }
                        if (ctr.getLocation() == null) ctr.setLocation(location);
                        ctr.loadField(tp);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Error getData in DynastyWiki 2");
        }

        JsonHandler.writeFile("Cultural.json", getData());
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
