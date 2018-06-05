package cn.hdu.HDU_Minitor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hdu.HDU_Minitor.dao.UserBuildDao;
import cn.hdu.HDU_Minitor.dao.UserDao;
import cn.hdu.HDU_Minitor.dao.UserRoomDao;
import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.service.ex.InsertAndUpdateException;
import cn.hdu.HDU_Minitor.util.MinitorResult;
import cn.hdu.HDU_Minitor.util.MinitorUtil;
import cn.hdu.HDU_Minitor.util.SendPhoneMsg;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);
	@Resource(name="userDao")
	private UserDao userDao;
	@Resource(name="userBuildDao")
	private UserBuildDao userBuildDao;
	@Resource(name="userRoomDao")
	private UserRoomDao userRoomDao;
	/*
	 * ��¼��֤
	 * (non-Javadoc)
	 * @see cn.hdu.HDU_Minitor.service.UserService#checkLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public MinitorResult<Object> checkLogin(String userPhone,String password) {
		//û����֤����
		User user=userDao.findByUserPhone(userPhone);
		MinitorResult<Object> result=new MinitorResult<Object>();
		if(user==null) {
			result.setStatus(1);
			result.setMsg("û�и��û�");	
			return result;
		}else {
			 if(user.getUser_password().equals(password)) {
				 String msg="��¼�ɹ���";
				 result.setStatus(0);
				 if("df38dd9ae1b34027acce21b8da08468f".equals(user.getUser_id()))
				 {
					 msg="����Ա  "+msg;
					 result.setMsg(msg);
				 }else {
					 //status������ͨ�û�
					 result.setStatus(3);
					 msg="��ͨ�û� "+msg;
					 result.setMsg(msg);
					 
				 }
				 Map<String,String> userInfo=new HashMap<String,String>();
				 userInfo.put("user_id", user.getUser_id());
				 userInfo.put("user_name",user.getUser_name());
				 result.setData(userInfo);
				 return result;
			 }else {
				 result.setStatus(2);
				 result.setMsg("�������");
				 return result;
			 }
		}
	}
	@Override
	/*
	 * ����������
	 * (non-Javadoc)
	 * @see cn.hdu.HDU_Minitor.service.UserService#RdmPwdGenertor(java.lang.String)
	 */
	public MinitorResult<Object> RdmPwdGenertor(String userPhone) {
		MinitorResult<Object> result=new MinitorResult<Object>();
		
		//��ѯ���ݿ⣬��֤�Ƿ��и��û�
		User user=userDao.findByUserPhone(userPhone);
		if(user==null) {
			result.setStatus(1);
			result.setMsg("û�и��û�");
			return result;
			
		}else {
		
			//����������뷵��
			String randompwd=MinitorUtil.createId().substring(0, 6);
			 //���Ͷ���
			System.out.println(userPhone+":"+randompwd);
			//Boolean bool=SendPhoneMsg.sendMsg(userPhone,randompwd);
			Boolean bool=false;
			if(bool) {
				result.setStatus(0);
				result.setMsg("��������ѷ���");
				
			}else {
				result.setStatus(2);
				result.setMsg("������뷢��ʧ��");
			}
			
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public MinitorResult<Object> insertOrUpdateUserInfo(User user) {
		String userID = user.getUser_id();
		User oldUser = userDao.findByUserID(user.getUser_id());
		MinitorResult<Object> result=new MinitorResult<Object>();
		if(oldUser==null) {
			try {
				userDao.saveUser(user);
				result.setStatus(0);
				result.setMsg("����û��ɹ�");
				result.setData(user);
				return result;
			} catch (Exception e) {
				throw new InsertAndUpdateException("����û�ʧ��",e);
			}
		}else {
			/*
			 * update�û�����Ϣ
			 */
			FutureTask<Map<String, String>> futureTaskUser = 
					new FutureTask<Map<String, String>>(new Callable<Map<String, String>>() {
						@Override
						public Map<String, String> call() throws Exception {
							return user.checkUser(oldUser);
						}
					});
			/*
			 * insert��delete��build��Ϣ
			 */
			FutureTask<Map<String,List<Build>>> futureTaskBuild = 
					new FutureTask<Map<String,List<Build>>>(new Callable<Map<String,List<Build>>>() {
						@Override
						public Map<String,List<Build>> call() throws Exception {
							/*
							 * ���Ҫ��ӵ�ֵ
							 */
							final List<String> oldbuildIDs;
							if(oldUser.getBuilds()!=null) {
								oldbuildIDs = oldUser.getBuilds().stream().map((x)->{
									return x.getBuildID();
								}).collect(Collectors.toList());
							}else {
								oldbuildIDs=null;
							}
							List<Build> buildsInsert;
							if(user.getBuilds()!=null) {
								buildsInsert = user.getBuilds().stream().filter((x)->!x.checkBuild(oldbuildIDs))
										.collect(Collectors.toList());
							}else {
								buildsInsert=null;
							}
							
							/*
							 * ���Ҫɾ����ֵ 
							 */
							List<String> newbuildIDs;
							if(user.getBuilds()!=null) {
								newbuildIDs = user.getBuilds().stream().map((x)->{
									return x.getBuildID();
								}).collect(Collectors.toList());
							}else {
								newbuildIDs=null;
							}
							List<Build> buildsDelete;
							if(oldUser.getBuilds()!=null) {
								buildsDelete = oldUser.getBuilds().stream().filter((x)->!x.checkBuild(newbuildIDs))
										.collect(Collectors.toList());
							}else {
								buildsDelete=null;
							}
//							buildsDelete.stream().forEach(x->x.setRooms(null));
							logger.info(buildsDelete);
							/*
							 * ���ಢ����Map
							 */
							HashMap<String, List<Build>> insertOrDeleteMap = new HashMap<String,List<Build>>();
							insertOrDeleteMap.put("insert", buildsInsert);
							insertOrDeleteMap.put("delete", buildsDelete);
							return insertOrDeleteMap;
						}
					});
			
			/*
			 * insert��delete��room��Ϣ
			 */
			FutureTask<Map<String,List<Room>>> futureTaskRoom = new FutureTask<Map<String,List<Room>>>(new Callable<Map<String,List<Room>>>() {
				@Override
				public Map<String, List<Room>> call() throws Exception {
					List<Room> oldRooms = new ArrayList<Room>();
					if(oldUser.getBuilds()!=null) {
						oldUser.getBuilds().stream().forEach(x->{
							List<Room> rooms = x.getRooms();
							oldRooms.addAll(rooms);
						});
					}
					
					List<Room> newRooms = new ArrayList<Room>();
					if(user.getBuilds() !=null) {
						user.getBuilds().stream().forEach(x->{
							List<Room> rooms = x.getRooms();
							if(rooms!=null) {
								newRooms.addAll(rooms);
							}
						});
					}
					/*
					 * ���Ҫ��ӵķ���
					 */
					List<String> oldroomIDs = oldRooms.stream().map((x)->{
						return x.getRoomID();
					}).collect(Collectors.toList());
					List<Room> roomsInsert = newRooms.stream().filter((x)->!x.checkRoom(oldroomIDs))
					.collect(Collectors.toList());
					logger.info(roomsInsert);
					/*
					 * ���Ҫɾ���ķ���
					 */
					List<String> newroomIDs = newRooms.stream().map((x)->{
						return x.getRoomID();
					}).collect(Collectors.toList());
					List<Room> roomsDelete = oldRooms.stream().filter((x)->!x.checkRoom(newroomIDs))
							.collect(Collectors.toList());
					logger.info(roomsDelete);
					/*
					 * ���ಢ����Map
					 */
					HashMap<String, List<Room>> insertOrDeleteMap = new HashMap<String,List<Room>>();
					insertOrDeleteMap.put("insert", roomsInsert);
					insertOrDeleteMap.put("delete", roomsDelete);
					return insertOrDeleteMap;
				}
			});
			new Thread(futureTaskUser).start();
			new Thread(futureTaskBuild).start();
			new Thread(futureTaskRoom).start();
			/*
			 * �����û��Ļ�����Ϣ
			 */
			try {
				Map<String, String> updateUserInfo = null;
				try {
					updateUserInfo = futureTaskUser.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				if(updateUserInfo.size()>1) {
					userDao.updateUser(updateUserInfo);
				}
				
				/*
				 * ���¼�ط��伴ɾ������Ӽ�ط���
				 */
				Map<String,List<Room>> insertOrDeleteRoomMap = null;
				try {
					insertOrDeleteRoomMap = futureTaskRoom.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				List<Room>  insertRooms = insertOrDeleteRoomMap.get("insert");
				if(insertRooms!=null&&insertRooms.size()>0) {
					User insertRoomUser = new User();
					insertRoomUser.setUser_id(userID);
					Build build = new Build();
					build.setRooms(insertRooms);
					List<Build> list = new ArrayList<Build>();
					list.add(build);
					insertRoomUser.setBuilds(list);
					userDao.saveUser(insertRoomUser);
				}
				List<Room> deleteRooms = insertOrDeleteRoomMap.get("delete");
				if(deleteRooms!=null&&deleteRooms.size()>0) {
					User deleteRoomUser = new User();
					deleteRoomUser.setUser_id(userID);
					Build build = new Build();
					build.setRooms(deleteRooms);
					List<Build> list = new ArrayList<Build>();
					list.add(build);
					deleteRoomUser.setBuilds(list);
					userDao.deleteUser(deleteRoomUser);
				}
				
				/*
				 * ���¼��¥��ɾ������Ӽ��¥
				 */
				Map<String,List<Build>> insertOrDeleteBuildMap = null;
				try {
					insertOrDeleteBuildMap = futureTaskBuild.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				List<Build> insertBuilds = insertOrDeleteBuildMap.get("insert");
				if(insertBuilds!=null&&insertBuilds.size()>0) {
					System.err.println(insertBuilds);
					insertBuilds.stream().forEach(x->x.setRooms(null));
					User insertBuildUser = new User();
					insertBuildUser.setUser_id(userID);
					insertBuildUser.setBuilds(insertBuilds);
					userDao.saveUser(insertBuildUser);
				}
				List<Build> deleteBuilds = insertOrDeleteBuildMap.get("delete");
				if(deleteBuilds!=null&&deleteBuilds.size()>0) {
					deleteBuilds.stream().forEach(x->x.setRooms(null));
					User deleteBuildUser = new User();
					deleteBuildUser.setUser_id(userID);
					deleteBuildUser.setBuilds(deleteBuilds);
					userDao.deleteUser(deleteBuildUser);
				}
				User finalUser = userDao.findByUserID(userID);
				if(finalUser.getBuilds()==null||finalUser.getBuilds().size()==0) {
					logger.info("builds's null");
					finalUser.setBuilds(null);
				}else {
					finalUser.getBuilds().stream().forEach(x->{
						if(x.getRooms()==null||x.getRooms().size()==0) {
							x.setRooms(null);
						}
					});
				}
				result.setStatus(0);
				result.setMsg("�����û��ɹ�");
				result.setData(finalUser);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new InsertAndUpdateException("�����û�ʧ��",e);
			}
		}
	}
	/*
	 * ��ѯ�������Ա֮��������û�
	 * @see cn.hdu.HDU_Minitor.service.UserService#loadAllUsers(java.lang.String)
	 */
	@Override
	public MinitorResult<Object> loadAllUsers(String adminID) {
		
		MinitorResult<Object> result=new MinitorResult<Object>();
			if(adminID==null) {
				result.setStatus(-1);
				
				return result;
			}
		List<User> users=userDao.findAllUser(adminID);
	
		    result.setStatus(users.size());
			result.setMsg("��ѯ�ɹ�");
			result.setData(users);
			return  result;
		}
	/*
	 * �����û�
	 */
	@Override
	public MinitorResult<Object> insertUser(String user_name, String user_phone) {
		MinitorResult<Object> result= new MinitorResult<Object>();
		
		if(user_name==null||user_name.length()<=0) {
			result.setStatus(-1);
			result.setMsg("�����û�������Ϊ��");
			return result;
		}
		if(user_phone==null||user_phone.length()<=0) {
			result.setStatus(-2);
			result.setMsg("�����û����ֻ��Ų���Ϊ��");
			return result;
		}
		//����û��Ƿ��Ѿ�����
		User user=userDao.findByUserPhone(user_phone);
		if(user!=null) {
			result.setStatus(-3);
			String info=user.getUser_name();
			result.setMsg("���ֻ��ŵ��û��Ѿ����ڣ�������:"+info);
	        return result;
		}else {
			User newUser=new User();
			newUser.setUser_id(MinitorUtil.createId());
			newUser.setUser_name(user_name);
			newUser.setUser_phone(user_phone);
			newUser.setUser_password(MinitorUtil.createId().substring(0,6));
			result.setStatus(0);
			result.setMsg("����û��ɹ�");
			result.setData(newUser.getUser_id());
			userDao.saveUser(newUser);
			return result;
		}
		
	}
	@Override
	public MinitorResult<Object> deleteUser(String user_id) {
		//������ʽ���(�Ƿ����ʣ�
		if(user_id==null||"".equals(user_id)) {
			return null;
		}
		MinitorResult<Object> result=new MinitorResult<Object>();
		//�쳣�ع�
		//ɾ��hdu_monitor_user�е���Ϣ
		userDao.deleteUserByUId(user_id);
		//ɾ��hdu_user_role_build�е���Ϣ
	    userBuildDao.deleteByUId(user_id);
		//ɾ��hdu_user_role_room�е���Ϣ
	    userRoomDao.deleteByUId(user_id);
	    
	    //��������ɹ�
	    result.setStatus(0);
	    result.setMsg("ɾ���ɹ�");
		return result;
	}

}
	
	


