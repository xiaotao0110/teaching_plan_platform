$(function(){
	
	function setParams(){
		var params = {};
		
		if($("#college").combobox('getValue')==""){
			params.collegeId=0;
		}else{
			params.collegeId=parseInt($("#college").combobox('getValue'));
		}

		if($("#building").combobox('getValue')==""){
			params.building=0;
		}else{
			params.building=parseInt($("#building").combobox('getValue'));
		}

		if($("#status").combobox('getValue')==""){
			params.mark=0;
		}else{
			params.mark=parseInt($("#status").combobox('getValue'))
		}

		return params;
	};
		
	var bodyHeight = $(window).height();
	var bodyWidth = $(window).width();
	$("#classroomDataGrid").datagrid({
		url:$('#classroomForm').attr('action'),
        title: "教室信息列表",
        width: bodyWidth - 20,  
        height: bodyHeight-180,       
        rownumbers: true,
        pagination: true,
        queryParams:setParams(),
        columns:[[    
           {field:"classroomId",hidden:"true"},
           {field:"thisId",hidden:"true"},
           {field:"collegeName",title:"所属院系",width:bodyWidth * 0.1},
           {field:"building",title:"楼栋",width:bodyWidth * 0.1},
           {field:"code",title:"教室编号",width:bodyWidth * 0.1}, 
           {field:"seats",title:"座位数",width:bodyWidth * 0.1}, 
           {field:"number",title:"考场人数",width:bodyWidth * 0.1},   
           {field:"oper",title:"操作",width:bodyWidth * 0.05,
        	   formatter: function(value,row,index){
        		   if(row.thisId == 0 || row.thisId == null || row.thisId == ''){
				 		return "<a>详情</a>"
				 	}else{
        			   return '<a style="color:blue" href="classroom/classroomDetails?collegeId='
   	       	        +row.collegeId+'&building='+row.building+''	
   	       	        +'&classroomId='+row.classroomId+''
   	       	        +'&mark='+row.mark+'"+">'
   	       	        +"详情"+'</a>';
        		   }	       	       
        	   } 
           }, 
        ]]
    });	
    
	
	$('#classroomSumbit').click(function(){		
		$("#classroomDataGrid").datagrid('load', setParams());//加载数据，不加表格数据不会显示
	});
	
	// 院系信息下拉框
	$('#college').combobox({
		url : "college/collegeSelect",
		valueField : 'id',
		textField : 'text',
		width : 165,
		onChange : function() {
			classroom();
		}
	});

	function classroom(){
		
		var collegeId = $('#college').combobox('getValue');
		if (collegeId == '') {
			collegeId = 0;
		}
		
		 //楼栋信息下拉框
		 $('#building').combobox({
			url: "classroom/BuildingSelect?collegeId="+collegeId,                        
	        valueField: 'id',
	        textField: 'text',
	        width:165,       
		 }); 
		
	}
	
	//初始化 
	classroom();
	
	// 考试状态下拉框
	$('#status').combobox({
		data : [ 
		         {"id" : 0,"text" : "全部"},
		         {"id" : 1,"text" : "是"}, 
		         {"id" : 2,"text" : "否"}	        
		        ],
		valueField : 'id',
		textField : 'text',
		width : 165
	});
});