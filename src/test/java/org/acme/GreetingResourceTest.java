package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.entity.Book;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.jboss.resteasy.reactive.RestResponse.Status.OK;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void shouldGetAllBooks() {
        Book[] books = given().contentType(MediaType.APPLICATION_JSON)
                .when().get("/api/books")
                .then()
                .statusCode(200)
                .extract().as(Book[].class);
        assertThat(books, arrayWithSize(1));
        assertThat(books[0].getGenre(), equalTo("Action"));
    }

    @Test
    public void shouldCountAllBooks() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/api/books/count")
                .then()
                .statusCode(200)
                .body(is("1"));
    }

    @Test
    public void getBook() {
        given().contentType(MediaType.APPLICATION_JSON)
                .pathParam("id", 1)
                .when()
                .get("/api/books/{id}")
                .then()
                .statusCode(OK.getStatusCode())
                .body("title", is("Top Gun Maverick"))
                .body("genre", is("Action"));
    }
}