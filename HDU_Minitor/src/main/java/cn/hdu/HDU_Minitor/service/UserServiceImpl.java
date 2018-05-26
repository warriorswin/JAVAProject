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

import cn.hdu.HDU_Minitor.dao.UserDao;
import cn.hdu.HDU_Minitor.entity.Build;
import cn.hdu.HDU_Minitor.entity.Room;
import cn.hdu.HDU_Minitor.entity.User;
import cn.hdu.HDU_Minitor.service.ex.InsertAndUpdateException;
import cn.hdu.HDU_Minitor.util.MinitorResult;

@Service("userService")
public class UserServiceImpl implements UserService{
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);
	@Resource(name="userDao")
	private UserDao userDao;

	@Override
	public MinitorResult<Object> checkLogin(String userPhone,String password) {
		User user=userDao.findByUserPhone(userPhone);
		MinitorResult<Object> result=new MinitorResult<Object>();
		if(user==null) {
			result.setStatus(1);
			result.setMsg("没有该用户");	
			return result;
		}else {
			 if(user.getUser_password().equals(password)) {
				 result.setStatus(0);
				 result.setMsg("登录成功！");
				 result.setData(user.getUser_id());
				 return result;
			 }else {
				 result.setStatus(2);
				 result.setMsg("密码错误");
				 return result;
			 }
		}
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
				result.setMsg("添加用户成功");
				result.setData(user);
				return result;
			} catch (Exception e) {
				throw new InsertAndUpdateException("添加用户失败",e);
			}
		}else {
			/*
			 * update用户的信息
			 */
			FutureTask<Map<String, String>> futureTaskUser = 
					new FutureTask<Map<String, String>>(new Callable<Map<String, String>>() {
						@Override
						public Map<String, String> call() throws Exception {
							return user.checkUser(oldUser);
						}
					});
			/*
			 * insert或delete的build信息
			 */
			FutureTask<Map<String,List<Build>>> futureTaskBuild = 
					new FutureTask<Map<String,List<Build>>>(new Callable<Map<String,List<Build>>>() {
						@Override
						public Map<String,List<Build>> call() throws Exception {
							/*
							 * 检查要添加的值
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
							 * 检查要删除的值 
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
							 * 归类并返回Map
							 */
							HashMap<String, List<Build>> insertOrDeleteMap = new HashMap<String,List<Build>>();
							insertOrDeleteMap.put("insert", buildsInsert);
							insertOrDeleteMap.put("delete", buildsDelete);
							return insertOrDeleteMap;
						}
					});
			
			/*
			 * insert或delete的room信息
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
					 * 检查要添加的房间
					 */
					List<String> oldroomIDs = oldRooms.stream().map((x)->{
						return x.getRoomID();
					}).collect(Collectors.toList());
					List<Room> roomsInsert = newRooms.stream().filter((x)->!x.checkRoom(oldroomIDs))
					.collect(Collectors.toList());
					logger.info(roomsInsert);
					/*
					 * 检查要删除的房间
					 */
					List<String> newroomIDs = newRooms.stream().map((x)->{
						return x.getRoomID();
					}).collect(Collectors.toList());
					List<Room> roomsDelete = oldRooms.stream().filter((x)->!x.checkRoom(newroomIDs))
							.collect(Collectors.toList());
					logger.info(roomsDelete);
					/*
					 * 归类并返回Map
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
			 * 更新用户的基本信息
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
				 * 更新监控房间即删除或添加监控房间
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
				 * 更新监控楼即删除或添加监控楼
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
				result.setMsg("更新用户成功");
				result.setData(finalUser);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new InsertAndUpdateException("更新用户失败",e);
			}
		}
	}
	
	

}
