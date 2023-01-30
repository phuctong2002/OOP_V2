package crawler.dynasty;

import org.json.simple.JSONArray;
import org.jsoup.nodes.Document;

public interface IDynasty {
    public String getName(Document doc);
    public String getStart( Document doc);
    public String getEnd( Document doc);
    public JSONArray getKings( Document doc);
    public String getBrief( Document doc);
}
