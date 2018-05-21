package Test;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cn.hdu.HDU_Minitor.dao.UserDao;
import cn.hdu.HDU_Minitor.entity.User;

public class TestUser extends TestBase {
	private  ApplicationContext ctx;
	private  UserDao userDao;
	
	@Before
	public void init() {
		ctx=super.getContext();
		userDao=ctx.getBean("userDao",UserDao.class);
		
	}
	@Test
	public void testUserDao() {
		User user=userDao.findByUserPhone("15549432607");
		System.out.println(user);
		
	}
	

}
