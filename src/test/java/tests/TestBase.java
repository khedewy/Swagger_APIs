package tests;

import api.Swagger;
import data.ReadJsonData;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

import java.io.FileNotFoundException;

public class TestBase {
    Swagger swagger;
    ReadJsonData registerData;
    ReadJsonData createNoteData;
    ReadJsonData putNoteData;
    ReadJsonData patchNoteData;


    @BeforeMethod
    public void setData() throws FileNotFoundException {
        swagger = new Swagger();
        RestAssured.baseURI = "https://practice.expandtesting.com/notes/api";
        registerData = new ReadJsonData("src/test/java/data/registerData.json");
        createNoteData = new ReadJsonData("src/test/java/data/createNoteData.json");
        putNoteData = new ReadJsonData("src/test/java/data/putNoteData.json");
        patchNoteData = new ReadJsonData("src/test/java/data/patchNoteData.json");
    }
}
