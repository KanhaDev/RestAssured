package com.restassuredDemo;

import java.util.ArrayList;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import pojo.Location;
import pojo.Place;

public class SpecBuilderTest {

	@Test
	public void serializeTest() {

		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);

		Place place = new Place();
		place.setLocation(location);
		place.setAccuracy(50);
		place.setName("Gaurav Gupta");
		place.setPhone_number("(+91) 983 893 3937");
		ArrayList<String> list = new ArrayList<>();
		list.add("shoe park");
		list.add("shop");
		place.setTypes(list);
		place.setWebsite("http://google.com");
		place.setAddress("29, side layout, cohen 09");
		place.setLanguage("French-IN");

//		RestAssured.baseURI = "https://rahulshettyacademy.com";
//		Response res = given().queryParam("key", "qaclick123").body(place)
//				.when().log().all().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
//				.extract().response();
		
		//Using SpecBuilder we can reuse template in all Rest API calls
		
		//Build RequestSpecBuilder
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setBasePath("maps/api/place").setContentType(ContentType.JSON)
		.addQueryParam("key", "qaclick123").build();
		
		//Build ResponseSpecBuilder
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		Response res  =  given().spec(requestSpec).body(place).when().post("add/json")
				.then().spec(responseSpec).extract().response();
		
		String resString = res.getBody().asPrettyString();
		
		System.out.println(resString);

	}

}
