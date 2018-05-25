$(function(){
	
	$('#switch_qlogin').click(function(){
		
		$('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_bottom').animate({left:'0px',width:'70px'});
		$('#qlogin').css('display','none');
		$('#web_qr_login').css('display','block');	
		});
	$('#switch_login').click(function(){
		
		$('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_bottom').animate({left:'154px',width:'70px'});
		
		$('#qlogin').css('display','block');
		$('#web_qr_login').css('display','none');
		
		});
	$('#switch_qlogin').trigger('click');


	});
 
/*
 * 账号格式验证
 */
function userPhoneCheck(){
	$(this).removeClass("invalid");
	var userPhone=$(this).val().trim();
	if(userPhone==""){
		$(this).attr("placeholder","账号不能为空");
	}else{
		var pattern=/^1[34578]\d{9}$/;
		if(!pattern.test(userPhone)){
			$(this).val("");
			$(this).attr("placeholder","输入的手机号格式不正确");
		}
	}
}
/*
 * 账号聚焦显示
 */
function UserphoneFocusDisplay(){
		$(this).removeClass("invalid");
		var userPhone=$(this).val().trim();
		if(userPhone==""){
			$(this).attr("placeholder","手机号");
		}
	}
/*
 * 登录显示样式
 */
function sytleForm(){
	//检查账号格式
	$("#userPhone").blur(userPhoneCheck);
	$("#user").blur(userPhoneCheck);
	
	$("#password").blur(function(){
		$(this).removeClass("invalid");
		var password=$(this).val().trim();
		if(password==""){
			$(this).attr("placeholder","密码不能为空");
		}else{
			if(password.length<6){
				$(this).val("");
				$(this).attr("placeholder","密码不能小于6位");
			}
		}
	});
	$("#userPhone").focus(UserphoneFocusDisplay);
	$("#password").focus(function(){
		$(this).removeClass("invalid");
		var password=$(this).val().trim();
		if(password==""){
			$(this).attr("placeholder","大于6位的字母或者数字");
		}
	});
	$("#passwd").focus(function(){
		
		$("#passwd").removeClass("invalid");
		$("#passwd").attr("placeholder","收到的随机密码");
		
	});
	
	
};
/*
 * 发送ajax请求
 */
function sendAjax(userPhone,password){
	$.ajax({
		url:path+"/user/login.do",
		type:"post",
		data:{"userPhone":userPhone,
			   "password":password
			   },
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//保存user_user_id为cookie
				var user_id=result.data;
				alert(user_id);
				addCookie("user_id",user_id,1);
				window.location.href="html/admin.html";
			}else{
				if(result.status==1){
					//没有该用户
					$("#userPhone").val("");
					$("#userPhone").addClass("invalid");
					$("#userPhone").attr("placeholder","没有该用户");
					
				}else{
					//密码错误
					$("#password").val("");
					$("#password").addClass("invalid");
					$("#password").attr("placeholder","密码错误");
				}
			}
		},
		error:function(){
			alert("error:请检查网络");
		}
		
	});
};
/*
 * 登录click函数1
 */
function userLogin1(){
	//获取输入参数
	var userPhone=$("#userPhone").val().trim();
	var password=$("#password").val().trim();
	//检查格式
	
	var ok=true;
	if(userPhone==""){
		$("#userPhone").attr("placeholder",'账号不能为空');
		ok=false;
	}
	if(password==""){
		$("#password").attr("placeholder","密码不能为空");
		ok=false;
	}
	//发送ajax请求
	if(ok){
		sendAjax(userPhone,password);
	}
	return false;
	
};

/*
 * 随机密码登录界面处理
 */
function userGetPass(){
	//获取输入参数
	var userPhone=$("#user").val().trim();
	var ok=true;
	if(userPhone==""){
		ok=false;
		$("#user").attr("placeholder","账号不能为空");
		
	}
	if(ok){
		$.ajax({
			url:path+"/user/getrdmpwd.do",
			type:"post",
			data:{"userPhone":userPhone},
			dataType:"json",
			success:function(result){
				//如果随机密码发送成功
				if(result.status!=1){
					$("#passwd").removeClass("invalid");
					
					//将获取随机密码的按钮id值和value
					if(result.status==0){
						$("#reg").css('display','none');
						$("#reg_login").css('display','block');
						$("#reg_login").click(function(){
							alert("reg_login successful");
						});
						
					}
					//显示倒计时
					showtime($("#passwd"),result.msg,result.status);
				}else{
					//账号不存在
					if(result.status==1){
						$("#user").val("");
						$("#user").addClass("invalid");
						$("#user").attr("placeholder","用户名不存在");
					}
				}
				
			},
			error:function(){
				$("#passwd").addClass("invalid");
				$("#passwd").attr("placeholder","获取随机密码错误,请重试！");
			
				
			}
			
		});
	}
}
/*
 * passwd 随机密码显示倒计时
 */
var countdown=60;
function showtime(obj,msg,status){
	if(countdown==0){
		obj.attr("placeholder","输入随机密码");
		countdown=60;
		//1min分钟后重启获取随机密码的按钮
		$("#reg").attr('disabled',false);
	
		$("#reg").css('display','block');
		//1min，无论成不成功，都隐藏登录按钮，禁用点击
		$("reg_login").attr('disabled',true);
		$("#reg_login").css('display','none');
		$("#reg").css('background','#2795dc');
		$("#reg").css('border-bottom','3px solid #0078b3');
		return;
	}else{
		
		$("#reg").attr('disabled',true);
		$("#reg").css('background','silver')//变成灰色
		$("#reg").css('border-bottom','3px solid silver');
		//若成功则显示登录按钮，隐藏重新获取随机密码按钮
		if(status==0){
			$("#reg").css('display','none');
			$("reg_login").attr('disabled',false);
			$("#reg_login").css('display','block');
			$("#reg_login").css('background','teal')
		}
		obj.attr("placeholder",msg+","+countdown+"s后重新发送");
		countdown--;
		
	}
	setTimeout(function(){
		showtime(obj,msg)},
		1000);
};
/*
 * 随机密码登录
 */
/*
 * 登录页面处理
 */
$(function(){
	 sytleForm();
	$("#login").click(userLogin1);
	$("#reg").click(userGetPass);
	
});




