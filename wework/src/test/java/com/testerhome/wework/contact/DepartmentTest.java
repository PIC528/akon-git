package com.testerhome.wework.contact;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.*;

import com.testerhome.wework.contact.Department;

import static org.hamcrest.Matchers.equalTo;

class DepartmentTest {
	Department department;
	//定义时间戳，使新建的部门名称不冲突
	String random = String.valueOf(System.currentTimeMillis());
	
	@BeforeEach
	void setup() {
		if(department == null) {
			department = new Department();
			department.deleteAll();
		}
	}

	@Test
	void list() {
		department.list("2").then().log().all().statusCode(200);
		
	}
	@Test
	void create() {
		//新建一个新的部门,并添加断言
		department.create("test2"+random,"1").then().body("errcode", equalTo(0));
		//接下来我们试图新建一个已存在的部门,并添加断言
		//department.create("test2","1").then().body("errcode", equalTo(60008));
	}
	@Test
	void createWithChinese() {
		//新建一个中文名称的部门,并添加断言
		//department.create("测试2","1").then().body("errcode", equalTo(0));
	}
	@Test
	void createByMap() {
		//利用hashmap一次可以上传多个数据
		HashMap<String,Object> map = new HashMap<String,Object>(){{
			put("parentid","1");
			put("name",String.format("test_map",random));
		}
		};
		department.create(map).then().body("errcode",equalTo(0));
	}
	
	@Test
	//小技巧，删除一个新的部门的时候不要假设这个用例依赖于之前的case
	//也就是删除前同时先建一个专门用于修改的case
	void delete() {
		//String nameOld = "forDelete";
		String nameOld = "forDelete"+random;
		department.create(nameOld,"1");
		String id = department.list("")
		.path("department.find{it.name == '"+nameOld+"'}.id").toString();
		department.delete(id).then().log().all().statusCode(200)
		.body("errcode", equalTo(0))
		.body("errmsg",equalTo("deleted"));
		//下面是修改前，不新增直接删除的
		//department.delete("5").then().log().all().statusCode(200)
		//.body("errmsg",equalTo("deleted"));
	}
	@Test
	//小技巧，修改或者删除一个新的部门的时候不要假设这个用例依赖于之前的case
	//也就是修改前同时先建一个专门用于修改的case
	
	//这里会发现，如果在新建前该部门已经存在，那么新建就会报错，后面的更新和删除则无法执行
	//这里可以使用时间戳，随机生成机构名称，保证每次新建的部门不冲突。时间戳定义在前面
	void update() {
        // String nameOld = "forChange";
		String nameOld = "forChange"+random;
        department.create(nameOld,"1");
        String id = department.list("")
        		.path("department.find{it.name == '"+nameOld+"'}.id").toString();
        department.update(id, nameOld).then()
        .body("errcode", equalTo(0))
        .body("errmsg", equalTo("updated"));
  }
	
	@Test
	void deleteAll() {
		department.deleteAll();
	}

}
