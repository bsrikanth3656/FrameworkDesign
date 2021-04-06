package com.common.function.library;

import org.testng.annotations.Test;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBugzilla {

	public static RequestSpecification httpRequest;
	public static Response response;

//	@Test
	public void testGetID() throws ConnectionException, BugzillaException {

		RestAssured.baseURI = "https://bugzilla.mozilla.org";
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("srikanth281191shirmisha@zohomail.in");
		authScheme.setPassword("EnS3UV&2+_TV$ag#!@");
		RestAssured.authentication = authScheme;
		httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.queryParam("Bugzilla_api_key", "1kOtiCOAA3F83ajiIFSqQl9OwidLowBYLILUM48n");
		String summary = "Test";
		httpRequest.body("{ \"summary\":\"" + summary + "\",\"product\" : \"Bugzilla\",\"component\" : \"Query/Bug List\",\"version\" : \"unspecified\",\"op_sys\" : \"All\",\"priority\" : \"P1\",\"platform\" : \"All\",\"type\" : \"defect\"}");

		response = httpRequest.request(Method.POST, "/rest/bug");
		String ResponseBody = response.getBody().asString();
		System.out.println("Response status code is: " + response.getStatusCode());
		System.out.println("Response status line is: " + response.getStatusLine());
		System.out.println(ResponseBody);

		String bugId = ResponseBody.substring(6,13);
		System.out.println(bugId);

		RequestSpecification httpRequest2 = RestAssured.given();
		httpRequest2.header("Content-Type", "application/json");
		httpRequest2.queryParam("Bugzilla_api_key", "1kOtiCOAA3F83ajiIFSqQl9OwidLowBYLILUM48n");
		httpRequest2.queryParam("id", bugId);
		httpRequest2.queryParam("include_fields", "summary,status,resolution");
		Response response2 = httpRequest2.request(Method.GET, "/rest/bug");
		System.out.println(response2.getBody().asString());

	}

}
