package Test;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cn.hdu.HDU_Minitor.dao.UserDao;
import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;
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
	/*
	 * 信息要全部添加进去
	 */
	@Test
	public void testSaveUser() {
		User user = new User();
		user.setUser_id("c62803f57c5c4661b6fa6e527eb446f6");
		user.setUser_name("admin1");
		user.setUser_password("123456");
		user.setUser_phone("11111111111");
		Build build1 = new Build();
		Build build2 = new Build();
		build1.setBuildID("24f3508be4154820b12c336fc30cd343");
		build2.setBuildID("1cf8b3aaaef043f5b3fb85e58ff3555e");
		Room room1 = new Room();
		Room room2 = new Room();
		Room room3 = new Room();
		room1.setRoomID("a578e0ed9a844bf5b86964dafc4b7e1f");
		room2.setRoomID("9ef7f446f81544deb4065943079d63eb");
		room3.setRoomID("6965ffc5afad4573bce0e097826d1da7");
		List<Room> rooms1 = new ArrayList<Room>();
		List<Room> rooms2 = new ArrayList<Room>();
		rooms1.add(room1);
		rooms2.add(room2);
		rooms2.add(room3);
		/*
		 * 将房间与楼层进行绑定
		 */
		build1.setRooms(rooms1);
		build2.setRooms(rooms2);
		//管理楼层的集合
		List<Build> builds = new ArrayList<Build>();
		builds.add(build1);
		builds.add(build2);
		//将管理楼层与用户绑定
		user.setBuilds(builds);
		
		System.out.println(userDao.saveUser(user));
	}
	

}
