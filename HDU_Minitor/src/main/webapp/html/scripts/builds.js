function loadBuild(){
	
	
	//给动态加载的user_ul下的li元素动态绑定click事件
	$("#user_ul").on("click","li",function(){
		$("#user_ul div").hide();
		//选中样式
		$("#user_ul a").removeClass("checked");
		$(this).find("a").addClass("checked");
		//清除build_ul所有的元素
		$("#build_ul").empty();
		//alert("cliked li successful");
		/*
		 * 加载楼栋信息
		 */
		//获取参数
		var user_id=$(this).data("user_id");
		var cookie=getCookie("user_id");
		//检查格式
		//表格处理
		
		tables.clear();
		clearTimeout(t1);
		if(hadTable){
		  tables.destroy();
		  hadTable=false;
		}
		
		
		if(cookie==null){
			window.location.href="../login.html";
		}else{
			//发送ajax请求
			$.ajax({
				url:path+"/build/loadbuild.do",
				type:"post",
				data:{"user_id":user_id},
				dataType:"json",
				success:function(result){
					var builds=result.data;
					for(var i=0;i<result.status;++i){
						var build_id=builds[i].buildID;
						var build_nu=builds[i].buildNumber;
						var build_name=builds[i].buildName;
						 createBuildLi(build_nu,build_name,build_id,user_id);
					}
				},
				error:function(){
					alert("error:"+user_id);
				}
			});
			
		}
	
		
	});
};
/**
*   生成build_ul下的li 元素
*/
function createBuildLi(build_nu,build_name,build_id,user_id){
	var sli="";
	sli+='<li class="online">';
	sli+='<a>';
	sli+='<i class="glyphicon glyphicon-home" style="color:#52808c;padding-right: 5px;" title="online" rel="tooltip-bottom"></i>';
	sli+=build_nu+" "+build_name;
	sli+='<button type="button" style="padding:3px 5px;" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
	sli+='</a>';
	sli+='<div class="note_menu"  tabindex="-1">';
	sli+='<dl>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_move" style="padding:3px 3px;" title="编辑"><i class="fa fa-random"></i></button></dt>';
    //sli+='<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
	sli+=' </dl>';
	sli+=' </div>';
	sli+='</li>';
	$li=$(sli);
	//加入build_id 和user_id
	$li.data("build_id",build_id);
	$li.data("user_id",user_id);
	$("#build_ul").append($li);	
};//给用户增加build信息
/**
 * 给用户增加楼栋，楼的名字唯一，
 * 根据楼名查询数据库，如果有且没有显示出来，则显示出来，且在hdu_user_role_build中插入数据
 * 如果没有则新建该楼栋数据
 * @returns
 */
function addBuild(){
	$("#add_build").click(function(){
		//判断是否选了用户
		$user_li=$("#user_ul a.checked").parent();
		var user_id=$user_li.data("user_id");
		//alert("user_id:"+user_id);
		if(user_id==undefined){
			alert("添加用户管理权限之前，请先选中用户");
		}else{
			$("#can").load( "alert/alert_addbuild.html");
			$('#can').off("click",'#addbuild_sure');
		
			//加载该用户还没用管理权限的楼栋
			$.ajax({
				url:path+"/build/loadotherbuild",
				type:"post",
				data:{"user_id":user_id},
				dataType:"json",
				success:function(result){
					if(result.status==0){
						var builds=result.data;
						  var b=$("#select_build option:first").text();  
						  $("#select_build").html('<option>'+b+'</option>'); 
						for(var i=0;i<builds.length;++i){
							
							$("#select_build").append(
									"<option value='"+builds[i].buildID+"'>编号:"
									+builds[i].buildNumber+"&nbsp;&nbsp;&nbsp;名称:"+builds[i].buildName+"</option>");
						
						}
					}else{
						alert(result.msg);
					}
				},
				error:function(){
					alert("加载没有管理权限的房间，请求错误！");
				}
			});
			//按钮事件绑定On
			$("#can").on("click","#addbuild_sure",function(){
				var ok=true;
//				//参数获取
//				 var build_num=$("#input_buildnum").val().trim();
//				 var build_name=$("#input_buildname").val().trim();
//				 //格式检查
//				 if(build_num==""){
//					 ok=false;
//					 $("#input_buildnum").attr("placeholder","楼栋编号不能为空");
//				 };
//				 if(build_name==""){
//					 ok=false;
//					 $("#input_buildname").attr("placeholder","楼栋名字不能为空");
//					 
//				 };
				//获取参数build_id
				var build_id=$("#select_build").val();
				var text=$("#select_build option:selected").text();
				//字符串截取
				var build_num=text.substring(text.indexOf(':')+1,text.indexOf('名')).trim();
				var build_name=text.substring(text.lastIndexOf(':')+1).trim();
				console.log(build_num+":"+build_name);
				if(build_id=='请选择楼栋'){ok=false;alert("您没有选择楼栋");}
				//发送ajax请求
				 if(ok){
					 $.ajax({
						 url:path+"/build/addbuild.do",
						 type:"post",
						 data:{
							   "user_id":user_id,
							   "build_id":build_id
							   },
						dataType:"json",
						success:function(result){
							var status=result.status;
							if(status==0){
								
								createBuildLi(build_num,build_name,build_id,user_id);
							}
							alert(result.msg);
							
						},
						error:function(){
							alert("请求出现错误");
						}
					 });
				 }
			});
			
		}
	});
	return false;
};
function showBuildSlideDown(){
	$("#build_ul").on("click",".btn_slide_down",function(){
		//隐藏笔记菜单
	 	$("#build_ul div").hide();
		var user_menu=$(this).parents("li").find("div");
		user_menu.slideDown(100);
		return false;
	});
	//点击body隐藏菜单
	$("body").click(function(){
		 $("#build_ul div").hide();
	});
};

function deleteBuild(){
	 //此处删除只是从hdu_user_role_build中删除user和build的关系
	 //以及hdu_user_role_room中room_id属于该楼栋use_id为该用户的关系
	 $("#build_ul").on("click",".btn_delete",function(){
		 //获取选中的父元素
		 $li=$(this).parents("li");
		 //获取参数
		 var user_id=$li.data("user_id");
		 var build_id=$li.data("build_id");
		 console.log(user_id+"--"+build_id);
		 //发送ajax请求
		 $.ajax({
			url:path+"/build/removebuild.do",
			type:"post",
			data:{"user_id":user_id,
				  "build_id":build_id
				  },
			dataType:"json",
			success:function(result){
				if(result.status!=-1){
					$li.remove();
					//清除定时器
					
				}
				alert(result.msg);
				
			},
			error:function(){
				alert("删除失败");
			}
			
		 });
		 
	 });
	
};
