package api;

import api.services.BookService;
import io.restassured.RestAssured;

import static config.Config.API_BASE_PATH;
import static config.Config.API_URL;

class BaseServiceTest {
    BookService bookService = new BookService();

    BaseServiceTest() {
        RestAssured.baseURI = API_URL;
        RestAssured.basePath = API_BASE_PATH;
    }
}
