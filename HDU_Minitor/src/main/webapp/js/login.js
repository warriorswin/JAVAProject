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
 * 登录显示样式
 */
function sytleForm(){
	$("#userPhone").blur(function(){
		var userPhone=$(this).val().trim();
		if(userPhone==""){
			$(this).attr("placeholder","账号不能为空");
		}
	});
	$("#password").blur(function(){
		var password=$(this).val().trim();
		if(password==""){
			$(this).attr("placeholder","密码不能为空");
		}
	});
	$("#userPhone").focus(function(){
		var userPhone=$(this).val().trim();
		if(userPhone==""){
			$(this).attr("placeholder","手机号");
		}
	});
	$("#password").focus(function(){
		var password=$(this).val().trim();
		if(password==""){
			$(this).attr("placeholder","大于6位的字母或者数字");
		}
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
			   "password":password},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.msg);
				window.location.href="eidt.html";
			}else{
				alert(result.msg);
			}
		},
		error:function(){
			alert("error");
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
 * 登录页面处理
 */
$(function(){
	 sytleForm();
	$("#login").click(userLogin1);
	
});




