package com.testerhome.wework;
//import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.JsonPath;
import com.testerhome.wework.Wework;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGetToken {
	@Test
	void testToken() {
		//使用given方法
		//Wework wework = new Wework();
		String token = Wework.obtainToken();
		//assertThat(token,not(equalTo(null)));
		System.out.println(token);
	}
	
}
