package hust.crawler.person;

import hust.crawler.Crawler;
import hust.model.King;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PersonWiki extends Crawler {

    public PersonWiki(){
        setData( "Person.json");
//        setData(JsonHandler.readFile("Person.json"));
    }
    @Override
    public void get() {
        String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam";
        try{
            Document doc = Jsoup.connect( url).get();
            Elements table1 = doc.select(".mw-parser-output").get(0).select(">table:contains(Chân dung)");
            Elements links = table1.select(">tbody>tr:gt(0)>td:gt(0):lt(2)>b:lt(1)>a:lt(1)");
            for( int i = 0; i < links.size(); ++i){
                try{
                    Document document = Jsoup.connect("https://vi.wikipedia.org/" + links.get(i).attr("href")).get();
                    String name = links.get(i).text().trim();
                    JSONObject tmp = findObject(name);
                    if( tmp == null){
                        // khong ton tai o day nhe
                        JSONObject jsonObject = new JSONObject();
                        King king = new King();
                        king.setName(name);
                        king.setBirth( getBirth( document));
                        king.setDeath( getDeath( document));
                        king.setDynasty( getDynasty(document));
                        king.setTime( getTime(document));
                        king.setPredecessor( getPredecessor( document));
                        king.setSuccessor( getSuccessor( document));
                        king.loadField( jsonObject);
                        getData().add( jsonObject);
                    }else{
                        King king = new King( tmp);
                        if( king.getBirth() == null) king.setBirth( getBirth( document));
                        if( king.getDeath() == null) king.setDeath( getDeath( document));
                        if( king.getDynasty() == null) king.setDynasty( getDynasty( document));
                        if( king.getTime() == null) king.setTime( getTime(document));
                        if( king.getPredecessor() == null) king.setPredecessor( getPredecessor( document));
                        if( king.getSuccessor() == null) king.setSuccessor( getSuccessor(document));
                        king.loadField( tmp);
                    }
                }catch (Exception e){
                }
            }
        }catch ( Exception e){
            System.out.println( "Error 1");
        }
//        JsonHandler.writeFile("Person.json", getData());
        saveData("Person.json");
    }


    private String getSuccessor(Document document) {
        Elements items = document.select(".infobox>tbody>tr:has(>th:contains(Kế nhiệm))>td");
        if( items.size() != 0) return items.get(0).text().trim();
        return null;
    }

    private String getPredecessor(Document document) {
        Elements items = document.select(".infobox>tbody>tr:has(>th:contains(Tiền nhiệm))>td");
        if( items.size() != 0) return items.get(0).text().trim();
        return null;
    }

    private String getBirth(Document document) {
        Elements items = document.select(".infobox>tbody>tr:has(>th:contains(Sinh))>td");
        if( items.size() != 0) return items.get(0).text().trim();
        return null;
    }
    private String getDeath(Document document) {
        Elements items = document.select(".infobox>tbody>tr:has(>th:contains(Mất))>td");
        if( items.size() != 0) return items.get(0).text().trim();
        return null;
    }
    private String getDynasty(Document document) {
        Elements items = document.select(".infobox>tbody>tr:has(>th:contains(Triều đại))>td");
        if( items.size() != 0) return items.get(0).text().trim();
        return null;
    }
    private String getTime(Document document) {
        Elements items = document.select(".infobox>tbody>tr:has(>th:contains(Trị vì))>td");
        if( items.size() != 0) return items.get(0).text().trim();
        return null;
    }
}
