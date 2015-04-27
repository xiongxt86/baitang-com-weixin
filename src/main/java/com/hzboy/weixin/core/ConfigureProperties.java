package com.hzboy.weixin.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.hzboy.weixin.util.FileUtil;

/**
 * 配置文件读取
 * @ClassName: ConfigureProperties 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author xiongxiaotun@126.com 
 * @date 2015年4月24日 下午1:34:32 
 *
 */
public class ConfigureProperties {
	/**
	 * 配置
	 */
	public static final Properties prop = new Properties();
	static{
		InputStream input = null;
		try {
			input = ConfigureProperties.class.getResourceAsStream("classpath:properties/project.properties");
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			FileUtil.release(input, null);
		}
	}
}
