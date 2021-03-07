package com.testerhome.wework;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class WeworkConfig {
	public String agentId = "1000002";
	public String secret = "EkEqt9k29iblWVcGzp5_FJboL1invZ4SVe5UmjqZ__o";
	public String corpid = "wwdbd12c3a434bc291";
	public String contactSecret = "DyvJgtFcAceVi5xSuSgSim2U6hDLStZOE2IMKjsdgGo";
	//使用单例模式维护配置
	//下面是一个简单的单例，不完整
	//首先创建一个实例
	private static WeworkConfig weworkconfig;
	//创建一个公共的静态方法，允许别人通过这个方法去调用
	public static WeworkConfig getInstance() {
		//若私有变量为空，则第一次对他做初始化
		if(weworkconfig == null) {
			//之前使用new获得的是默认值，这次不用默认值，而是从配置文件里读取
			weworkconfig = load("/config/WeworkConfig.yaml");
			//weworkconfig = new WeworkConfig();
			//System.out.println(weworkconfig);
			System.out.println(weworkconfig.agentId);
		}
		return weworkconfig;
	}
	//get方法获取的给定的值，将来实际上可能需要从配置文件yaml或者json中读取，下面给出一个方法
	//1.给定一个路径,从yaml或者json读取数据
	public static WeworkConfig load(String path) {
		//todo:read from yaml or json
		//读取yaml
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		//获取实例并以yaml格式打印出来
			try {
				return mapper.readValue(WeworkConfig.class.getResourceAsStream(path), WeworkConfig.class);
				//读取一个默认的配置文件，交给WeworkConfig类的一个实例，并把实例返回
			//System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//如果文件不存在，抛出异常
				return null;
			}
	}
}
