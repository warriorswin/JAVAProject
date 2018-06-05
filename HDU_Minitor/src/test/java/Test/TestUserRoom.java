package Test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cn.hdu.HDU_Minitor.dao.UserRoomDao;
import cn.hdu.HDU_Minitor.entity.UserRoom;

public class TestUserRoom extends TestBase{
	private ApplicationContext ctx;
	private UserRoomDao userRoomDao;
	
	@Before
	public void init() {
		ctx=super.getContext();
		userRoomDao=ctx.getBean("userRoomDao", UserRoomDao.class);
	}
	@Test
	public void testFindURByRId() {
		
		UserRoom userRoom=userRoomDao.findURByRId("1");
		System.out.println(userRoom);
		
	}
   @Test
   public void testSave() {
	   Map<String,String> ids=new HashMap<String,String>();
	   ids.put("user_id","bdf61e8ede304406a828f220ff3d1f6d");
	   ids.put("room_id", "3");
	   int i=userRoomDao.save(ids);
	   System.out.println(i);
   }
}