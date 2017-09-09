package com.happylifeplat.wechat.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 获取配置文件*.properties属性
 * 
 * @author liupan
 * @date 2016年12月8日
 */
public class PropertiesUtil extends PropertyPlaceholderConfigurer {

	private static Map<String, String> propertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {

		super.processProperties(beanFactory, props);
		// load properties to propertiesMap
		propertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			propertiesMap.put(keyStr, value);
		}
	}

	/**
	 * 获取*.properties文件属性： String value = PropertiesUtil.getContextProperty("jdbc.url");
	 * 
	 * @param name
	 * @return
	 */
	public static String getContextProperty(String key) {
		return propertiesMap.get(key);
	}
}
