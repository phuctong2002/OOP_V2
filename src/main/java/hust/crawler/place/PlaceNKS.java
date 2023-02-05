package hust.crawler.place;

import hust.crawler.Crawler;
import hust.model.Place;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class PlaceNKS extends Crawler {

    public PlaceNKS() {
        setData("Place.json");
    }

    @Override
    public void get() {
        String base = "https://nguoikesu.com/dia-danh";
        int index = 0;
        JSONArray arr = new JSONArray();
        while (true) {
            String url = base + "?start=" + index;
            try {
                Document doc = Jsoup.connect(url).get();
                Elements com = doc.select(".com-content-category-blog__item");
                if (com.size() == 0) break;

                for (int i = 0; i < com.size(); i++) {
                    try {
                        if (com.get(i).select("[itemprop=url]").size() == 0) continue;
                        Document detail = Jsoup.connect("https://nguoikesu.com" + com.get(i).select("[itemprop=url]").get(0).attr("href")).get();
                        Elements info = detail.select("div.infobox");
                        if (info.size() != 0) {
                            Elements el = info.get(0).select(">table>tbody>tr:has(>th:contains(Quốc gia))");
                            if (el.size() != 0) {
                                if (!el.get(0).select(">td").get(0).text().contains("Việt Nam"))
                                    continue;
                            }
                            String name = com.get(i).select("[itemprop=url]").get(0).text().trim();
                            System.out.println(name);
                            JSONObject obj = findObject(name);
                            if (obj == null) {
                                Place place = new Place();
                                JSONObject jsonObject = new JSONObject();
                                place.setName(name);
                                place.setLocation(getLocation(info.get(0)));
                                place.setBrief(detail.select("div.com-content-article__body >p").first().text());
                                place.loadField(jsonObject);
                                getData().add(jsonObject);
                            } else {
                                Place place = new Place(obj);
                                if (place.getLocation() == null) place.setLocation(getLocation(info.get(0)));
                                if (place.getBrief() == null)
                                    place.setBrief(detail.select("div.com-content-article__body >p").first().text());
                                place.loadField(obj);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            index += 5;
        }
        saveData("Place.json");
    }


    private String getLocation(Element el) {
        String location;
        try {
            Elements getDiaChi = el.select("table.infobox tbody tr:has(>th:contains(Tỉnh)),"
                    + "table.infobox tbody tr:has(>th:contains(Huyện)),"
                    + "table.infobox tbody tr:has(>th:contains(Địa chỉ)),"
                    + "table.infobox tbody tr:has(>th:contains(Tọa độ)),"
                    + "table.infobox tbody tr:has(>th:contains(Khu vực)),"
                    + "table.infobox tbody tr:has(>th:contains(Địa điểm))");
            location = getDiaChi.get(0).select(">td").text();
        } catch (Exception e) {
            location = null;
        }
        return location;
    }

}
