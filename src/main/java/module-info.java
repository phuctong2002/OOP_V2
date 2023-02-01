module com.example.oop_v3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.jsoup;


    exports hust.main;
    opens hust.main to javafx.fxml;
    opens hust.util to json.simple;
}