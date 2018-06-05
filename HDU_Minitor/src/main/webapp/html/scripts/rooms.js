var tables=$('#sample_129').DataTable();
var deviceArray=new Array();
var flag=true;
var t1;
var hadTable=true;
//
//"oLanguage": {  
//	"sLengthMenu": "每页显示 _MENU_ 条记录",  
//	"sZeroRecords": "抱歉， 没有找到",  
//	"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",  
//	"sInfoEmpty": "没有数据",  
//	"sInfoFiltered": "(从 _MAX_ 条数据中检索)",  
//	"oPaginate": {  
//	"sFirst": "首页",  
//	"sPrevious": "前一页",  
//	"sNext": "后一页",  
//	"sLast": "尾页"  
//	},  
//	"sZeroRecords": "没有检索到数据",  
//
//	}
function createTable(build_id,user_id){
	
	//变量
	tables=$('#sample_129').DataTable( {
         "aoColumnDefs": [
             {"bSortable": false, "aTargets": [ 0 ] }
         ],
       
          "aaSorting": [[1, 'asc']],
          "aLengthMenu": [
             [5, 10, 15, 20, -1],
             [5, 10, 15, 20, "All"] // change per page values here
         ],
         // set the initial value 默认显示条数
         "iDisplayLength": 5, //每页显示多少条
         "bRetrieve": true,
         "bDestory":true,
         "bAutoWidth": false,
         "bServiceSide":false,
         "columnDefs": [{
        	 	  "data":"hasPo",
        	      "targets": [0],
        	      "searchable": false,
        	      //"defaultContent": '<span class="row-details row-details-close"></span>',
        	      
         }],
         "ajax":{
        	 "url":path+"/room/loadroom.do?ran="+Math.random(),
        	 "type":"post",
        	 "data":{"build_id":build_id,
        		 	  "user_id":user_id},
         },
    		
    		
         
          "columns": [  
          	{
          		"sTitle": "",
          	    "defaultContent": '<span class="row-details row-details-close"></span>',	
 	  
          	},
          
              {  
                 
          		  "data":"roomName",
                  "sClass":"center"
              },  
              {  
                  
             	 "data":"device.device_tc",
                  "sClass": "center", 
                "defaultContent":"null"
              },  
              {  
                 
             	 "data":"device.device_tt",
             	 "sClass": "center",
             	"defaultContent":"null",
             	
              },  
              {  
                  //"sTitle": "device_ic",
             	 "data":"device.device_ic",
                  "sClass": "center",
                  "defaultContent":"null",
              },    
              {  
                 
             	 "data":"device.device_it",
                  "sClass": "center",
                  "defaultContent":"null",
              },  
              {  
                  
             	 "data":"device.device_time",
                  "sClass": "center",
                  "defaultContent":"null",
                  
              },  
              {  
                   
             	 "data":"device.device_status",
                  "bSortable": false ,
                  "sClass": "center",
                  "defaultContent":"null",
              },
              {  
            	   "sTitle": "device_id",
              	   "data":"device.device_id",
                   "bSortable": false ,
                   "defaultContent":"null",
                   "visible": false
               },{
            	   "data":"roomID",
            	   "aSortable":false,
            	   "visible":false,
               }
              ]
            
             
        });//end table
	
	bandClick();
    reload();
    hadTable=true;

	
};
function reload(){
	  t1=setTimeout(function(){
		  	//tables.ajax.url(path+"/room/loadroom.do?ran="+Math.random()+"&build_id:"+build_id).load(); 
		 	 tables.ajax.reload();
		  	reload();
		 	 },
		 	 7000);
		
};
function loadRoom(){
	//动态绑定点击事件
	$("#build_ul").on("click","li",function(){
		
		//选中样式
		$("#build_ul a").removeClass("checked");
		$(this).find("a").addClass("checked");
		//获取buid_id,以及user_id/一栋楼可能有不止一个user
		var build_id=$(this).data("build_id");
		var user_id=$(this).data("user_id");
		//解绑
		$('#sample_129').off("click",'tbody td .row-details');
		tables.clear();
		
		if(hadTable){
			tables.destroy();
			hadTable=false;
		}
		clearTimeout(t1);
		createTable(build_id,user_id);
	
		
	});
};

function createRoomLi(room){
	var device=room.device;
	
		var sli="";
		sli+='<tr><td><span class="row-details row-details-close"></span></td>';
		sli+="<td>";
		sli+=room.roomName;
		sli+="</td><td>";
		sli+=device.device_tc;
		sli+="</td><td>";
		sli+=device.device_tt;
		sli+="</td><td>";
		sli+=device.device_ic;
		sli+="</td><td>";
		sli+=device.device_it;
		sli+="</td><td>";
		sli+=device.device_time;
		sli+="</td><td>"
		sli+="正常";
		sli+="</td></tr>"
	
		$li=$(sli);
		$li.data("deivce_id",device.device_id);
		$li.data("room_id",room.room_id);
		$("#sample_129 tbody").append($li);
};

function addRoom(){
	$("#add_room").click(function(){
		//先判断选中楼栋，获取build_id
		$build_li=$("#build_ul a.checked").parent();
		var build_id=$build_li.data("build_id");
		var user_id=$build_li.data("user_id");
		if(user_id==undefined){
			alert("请先选择用户和楼栋");
		}else if(build_id==undefined){
			alert("请选择楼栋");
		
		}else{
		
			$("#can").load("alert/alert_addroom.html");//解除绑定
			$('#can').off("click",'#addroom_sure');
			$("#can").on("click","#addroom_sure",function(){
				//获取请求参数
				var room_name=$("#input_roomname").val().trim();
				var device_id=$("#input_deviceid").val().trim();
				//检查格式
				if(room_name==''){
					$("#input_roomname").attr("placeholder","房间名不能为空");
				}
				if(device_id==''){
					$("#input_deviceid").attr("placeholder","设备ID不能为空");
				}
				//发送ajax请求
				$.ajax({
					url:path+"/room/addroom.do",
					type:"post",
					data:{ "user_id":user_id,
						   "build_id":build_id,
						   "room_name":room_name,
						   "device_id":device_id
						   },
				    dataType:"json",
				    success:function(result){
				    	alert(result.msg);
				    },
				    error:function(){
				    	alert("error:请求出现错误");
				    }
					
					
				});
			});
		}
	});
	
}
