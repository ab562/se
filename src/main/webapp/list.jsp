<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
<thead >
<tr>
<th style="width: 100px;">id</th>
<th style="width: 100px;">名字</th>
<th style="width: 100px;">年龄</th>
</tr>
</thead>
<tbody id="tb">

</tbody>
</table>
</body>
<script type="text/javascript" src="resource/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	var h="";
	$.ajax({
		url:"test/list",
		data:{id:1,name:"wqq",age:12},
		type:"post",
		success:function(d){
			for(var i =0;i<d.length;i++){
				h+='<tr>';
				h+='<td>'+d[i].id+'</td>';
				h+='<td>'+d[i].name+'</td>';
				h+='<td>'+d[i].age+'</td>';
				h+='</tr>';
			}
			$("#tb").html(h);
		},
		error:function(e){
			
		}
		
	});
});
</script>
</html>