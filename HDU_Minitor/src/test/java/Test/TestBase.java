package Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public  abstract class TestBase {
	public ApplicationContext getContext() {
		System.out.println("getContext()");
		String[] conf= {"conf/spring-mvc.xml",
						"conf/spring-mybatis.xml"};
		ApplicationContext ctx=new 
				ClassPathXmlApplicationContext(conf);
		return ctx;
		
	}

}
