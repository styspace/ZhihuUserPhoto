<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开始页面</title>
<base href="<%=basePath%>">
</head>

<!-- 引入juery -->
<script src="jsp/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#start").click(function(){
		$.ajax({
			type:"POST",
			url:"<%=basePath%>start/getPhoto",
			data:$('#form').serialize(),
            dataType: "json",
            success: function (data) {
                //获取内容
            	if(1000 == data.errorCode){
                	$('#content').html("【结果】：" + data.errorDesc);
                }else{
                	var captchImg = document.getElementById("captchImg");
                	if(typeof($("#captchImg").attr("src"))=="undefined"){
                		captchImg.setAttribute("src", data.body.picUrl);
                    	var form = document.getElementById("form");
                    	var captchInput = document.createElement("input");
                    	captchInput.type = "text";
                    	captchInput.name = "captch";
                    	form.appendChild(captchInput);
                	}
                }
            },
            error: function (err) {
                alert(err);
            }
		});
	});
});

</script>

<body>
<img id="captchImg"></img>
<form id="form" style="font-size:16px;" >
	<input id="start" type="button" value="开始">
</form>

<div id="content" style="color:#FF0000">
</div>
</body>

</html>