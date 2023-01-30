module com.example.oop_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.jsoup;

    opens main to javafx.fxml;
    exports main;
    opens util to json.simple;
}