package Test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cn.hdu.HDU_Minitor.dao.UserBuildDao;
import cn.hdu.HDU_Minitor.entity.UserBuild;

public class TestUserBuild extends TestBase{
	private ApplicationContext ctx;
	private UserBuildDao userBuildDao;
	
	@Before
	public void init() {
		ctx=super.getContext();
		userBuildDao=ctx.getBean("userBuildDao",UserBuildDao.class);
		
	}
	@Test
	public void testfindByUid() {
		List<UserBuild> ubs=userBuildDao.findByUserId("bdf61e8ede304406a828f220ff3d1f6d");
		System.out.println(ubs);
	}

}
