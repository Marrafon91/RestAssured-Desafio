package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class MovieControllerRA {

    private Long existsMovieId, nonExistsMovieId;
    private String clientToken, adminToken, invalidToken;
    private String clientUsername, clientPassword, adminUsername, adminPassword;

    private Map<String, Object> postMovieInstance;

    @BeforeEach
    public void setup() throws JSONException {
        baseURI = "http://localhost:8080";

        existsMovieId = 1L;
        nonExistsMovieId = 999L;

        clientUsername = "joaquim@gmail.com";
        clientPassword = "123456";

        adminUsername = "maria@gmail.com";
        adminPassword = "123456";

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";

        postMovieInstance = new HashMap<>();
        postMovieInstance.put("title", "Test Movie");
        postMovieInstance.put("score", 0.0);
        postMovieInstance.put("count", 0);
        postMovieInstance.put("image", "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg");
    }

    @Test
    public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {

        given()
                .when()
                .get("/movies")
                .then()
                .statusCode(200);
    }

    @Test
    public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {
        given()
                .queryParam("title", "Matrix")
                .when()
                .get("/movies")
                .then()
                .statusCode(200);
    }

    @Test
    public void findByIdShouldReturnMovieWhenIdExists() {
        given()
                .when()
                .get("/movies/{id}", existsMovieId)
                .then()
                .statusCode(200);
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {
        given()
                .when()
                .get("/movies/{id}", nonExistsMovieId)
                .then()
                .statusCode(404);
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {
		postMovieInstance.put("title", "   ");
        JSONObject newMovieInstance = new JSONObject(postMovieInstance);

        given()
                .header("Content-Type", "application/json ")
                .header("Authorization", "Bearer " + adminToken)
                .body(newMovieInstance.toJSONString())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/movies")
                .then()
                .statusCode(422);
    }

    @Test
    public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
		JSONObject newMovieInstance = new JSONObject(postMovieInstance);

		given()
				.header("Content-Type", "application/json ")
				.header("Authorization", "Bearer " + clientToken)
				.body(newMovieInstance.toJSONString())
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.post("/movies")
				.then()
				.statusCode(403);
    }

    @Test
    public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
		JSONObject newMovieInstance = new JSONObject(postMovieInstance);

		given()
				.header("Content-Type", "application/json ")
				.header("Authorization", "Bearer " + invalidToken)
				.body(newMovieInstance.toJSONString())
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.post("/movies")
				.then()
				.statusCode(401);
    }
}
