package hust.util;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;

public class JsonHandler {
    public static void writeFile(String fileName, JSONArray data) {
        try {
            FileWriter f = new FileWriter("src/main/resources/data/" + fileName);
            f.write(data.toJSONString());
            f.close();
        } catch (Exception e) {
            System.out.println("Error in writeFile method");
        }
    }

    public static JSONArray readFile(String fileName) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONArray) parser.parse(new FileReader("src/main/resources/data/" + fileName));
        } catch (Exception e) {
            System.out.println("Error in readFile method");
//            e.printStackTrace();
        }
        return new JSONArray();
    }
}
