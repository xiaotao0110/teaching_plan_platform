$(function(){
	
	function setParams(){
		var params = {};
		if($("#profession").combobox('getValue')==""){
			params.professionId=0;
		}else{
			params.professionId=parseInt($("#profession").combobox('getValue'));
		}
		
		return params;
	};
	
	var bodyHeight = $(window).height();
	var bodyWidth = $(window).width();
	$("#classsDataGrid").datagrid({
		url:$('#classsForm').attr('action'),
        title: "班级信息列表",
        width: bodyWidth - 20,  
        height: bodyHeight-180,       
        rownumbers: true,
        pagination: true,
        queryParams:setParams(),
        columns:[[  
           {field:"professionId",hidden:"true"},   
           {field:"classsId",hidden:"true"},  
           {field:"collegeName",title:"所属院系",width:bodyWidth * 0.1},
           {field:"professionName",title:"所属专业",width:bodyWidth * 0.1},    
           {field:"classsCode",title:"班级编号",width:bodyWidth * 0.1},   
           {field:"numbers",title:"班级人数",width:bodyWidth * 0.1},    
           {field:"teacherName",title:"辅导员",width:bodyWidth * 0.1},   
           {field:"oper",title:"操作",width:bodyWidth * 0.05,
        	   formatter: function(value,row,index){
        		   return '<a style="color:blue" href="classs/classsDetails?professionId='
       	        	+row.professionId+'&classsId='+row.classsId+'"+">'+"详情"+'</a>';
        	   } 
           }, 
        ]]
    })
    

	$('#classsSumbit').click(function(){		
		$("#classsDataGrid").datagrid('load', setParams());//加载数据，不加表格数据不会显示
	});
	
     //专业信息下拉框
	 $('#profession').combobox({
		url: "profession/professionSelect",                        
	    valueField: 'id',
	    textField: 'text',
	    width:165
     });       
	
})