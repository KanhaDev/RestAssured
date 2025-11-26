package com.restassuredDemo;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(Payload.coursePrice());
		
		//Print No of courses returned by API
		//int size = js.getList("courses").size();
		int size = js.getInt("courses.size()"); //Here we are passing size method directly
		System.out.println("No of courses returned by API: "+size);
		
		//Print Purchase Amount
		int totalPrice = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount: "+totalPrice);
		
		//Print Title of the first course
		System.out.println("Title of the first course: "+js.get("courses[0].title"));
		
		//Print All course titles and their respective Prices
		for(int i=0;i<size;i++) {
			System.out.println("Course title: "+js.get("courses["+i+"].title")+", Price :"+js.get("courses["+i+"].price"));
		}
		
		//Print no of copies sold by RPA Course
		for(int i=0;i<size;i++) {
			if(js.get("courses["+i+"].title").toString().equals("RPA")) {
				System.out.println(js.get("courses["+i+"].copies").toString());
			}
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for(int i=0;i<size;i++) {
			
			int coursePrice = js.getInt("courses["+i+"].price");
			int courseCopies = js.getInt("courses["+i+"].copies");
			sum += coursePrice*courseCopies;
			
		}
		
		if(totalPrice == sum) {
			System.out.println("Sum of all courses price matches with purchased amount");
		}
		else {
			System.out.println("Sum of all courses price not matches with purchased amount");
		}
		

	}

}
