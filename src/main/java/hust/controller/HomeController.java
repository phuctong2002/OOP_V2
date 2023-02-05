package hust.controller;

import hust.service.Search;
import hust.util.JsonHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class HomeController {
    Search service;
    @FXML
    private TextField search;

    @FXML
    private ChoiceBox<String> choose;

    @FXML
    private TableView<String> tableView;

    @FXML
    private TableColumn<String, String> nameCol;
    @FXML
    private TextArea textArea;

    @FXML
    public void initialize() {
        Map<String, String> map = new HashMap<>();
        map.put("Nhân vật lịch sử", "Person.json");
        map.put("Triều đại", "Dynasty.json");
        map.put("Lễ hội", "Cultural.json");
        map.put("Địa điểm", "Place.json");
        map.put("Sự kiện lịch sử", "Event.json");
        choose.getItems().add("Nhân vật lịch sử");
        choose.getItems().add("Triều đại");
        choose.getItems().add("Lễ hội");
        choose.getItems().add("Địa điểm");
        choose.getItems().add("Sự kiện lịch sử");
        choose.setValue(choose.getItems().get(1));
        service = new Search();
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        textArea.setWrapText( true);


        showListData(service.search(map.get(choose.getSelectionModel().getSelectedItem())));
        choose.setOnAction(actionEvent -> {
            showListData(service.search(map.get(choose.getSelectionModel().getSelectedItem())));
        });

        search.textProperty().addListener((observableValue, s, t1) -> {
            JSONArray arr = service.search(t1.trim(), map.get(choose.getSelectionModel().getSelectedItem()));
            showListData(arr);
        });
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                JSONObject obj = (JSONObject) service.search(t1.trim(), map.get(choose.getSelectionModel().getSelectedItem())).get(0);
                Iterator<?> keys = obj.keySet().iterator();
                StringBuilder detail = new StringBuilder();
                while (keys.hasNext()) {

                    String key = (String) keys.next();
                    // tim duoc key roi  nhe
                    String value = "";// tinh gia tri
                    if (obj.get(key) != null){
                        if( obj.get(key) instanceof JSONArray){
                            JSONArray arr = (JSONArray) obj.get(key);
                            for( int i = 0; i < arr.size(); ++i){
                                if( i != arr.size() - 1)
                                    value += arr.get(i) + ", ";
                                else
                                    value += arr.get(i) ;
                            }
                        }else{
                            value = obj.get(key).toString();
                        }
                    }
                    if( value.length() != 0)
                        detail.append("+ ").append(key).append(" : ").append(value).append("\n\n");
                }
                textArea.setText(detail.toString());
            }
        });
    }

    public void showListData(JSONArray arr){
        List<String> list = new ArrayList<>();
        for (Object o : arr) {
            list.add((String) ((JSONObject) o).get("tên"));
        }
        ObservableList<String> names = FXCollections.observableArrayList(list);
        tableView.setItems(names);
    }

}
