package com.common.function.library;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Listeners;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Listeners(TestListener.class)
public class API_Functionlibrary {

	public static RequestSpecification httpRequest;
	public static String responsebody;
	public static Response response;

	@Step("API Get Method")
	public static void apiGet(String URI, String BasePath) {
		try {
			RestAssured.baseURI = URI;
			response = given().contentType("application/json").when().get(BasePath).then().assertThat().statusCode(200)
					.and().extract().response();
			System.out.println("Get Method Executed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Post Method")
	public static void apiPost(String URI, String Body, String BasePath) throws Exception {
		try {
			RestAssured.baseURI = URI;
			response = given().contentType(ContentType.JSON).body(Body).when().post(BasePath).then().assertThat()
					.statusCode(201).and().extract().response();
			System.out.println("Post Method Executed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Put Method")
	public static void apiPut(String URI, String Body, String BasePath) throws Exception {
		try {
			RestAssured.baseURI = URI;
			response = given().contentType(ContentType.JSON).body(Body).when().put(BasePath).then().assertThat()
					.statusCode(200).and().extract().response();
			System.out.println("Put Method Executed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Delete Method")
	public static void apiDelete(String URI, String BasePath) throws Exception {
		try {
			RestAssured.baseURI = URI;
			response = given().contentType(ContentType.JSON).when().delete(BasePath).then().assertThat().statusCode(204)
					.and().extract().response();
			System.out.println("Delete Method Executed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Post Method with cookie")
	public static void addCookieAndDoPostCall(String URI, String body, String cookie, String BasePath)
			throws Exception {
		try {
			RestAssured.baseURI = URI;
			response = given().cookie(cookie).contentType("application/json").when().body(body).post(BasePath).then()
					.assertThat().statusCode(201).and().extract().response();
			responsebody = response.getBody().asString();
			System.out.println(responsebody);
			System.out.println("Post Method Executed Successfully with cookie");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Method to get status code of response")
	public static int getStatusCode() throws Exception {
		int responseCode = 0;
		try {
			responseCode = response.getStatusCode();
			System.out.println("Response Status Code is " + response.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseCode;
	}

	@Step("API Method to get status line of response")
	public static String getStatusLine() throws Exception {
		String responseStatusLine = null;
		try {
			responseStatusLine = response.getStatusLine();
			System.out.println("Response Status line is " + response.getStatusLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseStatusLine;
	}

	@Step("API Method to get response")
	public static String getResponse() throws Exception {
		try {
			responsebody = response.getBody().asString();
			System.out.println("Response Body is " + responsebody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responsebody;
	}

	@Step("API Method to get error code in response")
	public static int getErrorCodeInResponse() throws Exception {
		int errorCodeInResponse = 0;
		try {
			errorCodeInResponse = response.getBody().jsonPath().get("errorCode");
			System.out.println("Error code in Response is " + errorCodeInResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorCodeInResponse;
	}

	@Step("API Method to get error description in response")
	public static String getErrordescriptionInResponse() throws Exception {
		String ErrordescriptionInResponse = null;
		try {
			ErrordescriptionInResponse = response.getBody().jsonPath().get("errorDescription");
			System.out.println("Error description in Response is " + ErrordescriptionInResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ErrordescriptionInResponse;
	}

	@Step("API Method to check error code in response")
	public static String checkErrorCodeResponse(String responseCode) throws Exception {
		try {
			assertThat(getErrorCodeInResponse()).isEqualTo(responseCode);
			System.out.println("Error code matches");
		} catch (Exception e) {
			System.out.println("Execption in Error code, no match found: " + e.getMessage());
		}
		return responsebody;
	}
}
