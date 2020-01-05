$(function() {
	
	// 考试状态下拉框
	$('#status').combobox({
		data : [ 
		         {"id" : 0,"text" : "全部"},
		         {"id" : 1,"text" : "是"}, 
		         {"id" : 2,"text" : "否"}	        
		        ],
		valueField : 'id',
		textField : 'text',
		width : 165,
		onLoadSuccess: function () { //加载完成后,设置选中第一项
            var datas = $(this).combobox('getData');
            for ( var i = 0; i <datas.length; i++){
           	 if(datas[i].id==$('#mark').val()){
               	 $(this).combobox('select', datas[i].id);
               }
            }   
	    }
	});
	
	  //课程代号下拉框
  	 $('#course').combobox({
		   url: "course/courseSelect2?collegeId=" + $('#CID').val()+"&professionId="
		   +$('#PID').val() +"&teacherId="+$('#TID').val(),  
		   
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
	
	
	$('#emit').click(function(){
		$('#status').combobox("enable");
		$('#keep').show();
	});
	
	$('#keep').click(function(){
		 $.ajax({
             type: "post",
             url: "teacher/teacherStatus",
             data:  {"mark":$('#status').combobox("getValue"),
         	 	"teacherId":$('#TID').val()},
             dataType: "json",
             success: function(data){
            	 var msg = "失败";
            	if(data.msg == "ok"){
            		msg = "成功";
            	}
                $.messager.show({
					  title: '提示信息' , 
					  msg: '修改' + msg
				});
                $('#status').combobox("disable");
                $('#keep').hide();
              }
         });
  });
	
});
