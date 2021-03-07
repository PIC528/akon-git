package com.testerhome.wework.contact;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvFileSource;;


class MemberTest {
	static Member member;
	@BeforeAll
	static void setup() {
		member = new Member();
	}
	
	@ParameterizedTest
	//相当于一个数据驱动，想给几个文件就可以给几个文件
	//通过三个数据生成多种数据，来校验用例正确性
	@ValueSource(strings = {"akon","billy","tommy"})
	void create(String name) {
		String nameNew = name+member.random;
		String random = String.valueOf(System.currentTimeMillis()).substring(5+0, 5+8);
		//注意这里取的是毫秒，5-13位会有变化，而短时间内前8位没变，仍然会重复，所以要加5
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userid",nameNew);
		map.put("name",nameNew+member.random);
		map.put("alias",nameNew+member.random);
		map.put("mobile","151"+random);
		//random短时间内生成的随机数是相同的，System.currentTimeMillis()可以保证每次生成的随机数都是不同的，
		map.put("email",random+"@qq.com");
		member.create(map).then().statusCode(200).body("errcode",equalTo(0));
	}
	
	@ParameterizedTest
	//相当于一个数据驱动，想给几个文件就可以给几个文件
	//通过三个数据生成多种数据，来校验用例正确性
	@CsvFileSource(resources = "/data/member.csv")
	void create(String name,String alias) {
		String nameNew = name+member.random;
		String random = String.valueOf(System.currentTimeMillis()).substring(5+0, 5+8);
		//注意这里取的是毫秒，5-13位会有变化，而短时间内前8位没变，仍然会重复，所以要加5
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userid",nameNew);
		map.put("name",nameNew+member.random);
		map.put("alias",alias);
		map.put("mobile","151"+random);
		//random短时间内生成的随机数是相同的，System.currentTimeMillis()可以保证每次生成的随机数都是不同的，
		map.put("email",random+"@qq.com");
		member.create(map).then().statusCode(200).body("errcode",equalTo(0));
	}
}
