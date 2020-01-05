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

<title></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css" />
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" ></script>
	
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/resources/js/crtvn/sys/login.js"></script>		
		
</head>
<body>
	<div class="login">
		<div class="content clearfix">
			<div class="content-left">
				<div class="logo">
					<img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="" />
					<p>教务管理系统</p>
				</div>
			</div>
			<div class="shu"></div>
			<div class="content-right">
				<div class="login-form">
					<form id="loginForm" action="" method="post">
					<h2>用户登录/LOGIN</h2>
					<div class="identifire">
						<span>身 份：</span>
							<select id="identity">
								<option value="academician">教务人员</option>
								<option value="admin">管理员</option>
							</select>
					</div>
					<div class="account clearfix">
						<span>账 号：</span> <input id = "name" type="text" name="name" />
					</div>
					<div class="password clearfix">
						<span>密 码：</span> <input id = "password" type="password" name="password" />						
					</div>	
					
					<div class="password clearfix">
					   <span>开课时间：</span>
					   <select id = "startcd" name="startcd" style="width: 200px;"></select>
					</div>
						
					<div class="code clearfix">
						<span>验证码：</span> <input id="code" type="text" name="code" />
						<em>
						 <img src="login/verificationCode" width="127" height="35" id="codeImg" alt="点击更换" title="点击更换">
						</em>
					</div>
					<span style=" padding-left:120px;color:red;">${code_msg}</span>
					<div class="btn">
						<span id="login">
							<input type="button" value="登录" id="loginId"/>
						</span>    
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>