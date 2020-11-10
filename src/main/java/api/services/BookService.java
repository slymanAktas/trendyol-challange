package api.services;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BookService {
    public ValidatableResponse insertBook(int bookId, String author, String title) {
        ValidatableResponse response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(getRequestBody(author, title))
                .when()
                .put("/create/"+bookId+"")
                .then();
        response.extract().response().prettyPrint();
        return response;
    }

    public ValidatableResponse listBook(int bookId) {
        ValidatableResponse response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .put("/create/"+bookId+"")
                .then();
        response.extract().response().prettyPrint();
        return response;
    }

    private static HashMap<String, Object> getRequestBody(String author, String title) {
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("author", author);
        requestParams.put("title", title);
        return requestParams;
    }
}
