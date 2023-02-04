package hust.test;

import hust.crawler.Crawler;
import hust.crawler.event.EventNguoiKeSu;
import hust.crawler.event.EventWiki;

public class Test {
    public static void main(String[] args) {
        Crawler crawler1 = new EventWiki();
        Crawler crawler2 = new EventNguoiKeSu();
    }
}
