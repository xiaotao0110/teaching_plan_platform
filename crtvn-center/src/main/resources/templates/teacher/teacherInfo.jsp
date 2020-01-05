<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<title>教师信息</title>
<link rel="stylesheet" href="/css/bootstrap.css" />
<link rel="stylesheet" href="/css/css.css" />
<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>

<script type="text/javascript"
	src="/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/js/jquery-easyui-1.2.6/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="/js/jquery-easyui-1.2.6/themes/icon.css" />
<script type="text/javascript"
	src="/js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="/js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" 
	src="/js/crtvn/teacher/teacherInfo.js"></script>	
</head>

<body>

	<div id="middle">

		<div class="right_cont">

			<div style="width: 100%; margin: auto">
			<form  id="teacherForm" action="teacher/teacherDataGrid">
				<table class="table table-bordered">
					<tr>
						<td width="10%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">院系：</td>
						<td width="23%">
							<input id="college"  class="easyui-combobox" >
						</td>
					
						<td width="10%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">专业：</td>
						<td width="23%">
							<input id="profession"  class="easyui-combobox" >
						</td>
						<td width="10%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">教师工号：</td>
						<td width="23%">
							<input id="teacherCode"  class="easyui-combobox" >
						</td>					
					</tr>
					
					<tr>
						<td width="10%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">教授课程：</td>
						<td width="23%">
							<input id="courseCode"  class="easyui-combobox" >
						</td>
						
						<td width="10%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">教师名称：</td>
						<td><input id="teacherName" type="text" class=" span1-1" style="width: 160px;"/></td>
						
						<td width="10%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">是否监考：</td>
						<td width="23%">
							<input id="status"  class="easyui-combobox" >
						</td>
						
					</tr>
				</table>

				<table class="margin-bottom-20 table  no-border">
					<tr>
						<td class="text-center">
							<input id="teacherSumbit" type="button" value="查询" class="btn btn-info " style="width: 80px;" />
						</td>
					</tr>
				</table>
			</form>	
			
				<div id="teacherDataGrid" ></div>			
			</div>
		</div>
	</div>
</body>
</html>