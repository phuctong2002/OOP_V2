package crawler.event;

import crawler.Crawler;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EventSGK extends EventCrawler implements IEvent {



    public EventSGK(){
        setData("Event.json");
        get();
        saveData("Event.json");
    }

    @Override
    public void get() {
        try {
            String[] url = {"https://baitapsgk.com/lop-6/lich-su-lop-6/lap-bang-thong-ke-nhung-su-kien-lon-dang-ghi-nho-cua-lich-su-nuoc-ta-tu-khi-dung-nuoc-den-nam-938.html",
            "https://baitapsgk.com/lop-7/lich-su-lop-7/bang-thong-ke-nhung-su-kien-dang-ghi-nho-cua-lich-su-nuoc-ta-tu-the-ki-den-giua-the-ki-xix.html"};

            for(int j = 0; j < url.length; j++){
                Document document = Jsoup.connect(url[j]).get();
                Elements elements = document.select(".td-post-content table tr");
                for (int i = 1; i < elements.size(); ++i) {
                    Elements td = elements.get(i).children();
                    time = getTime(td.get(0));
                    name = getName(td.get(1));
                    relatedInformation = getRelatedInformation(td.get(2));
                    result = getResult(td.get(3));
                    handleNewData(getHistoricEvent());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getName(Element element) {
        return element.text();
    }

    @Override
    public String getTime(Element element) {
        if(element.text().contains("Năm"))
           return element.text().replace("Năm ","");
        return element.text();
    }

    @Override
    public String getSummary(Element element) {
        return null;
    }

    @Override
    public String getResult(Element element) {
        return element.text();
    }

    @Override
    public List<String> getRelatedInformation(Element element) {
        List<String> relatedInformation = new ArrayList<>();
        String str = element.text();
        relatedInformation  =  Arrays.stream(str.split(",")).toList();
        return  relatedInformation;
    }
}
