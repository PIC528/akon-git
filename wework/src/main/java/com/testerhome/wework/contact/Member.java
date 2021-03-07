package com.testerhome.wework.contact;
import io.restassured.response.Response;
import java.util.*;
//人员管理
public class Member extends Contact{
	public Response create(HashMap<String,Object> map) {
		//模板引擎
		String body = template("/data/member.json",map);
		return getDefaultRequestSpecification()
			.body(body)
			.when().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
			.then().log().all().extract().response();
	}
	
}
