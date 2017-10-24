package com.fly.utils.ioc;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fly.utils.annotation.AOP;
import com.fly.utils.aop.InvocationHandlerImpl;

/**
 * 基于xml文件的bean工厂实现类
 * 采用懒汉式单例模式
 * 初始化时会加载对应的xml配置文件
 * 默认加载applicationContext.xml
 */
public class XmlBeanFactory implements BeanFactory {

	private static XmlBeanFactory INSTANCE;

	public static BeanFactory getFactory(String xmlPath) {

		if (INSTANCE == null) {
			synchronized(XmlBeanFactory.class) {
				if (INSTANCE == null)
					INSTANCE = new XmlBeanFactory(xmlPath);				
			}
		}
		return INSTANCE;
	}

	public static BeanFactory getFactory() {
		if (INSTANCE == null) {
			synchronized(XmlBeanFactory.class) {
				if (INSTANCE == null)
					INSTANCE = new XmlBeanFactory();				
			}
		}
		return INSTANCE;
	}

	@SuppressWarnings("rawtypes")
	Map<String, Class> classMap = new HashMap<>();
	Map<String, Object> map = new HashMap<>();

	private XmlBeanFactory() {
		this("applicationContext.xml");
	}

	private XmlBeanFactory(String xmlPath) {
		try {
			Document doc = new SAXReader()
					.read(BeanFactory.class.getClassLoader().getResourceAsStream(xmlPath));
			Element root = doc.getRootElement();
			List<Element> beans = root.elements("bean");
			for (Element bean : beans) {
				String classValue = bean.attributeValue("class");
				String id = bean.attributeValue("id");
				System.out.println(id + " " + classValue);
				Class<?> clazz = Class.forName(classValue);

				//懒加载，用的时候实例化
				classMap.put(id, clazz);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getBean(String id) {
		Class<?> c = classMap.get(id);
		if (c != null) {
			//单例化
			Object o = map.get(id);
			if (o == null) {
				try {
					o = c.newInstance();
					if (c.isAnnotationPresent(AOP.class)) {
						o = Proxy.newProxyInstance(this.getClass().getClassLoader(),
								o.getClass().getInterfaces(),
								new InvocationHandlerImpl(o));
					}
					map.put(id, o);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return map.get(id);
	}

}
