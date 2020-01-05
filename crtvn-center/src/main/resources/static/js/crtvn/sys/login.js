$(function(){
	var myDate = new Date();
	var year = myDate.getFullYear(); 
	var month = myDate.getMonth(); //获取当前月份(0-11,0代表1月)
	$("#startcd").append("<option value="+(year-1)+"-上学期"+">"+(year-1)+"-上学期"+"</option>"); 
	$("#startcd").append("<option value="+(year-1)+"-下学期"+">"+(year-1)+"-下学期"+"</option>"); 
	$("#startcd").append("<option value="+year+"-上学期"+">"+year+"-上学期"+"</option>"); 
	$("#startcd").append("<option value="+year+"-下学期"+">"+year+"-下学期"+"</option>"); 
	
	document.onkeydown=function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode==13){ 
			sumbitFun();
		}
	}
	
	$('#loginId').click(function(){	
		sumbitFun();		
	});
	
	$('#codeImg').click(function(){	
		$("#codeImg").attr("src", $("#codeImg").attr("src")+"?time=" + new Date().getTime());
		$("#code").val("");
	});
	
	function sumbitFun(){
		
		var startcd = $('#startcd').val();
		var startcd
		
		var identity = $('#identity').val();
		if(identity == 'admin'){
			$('#loginForm').attr('action','login/adminLogin');		
		}else if(identity == 'academician'){
			$('#loginForm').attr('action','login/academicianLogin');
		}
		$('#loginForm').submit();	
	}
	  
});