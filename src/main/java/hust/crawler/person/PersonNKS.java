package hust.crawler.person;

import hust.crawler.Crawler;
import hust.model.Character;
import hust.model.King;
import hust.model.Person;
import hust.util.JsonHandler;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PersonNKS extends Crawler {
    public PersonNKS() {
        setData(JsonHandler.readFile("Person.json"));
    }

    @Override
    public void get() {
        String url = "https://nguoikesu.com/nhan-vat";
        do {
            try {
                Document doc= Jsoup.connect(url).get();
                Elements list1 = doc.select(".blog-item");
                Elements list2 = doc.select(".com-content-category-blog__pagination ul.pagination>li");
                for (int i = 0; i < list1.size(); i++) {
                    JSONObject obj = new JSONObject();
                    if (list1.get(i).select("h2>a").text().contains("nhà")) continue;
                    if (list1.get(i).select("h2>a").text().contains("Nhà")) continue;
                    System.out.println(list1.get(i).select("h2>a").text());
                    Document detail = Jsoup.connect("https://nguoikesu.com" + list1.get(i).select("h2>a").attr("href")).get();
                    Elements infoBoxes = detail.select(".infobox");
                    if (infoBoxes.size() == 0) continue;
                    // ton tai infobox nhe
                    String name = list1.get(i).select("h2>a").text().trim();
                    JSONObject person = findObject(name);
                    // tim trong infobox coi co vua chua gi khong
                    Elements tmp = detail.select(".infobox>tbody>tr:contains(Tiền nhiệm),"
                            + ".infobox>tbody>tr:contains(Kế nhiệm)");
                    Person person1;
                    if (tmp.size() != 0) {
                        person1 = new King();
                    } else person1 = new Character();
                    if (person == null) {
                        JSONObject jsonObject = new JSONObject();
                        if (person1 instanceof King) {
                            // king
                            King a = new King();
                            a.setName(name);
                            a.setBirth(getBirth(infoBoxes.get(0)));
                            a.setDeath(getDeath(infoBoxes.get(0)));
                            a.setDynasty(getDynasty(infoBoxes.get(0)));
                            a.setTime(getTime(infoBoxes.get(0)));
                            a.setPredecessor(getPredecessor(infoBoxes.get(0)));
                            a.setSuccessor(getSuccessor(infoBoxes.get(0)));
                            a.loadField(jsonObject);
                            getData().add(jsonObject);
                        } else {
                            //character
                            Character a = new Character();
                            a.setName(name);
                            a.setBirth(getBirth(infoBoxes.get(0)));
                            a.setDeath(getDeath(infoBoxes.get(0)));
                            a.setJob(getJob(infoBoxes.get(0)));
                            a.loadField(jsonObject);
                            getData().add(jsonObject);
                        }
                    } else {
                        if (person1 instanceof King) {
                            // king
                            King a = new King(person);
                            if (a.getBirth() == null) a.setBirth(getBirth(infoBoxes.get(0)));
                            if (a.getDeath() == null) a.setDeath(getDeath(infoBoxes.get(0)));
                            if (a.getPredecessor() == null) a.setPredecessor(getPredecessor(infoBoxes.get(0)));
                            if (a.getDynasty() == null) a.setDynasty(getDynasty(infoBoxes.get(0)));
                            if (a.getSuccessor() == null) a.setSuccessor(getSuccessor(infoBoxes.get(0)));
                            if (a.getTime() == null) getTime(infoBoxes.get(0));
                            a.loadField(person);
                        } else {
                            //character
                            Character a = new Character(person);
                            if (a.getBirth() == null) a.setBirth(getBirth(infoBoxes.get(0)));
                            if (a.getDeath() == null) a.setDeath(getDeath(infoBoxes.get(0)));
                            if (a.getJob() == null) a.setJob(getJob(infoBoxes.get(0)));
                            a.loadField(person);
                        }
                    }
                }
                Elements links = list2.get(list2.size() - 2).select("a");
                if (links.size() == 0) break;
                url = "https://nguoikesu.com" + list2.get(list2.size() - 2).select("a").attr("href");
                System.out.println(url);
            } catch (IOException e) {
                continue;
            }

        } while (true);
        JsonHandler.writeFile("Person.json", getData());
    }

    private String getJob(Element element) {
        Elements items = element.select(".infobox>tbody>tr:has(>th:contains(Nghề nghiệp))>td,"
                + ".infobox>tbody>tr:has(>th:contains(Chức vụ))>td");
        if (items.size() != 0) return items.get(0).text().trim();
        return null;
    }

    private String getSuccessor(Element element) {
        Elements items = element.select(".infobox>tbody>tr:has(>th:contains(Kế nhiệm))>td");
        if (items.size() != 0) return items.get(0).text().trim();
        return null;
    }

    private String getPredecessor(Element element) {
        Elements items = element.select(".infobox>tbody>tr:has(>th:contains(Tiền nhiệm))>td");
        if (items.size() != 0) return items.get(0).text().trim();
        return null;
    }

    private String getTime(Element element) {
        Elements items = element.select(".infobox>tbody>tr:has(>th:contains(Tại vị))>td");
        if (items.size() != 0) return items.get(0).text().trim();
        return null;
    }

    private String getDynasty(Element element) {
        Elements items = element.select(".infobox>tbody>tr:has(>th:contains(Triều đại))>td");
        if (items.size() != 0) return items.get(0).text().trim();
        return null;
    }

    private String getDeath(Element element) {
        Elements items = element.select(".infobox>tbody>tr:has(>th:contains(Mất))>td");
        if (items.size() != 0) return items.get(0).text().trim();
        return null;
    }

    private String getBirth(Element element) {
        Elements items = element.select(".infobox>tbody>tr:has(>th:contains(Sinh))>td");
        if (items.size() != 0) return items.get(0).text().trim();
        return null;
    }
}
