$(function(){
	
	function setParams(){
		var params = {};
		if($("#profession").combobox('getValue')==""){
			params.professionId=0;
		}else{
			params.professionId=parseInt($("#profession").combobox('getValue'));
		}
		
		if($("#classs").combobox('getValue')==""){
			params.classsId=0;
		}else{
			params.classsId=parseInt($("#classs").combobox('getValue'))
		}
		
		params.studentName=$("#studentName").val();
		params.SID=$("#studentSID").val();
		
		return params;
	};
	
	var bodyHeight = $(window).height();
	var bodyWidth = $(window).width();
	$("#studentDataGrid").datagrid({
		url:$('#studentForm').attr('action'),
        title: "学生信息列表",
        width: bodyWidth - 20,  
        height: bodyHeight-180,       
        rownumbers: true,
        pagination: true,
        queryParams:setParams(),
        columns:[[    
           {field:"professionId",hidden:"true"},     
           {field:"classsId",hidden:"true"},
           {field:"studentId",hidden:"true"},
           {field:"professionName",title:"所属专业",width:bodyWidth * 0.1},
           {field:"classsCode",title:"班级编号",width:bodyWidth * 0.1},    
           {field:"sid",title:"学号",width:bodyWidth * 0.1},   
           {field:"studentName",title:"名称",width:bodyWidth * 0.1},    
           {field:"oper",title:"操作",width:bodyWidth * 0.05 ,
		       formatter: function(value,row,index){
			        return '<a style="color:blue" href="student/studentDetails?professionId='
			        +row.professionId+'&classsId='+row.classsId+''
			        +'&studentId='+row.studentId+'"+">'
			        +"详情"+'</a>';
		       }
           } 

        ]]
    })	
    
    $('#studentSumbit').click(function(){		
		$("#studentDataGrid").datagrid('load', setParams());//加载数据，不加表格数据不会显示
	});
    
    //二级联动
	 //专业信息下拉框
	 $('#profession').combobox({
		url: "profession/professionSelect",                        
	    valueField: 'id',
	    textField: 'text',
	    width:150,
	    onLoadSuccess: function () { //加载完成后,设置选中第一项
           var val = $(this).combobox('getData');
           for (var item in val[0]) {
               if (item == 'id') {
                   $(this).combobox('select', val[0][item]);
               }
           }
           loadClasss();
	    },
	    onChange:function(){
	    	loadClasss();
	    }
    });       
	
	 function loadClasss(){
		 var pid = $('#profession').combobox('getValue');
		 if(pid == '' || pid == null){
			 pid = 0;
		 }
		 
		 //教学班号下拉框
		 $('#classs').combobox({
			url: "classs/classsSelect2?professionId="+pid,                        
	        valueField: 'id',
	        textField: 'text',
	        width:150,
	        onLoadSuccess: function () { //加载完成后,设置选中第一项
	            var val = $(this).combobox('getData');
	            for (var item in val[0]) {
	                if (item == 'id') {
	                    $(this).combobox('select', val[0][item]);
	                }
	            }
		    }
	    }); 
		 
	 };
	 
	 //初始化
	 loadClasss();
	 
});