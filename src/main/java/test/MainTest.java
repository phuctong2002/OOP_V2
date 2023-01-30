package test;

import crawler.Crawler;
import crawler.dynasty.DynastyWiki;


public class MainTest {
    public static void main(String[] args){
        Crawler obj = new DynastyWiki();
        obj.getData();
        System.out.println( obj.getData().size());
    }
}
