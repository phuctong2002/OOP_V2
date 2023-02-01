package crawler.event;

import org.jsoup.nodes.Element;

import java.util.List;

public interface IEvent {
    public String getName(Element element);
    public String getTime(Element element);
    public String getSummary(Element element);
    public List<String> getRelatedInformation(Element element);
}
