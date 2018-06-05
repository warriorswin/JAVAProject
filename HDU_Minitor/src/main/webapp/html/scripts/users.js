/**
 * admin.html加载完成发送ajax请求
 */	
function loadUser(){
	//获取cookie
	 		 var user_id=getCookie("user_id");
	 		 var user_name=getCookie("user_name");
	 		
	 		 if(user_id!=null){
	 			$("#admin_name").html(user_name);
	 		 $.ajax({
	 			 url:path+"/admin/loaduser.do",
	 			 type:"post",
	 		 	 data:{"user_id":user_id},
	 		 	 dataType:"json",
	 		 	 success:function(result){
	 		 		 if(result.status==-1){
	 		 			 window.location.href="../login.html";
	 		 		 }else{
	 		 		 	var users=result.data;
						// alert(users[0].user_id+":"+users[0].user_phone+":"+users[0].user_name);
	 		 		 	for(var i=0;i<result.status;++i){
	 		 		 		createUserLi(users[i].user_name,users[i].user_id);
	 		 		 	}
	 		 		 }
	 		 	 },
	 		 	 error:function(){
	 		 		 //alert("load user error");
	 		 	 }
	 		 });
	 	
	 		 }else{
	 			 window.location.href="../login.html";
	 		 }
};

/*
**显示用户
*<li class="online">
	<a  class='checked'>
		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> 大军<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>
	</a>
	<div class="note_menu" tabindex='-1'>
		<dl>
			<dt><button type="button" class="btn btn-default btn-xs btn_move" title='移动至...'><i class="fa fa-random"></i></button></dt>
			<dt><button type="button" class="btn btn-default btn-xs btn_share" title='分享'><i class="fa fa-sitemap"></i></button></dt>
			<dt><button type="button" class="btn btn-default btn-xs btn_delete" title='删除'><i class="fa fa-times"></i></button></dt>
		</dl>
	</div>
</li>
*/
function createUserLi(user_name,user_id){
	var sli="";
	sli+='<li class="online">';
	sli+='<a>';
	sli+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
	sli+=user_name;
	sli+='<button type="button" style="padding:3px 5px;" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
	sli+='</a>';
	sli+='<div class="note_menu"  tabindex="-1">';
	sli+='<dl>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_move" style="padding:3px 3px;" title="编辑"><i class="fa fa-random"></i></button></dt>';
	//sli+='<dt><button type="button" class="btn btn-default btn-xs btn_share" style="padding:3px 3px;" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
	sli+=' </dl>';
	sli+=' </div>';
	sli+='</li>';
	$li=$(sli);//将字符串装换为元素
	$li.data("user_id",user_id);//给data属性赋值
	//追加#user_ul元素后面
	$("#user_ul").append($li);
	
};


//增加用户
function addUser(){
	$("#add_user").click(function(){
		//加载对话框
		var user_name="";
		var user_phone="";
		 $("#can").load("alert/alert_adduser.html");
		 $("#can").on("blur","#input_name",function(){
			  user_name=$("#input_name").val().trim();
			 if(user_name==""){
			  $("#input_name").attr("placeholder","用户姓名不能为空");
			 }
		 });
		 $("#can").on("blur","#input_phone",function(){
			 user_phone=$("#input_phone").val().trim();
			 if(user_phone==""){
				 $("#input_phone").attr("placeholder","手机号不能为空");
			 }else{
			     isPhone(user_phone,this);
			 }
			 
		 });
		 $('#can').off("click",'#adduser_sure');
		 $("#can").on("click","#adduser_sure",function(){
			 //获取参数
			  user_name=$("#input_name").val().trim();
			  user_phone=$("#input_phone").val().trim();
			 var ok=true;
			 //格式检查
			 if(!isPhone(user_phone,"#input_phone")){
				 ok=false;
			 }
			  if(user_name==""){
				  ok=false;
				  
					  $("#input_name").val("");
					  $("#input_name").attr("placeholder","用户姓名不能为空");
			  }
			 //发送ajax请求
			  if(ok){
				  $.ajax({
					  url:path+"/user/adduser.do",
					  type:"post",
					  data:{"user_name":user_name,"user_phone":user_phone},
					  dataType:"json",
					  success:function(result){
						  //添加成功
						  if(result.status==0){
							  //创建li加入列表显示
							  createUserLi(user_name,result.data);
						  }
						  alert(result.msg);
						  
					  },
					  error:function(){
						  alert("添加用户失败");
					  }
				  });
			  };
			
			 
		 });
	})
};
         
function showUserSlideDown(){
	$("#user_ul").on("click",".btn_slide_down",function(){
		//隐藏笔记菜单
	 	$("#user_ul div").hide();
		var user_menu=$(this).parents("li").find("div");
		user_menu.slideDown(100);
		return false;
	});
	//点击body隐藏菜单
	$("body").click(function(){
		 $("#user_ul div").hide();
	});
//	//鼠标离开的时候隐藏
//	$("#user_ul").on("blur",".btn_slide_down",function(){
//		
//		$("#user_ul div").hide();
//		return false;
//	});

};


function deleteUser(){
	 $("#user_ul").on("click",".btn_delete",function(){
		 //获取cookie值,如果没有则返回主页
		 //获取请求参数
		 var $li=$(this).parents("li");
		 var user_id=$li.data("user_id");
		 //格式检查
		 //发送ajaxq请求
		 $.ajax({
			 url:path+"/user/deleteuser.do",
			 type:"post",
			 data:{"user_id":user_id},
			 dataType:"json",
			 success:function(result){
				 if(result.status==0){
					 $li.remove();
				 }
				 alert(result.msg);
				 
				 
			 },
			 error:function(){
				 alert("user_id:"+user_id+"删除失败");
			 }
			 
		 });
		 
		 
	 });	
	
};