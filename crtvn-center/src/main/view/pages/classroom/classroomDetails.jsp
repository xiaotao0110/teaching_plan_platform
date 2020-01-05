<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>教室信息</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/student.css">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
	
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.2.6/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.2.6/themes/icon.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/js/crtvn/classroom/classroomDetails.js"></script>	
	
</head>
<body>
	<div class="container">
		<div class="content">
			<div class="header clearfix">
				<div class="title">
					<h1>教室信息</h1>
				</div>
				<div class="bottom fr">
					<a id="emit" href="javascript:;">编辑</a> 
					<a id="keep" href="javascript:;" style="display:none">保存</a>
				</div>
			</div>
			<div class="main">

				<div class="content clearfix">
					<div class="left fl">
						<label for="">是否考场：</label> 
						<input id="status" name="name" class="easyui-combobox" disabled/>
					</div>
					
					<div class="right fr">
						<label for="">考场人数：</label> 
						<input id="number" name="number" value="${classroomVO.number }" disabled/>
					</div>
				</div>

				<!--基础信息-->
				<div class="BasicInformation">
					<div class="title">基础信息</div>
				</div>
				<div class="content clearfix">
					<div class="left fl">
					
						<!-- 默认选中条件 -->
						<input id="mark" type="text" value="${classroomVO.mark}" style="display:none" />
						<!-- 查询条件 -->
						<input id="CSID" type="text" value="${classroomVO.classroomId}" style="display:none" />
						
						<div>
							<label for="">所属院系：</label> <input id="a1" type="text"
								value="${classroomVO.collegeName }" disabled />
						</div>

						<div>
							<label for="">楼栋：</label> <input id="a2" type="text"
								value="${classroomVO.building}教" disabled />
						</div>


					</div>
					<div class="right fr">
						
						<div>
							<label for="">教室编号：</label> <input id="a3" type="text"
								value="${classroomVO.code }" disabled />
						</div>

						<div>
							<label for="">座位数：</label> <input id="a4" type="text"
								value="${classroomVO.seats }" disabled />
						</div>
						
						
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>