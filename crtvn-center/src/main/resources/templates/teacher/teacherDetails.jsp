<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<title>教师信息</title>
<link rel="stylesheet"
	href="/css/common.css">
<link rel="stylesheet"
	href="/css/student.css">
<script
	src="/js/jquery-1.9.1.min.js"></script>

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
	src="/js/crtvn/teacher/teacherDetails.js"></script>
	
</head>
<body>
	<div class="container">
		<div class="content">
			<div class="header clearfix">
				<div class="title">
					<h1>教师信息</h1>
				</div>
				<div class="bottom fr">
					<a id="emit" href="javascript:;">编辑</a> 
					<a id="keep" href="javascript:;" style="display:none">保存</a>
				</div>
			</div>
			<div class="main">

				<div class="content clearfix">
					<div class="left fl">
						<label for="">是否监考：</label> 
						<input id="status" name="name" class="easyui-combobox" disabled>
					</div>
					
					<div class="right fr">
						<label for="">教授课程：</label> 
						<input id="course"  class="easyui-combobox" >
					</div>
					
				</div>

				<!--基础信息-->
				<div class="BasicInformation">
					<div class="title">基础信息</div>
				</div>
				<div class="content clearfix">
					<div class="left fl">

						<!-- 默认选中条件 -->
						<input id="mark" type="text" value="${teacherVO.mark}" style="display:none" />
						<!-- 查询条件 -->
						<input id="TID" type="text" value="${teacherVO.teacherId}" style="display:none" />
						<input id="CID" type="text" value="${teacherVO.collegeId}" style="display:none" />
						<input id="PID" type="text" value="${teacherVO.professionId}" style="display:none" />
						
						<div>
							<label for="">所属院系：</label> <input id="a1" type="text"
								value="${teacherVO.collegeName}" disabled />
						</div>

						<div>
							<label for="">所属专业：</label> <input id="a2" type="text"
								value="${teacherVO.professionName}" disabled />
						</div>

						<div>
							<label for="">教师工号：</label> <input id="a3" type="text"
								value="${teacherVO.teacherCode}" disabled />
						</div>

						<div>
							<label for="">教师名称：</label> <input id="a4" type="text"
								value="${teacherVO.teacherName}" disabled />
						</div>
						
					</div>
					<div class="right fr">
						 <div>
                            <label for="">性别：</label>
                            <input value = "${teacherVO.gender eq 0 ? '男':'女'}" disabled> 
                        </div>

						<div>
							<label for="">年龄：</label> <input id="a6" type="text"
								value="${teacherVO.age}" disabled />
						</div>

						<div>
							<label for="">职称：</label> <input id="a7" type="text"
								value="${teacherVO.position}" disabled />
						</div>
						
						<div>
							<label for="">手机号码：</label> <input id="phone" type="number"
								value="${teacherVO.phone}" disabled>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>