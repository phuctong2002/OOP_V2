package hust.test;

import hust.crawler.Crawler;
import hust.crawler.event.EventWiki;
import hust.crawler.person.PersonWiki;


public class MainTest {
    public static void main(String[] args) {
        Crawler obj = new EventWiki();
        obj.get();
    }
}
