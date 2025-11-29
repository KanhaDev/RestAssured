package com.restassuredDemo;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.CourseDetails;
import pojo.GetCourseDetails;

public class OAuthTest {

	@Test
	public void serverAuthorization() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		HashMap<String, String> map = new HashMap<>();
		map.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
		map.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
		map.put("grant_type", "client_credentials");
		map.put("scope", "trust");

		//Form Parameter is safer than Query Parameter as it is not publicly visible in URL
		String res = given().formParams(map).when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all()
				.assertThat().statusCode(200).extract().asString();

		JsonPath js = new JsonPath(res);

		String token = js.getString("access_token");

		System.out.println("API Token: "+token);

		//Getting response as class object so that we can de-serialize it from JSON(JavaScript Object Notation)into POJO (Plain Old Java Object)
		GetCourseDetails gcd = given().queryParam("access_token", token).when().get("oauthapi/getCourseDetails")
				.as(GetCourseDetails.class);

		//First course title
		String firstCourseTitle = gcd.getCourses().getWebAutomation().get(0).getCourseTitle();
		
		//Get LinkedIn URL
		String linkedIn = gcd.getLinkedIn();

		System.out.println("First Course Title: "+firstCourseTitle);
		System.out.println("Linked URL: "+linkedIn);
		
		//Get price of SoapUI Webservices testing in API
		List<CourseDetails> apiCourses = gcd.getCourses().getApi();
		int apiCoursesSize = apiCourses.size();
		
		for(int i=0;i<apiCoursesSize;i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				String price = apiCourses.get(i).getPrice();
				System.out.println("SoapUI Webservices testing Price: "+price);
				break;
			}
		}
		
		//Get the total price of all courses
		List<CourseDetails> webAutomationCourses = gcd.getCourses().getWebAutomation();
		List<CourseDetails> mobileCourses = gcd.getCourses().getMobile();
		
		
		int totalApiPrice = 0;
		int totalwebAutomationPrice = 0;
		int totalMobilePrice = 0;
		
		//Print all course Title & Compare the actual courses from the expected
		String [] coursesInAutomation = {"Selenium Webdriver Java","Cypress","Protractor"};
		ArrayList<String> actualCourses = new ArrayList<>();
		for(CourseDetails courses : webAutomationCourses) {
			String price  = courses.getPrice();
			String courseTitle = courses.getCourseTitle();
			actualCourses.add(courseTitle);
			//System.out.println(courseTitle);
			totalwebAutomationPrice = totalwebAutomationPrice + Integer.parseInt(price);
		}
		
		List<String> expectedCourses = Arrays.asList(coursesInAutomation);
		
		System.out.println(actualCourses);
		Assert.assertEquals(actualCourses,expectedCourses);
		
		for(CourseDetails courses : apiCourses) {
			String price  = courses.getPrice();
			totalApiPrice = totalApiPrice + Integer.parseInt(price);
		}
		for(CourseDetails courses : mobileCourses) {
			String price  = courses.getPrice();
			totalMobilePrice = totalMobilePrice + Integer.parseInt(price);
		}
		int allCoursesPrice = totalApiPrice + totalwebAutomationPrice + totalMobilePrice;
		
		System.out.println("WebAutomation Courses Price: "+totalwebAutomationPrice);
		System.out.println("API Courses Price: "+totalApiPrice);
		System.out.println("Mobile Courses Price: "+totalMobilePrice);
		System.out.println("All Courses Price: "+allCoursesPrice);
		
	}


}
