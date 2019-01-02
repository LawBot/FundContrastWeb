<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>{LawBot}</title>
<style>
body {
	background-color: rgba(0, 0, 0, .8);
}

.main {
	padding-top: 1em;
	padding-bottom: 8em;
	background-image: url(images/bg.png);
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	color: white;
}

.wrap {
	margin: 0 auto;
	max-width: 1096px;
	margin-top: 10%;
	text-align: center;
}

.search {
	
}

.searchBtn {
	margin-top: 1%;
}

.download {
	margin-top: 5%;
}

.file {
	width: 550px;
	line-height: 48px;
	height: 48px;
	text-indent: 10px;
	margin-left: 40px;
}

.btn {
	background-color: #4b5760;
	display: inline-block;
	line-height: 48px;
	height: 48px;
	width: 117px;
	color: #fff;
	padding: 0 23px;
	margin: 0px 16px 0px 11px;
	cursor: pointer;
}

.btn_grey {
	background-color: #4b5760;
	display: inline-block;
	line-height: 48px;
	height: 48px;
	color: #808080;
	padding: 0 23px;
	margin-left: 13px;
}

.downloadBtn_grey {
	background-color: #4b5760;
	display: inline-block;
	line-height: 48px;
	height: 60px;
	width: 170px;
	color: #808080;
	padding: 0 23px;
	margin-left: 13px;
}

.circle1 {
	margin-top: 5%;
}

.circle2 {
	margin-top: 2%;
}

.circle3 {
	margin-top: 2%;
}

.item {
	display: inline-block;
	width: 117px;
	height: 45px;
	padding-top: 12px;
	margin: 0px 16px 0px 11px;
	cursor: pointer;
}

.c1 {
	background-color: #a2c6f2;
}

.c2 {
	background-color: #02d1a9;
}

.c3 {
	background-color: #01d6de;
}

.c4 {
	background-color: #01a8df;
}

.c5 {
	background-color: #0180cf;
}

.c6 {
	opacity: 0;
}

.c_grey {
	background-color: #808080;
}

.item a {
	color: #fff;
	text-decoration: none;
	width: 150px;
	height: 150px;
}

.item a span {
	font-size: 15px;
}

#selectBtn {
	cursor: pointer;
}

#updateBtn {
	cursor: pointer;
	width: 150px;
}

#updateBtnAktiv {
	
}

#downloadBtn {
	cursor: pointer;
	width: 130px;
	background-color: #4b5760;
}
</style>
</head>
<body>
	<style>
* {
	padding: 0;
	margin: 0;
	border: 0;
	font-family: Microsoft YaHei, Helvitica, Verdana, Arial, san-serif;
	-webkit-tap-highlight-color: transparent;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-ms-box-sizing: border-box;
	-o-box-sizing: border-box;
	box-sizing: border-box;
	font-size: 16px;
}

p {
	margin: 0;
	padding: 0;
}

ul li {
	list-style: none;
}

#head {
	background-color: #3b4348;
	height: 60px;
}

.head_box {
	margin: 0 auto;
	max-width: 1096px;
	height: 60px;
}

.logo {
	display: inline-block;
	font-size: 26px;
	color: #fff;
	cursor: pointer;
	font-family: monospace;
	line-height: 30px;
	font-weight: 600;
}

.tylogo {
	display: inline-block;
	width: 300px;
	height: 45px;
	text-align: center;
	margin-left: 250px;
}

.nav {
	display: inline-block;
	float: right;
}

.nav_title {
	display: inline-block;
	cursor: pointer;
	color: white;
}

.nav_title a {
	color: #c1cad1;
	text-decoration: none;
	display: inline-block;
	line-height: 60px;
	padding: 0px 15px;
}

.nav_title a:hover {
	color: #fff;
}

.menu {
	position: absolute;
	left: -2px;
	top: 60px;
	width: 116px;
	display: none;
	z-index: 99999;
}

.menu_title {
	background-color: #3b4348;
	color: #c1cad1;
	border-bottom: 1px solid #555;
	text-align: center;
}

.menu_title a {
	text-decoration: none;
	color: #c1cad1;
	padding: 0;
	line-height: 35px;
}

.menu_title a:hover {
	color: #fff;
}

.tslss {
	
}

.tslss.last {
	border-bottom: 0;
	border-bottom-left-radius: 4px;
	border-bottom-right-radius: 4px;
}

.cld {
	font-size: 14px;
}

#experience {
	position: relative;
}

.navbar {
	display: inline-block;
	position: relative;
	left: 500px;
	background-color: #3b4348;
	font-family: Arial, Helvetica, sans-serif;
}

.navbar a {
	float: left;
	font-size: 16px;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

.dropdown {
	float: left;
	overflow: hidden;
}

.dropdown .dropbtn {
	font-size: 16px;
	border: none;
	outline: none;
	color: white;
	padding: 14px 16px;
	background-color: inherit;
	font-family: inherit;
	margin: 0;
}

.navbar a:hover, .dropdown:hover .dropbtn {
	cursor: pointer;
	background-color: #ddd;
	color: black;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #ddd;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	float: none;
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover {
	background-color: #aaa;
}

.dropdown:hover .dropdown-content {
	display: block;
}
</style>
	<div id="head">
		<div class="head_box">
			<div class="logo" onclick="window.location.href=&#39;index.jsp&#39;">
				{LawBot}
				<p style="padding-left: 28px;">小法博</p>
			</div>
			<!-- <a href="http://www.tylaw.com.cn"><img class="tylogo" src="images/Logo.png"></a> -->
			<!-- 
			<ul class="nav">
				<li class="nav_title"><span>并购合同比对</span></li>
				<li class="nav_title"><span>思维导图</span></li>
				<li class="nav_title"><span>关键因子</span></li>
				<li class="nav_title"><a
					href="https://www.v5kf.com/public/ailaw/about.html" " id="aboutId"><span>关于我们</span></a></li>
			</ul>
			 -->
			<div class="navbar">
				<a href="acquisitionbot.html" target="_blank">并购机器人</a>
				<div class="dropdown">
					<button class="dropbtn">
						AI比对 <i class="fa fa-caret-down"></i>
					</button>
					<div class="dropdown-content">
						<a href="#">投资协议</a>
						<a href="#">股东协议</a>  
						<a href="#">文档编辑错误检查</a>
						<a href="#">其他</a>
					</div>
				</div>
				<div class="dropdown">
					<button class="dropbtn">
						思维导图 <i class="fa fa-caret-down"></i>
					</button>
					<div class="dropdown-content">
						<a href="#">投资协议</a>
						<a href="shareholderagreement.html" target="_blank">股东协议</a>
						<a href="acquisitionbot.html" target="_blank">并购流程</a>
					</div>
				</div>
				<div class="dropdown">
					<button class="dropbtn">
						法律、法规 <i class="fa fa-caret-down"></i>
					</button>
					<!-- 
					<div class="dropdown-content">
						<a href="#">投资协议</a>
						<a href="shareholderagreement.html" target="_blank">股东协议</a>
						<a href="#">并购流程</a>
					</div>
					 -->
				</div>
				<div class="dropdown">
					<button class="dropbtn">
						关键因子 <i class="fa fa-caret-down"></i>
					</button>
					<div class="dropdown-content">
						<a href="#">关键因子1</a> <a href="#">关键因子2</a> <a href="#">关键因子3</a>
					</div>
				</div>
				<a href="http://118.24.36.115:8080/QA/" target="_blank">机器人问答</a>
			</div>
		</div>
	</div>
	<script src="./files/jquery-2.1.1.min.js.Download"></script>
	<script src="./js/jquery-2.0.3.min.js"></script>
	<script src="./js/form.js"></script>
	<script>

         $("#experience").mouseleave(function() {
         $('.menu').hide(200);
         }).mouseover(function() {
         $('.menu').show(200);
         });

         $(".menu").mouseleave(function() {
         $(this).hide(200);
         }).mouseover(function() {
         $(this).show(200);
         });

         var chatbot	= "toolbar=0,scrollbars=0,location=0,menubar=0,resizable=1,top=" + (window.screen.availHeight - (window.screen.availHeight/2+275+40)) + ",left=" + (window.screen.availWidth - (window.screen.availWidth/2+365+20)) + ",width=730,height=550";
         function openChat(){
         window.open('http://118.24.36.115:8080/QA/', '_blank', chatbot);
         // win.resizeTo(800,600);
         // win.moveTo(100,100);
         }

         </script>
	<div class="main">
		<div class="wrap">
			<div class="search">
				<div>
					<span>原始文件</span><input type="text" id="orignFile"
						readonly="readonly" placeholder="请选择上传原始文件" class="file"
						onkeypress="fileNameChanged1()" value="${uploadName1}">
					<div class="btn" id="selectBtn1" onclick="openFileDialog1()">选择文件</div>

					<div class="searchBtn">
						<form id="fileDataForm1" enctype="multipart/form-data"
							method="post" action="uploadFile1.do"
							onsubmit="return saveReport1();">
							<input type="hidden" name="uploadName1" id="uploadName1">
							<input type="file" id="targetFile1" name="targetFile1"
								style="display: none" onchange="fileSelected1(this)">
						</form>
					</div>
				</div>
			</div>
			
			<div class="download">
				<div>
					<form action="errorCheck.do" id="downloadForm">
						<input type="hidden" name="reasonColumn" id="reasonColumn"
							value="1" />

						<div class="btn c_grey" id="updateBtn"
							onclick="checkErrorClicked();">生成校对文件</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<style>
#footer {
	padding-top: 60px;
	background-color: #3b4348;
	color: #fff;
}

.fUl {
	margin: 0 auto;
	max-width: 1096px;
}

.f_item {
	display: inline-flex;
	padding-left: 30px;
	margin-bottom: 50px;
	width: 20%;
}

.h_item {
	font-size: 18px;
	margin-bottom: 20px;
}

.fhI {
	margin-bottom: 6px;
	font-size: 14px;
}

.fhI a {
	color: #fff;
	font-size: 14px;
	text-decoration: none;
}

.copyright {
	font-size: 12px;
}

.logoText a {
	font-size: 30px;
	font-family: monospace;
	text-decoration: none;
	color: #fff;
}

.bl {
	border-left: 1px solid #272e31;
}
</style>
	<div id="footer">
		<ul class="fUl">
			<li class="f_item" style="width: 35%;">
				<ul>
					<li class="h_item logoText"><a href="javaSctipt:void(0);">{LawBot}</a></li>
					<li
						style="font-size: 16px; margin-bottom: 8px; margin-top: -17px; padding-left: 10px">小法博</li>
					<li><img src="images/qcode.png" width="120" height="120"></li>
					<li><span class="copyright">Copyright ©2018
							xiaofabo.com.cn. ALL Rights Reserved</span></li>
					<li style="margin-top: -3px;"><span class="copyright">粤ICP备05005391号</span></li>
				</ul>
			</li>
			<li class="f_item bl">
				<ul>
					<li class="h_item">核心系统</li>
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/ajfx.html">AI案件分析</a></li>
					<li class="fhI"><a href="javascript: openChat();">法律Chatbot</a></li>
					<!-- <li class="fhI"><a href="lab.html">法律实验室</a></li> -->
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/adju.html">AI裁决书</a></li>
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/product.html">产品列表</a></li>
				</ul>
			</li>
			<li class="f_item bl">
				<ul>
					<li class="h_item">检索系统</li>
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/casetype.html">案件类型</a></li>
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/law.html">律师/律所</a></li>
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/court.html">法官/法院</a></li>
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/compar.html">对比统计</a></li>
				</ul>
			</li>
			<li class="f_item bl">
				<ul>
					<li class="h_item">关于</li>
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/about.html">关于我们</a></li>
					<li class="fhI"><a
						href="https://www.v5kf.com/public/ailaw/about.html">联系我们</a></li>
					<!--
				<li class="fhI">xiaofabo_at_xiaofabo_dot_com_dot_cn (replace "_at_" with "@" and "_dot_" with ".")</li>
				-->
				</ul>
			</li>
		</ul>
	</div>
	<script type="text/javascript">
      function saveReport2() { 
		// jquery 表单提交 
		$("#fileDataForm2").ajaxSubmit(function(message) { 
		// 对于表单提交成功后处理，message为提交页面saveReport.htm的返回内容 
		}); 
		
		return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转 
		} 
		
	  function saveReport1() { 
		// jquery 表单提交 
		$("#fileDataForm1").ajaxSubmit(function(message) { 
		// 对于表单提交成功后处理，message为提交页面saveReport.htm的返回内容 
		}); 
		
		return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转 
		} 
      
      function openFileDialog1()
      {
    	 $("#targetFile1").val("");  
     	 document.getElementById("targetFile1").click();
     
      }
      
      
      function fileSelected1(input){
	      var fileName = input.files[0].name;
	      var file = $("#targetFile1").val();
		  var pos=file.lastIndexOf("\\");
	      document.getElementById('orignFile').value = file.substring(pos+1);
	      if ($("#file").val()!="" && $("#file").val()!=null && $("#orignFile").val()!="" && $("#orignFile").val()!=null) {
		      checkFileName(fileName);
		      //$("#updateBtn").attr("onclick","updateFile();");
	      }
	      $("#uploadName1").val($("#orignFile").val());
	      $("#fileDataForm1").submit();
      }

      function checkFileName(input){

      document.getElementById('updateBtn').removeAttribute('disabled');
      document.getElementById('updateBtn').setAttribute('class','btn');
      }
      function fileNameChanged2(){
    	  //alert(2);
	      var str = document.getElementById('file').value;
	      /* if(str.length <= 3)
	      document.getElementById('updateBtn').setAttribute('class','btn_grey');
	      document.getElementById('updateBtn').setAttribute('disabled','disabled'); */
      }
      function fileNameChanged1(){
    	  //alert(1);
	      var str = document.getElementById('orignFile').value;
	      /* if(str.length <= 3)
	      document.getElementById('updateBtn').setAttribute('class','btn_grey');
	      document.getElementById('updateBtn').setAttribute('disabled','disabled'); */
      }
      function updateFile(){
    	  
    	  if($("#targetFile1").val()==""){
    		  alert("请选择一个文件上传");
    		  $("#downloadBtn").removeAttr("onclick");//上传文件失败移除下载点击效果
    		  return;
    	  }
    	  $("#uploadName").val($("#file").val());
    	  //基金类型
    	  //$("#fundType").val($(".circle1").find(".btn").eq(0).attr('data-id'));
      	 $("#fileDataForm").submit();

      }
      
      $(function(){     
          var options = {   
              type: 'POST',  
              url: 'uploadFile.do',  
              success:showResponse,    
              error : function(xhr, status, err) {              
                  alert("操作失败");  
              }  
          };   
          $("#fileDataForm").submit(function(){   
              $(this).ajaxSubmit(options);   
              return false;   //防止表单自动提交  
          });
  });  
    
   
  function showResponse(responseText, statusText, xhr, $form){      
      if(statusText == "success"){  
          /** 
          * 请求成功后的操作 
          */  
        	  //alert("上传成功");  
        	  checkErrorClicked ();
      } else {  
    	  $("#downloadBtn").remove("onclick");
          alert("上传失败");  
      }
  }
      
      function checkErrorClicked (){
    	  var file1 = $("#targetFile1").val();
    	  if(file1==""){
    		  alert("请选择需要被校对的原始文件！");
    		  return;
    	  }
    	  var pos=file1.lastIndexOf("\\");
    	  $("#fileName").val($("#file").val());
    	  //alert($("#jjSelect").val() +$("#fxSelect").val());
    	  $("#downloadForm").submit();
    	  document.getElementById('orignFile').value = "请选择上传原始文件";
      }
     
     function clickReason(reasonColumn){
    	 if(reasonColumn == '1'){
    		 $("#reason_y").attr('class','btn');
    		 $("#reason_n").attr('class','item c_grey');
    	 }else{
    		 $("#reason_n").attr('class','btn');
    		 $("#reason_y").attr('class','item c_grey');
    	 }
    	 
    	 $("#reasonColumn").val(reasonColumn);
     }

      </script>
</body>
</html>
