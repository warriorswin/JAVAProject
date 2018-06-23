function manageRoom(){
	$("#manage_room_id").click(function(){
		$("#can").load("alert/alert_manage_room.html");
	});
	var select_room_buildName_yw;
	var input_roomNumber_yw;
	var input_roomName_yw;
	$("#can").on("click","#add_room_yw",function(){
		$("#can").load("alert/alert_manage_room_add.html");
		$.ajax({
			url:"/HDU_Minitor/manage/build/ALL",
			type:"get",
			success:function(result){
				if(result==0){
					alert(result.msg);
				}else{
					for(var i=0;i<result.data.length;i++){
						$("#select_room_buildName_yw").append("<option value='"+result.data[i].buildID+"'>"+result.data[i].buildName+"</option>");
						
					}
				}
			},
			error:function(){
				alert("系统繁忙，请稍后再试");
			}
		});
	});
	
	$("#can").on("click","#add_room_sure_yw",function(){
		select_room_buildName_yw = $("#select_room_buildName_yw").val();
		input_roomName_yw = $("#input_roomName_yw").val();
		input_roomNumber_yw = $("#input_roomNumber_yw").val();
		$.ajax({
			url:"/HDU_Minitor/manage/room",
			type:"post",
			dataType:"json",
			data:JSON.stringify({buildID:select_room_buildName_yw,roomName:input_roomName_yw,deviceID:input_roomNumber_yw}),
			contentType:"application/json",
			success:function(result){
				if(result.status==2){
					$("#can").load("alert/alert_manage_room_repeat.html",function(){
						$("#input_buildName_repeat_yw").val(result.data.build.buildName);
						$("#input_roomName_repeat_yw").val(result.data.roomName);
						$("#input_roomNumber_repeat_yw").val(result.data.deviceID);
						$("#modalBasicLabel_2").text(result.msg);
					});
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				alert("系统繁忙，请稍后再试");
			}
		});
	});
	$("#can").on("click","#cancel_room_repeat_yw2,#cancel_room_repeat_yw",function(){
		$("#can").load("alert/alert_manage_room_add.html",function(){
			$("#input_roomName_yw").val(input_roomName_yw);
			$("#input_roomNumber_yw").val(input_roomNumber_yw);
			$.ajax({
				url:"/HDU_Minitor/manage/build/ALL",
				type:"get",
				success:function(result){
					if(result==0){
						alert(result.msg);
					}else{
						for(var i=0;i<result.data.length;i++){
							if(result.data[i].buildID==select_room_buildName_yw){
								$("#select_room_buildName_yw").append("<option value='"+result.data[i].buildID+"' selected>"+result.data[i].buildName+"</option>");
							}else{
								$("#select_room_buildName_yw").append("<option value='"+result.data[i].buildID+"'>"+result.data[i].buildName+"</option>");
							}
							
						}
					}
				},
				error:function(){
					alert("系统繁忙，请稍后再试");
				}
			});
		});
	});
	$("#can").on("click","#cancel_room_yw,#cancel_room_yw2",function(){
		$("#can").load("alert/alert_manage_room.html");
	});
	
	$("#can").on("click","#update_room_yw,#cancel_update_room_yw2,#cancel_update_room_yw",function(){
		$("#can").load("alert/alert_manage_room_update.html");
		$.ajax({
			url:"/HDU_Minitor/manage/build/ALL",
			type:"get",
			success:function(result){
				if(result==0){
					alert(result.msg);
				}else{
					for(var i=0;i<result.data.length;i++){
						$("#select_room_buildName_yw_2").append("<option value='"+result.data[i].buildID+"'>"+result.data[i].buildName+"</option>");
//						$("#")
					}
				}
			},
			error:function(){
				alert("系统繁忙，请稍后再试");
			}
		});
	});
//	$("#can").on("click","#delete_room_yw",function(){
//		
//	});
	$("#can").on("change","#select_room_buildName_yw_2",function(){
		var buildName = $("#select_room_buildName_yw_2").val();
		$("#select_roomName_yw_2").find("option").not(":first").remove();
		$("#input_roomNumber_yw_2").val("");
		if(buildName!="请选择楼栋名字"){
			$.ajax({
				url:"/HDU_Minitor/manage/room/ALL/"+buildName,
				type:"get",
				success:function(result){
					if(result.status==0){
						alert(result.msg);
					}else{
						for(var i=0;i<result.data.length;i++){
							$("#select_roomName_yw_2").append("<option value='"+result.data[i].roomID+"'>"+result.data[i].roomName+"</option>");
						}
					}
				},
				error:function(){
					alert("系统繁忙，请稍后再试");
				}
			});
		}
	});
	$("#can").on("change","#select_roomName_yw_2",function(){
		var roomID = $("#select_roomName_yw_2").val();
		$("#input_roomNumber_yw_2").val("");
		if(roomID!="请选择楼栋名字"){
			$.ajax({
				url:"/HDU_Minitor/manage/room/"+roomID+"/null",
				type:"get",
				success:function(result){
					if(result.status==0){
						alert(result.msg);
					}else{
						$("#input_roomNumber_yw_2").val(result.data);
					}
				},
				error:function(){
					alert("系统繁忙，请稍后再试");
				}
			});
		}
	});
	
	$("#can").on("click","#update_room_sure_yw",function(){
		var buildName = $("#select_room_buildName_yw_2").find("option:selected").text();
		var roomName = $("#select_roomName_yw_2").find("option:selected").text();
		var deviceID = $("#input_roomNumber_yw_2").val().trim();
		var roomID = $("#select_roomName_yw_2").val();
		var buildID = $("#select_room_buildName_yw_2").val();
		if(buildName!="请选择楼栋名字"&&roomName!="请选择楼栋名字"){
			$("#can").load("alert/alert_manage_room_update_sure.html",function(){
				$("#input_update_buildName_room_yw").val(buildName);
				$("#input_update_roomName_room_yw").val(roomName);
				$("#input_update_roomNumber_room_yw").val(deviceID);
				$("#hidden_update_roomID_yw").val(roomID);
				$("#hidden_update_buildID_yw").val(buildID);
			});
		}
	});
	var buildName;
	var roomName;
	var deviceID;
	var roomID;
	var buildID;
	$("#can").on("click","#update_room_sure_sure_yw",function(){
		buildName = $("#input_update_buildName_room_yw").val();
		roomName = $("#input_update_roomName_room_yw").val();
		deviceID = $("#input_update_roomNumber_room_yw").val();
		roomID = $("#hidden_update_roomID_yw").val();
		buildID = $("#hidden_update_buildID_yw").val();
		$.ajax({
			url:"/HDU_Minitor/manage/room",
			type:"put",
			dataType:"json",
			contentType:"application/json",
			data:JSON.stringify({roomID:roomID,roomName:roomName,deviceID:deviceID,buildID:buildID}),
			success:function(result){
				if(result.status==2){
					$("#can").load("alert/alert_manage_room_repeat2.html",function(){
						$("#input_buildName_repeat_yw").val(result.data.build.buildName);
						$("#input_roomName_repeat_yw").val(result.data.roomName);
						$("#input_roomNumber_repeat_yw").val(result.data.deviceID);
						$("#modalBasicLabel_2").text(result.msg);
					});
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				alert("系统繁忙，请稍后再试");
			}
		});
	});
	$("#can").on("click","#cancel_room_repeat2_yw2,#cancel_room_repeat2_yw",function(){
		$("#can").load("alert/alert_manage_room_update_sure.html",function(){
			$("#input_update_buildName_room_yw").val(buildName);
			$("#input_update_roomName_room_yw").val(roomName);
			$("#input_update_roomNumber_room_yw").val(deviceID);
			$("#hidden_update_roomID_yw").val(roomID);
			$("#hidden_update_buildID_yw").val(buildID);
		});
	});
	$("#can").on("click","#delete_room_yw",function(){
		$("#can").load("alert/alert_manage_room_delete.html");
		$.ajax({
			url:"/HDU_Minitor/manage/build/ALL",
			type:"get",
			success:function(result){
				if(result==0){
					alert(result.msg);
				}else{
					for(var i=0;i<result.data.length;i++){
						$("#select_room_buildName_yw_2").append("<option value='"+result.data[i].buildID+"'>"+result.data[i].buildName+"</option>");
//						$("#")
					}
				}
			},
			error:function(){
				alert("系统繁忙，请稍后再试");
			}
		});
	});
	$("#can").on("click","#delete_room_sure_yw",function(){
		if($("#checkbox_sure_room_yw").is(':checked')){
			var roomID = $("#select_roomName_yw_2").val();
			if(roomID!="请选择房间名字"){
				$.ajax({
					url:"/HDU_Minitor/manage/room/"+roomID,
					type:"delete",
					success:function(result){
						alert(result.msg);
					},
					error:function(){
						alert("系统繁忙，请稍后再试");
					}
				
				});
				
			}
		}
	});
	
}
function deleteBuild_yw(){
	$("#can").on("click","#delete_build_yw",function(){
		$("#can").load("alert/alert_manage_build_delete.html");
		$.ajax({
			url:"/HDU_Minitor/manage/build/ALL",
			type:"get",
			success:function(result){
				if(result==0){
					alert(result.msg);
				}else{
					for(var i=0;i<result.data.length;i++){
						$("#select_buildName_yw_2").append("<option value='"+result.data[i].buildID+"'>"+result.data[i].buildName+"</option>");
					}
				}
			},
			error:function(){
				alert("系统繁忙，请稍后再试");
			}
		});
	});
	$("#can").on("click","#delete_build_sure_yw",function(){
		if($("#checkbox_sure_yw").is(':checked')){
			$.ajax({
				url:"/HDU_Minitor/manage/build/"+$("#select_buildName_yw_2").val(),
				type:"delete",
				success:function(result){
					alert(result.msg);
				},
				error:function(){
					alert("系统繁忙，请稍后再试");
				}
			});
		}
		
	});
}
function updateBuild_yw(){
	$("#can").on("click","#update_build_sure_sure_yw",function(){
		var buildID = $("#build_uuid_yw").val();
		var buildNumber = $("#input_update_buildNumber_yw").val();
		var buildName = $("#input_update_buildName_yw").val();
		$.ajax({
			url:"/HDU_Minitor/manage/build",
			type:"put",
			dataType:"json",
			data:JSON.stringify({buildID:buildID,buildNumber:buildNumber,buildName:buildName}),
			contentType:"application/json",
			success:function(result){
				alert(result.msg);
			},
			error:function(){
				alert("系统繁忙，请稍后再试");
			}
		});
	});
};
function manageUser(){
	$("#manage_build_id").click(function(){
		
		$("#can").load("alert/alert_manage_build.html");
		$("#can").off("click","#add_build_yw");
		$("#can").on("click","#add_build_yw",function(){
			$("#can").load("alert/alert_manage_build_add.html");
			$("#can").off("click","#add_build_sure_yw");
			$("#can").on("click","#add_build_sure_yw",function(){
				var buildNumber = $("#input_buildNumber_yw").val().trim();
				var buildName = $("#input_buildName_yw").val().trim();
				$.ajax({
					url:"/HDU_Minitor/manage/build",
					type:"post",
					dataType:"json",
					data:JSON.stringify({buildNumber:buildNumber,buildName:buildName}),
					contentType:"application/json",
					success:function(result){
						if(result.status==0){
							alert(result.msg);
						}else{
							alert(result.msg);
						}
					},
					error:function(){
						alert("系统繁忙，请稍后再试");
					}
				});
			})
			
			
		});
		$("#can").off("click","#update_build_yw,#cancel_build_update_yw,#cancel_build_update_yw2");
		$("#can").on("click","#update_build_yw,#cancel_build_update_yw,#cancel_build_update_yw2",function(){
			$("#can").load("alert/alert_manage_build_update.html");
			$.ajax({
				url:"/HDU_Minitor/manage/build/ALL",
				type:"get",
				success:function(result){
					if(result==0){
						alert(result.msg);
					}else{
						for(var i=0;i<result.data.length;i++){
							$("#select_buildName_yw").append("<option value='"+result.data[i].buildNumber+"'>"+result.data[i].buildName+"</option>");
						}
					}
				},
				error:function(){
					alert("系统繁忙，请稍后再试");
				}
			});
			$("#can").off("click","#update_build_sure_yw")
			$("#can").on("click","#update_build_sure_yw",function(){
				
				$.ajax({
					url:"/HDU_Minitor/manage/build/"+$("#select_buildName_yw").val(),
					type:"get",
					success:function(result){
						if(result.status==0){
							alert(result.msg);
						}else{
							$("#can").load("alert/alert_manage_build_update_sure.html",function(){
								$("#input_update_buildNumber_yw").val(result.data.buildNumber);
								$("#input_update_buildName_yw").val(result.data.buildName);
								$("#build_uuid_yw").val(result.data.buildID);
							});
							
						}
					},
					error:function(){
						alert("系统繁忙，请稍后再试");
					}
				})
			});
		});
		
	});
	$("#can").off("click","#cancel_build_yw,#cancel_build_yw2");
	$("#can").on("click","#cancel_build_yw,#cancel_build_yw2",function(){
		$("#can").load("alert/alert_manage_build.html");
	});
	updateBuild_yw();
	deleteBuild_yw();
	manageRoom();
};


