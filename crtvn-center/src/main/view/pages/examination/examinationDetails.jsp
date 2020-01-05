<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath %>" >
<title>考试信息</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/css.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery1.9.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

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
	src="${pageContext.request.contextPath}/resources/js/crtvn/examination/examinationDetails.js"></script>	

</head>

<body>
	<div id="middle">
		<div class="right_cont">
			<div style="width: 100%; margin: auto">
				<form id="examForm" action="examination/examDataGrid" method="post">
				
				<!-- 默认选中条件 -->
				<input id="examinationId" type="text" value="${examinationId}" style="display:none" />
						
				<table class="margin-bottom-20 table  no-border">
					<tr>
						<td class="text-center">
							<input id="downloade" type="button" value="下载考试信息" class="btn btn-info " style="width: 100px;" />							
						</td>
					</tr>
				</table>
			</form>
				<div id="examDataGrid"></div>
			</div>
		</div>
	</div>
</body>
</html>