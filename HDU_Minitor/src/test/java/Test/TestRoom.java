package Test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cn.hdu.HDU_Minitor.dao.RoomDao;
import cn.hdu.HDU_Minitor.entity.Device;
import cn.hdu.HDU_Minitor.entity.Room;

public class TestRoom extends TestBase {
	
	private ApplicationContext ctx;
	private RoomDao roomDao;
	
	@Before
	public void init() {
		ctx=super.getContext();
		roomDao=ctx.getBean("roomDao",RoomDao.class);
		
	}
	@Test
	public void testFindRoom() {
		
		List<Room> rooms=roomDao.findRoomByBuildId("24f3508be4154820b12c336fc30cd343");
		System.out.println(rooms);
		
	}
	@Test
	public void testFindRoomByDId() {
		Room room=roomDao.findRoomByDId("3");
		System.out.println(room);
	}
	@Test
	public void testSave() {
		Room room=new Room();
		room.setRoomID("1");
		room.setBuildID("1");
		room.setDeviceID("1");
		room.setRoomName("test");
		roomDao.save(room);
		
	}
	@Test
	public void testfindDevice() {
		Device device=roomDao.findDeviceByDeId("1");
		System.out.println(device);
		
	}

	
}
