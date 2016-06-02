<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>秒杀列表页</title>
   <%@include file="common/head.jsp" %>
   </head>
   <body>
      <div class="container">
      	<div class="panel panel-default">
      	<div class="panel-heading text-center">
      		<h2>秒杀列表</h2>
      	</div>
      	<div class="panel-body">
      		<table class="table table-hover">
      		<thead>
      		<tr>
      			<th>名称</th>
      			<th>库存</th>
      			<th>开始时间</th>
      			<th>结束时间</th>
      			<th>创建时间</th>
      			<th>详情页</th>
      		</tr>
      		</thead>
      		<tbody>
      		<c:forEach var ="sk" items="${list }">
      		<tr>
      		<td>${sk.name}</td>
      		<td>${sk.number}</td>
      		<td><fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
      		<td><fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
      		<td><fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
      		<td><a class="btn btn-info" href="${pageContext.request.contextPath }/seckill/${sk.seckillId}/detail">link</a></td>
      		</tr>
      		</c:forEach>
      		</tbody>
      		</table>
      	</div>
      	</div>
      </div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="${pageContext.request.contextPath}/resource/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${pageContext.request.contextPath}/resource/bootstrap.min.js"></script>
   </body>
</html>