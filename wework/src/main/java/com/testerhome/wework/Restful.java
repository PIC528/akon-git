package com.testerhome.wework;

import java.util.HashMap;

//定义一个接口所需要的所有细节
//URL、method、query参数
public class Restful {
	public String url;
	public String method;
	public HashMap<String,String> headers;
	public HashMap<String,String> query;
	public String body;
}
