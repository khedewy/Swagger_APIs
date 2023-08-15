package data;

import io.restassured.path.json.JsonPath;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadJsonData {
    private String JsonFilePath;
    private FileReader reader;

    public ReadJsonData(String jsonFilePath) throws FileNotFoundException {
        this.JsonFilePath = jsonFilePath;
        reader = new FileReader(jsonFilePath);
    }

    public String getTestData(String jsonPath) throws FileNotFoundException {
        Object testData;
        reader = new FileReader(JsonFilePath);
        testData = JsonPath.from(reader).getString(jsonPath);
        return String.valueOf(testData);
    }
}
