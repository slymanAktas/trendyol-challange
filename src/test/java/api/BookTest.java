package api;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.StringUtils;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookTest extends BaseServiceTest {
    @BeforeClass
    public static void setup() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void shouldCreateBook() {
        String title = StringUtils.getRandomString(5);
        ValidatableResponse response = bookService.insertBook(
                new Random().nextInt(),
                title,
                StringUtils.getRandomString(10)
        );
        assertThat("User couldn't add book with title as " + title + "...", response.extract().path("code"), is(equalTo(200)));
        assertThat("User couldn't add book with title as " + title + "...", response.extract().path("title"), is(equalTo(title)));
    }

    @Test
    public void shouldNotCreateBookWithEmptyRequiredField() {
        ValidatableResponse response = bookService.insertBook(
                new Random().nextInt(),
                "",
                ""
        );
        assertThat("User could add book without required fileds...", response.extract().path("code"), is(equalTo(400)));
        assertThat("User could add book without required fileds...", response.extract().path("error"), is(equalTo("Field 'author' is required")));
    }

    @Test
    public void shouldNotCreatedBookWithExistBookId() {
        int bookId = new Random().nextInt();

        bookService.insertBook(
                bookId,
                StringUtils.getRandomString(5),
                StringUtils.getRandomString(10)
        );

        ValidatableResponse response = bookService.insertBook(
                bookId,
                StringUtils.getRandomString(5),
                StringUtils.getRandomString(10)
        );

        assertThat("User could create book with id which already exist as " + bookId + "...", response.extract().path("code"), is(not(equalTo(200))));
    }

    @Test
    public void shouldListCreatedBook() {
        int bookId = new Random().nextInt();

        bookService.insertBook(
                bookId,
                StringUtils.getRandomString(5),
                StringUtils.getRandomString(10)
        );

        ValidatableResponse response = bookService.listBook(
                bookId
        );

        assertThat("User couldn't list book with id as " + bookId + "...", response.extract().path("code"), is(equalTo(200)));
        assertThat("User couldn't list book with id as " + bookId + "...", response.extract().path("book_id"), is(equalTo(bookId)));
    }
}
