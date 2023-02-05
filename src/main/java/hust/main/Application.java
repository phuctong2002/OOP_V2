package hust.main;


import hust.crawler.Crawler;
import hust.crawler.cultural.CulturalWiki2;
import hust.crawler.dynasty.DynastyWiki;
import hust.crawler.person.PersonNKS;
import hust.crawler.person.PersonWiki;
import hust.crawler.place.PlaceNKS;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home_v2.fxml"));
        stage.setTitle("Group 9");
        Image icon = new Image(String.valueOf(getClass().getResource("/img/vietnam.png")));
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        Crawler obj = new PlaceNKS();
//        obj.get();
//        Crawler obj = new DynastyWiki();
        Crawler obj = new CulturalWiki2();
        obj.get();
        launch();
    }
}