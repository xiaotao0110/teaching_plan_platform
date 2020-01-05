$(function(){
	
	 //考试形式下拉框
	 $('#courseManner').combobox({
		 data: [
		        {"id":0,"text":"全部"},
		        {"id":1,"text":"闭卷"},
		        {"id":2,"text":"开卷"}
		        ],                        
         valueField: 'id',
         textField: 'text',
         width:150,
         onLoadSuccess: function () { //加载完成后,设置选中第一项
             var datas = $(this).combobox('getData');
             for ( var i = 0; i <datas.length; i++){
            	 if(datas[i].id==$('#manner').val()){
                	 $(this).combobox('select', datas[i].id);
                }
             }   
 	    }
     });  

	 //教学班号下拉框
	 $('#classs').combobox({
		url: "classs/classsSelect?professionId="+$('#PID').val()+"&courseId="+$('#CID').val(),                        
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
	 
	 $('#emit').click(function(){
			$('#courseManner').combobox("enable");
			$('#keep').show();
		});
		
		$('#keep').click(function(){
			 $.ajax({
	             type: "post",
	             url: "course/courseStatus",
	             data: {"courseManner":$('#courseManner').combobox("getValue"),
	            	 	"courseId":$('#CID').val()},
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
	                $('#courseManner').combobox("disable");
	                $('#keep').hide();
	              }
	         });
	  });
	    
    
})