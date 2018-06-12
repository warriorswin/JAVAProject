 function alertAddBookWindow(){ 
	 					//弹出新建笔记本对话框
    					 $("#can").load("alert/alert_notebook.html");
    					 //显示背景
    		    		 $(".opacity_bg").show();
    				 };
 //关闭对话框
 function closeAlertWindow(){
	 //清空div 内容
	 $("#can").html("");
	 //隐藏背景色
	 $(".opacity_bg").hide();
	 
 };
 //增加笔记
 function addBook(){
	 //获取创建笔记本名称
	 var bookName=$("#input_notebook").val().trim();
	 		alert(bookName);
	 	if(bookName.length==0){
	 		alert("笔记本名称不能为空");
	 	}else{
				   var userId=getCookie("userId");
				 //发送ajax请求
				 $.ajax({
					 url:path+"/book/add.do",
					 type:"post",
					 data:{"bookName":bookName,"userId":userId},
					 dataType:"json",
					 success:function(result){
						if(result.status==0){
							//在book列表中增加该名称
							var bookId=result.data;
							createBookLi(bookId,bookName);
							alert("创建成功");
							
					 	}else{
					 		alert("创建失败")
					 	}
					 },
					 error:function(){
						 alert("error:"+userId+" "+bookName);
					 }
				 });
				 closeAlertWindow();
				
	 	  }
	 	 
 };
 
//点击增加笔记页面
 function alertAddNoteWindow(){
	//判断是否有笔记本被选中
	 var $li=$("#book_ul a.checked").parent();
	 if($li.length==0){
		 alert("请选择笔记本");
	 }else{//弹出笔记对话框
		 $("#can").load("alert/alert_note.html");
		 $(".opacity_bg").show();
		 
	 }
 	
 };
 //增加笔记
 function addNote(){
		
		//获取请求参数
		//获取笔记标题
		 var title=$("#input_note").val().trim();
		 var userId=getCookie("userId");
		 var $li=$("#book_ul a.checked").parent();
		 var bookId=$li.data("bookId");
		 alert("bookId:"+bookId);
		 
		 //数据格式检查
		 var ok=true;
		 if(title==""){
			 ok=false;
			 $("#title_span").html("标题不能为空");
		 }
		 if(userId==null){
			 ok=false;
			 window.location.href="log_in.html";
		 }
		 //发送ajax请求
		 if(ok){
		 $.ajax({
			 		url:path+"/note/add.do",
			 		type:"post",
			 		data:{"userId":userId,
			 			  "bookId":bookId,
			 			  "noteTitle":title},
			 		dataType:"json",
			 		success:function(result){
			 			if(result.status==0){
			 			createNoteLi(result.data,title)
			 				alert(result.msg);
			 			}
			 		},
			 		error:function(){
			 			alert("创建笔记失败");
			 		}
		       });
		 }
};