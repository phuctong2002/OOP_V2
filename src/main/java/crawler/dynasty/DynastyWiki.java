package crawler.dynasty;

import crawler.Crawler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.JsonHandler;

import java.io.IOException;


public class DynastyWiki extends Crawler implements IDynasty {

    public DynastyWiki(){
        setData(JsonHandler.readFile("Dynasty.json"));
    }
    @Override
    public void get() {
        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
        try {
            Document doc = Jsoup.connect( url).get();
            Elements com = doc.select(".toccolours>tbody>tr>td>table>tbody>tr");
            for( Element el : com){
                Elements links = el.select("b:has(a)");
                if( links.size() == 0) continue;
                for( Element link : links){
                    try{
                        Document document = Jsoup.connect("https://vi.wikipedia.org" + link.firstChild().attr("href")).get();
                        JSONObject obj = new JSONObject();
                        String name = link.text();
                        JSONObject tmp = findObject( name);
                        if( tmp == null){
                            // truong hop thuc the khong ton tai trong file

                        }else{
                            // truong hop thuc the ton tai trong file
                        }
                    }catch ( Exception e){
                        System.out.println("Error getData in DynastyWiki");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error getData in DynastyWiki");
        }


    }

    @Override
    public String getName(Document doc) {
        return null;
    }

    @Override
    public String getStart(Document doc) {
        return null;
    }

    @Override
    public String getEnd(Document doc) {
        return null;
    }

    @Override
    public JSONArray getKings(Document doc) {
        return null;
    }

    @Override
    public String getBrief(Document doc) {
        return null;
    }
}
