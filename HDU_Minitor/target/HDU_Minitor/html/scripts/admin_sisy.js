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
	 		 		 		createUserLi(users[i].user_name,user[i].user_id);
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
	sli+='<button type="button"  class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
	sli+='</a>';
	sli+='<div class="note_menu"  tabindex="-1">';
	sli+='<dl>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
	sli+='<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
	sli+=' </dl>';
	sli+=' </div>';
	sli+='</li>';
	$li=$(sli);//将字符串装换为元素
	$li.data("user_id",user_id);//给data属性赋值
	$("#user_ul").append($li);
	
};