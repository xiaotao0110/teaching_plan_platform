$(function(){
		
	var bodyHeight = $(window).height();
	var bodyWidth = $(window).width();
	$("#examDataGrid").datagrid({
		url:getBaseUrl() +$('#examForm').attr('action'),
        title: "考试信息列表",
        width: bodyWidth - 20,  
        height: bodyHeight-180,       
        rownumbers: true,
        pagination: true,
        queryParams : {"examinationId":$('#examinationId').val()},
        columns:[[    
           {field:"sid",title:"学号",width:bodyWidth * 0.1},
           {field:"studentName",title:"姓名",width:bodyWidth * 0.1},    
           {field:"classsCode",title:"班级",width:bodyWidth * 0.1},   
           {field:"courseName",title:"课程",width:bodyWidth * 0.1},    
           {field:"examinationCode",title:"考场号",width:bodyWidth * 0.1},   
           {field:"examinationTime",title:"考试时间",width:bodyWidth * 0.2}, 
           {field:"seatNo",title:"座位号",width:bodyWidth * 0.05},  
        ]]
    })
    
    
    //下载考试
	$('#downloade').click(function(){	
		$.messager.confirm('提示','是否下载查询到的考场对应的考试信息？',function(r){   
			if (r){   
			  window.location.href=getBaseUrl() +"examination/exportExcelExamn?examinationId="+$('#examinationId').val();
			}   
		});
		 
	});	
 
})