package test;

import crawler.Crawler;
import crawler.dynasty.DynastyWiki;
import crawler.event.EventSGK;
import crawler.event.EventWiki;


public class MainTest {
    public static void main(String[] args){
        Crawler obj1 = new EventWiki();
        Crawler obj2 = new EventSGK();
//        obj.getData();
//        System.out.println( obj.getData().size());
    }
}
