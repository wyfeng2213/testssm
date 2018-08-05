<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
	request.setAttribute("imgpath", "upload/u=415293130,2419074865&fm=27&gp=0.jpg");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>FileUpload</title>
</head>
<body>
	<form action="${basepath}user/upload" method="post"
		enctype="multipart/form-data">
		<label>用户名：</label><input type="text" name="name" /><br /> <label>头
			像</label><input type="file" name="file" /><br /> <input type="submit"
			value="提  交" />
	</form>

	<img src="${basePath}upload/u=415293130,2419074865&fm=27&gp=0.jpg">
	<img width="100px" height="100px" src="${basePath}${imgpath}">

	<!-- <form action="book/list" method="post">
		<label>用户名：</label><input type="text" name="name" /><br /> <label>密
			码：</label><input type="password" name="password" /><br /> <label>头
			像</label><br /> <input type="submit" value="提  交" />
	</form>-->

</body>
</html>
