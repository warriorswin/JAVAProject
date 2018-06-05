package Test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cn.hdu.HDU_Minitor.dao.BuildDao;
import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.util.MinitorUtil;

public class TestBuild extends TestBase {
	private ApplicationContext ctx;
	private BuildDao buildDao;
	
	@Before
	public void init() {
		ctx=super.getContext();
		buildDao=ctx.getBean("buildDao",BuildDao.class);
		
	}

	@Test
	public void testFindByNu() {
		Build build=buildDao.findBuildByNum("1");
		System.out.println(build);

	}
	@Test
	public void testSave() {
		Build build=new Build();
		build.setBuildID(MinitorUtil.createId());
		build.setBuildName("test");
		build.setBuildNumber("13");
		buildDao.save(build);
		
	}
	

}
