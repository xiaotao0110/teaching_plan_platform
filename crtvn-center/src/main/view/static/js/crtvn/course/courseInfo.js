$(function(){
	
	function setParams(){
		var params = {};

		if($("#profession").combobox('getValue')==""){
			params.professionId=0;
		}else{
			params.professionId=parseInt($("#profession").combobox('getValue'));
		}
		
		if($("#courseCode").combobox('getValue')==""){
			params.courseId=0;
		}else{
			params.courseId=parseInt($("#courseCode").combobox('getValue'))
		}
		
		if($("#courseManner").combobox('getValue')==""){
			params.courseManner=0;
		}else{
			params.courseManner=parseInt($("#courseManner").combobox('getValue'))
		}
		params.courseName=$("#courseName").val();
		return params;
	};

	var bodyHeight = $(window).height();
	var bodyWidth = $(window).width();
	$("#courseDataGrid").datagrid({
		url:$('#courseForm').attr('action'),
        title: "课程信息列表",
        width: bodyWidth - 20,  
        height: bodyHeight-180,       
        rownumbers: true,
        pagination: true,
        queryParams:setParams(),
        columns:[[  
           {field:"courseId",hidden:"true"},     
           {field:"professionId",hidden:"true"},    
           {field:"professionName",title:"所属专业",width:bodyWidth * 0.1},
           {field:"courseCode",title:"课程代码",width:bodyWidth * 0.1},    
           {field:"courseName",title:"课程名称",width:bodyWidth * 0.1},   
           {field:"courseNature",title:"性质",width:bodyWidth * 0.06},   
           {field:"courseCredit",title:"学分",width:bodyWidth * 0.05},
           {field:"courseManner",title:"考试形式",width:bodyWidth * 0.05,
        	   formatter: function(value,row,index){
        		   if(value==2){ return '开卷'; }
        		   return '闭卷';
        	   } 
           },  
           {field:"oper",title:"操作",width:bodyWidth * 0.05,
        	   formatter: function(value,row,index){
        	        return '<a style="color:blue" href="course/courseDetails?courseId='
        	        +row.courseId+'&professionId='+row.professionId+'"+">'+"详情"+'</a>';
        	   } 
           }, 
        ]]
    });
    

	$('#courseSumbit').click(function(){		
		$("#courseDataGrid").datagrid('load', setParams());//加载数据，不加表格数据不会显示
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
            loadCourse();
	    },
	    onChange:function(){
	    	loadCourse();
	    }
     });       
	
	 function loadCourse(){
		  //课程代号下拉框
	   	 $('#courseCode').combobox({
   		 	url: "course/courseSelect?professionId=" + $('#profession').combobox('getValue'),                        
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
    	    },
	     });  
		 
	 };

	 //考试形式下拉框
	 $('#courseManner').combobox({
		 data: [
		        {"id":0,"text":"全部"},
		        {"id":1,"text":"闭卷"},
		        {"id":2,"text":"开卷"}
		        ],                        
         valueField: 'id',
         textField: 'text',
         width:150
     });  


})