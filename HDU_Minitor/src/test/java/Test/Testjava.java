package Test;

import java.util.ArrayList;
import java.util.List;

import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.util.MinitorResult;

public class Testjava {

	public static void main(String[] args) {
		List<? super Room> list = new ArrayList<Room>();
		Room room = new Room();
		list.add(room);
		list.get(0);
		MinitorResult<? super Room> m = new MinitorResult<Room>();
		Object ob = m.getData();
		System.out.println(ob);
	}

}
