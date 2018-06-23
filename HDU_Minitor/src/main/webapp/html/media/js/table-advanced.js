/*
 * 下拉显示
 * 参数：表格
 * nTr:行
 */
function fnFormatDetails ( row, nTr )
   {
			var device_id=row.data().device.device_id;
			var room_id=row.data().roomID;
			var tds=nTr.cells;
			
			var aData=new Array();
			for(var i=0;i<tds.length;++i){
				aData[i]=tds[i].innerHTML;
			}
			
			 var sOut='<div style="width:30%;float:left;">';
            
			 
			sOut += '<table id="detailOfTable">';
            sOut += '<tr><td><span>房间编号:'+aData[1]+'</span></td></tr>';
            sOut += '<tr><td><span id="protocol">传输协议:'+'MQTT'+'</span></td></tr>';
            sOut += '<tr><td><span id="lastTemp">当前温度(C):'+aData[2]+'</span></td></tr>';
            sOut += '<tr><td><span id="lastTempT">温度阈值(C):'+aData[3]+'</span></td></tr>';
			sOut += '<tr><td><span id="lastCurrent">当前漏电流(mA):'+aData[4]+'</span></td></tr>';
			sOut += '<tr><td><span id="lastCurrentT">漏电流阈值(mA):'+aData[5]+'</span></td></tr>';
			sOut += '<tr><td><span id="lastTime">上报时间:'+aData[6]+'</span></td></tr>';
			sOut += '<tr><td><span id="lastStatus">状态:'+aData[7]+'</span></td></tr>';
			sOut += '<tr><td><span>device_id:'+device_id+'</span></td></tr>';
			
			sOut += '<tr><td><span style="display:none;">'+room_id+'</span><input type="button" class="fa" value="设置参数"/><input type="button" class="fa" id="deleteRoomRlashp" value="删除权限"/></td></tr>';
            sOut += '</table>';
            
            sOut+='</div>';
            
            
            sOut+='<div id="detailinfo" style="width:70%;height:300px;float:right;">';
            sOut+='</div>';
            /*
             * 设备信息
             */
            //当前温度线
            var device_tc=[];
            //设备温度阈值线
            var device_tt=[];
            //当前剩余电流线
            var device_ic=[];
            //当前剩余电流阈值线
            var device_it=[];
            //数据上传时间x轴
            var device_time=[];
       
            var device_arrtest=[];
            var chart=null;
            /*
            //发送ajax请求历史数据,首次加载数据
            $.ajax({
            	url:path+"/device/loadhistorydata.do",
            	type:"post",
            	data:{"device_id":device_id},
            	dataType:"json",
            	success:function(result){
            		
            		var data=result.data;
            		
            		for(j = 0; j < data.length; j++) {
            			   device_tc.push(parseFloat(data[j].device_tc));
            			   device_tt.push(parseFloat(data[j].device_tt));
            			   device_ic.push(parseFloat(data[j].device_ic));
            			   device_it.push(parseFloat(data[j].device_it));
            			   device_time.push(parseFloat(data[j].device_time));
            		} 
            		console.log(device_tt);
            		for(i=0;i<10;++i){
            			device_arrtest[i]=device_tt[i];
            		}
            */
            
            		$.getJSON(path+"/device/loadhistorydata.do?device_id="
            				+device_id,function(result){
            			var data=result.data;
                		for(j = 0; j < data.length; j++) {
                			//将字符串转化为时间
                		       device_time.push(data[j].device_time);
                			   device_tc.push(parseFloat(data[j].device_tc));
                			   device_tt.push(parseFloat(data[j].device_tt));
                			   device_ic.push(parseFloat(data[j].device_ic));
                			   device_it.push(parseFloat(data[j].device_it));
                			   
                		} 
                	 chart = Highcharts.chart('detailinfo', {
            			chart:{
            				zoomType:'x',
            			
            				events: {
                                //动态增加数据
                     				load:function(){
                     			
                     				chart =this;
                     				//activeLastPointToolip(chart);
                     				setInterval(function(){
                     					var x;
                     					$.ajax({
                     						url:path+"/device/loadlastdata.do",
                     						type:"post",
                     						data:{"device_id":device_id},
                     						dataType:"json",
                     						success:function(result){
                     							var data=result.data;
                     							//2018/6/21取消动态增加点
                     							var x=data.device_time;
                     							var tc=parseFloat(data.device_tc);
                     							var tt=parseFloat(data.device_tt);
                     							var ic=parseFloat(data.device_ic);
                     							var it=parseFloat(data.device_it);
                     							chart.series[0].addPoint([x,tc],false,false);
                     							chart.series[1].addPoint([x,tt],false,false);
                     							chart.series[2].addPoint([x,ic],false,false);
                     							chart.series[3].addPoint([x,it],false,false);
                     							//activeLastPointToolip(chart);
                     							chart.redraw();
                     							//更新详细信息中的值
                     							$("#lastTemp").html("当前温度(°C):"+data.device_tc);
                     							$("#lastTempT").html("温度阈值(°C):"+data.device_tt);
                     							$("#lastCurrent").html("当前漏电流(mA):"+data.device_ic);
                     							$("#lastCurrentT").html("漏电流阈值(mA):"+data.device_it);
                     							$("#lastTime").html("上报时间:"+data.device_time);
                     							if(data.device_status=='0'){
                     								$("#lastStatus").html("状态:正常");
                     								$("#lastStatus").css("color","");
                     							}else{
                     								$("#lastStatus").html("状态:异常");
                     								 $("#lastStatus").css("color","red");  
                     							}
                     							
                     							
                     						},
                     						error:function(){
                     							alert("loadlastdata error");
                     						}
                     						
                     					});
                     				},10000);
                     				
                     			},
                     		click: function (e) {
                                      $('.message').html( Highcharts.dateFormat('%Y-%m-%d', this.x) + ':<br/>  访问量：' +this.y );
                                  }
                             
                     		},
            			},
            			scrollbar:{
            	            enabled:true //是否产生滚动条
            				},
        				 yAxis: {
        				     gridLineWidth: 0
        				 },
            			boost: {
            		        useGPUTranslations: true
            		    },
                 		title: {
                 				text: '历史数据(2天)'
                 		},
                 		
                         
                 		tooltip:{
                 	        valueDecimals: 1,
                 	        shared: true,
                 	        crosshairs: true,
                 	  
                 	    },
                		credits: {
    						text: 'hdu.cn',
    						href: 'http://www.hdu.edu.cn/'
    				     },
                 		 yAxis: {  
                               title: {  
                                   text: '温度 (°C)/漏电流(mA)'  
                               },  
                               style:{
                            	   color:0x000
                               }
                    
                           }, 
                           xAxis:{
                        	   categories:device_time,
                               min:0, //别忘了这里
                      
                               labels:{
                            	   enabled:false
                               },
			                   title: {  
			                       text: '上传时间(s)'  
			                   },  
			                
			           	
                           },
                            
                       	plotOptions: {
                       		
            			    series: {
            			        marker: {
            			            enabled: true,
            			            symbol: 'circle',
            			            radius: 1
            			        }
            			    }
            			},
                 		series: [{
                 				name: '温度(°C)',
                 				data: device_tc,
                 				turboThreshold:0,
                 			
                 		}, {
                 				name: '温度阈值(°C)',
                 				turboThreshold:0,
                 				data: device_tt,
                 		}, {
                 				name: '漏电流(mA)',
                 				turboThreshold:0,
                 				data: device_ic
                 		}, {
                 				name: '漏电流阈值(mA)',
                 				turboThreshold:0,
                 				data: device_it
                 		}],
                 		
                 
                 });
            		
            	
            	});
   			
            		 
        
            
            return sOut;
  };
/**
 * 动态追加数据,刷新数据
 */
function activeLastPointToolip(chart){
	for(var i=0;i<chart.series.length;++i){
	 var points = chart.series[i].points;
	 chart.tooltip.refresh(points[points.length -1]);
	}
	
};
function theadAddTd(){
        /*
         * Insert a 'details' column to the table 增加加号按钮
         */
        var nCloneTh = document.createElement( 'th' );
        var nCloneTd = document.createElement( 'td' );
        nCloneTd.innerHTML = '<span class="row-details row-details-close"></span>';
         
        $('#sample_129 thead tr').each( function () {
           if($('#sample_129 thead tr').children('th').length==7){
        	this.insertBefore( nCloneTh, this.childNodes[0] );
           }
        } );

         
        $('#sample_129 tbody tr').each( function () {
            this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
        } );
         
        /*
         * Initialse DataTables, with no sorting on the 'details' column
         */
        

//        jQuery('#sample_1_wrapper .dataTables_filter input').addClass("m-wrap small"); // modify table search input
//        jQuery('#sample_1_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
//        jQuery('#sample_1_wrapper .dataTables_length select').select2(); // initialzie select2 dropdown
//         
        /* Add event listener for opening and closing details
         * Note that the indicator for showing which row is open is not controlled by DataTables,
         * rather it is done here
         */
//
};
 
function bandClick(){
 //点击加号显示 动态绑定
 $('#sample_129').on('click', 'tbody td .row-details', function () {
     var nTr = $(this).parents('tr')[0];
     
     var row=tables.row(nTr);
     
     if ( row.child.isShown() )
     {
         /* This row is already open - close it */
         $(this).addClass("row-details-close").removeClass("row-details-open");
         $("#sample_129").dataTable().fnClose( nTr );
         clearTimeout(t1);
         reload();//恢复更新
     }
     else
     {
    	 clearTimeout(t1);//禁止7s中请求一次
         /* Open this row */                
         $(this).addClass("row-details-open").removeClass("row-details-close");
       // row.child(fnFormatDetails(tables, nTr), 'details' ).show();
         $("#sample_129").dataTable().fnOpen( nTr, fnFormatDetails(row, nTr), 'details' )
     }
     return false;
 });
 return false;
};



      

   