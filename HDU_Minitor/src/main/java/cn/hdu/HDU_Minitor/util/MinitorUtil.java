package cn.hdu.HDU_Minitor.util;

import java.util.UUID;

public class MinitorUtil {
	/*
	 *ʹ��uuid �㷨��������
	 */
	public static String createId() {
		 UUID uuid=UUID.randomUUID();
		 String id=uuid.toString();
		return id.replace("-","");
	}
	public static void main(String[] args) {
		System.out.println(MinitorUtil.createId());
	}
}