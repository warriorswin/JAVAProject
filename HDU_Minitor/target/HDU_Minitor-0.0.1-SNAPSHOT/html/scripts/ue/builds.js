function loadBuild(){
	//给动态加载的user_ul下的li元素动态绑定click事件
	$("#user_ul").on("click","li",function(){
		alert("cliked li successful");
	});
};