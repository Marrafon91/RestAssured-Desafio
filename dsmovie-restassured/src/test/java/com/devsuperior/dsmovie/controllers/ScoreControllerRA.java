package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class ScoreControllerRA {

	private Long existsMovieId, nonExistsMovieId;
	private String clientToken, adminToken, invalidToken;
	private String clientUsername, clientPassword, adminUsername, adminPassword;

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
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {

		Map<String, Object> body = new HashMap<>();
		body.put("movieId", nonExistsMovieId);
		body.put("score", 4.0);

		given()
				.header("Authorization", "Bearer " + clientToken)
				.contentType(ContentType.JSON)
				.body(body)
				.when()
				.put("/scores")
				.then()
				.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {

		Map<String, Object> body = new HashMap<>();
		body.put("score", 4.0);

		given()
				.header("Authorization", "Bearer " + adminToken)
				.contentType(ContentType.JSON)
				.body(body)
				.when()
				.put("/scores")
				.then()
				.statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {

		Map<String, Object> body = new HashMap<>();
		body.put("movieId", existsMovieId);
		body.put("score", -4.0);

		given()
				.header("Authorization", "Bearer " + clientToken)
				.contentType(ContentType.JSON)
				.body(body)
				.when()
				.put("/scores")
				.then()
				.statusCode(422);
	}
}
