<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	function download22(){
		/* $.ajax({
	 	    type: "post",
            url: "download.do",
            dataType: "json",
            success: function(data) {
				alert(data);
            },error: function(xhr, type, errorThrown) {
            		console.log("下载异常");
            }
	 }) */
	 $("#downloadForm").submit();
	}

</script> 
</head>
<body>
${msg}
上传成功！！！<a id="" href="javaScript:void(0);" onclick="download22();">点击下载</a>
<form action="download.do" id="downloadForm">

</form>

</body>



</html>

