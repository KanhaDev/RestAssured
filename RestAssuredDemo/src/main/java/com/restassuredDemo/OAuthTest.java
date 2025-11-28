package com.restassuredDemo;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class OAuthTest {
	
	String token = "";
	
	
	@Test
	public void serverAuthorization() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		HashMap<String,String> map = new HashMap<>();
		map.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
		map.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
		map.put("grant_type", "client_credentials");
		map.put("scope", "trust");
		
		String res = given().formParams(map).when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all()
		.assertThat().statusCode(200).extract().asString();
		
		JsonPath js = new JsonPath(res);
		
		token = js.getString("access_token");
		
		System.out.println(token);
	}
	
	@Test
	public void getCourseDetails() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String res2 = given().queryParam("access_token", token).when().get("oauthapi/getCourseDetails").then()
		.log().all().assertThat().statusCode(401).extract().asString();
		
		JsonPath js2 = new JsonPath(res2);
		String firstCourseTitle = js2.get("courses.webAutomation[0].courseTitle");
		
		System.out.println(firstCourseTitle);
	}

}
