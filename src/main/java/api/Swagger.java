package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import registerAndLoginBody.*;

public class Swagger {
    RegisterBody registerBody;
    LoginBody loginBody;
    CreateNoteBody createNoteBody;
    PutNoteBody putNoteBody;
    PatchBody patchBody;
    public Response register(String name,String email, String password){
        registerBody = new RegisterBody();
        registerBody.setEmail(email);
        registerBody.setName(name);
        registerBody.setPassword(password);

        return RestAssured.given().log().all().header("Content-Type","application/json")
                .header("Accept","application/json").body(registerBody)
                .when().post("/users/register");

    }

    public Response login(String email,String password){
        loginBody = new LoginBody();
        loginBody.setEmail(email);
        loginBody.setPassword(password);

        return RestAssured.given().log().all().header("Content-Type","application/json")
                .header("Accept","application/json")
                .body(loginBody).when().post("/users/login");
    }

    public Response createNote(String title,String description,String category,String token){
         createNoteBody = new CreateNoteBody();
         createNoteBody.setTitle(title);
         createNoteBody.setDescription(description);
         createNoteBody.setCategory(category);

         return RestAssured.given().log().all().header("x-auth-token",token)
                 .header("Content-Type","application/json")
                 .header("Accept","application/json").body(createNoteBody)
                 .when().post("/notes");
    }

    public Response putNote(String title,String description,boolean complete, String category,String token,String id){
        putNoteBody = new PutNoteBody();
        putNoteBody.setTitle(title);
        putNoteBody.setDescription(description);
        putNoteBody.setCompleted(complete);
        putNoteBody.setCategory(category);

        return RestAssured.given().log().all().pathParam("id",id).headers("x-auth-token",token)
                 .header("Content-Type","application/json")
                .header("Accept","application/json").body(putNoteBody)
                .when().put("/notes/{id}");

    }

    public Response patchNote(boolean completed,String id,String token){
        patchBody = new PatchBody();
        patchBody.setCompleted(completed);

        return RestAssured.given().log().all().pathParam("id",id).headers("x-auth-token",token)
                .header("Content-Type","application/json")
                .header("Accept","application/json").body(patchBody)
                .when().patch("/notes/{id}");
    }

    public Response deleteNote(String id,String token){
        return  RestAssured.given().log().all().pathParam("id",id).headers("x-auth-token",token)
                .header("Content-Type","application/json").when().delete("/notes/{id}");
    }



}
