package hust.test;

import hust.crawler.Crawler;
import hust.crawler.cultural.CulturalWiKi;


public class MainTest {
    public static void main(String[] args) {
//        Crawler obj = new EventWiki();
        Crawler obj = new CulturalWiKi();
        obj.get();
    }
}
