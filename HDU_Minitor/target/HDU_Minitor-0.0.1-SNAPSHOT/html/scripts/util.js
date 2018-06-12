//验证是否为手机号
function isPhone(phone_num,selector){
	var pattern=/^1[34578]\d{9}$/;
		if(!pattern.test(phone_num)){
			$(selector).val("");
			$(selector).attr("placeholder","输入的手机号格式不正确");
			return false;
		}else{
			return true;
		}
}
//验证是否为空
