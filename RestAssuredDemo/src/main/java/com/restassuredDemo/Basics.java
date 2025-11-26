package com.restassuredDemo;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.testng.Assert;

import files.Payload;

public class Basics {

	public static void main(String[] args) throws IOException {

		// validate if add place API is working as expected

		// given --> all input details
		// when --> submit the API --> resource and http methods will be here only
		// then --> validate the response
		//Content of the file to String --> content of file can convert to byte --> byte data to string

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Add Place
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("/Users/kanhaiya/Desktop/JavaCode/RestAssuredDemo/src/main/java/files/addPlace.json")))).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().asPrettyString();

		System.out.println(response);

		JsonPath js = new JsonPath(response); // To parse the string to json object

		String placeId = js.getString("place_id");
		System.out.println("Place Id: " + placeId);

		// Update place
		String newAddress = "70,Pakuriya,Jharkhand";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.putPlace(placeId,newAddress)).when().put("maps/api/place/update/json").then().log().all()
				.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

		// Get Place
		HashMap<String, String> map = new HashMap<>();
		map.put("key", "qaclick123");
		map.put("place_id", placeId);
		String getResponse = given().log().all().queryParams(map).when()
				.get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js2 = new JsonPath(getResponse);
		String actualAddress = js2.get("address");
		System.out.println(actualAddress);
		Assert.assertEquals(newAddress, actualAddress);
	}

}
