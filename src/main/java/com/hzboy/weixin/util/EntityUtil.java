package com.hzboy.weixin.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Property;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 获取表的一些信息
 * @ClassName: EntityUtil 
 * @Description: TODO 获取表的一些信息
 * @author xiongxiaotun@126.com 
 * @date 2015年4月22日 上午10:34:39 
 *
 */
public class EntityUtil {
	/**
	 * hibernate配置
	 */
	private static Configuration hibernateConf;

	static {
		
		HttpServletRequest request = ( (ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		System.out.println(request);
		/**
		 * 单元测试使用
		 */
//		 BeanFactory f = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml","applicationContext-security.xml"});  
		/**
		 * 网络环境
		 */
		BeanFactory f = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());  
		LocalSessionFactoryBean configBean = (AnnotationSessionFactoryBean) (f.getBean("&sessionFactory"));//这里其实是获取到SessionFactory的上一级对象  
		hibernateConf = configBean.getConfiguration();  

//		if (hibernateConf == null) {
//			hibernateConf = new AnnotationConfiguration().configure();// 注解方式
//			hibernateConf.buildSessionFactory();// 注解方式必须的
//		}
	}

	private static PersistentClass getPersistentClass(Class<?> clazz) {
		synchronized (EntityUtil.class) {
			PersistentClass pc = hibernateConf.getClassMapping(
					clazz.getName());
			if (pc == null) {
				pc = hibernateConf.getClassMapping(clazz.getName());

			}
			return pc;
		}
	}

	/**
	 * 功能描述：获取实体对应的表名
	 * 
	 * @param clazz
	 *            实体类
	 * @return 表名
	 */
	public static String getTableName(Class<?> clazz) {
		return getPersistentClass(clazz).getTable().getName();
	}

	/**
	 * 功能描述：获取实体对应表的主键字段名称，只适用于唯一主键的情况
	 * 
	 * @param clazz
	 *            实体类
	 * @return 主键字段名称
	 */
	public static String getPrimaryKey(Class<?> clazz) {

		return getPrimaryKeys(clazz).getColumn(0).getName();

	}

	/**
	 * 功能描述：获取实体对应表的主键字段名称
	 * 
	 * @param clazz
	 *            实体类
	 * @return 主键对象primaryKey ，可用primaryKey.getColumn(i).getName()
	 */
	public static PrimaryKey getPrimaryKeys(Class<?> clazz) {

		return getPersistentClass(clazz).getTable().getPrimaryKey();

	}

	/**
	 * 功能描述：通过实体类和属性，获取实体类属性对应的表字段名称
	 * 
	 * @param clazz
	 *            实体类
	 * @param propertyName
	 *            属性名称
	 * @return 字段名称
	 */
	public static String getColumnName(Class<?> clazz, String propertyName) {
		PersistentClass persistentClass = getPersistentClass(clazz);
		Property property = persistentClass.getProperty(propertyName);
		Iterator<?> it = property.getColumnIterator();
		if (it.hasNext()) {
			Column column = (Column) it.next();
			return column.getName();
		}
		return null;
	}
	
	/**
	 * 功能描述：通过实体类，获取实体类属性对应的表列名
	 * 
	 * @param clazz
	 *            实体类
	 * @return 列名
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getColumnNames(Class<?> clazz) {
		List<String> result = new ArrayList<String>();
		PersistentClass persistentClass = getPersistentClass(clazz);
		Iterator<Property> propertyit = persistentClass.getPropertyIterator();
		while (propertyit.hasNext()) {
			Property property = (Property) propertyit.next();
			Iterator<Column>  columnit= property.getColumnIterator();
			while(columnit.hasNext()){
				Column column = (Column)columnit.next();
				result.add(column.getName());
			}
		}
		return result;
	}

}
