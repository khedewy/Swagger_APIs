package tests;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

public class SwaggerTest extends TestBase {
    String currentTime = String.valueOf(System.currentTimeMillis());
    String token;
    JsonPath jsonPath;
    String id;

    @Test
    public void register() throws FileNotFoundException {
        String response = swagger.register(registerData.getTestData("name")
                ,registerData.getTestData("email")+currentTime+"@gmail.com"
                        ,registerData.getTestData("password"))
                .then().statusCode(201).extract().response().asString();

        System.out.println(response);
    }

    @Test(priority = 1)
    public void login() throws FileNotFoundException {
        String response = swagger.login(registerData.getTestData("email")+currentTime+"@gmail.com"
                ,registerData.getTestData("password"))
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);

        jsonPath = new JsonPath(response);
        token = jsonPath.get("data.token");
        System.out.println(token);
    }

    @Test(priority = 2)
    public void createNote() throws FileNotFoundException {
        String response = swagger.createNote(createNoteData.getTestData("title"),
                createNoteData.getTestData("description"), createNoteData.getTestData("category"),token)
                .then().assertThat().statusCode(200).extract().response().asString();

        jsonPath = new JsonPath(response);
        id = jsonPath.get("data.id");

        String title = jsonPath.getString("data.title");
        Assert.assertEquals(createNoteData.getTestData("title"),title);

        System.out.println(response);

    }

    @Test(priority = 3)
    public void putNote() throws FileNotFoundException {

        String response = swagger.putNote(putNoteData.getTestData("title")
                ,putNoteData.getTestData("description"),Boolean.parseBoolean(putNoteData.getTestData("completed"))
                        ,putNoteData.getTestData("category"),token,id)
                .then().statusCode(200).extract().response().asString();

        System.out.println(response);
        jsonPath = new JsonPath(response);

        String message = jsonPath.getString("message");
        String title = jsonPath.getString("data.title");
        String description = jsonPath.getString("data.description");

        Assert.assertEquals(message,"Note successfully Updated");
        Assert.assertEquals(putNoteData.getTestData("title"),title);
        Assert.assertEquals(putNoteData.getTestData("description"),description);
    }

    @Test(priority = 4)
    public void patchNote() throws FileNotFoundException {
        String response = swagger.patchNote(
                Boolean.parseBoolean(patchNoteData.getTestData("completed"))
                ,id,token).then().extract().response().asString();

        System.out.println(response);

    }

    @Test(priority = 5)
    public void deleteNote(){
        String response = swagger.deleteNote(id,token)
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }
}
