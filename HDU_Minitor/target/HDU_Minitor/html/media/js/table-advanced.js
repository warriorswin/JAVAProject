 

      
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
			
			 var sOut='<div style="width:43%;float:left;border-right:1px solid #000">';
            
			 
			sOut += '<table>';
            sOut += '<tr><td><span>房间编号:'+aData[1]+'</span></td></tr>';
            sOut += '<tr><td><span>当前温度(C):'+aData[2]+'</span></td></tr>';
            sOut += '<tr><td><span>温度阈值(C):'+aData[3]+'</span></td></tr>';
			sOut += '<tr><td><span>当前漏电流(mA):'+aData[4]+'</span></td></tr>';
			sOut += '<tr><td><span>漏电流阈值(mA):'+aData[5]+'</span></td></tr>';
			sOut += '<tr><td><span>上报时间(s):'+aData[6]+'</span></td></tr>';
			sOut += '<tr><td><span>状态:'+aData[7]+'</span></td></tr>';
			sOut += '<tr><td><span>device_id:'+device_id+'</span></td></tr>';
			sOut += '<tr><td><span>room_id:'+room_id+'</span></td></tr>';
			sOut += '<tr><td><input type="button" class="fa" value="保存"/></td></tr>';
            sOut += '</table>';
            sOut+='</div>';
            
            
            sOut+='<div style="width:57%;float:left;">';
            sOut+='<span>历史数据</span>'
            sOut+='</div>';
            
            return sOut;
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
}
      

   