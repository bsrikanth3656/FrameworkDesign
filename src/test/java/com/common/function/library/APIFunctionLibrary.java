package com.common.function.library;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Listeners;

import com.utilities.Propertiesfileutil;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Listeners(TestListener.class)
public class APIFunctionLibrary {

	public static RestAssured restAssured;
	public static String responsebody;
	public static Response response;
	private static String URI;

	@Step("Method to API URI")
	public static String getWebServiceURI() throws Exception {
		try {
			URI = Propertiesfileutil.getEnvValue("API_URI");
			RestAssured.baseURI = URI;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {

			e.printStackTrace();
		}
		return URI;

	}

	@Step("Method to add parameter in URI")
	public static String addQueryParameterInWebServiceURI(String queryParameter) throws Exception {
		try {
			URI = URI + "?" + queryParameter;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return URI;

	}

	@Step("API Get Method")
	public static void apiGet(String BasePath) {
		try {
			Thread.sleep(2000);
			response = given().contentType("application/json").when().get(BasePath).then().assertThat().statusCode(200)
					.and().extract().response();
			System.out.println("Get Method Executed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Post Method")
	public static void apiPost(String Body, String BasePath) throws Exception {
		try {
			Thread.sleep(2000);
			response = given().contentType(ContentType.JSON).body(Body).when().post(BasePath).then().assertThat()
					.statusCode(201).and().extract().response();
			System.out.println("Post Method Executed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Put Method")
	public static void apiPut(String Body, String BasePath) throws Exception {
		try {
			Thread.sleep(2000);
			response = given().contentType(ContentType.JSON).body(Body).when().put(BasePath).then().assertThat()
					.statusCode(200).and().extract().response();
			System.out.println("Put Method Executed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Delete Method")
	public static void apiDelete(String BasePath) throws Exception {
		try {
			Thread.sleep(2000);
			response = given().contentType(ContentType.JSON).when().delete(BasePath).then().assertThat().statusCode(204)
					.and().extract().response();
			System.out.println("Delete Method Executed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Post Method with cookie")
	public static void addCookieAndDoPostCall(String body, String cookie, String BasePath) throws Exception {
		try {
			Thread.sleep(2000);
			response = given().cookie(cookie).contentType("application/json").when().body(body).post(BasePath).then()
					.assertThat().statusCode(201).and().extract().response();
			System.out.println("Post Method Executed Successfully with cookie");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("API Method to get status code of response")
	public static int getStatusCode() throws Exception {
		int responseCode = 0;
		try {
			Thread.sleep(2000);
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
			Thread.sleep(2000);
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
			Thread.sleep(2000);
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

	@Step("API Method to value for given key in response")
	public static int getValueForIntKeyInResponse(String key) throws Exception {
		int valueForKeyInResponse = 0;
		try {
			valueForKeyInResponse = response.getBody().jsonPath().get(key);
			System.out.println("Value for Key in Response is " + valueForKeyInResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueForKeyInResponse;
	}

	@Step("API Method to value for given key in response")
	public static String getValueForStringKeyInResponse(String key) throws Exception {
		String valueForKeyInResponse = null;
		try {
			valueForKeyInResponse = response.getBody().jsonPath().get(key);
			System.out.println("Value for Key in Response is " + valueForKeyInResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueForKeyInResponse;
	}

	@Step("API Method to value for given key in response array")
	public static String getValueForKeyInResponseArray(String Array, String key) throws Exception {
		String valueForKeyInResponse = null;
		try {
			Object obj = new JSONParser().parse(response.asString());
			JSONObject jo = (JSONObject) obj;
			JSONArray ja = (JSONArray) jo.get(Array);
			for (int it = 0; it < ja.size(); it++) {
				JSONObject contactItem = (JSONObject) ja.get(it);
				String value = (String) (contactItem.get(key));
				System.out.println(value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueForKeyInResponse;
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

	@Step("API Method for Bearer Token Authentication")
	public static void bearerTokenAuthentication(String username, String password, String bearerToken)
			throws Exception {
		try {
			given().headers("Authorization", "Bearer " + bearerToken, "Content-Type", ContentType.JSON, "Accept",
					ContentType.JSON);
			System.out.println("Bearer Authentication successfully");
		} catch (Exception e) {
			System.out.println("Execption in Bearer Authentication: " + e.getMessage());
		}
	}

	@Step("API Method for Form Authentication")
	public static void formAuthentication(String username, String password) throws Exception {
		try {

			System.out.println("Form Authentication successfully");
		} catch (Exception e) {
			System.out.println("Execption in Form Authentication: " + e.getMessage());
		}
	}

	@Step("API Method for Digest Authentication")
	public static void digestAuthentication(String username, String password) throws Exception {
		try {

			System.out.println("Digest Authentication successfully");
		} catch (Exception e) {
			System.out.println("Execption in Digest Authentication: " + e.getMessage());
		}
	}

	@Step("API Method for OAth2 Authentication")
	public static void OATH2Authentication(String username, String password) throws Exception {
		try {

			System.out.println("OAth2 Authentication successfully");
		} catch (Exception e) {
			System.out.println("Execption in OAth2 Authentication: " + e.getMessage());
		}
	}

	@Step("API Basic Authentication")
	public static void requestWithBasicAuthentication(String username, String password) throws Exception {
		try {
			given().auth().basic(username, password);
			System.out.println("Basic Authentication successfully");
		} catch (Exception e) {
			System.out.println("Execption in basic Authentication: " + e.getMessage());
		}
	}

	@Step("API Preemptive Authentication")
	public static void requestWithPreemptiveAuthentication(String username, String password) throws Exception {
		try {
			PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
			authScheme.setUserName(username);
			authScheme.setPassword(password);
			RestAssured.authentication = authScheme;
			System.out.println("Preemitive Authentication successfully");
		} catch (Exception e) {
			System.out.println("Execption in Preemitive Authentication: " + e.getMessage());
		}
	}
}
