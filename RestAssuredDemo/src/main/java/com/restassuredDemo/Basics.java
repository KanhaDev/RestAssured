package com.restassuredDemo;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

import files.Payload;

public class Basics {

	public static void main(String[] args) {
		
		//validate if add place API is working as expected
		
		//given --> all input details
		//when --> submit the API --> resource and http methods will be here only
		//then --> validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body(Payload.addPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server","Apache/2.4.52 (Ubuntu)");
		
		
	}

}
