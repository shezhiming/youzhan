<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>优栈</title>
	<%String path = request.getContextPath(); %>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="<%=path%>/css/bootstrap-theme.min.css" rel="stylesheet" media="screen">
	<link href="<%=path%>/css/header.css" rel="stylesheet" media="screen">
	<script src="<%=path%>/js/jquery-2.0.3.js"></script>
	<script src="<%=path%>/js/bootstrap.min.js"></script>

</head>
<body>
	<header id="js_navigation" class="siteheader" role="banner">
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<nav class="navbar navbar-default" role="navigation">
				
				<div class="header-info"><h2 >HOTEL</h2></div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						
						<li class="dropdown">
							 <a href="#" class="dropdown-toggle" data-toggle="dropdown">用户<strong class="caret"></strong></a>
							<ul class="dropdown-menu">
								<c:choose>
								<c:when test="${empty sessionScope.sessionUser}">
								<li>
									<a href="<%=path%>/user/login">登陆</a>
								</li>
								<li>
									<a href="<%=path%>/user/register">注册</a>
								</li>
								</c:when>
								<c:otherwise>
								<li>
									<a href="<%=path%>/user/index">用户中心</a>
								</li>
								<li>
									<a href="<%=path%>/user/logout">注销账号</a>
								</li>
								</c:otherwise>
								</c:choose>
								<li class="divider">
								</li>
								<li>
									<a href="#">Separated link</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				
			</nav>
		</div>
	</div>
</div>
	</header>