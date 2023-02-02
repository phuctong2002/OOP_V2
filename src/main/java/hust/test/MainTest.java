package hust.test;

import hust.crawler.Crawler;
import hust.crawler.cultural.CulturalWiki;
import hust.crawler.event.EventWiki;
import hust.crawler.person.PersonNKS;
import hust.crawler.person.PersonWiki;
import hust.crawler.place.PlaceNKS;


public class MainTest {
    public static void main(String[] args) {
        Crawler obj = new PersonNKS();
        obj.get();
    }
}
