<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>{LawBot}</title>
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
         .logoText a{
         font-size: 30px;
         font-family: monospace;text-decoration: none;color: #fff;
         }
         .bl {
         border-left: 1px solid #272e31;
         }
      </style>
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
         margin: 0; padding: 0;
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
         .nav {
         display: inline-block;
         float: right;
         }
         .nav_title {
         display: inline-block;
         cursor: pointer;
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
         .mystyle {
		
		width: 250px;
		
		height: 250px;
		
		position: relative;
		
		cursor: pointer;
		
		outline: medium none;
		
		filter: alpha(opacity=0);
		
		-moz-opacity: 0;
		
		opacity: 0;
		
		top: -80%;
		
		left: 10px;
		
		}
      </style>
      <style>
         body {
         background-color: rgba(0, 0, 0, .8);
         }
         .main {
         /*
         * 	position: fixed;
         top: 60px;
         right: 0;
         bottom: 0;
         left: 0;
         */
         padding-top: 1em;
         padding-bottom: 12em;
         background-image: url(images/bg.png);
         background-size: cover;
         background-position: center;
         background-repeat: no-repeat;
         color: white;
         }
         .wrap {
         margin: 0 auto;
         max-width: 1096px;
         margin-top: 12%;
         text-align: center;
         }
         .search {
         }
         .searchBtn{
         margin-top: 1%;
         }
         .download{
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
         .c1 { background-color: #a2c6f2; }
         .c2 { background-color: #02d1a9; }
         .c3 { background-color: #01d6de; }
         .c4 { background-color: #01a8df; }
         .c5 { background-color: #0180cf; }
         .c6 { opacity: 0; }
         .c_grey{ background-color:#808080; }
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
      <div id="head">
         <div class="head_box">
            <div class="logo" onclick="window.location.href=&#39;index.html&#39;">
               {LawBot}
               <p>小法博智能系统</p>
            </div>
            <ul class="nav">
               <li class="nav_title"><a href="https://" id="foundCompareId" style="color: rgb(255, 255, 255);"><span>基金条文对照</span></a></li>
               <li class="nav_title"><a href="https://" id="legalId"><span>法律意见书</span></a></li>
               <li class="nav_title"><a href="https://www.v5kf.com/public/ailaw/about.html" id="aboutId"><span>关于我们</span></a></li>
            </ul>
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

         var v5_chat_attrs	= "toolbar=0,scrollbars=0,location=0,menubar=0,resizable=1,top=" + (window.screen.availHeight - (window.screen.availHeight/2+275+40)) + ",left=" + (window.screen.availWidth - (window.screen.availWidth/2+365+20)) + ",width=730,height=550";
         function openChat(){
         window.open('http://chat.v5kf.com/desk/kehu.html?site_id=123898', '_blank', v5_chat_attrs);
         // win.resizeTo(800,600);
         // win.moveTo(100,100);
         }

         </script>
      <div class="main">
         <div class="wrap">
            <div class="search">
               <div>
                  <input type="text" id="file" readonly="readonly" placeholder="请选择上传合同文件" class="file" onkeypress = "fileNameChanged()" value="${uploadName}">
                  <div class="btn" id="selectBtn" onclick= "openFileDialog()" >选择文件</div>
                  <div class = "searchBtn">
                  <form id="fileDataForm" enctype="multipart/form-data" method="post">
                  <!-- <input class="btn mystyle" type="file"  name="targetFile1" id="targetFile1"> -->
                  <input type="hidden" name="uploadName" id="uploadName">
                  <input type="hidden" name="fundType" id="fundType"/> 
                  <input type="hidden" name="reasonColumn" id="reasonColumn" value="1"/> 
                  <input type="file" id="targetFile1" name="targetFile1" style="display:none" onchange = "fileSelected(this)">
                  
                   </form>
               </div>
               <!-- <div class="btn c_grey" id="updateBtn" >上传文件</div> -->
                  </div>
                  
            </div>
            <div class="circle1">
            <ul>
               <li class="item a" id="foundType"><a ><span>基金类型</span></a></li>
               <li class="btn" id="btn_stockType" onclick="clickJijin('btn_stockType');" data-id="0"><a id="1"><span id="2">股票型</span></a></li>
               <li class="item c_grey" id="btn_exponentType" onclick="clickJijin('btn_exponentType');" data-id="1"><a><span>指数型</span></a></li>
               <li class="item c_grey" id="btn_bondType" onclick="clickJijin('btn_bondType');" data-id="2"><a><span>债卷型</span></a></li>
               <li class="item c_grey" id="btn_currencyMarket" onclick="clickJijin('btn_currencyMarket');" data-id="3"><a><span>货币市场</span></a></li>
            </ul>
            </div>
            <div class="circle3">
            <ul>
               <li class="item a" id="updateReason"><a><span>修改理由</span></a></li>
               <li class="btn" id="reason_y" onclick="clickReason('1');"><a><span>有</span></a></li>
               <li class="item c_grey" id="reason_n" onclick="clickReason('0');"><a><span>无</span></a></li>
               <li class="item c6" id="btn_jiutai2"><a><span> 不顯示占位</span></a></li>
               <li class="item c6" id="btn_jiutai2"><a><span> 不顯示占位</span></a></li>
            </ul>
            </div>
            <div class="download">
               <div>
               	  <form action="download.do" id="downloadForm">
                        <input type="hidden" name="fileName" id="fileName"/>
                        <!-- <button class="btn" type="button" id="downloadBtn"  onclick = "downloadClicked();">下载对照表</button> -->
                        <div class="btn c_grey" id="downloadBtn">下载对照表</div>
                   </form>
               </div>
            </div>
         </div>
      </div>
 <div id="footer">
	<ul class="fUl">
		<li class="f_item" style="width:35%;"> 
  
			<ul>
				<li class="h_item logoText"><a href="index.html">{LawBot}</a></li>
				<li style="font-size:16px;margin-bottom:8px;margin-top: -17px;">法律人工智能系统</li>
				<li><img src="images/qcode.png" width="100" height="100"></li>
				<li><span class="copyright">Copyright ©2016 ailaw.com. ALL Rights Reserved</span></li>
				<li style="margin-top: -3px;"><span class="copyright">粤ICP备05005391号</span></li>
			</ul>
		</li>
		<li class="f_item bl">
			<ul>
				<li class="h_item">核心系统</li>
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/ajfx.html">AI案件分析</a></li>
				<li class="fhI"><a href="javascript: openChat();">法律Chatbot</a></li>
				<!-- <li class="fhI"><a href="lab.html">法律实验室</a></li> -->
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/adju.html">AI裁决书</a></li>
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/product.html">产品列表</a></li>
			</ul>
		</li>
		<li class="f_item bl">
			<ul>
				<li class="h_item">检索系统</li>
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/casetype.html">案件类型</a></li>
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/law.html">律师/律所</a></li>
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/court.html">法官/法院</a></li>
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/compar.html">对比统计</a></li>
			</ul>
		</li>
		<li class="f_item bl">
			<ul>
				<li class="h_item">关于</li>
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/about.html">关于我们</a></li>
				<li class="fhI"><a href="https://www.v5kf.com/public/ailaw/about.html">联系我们</a></li>
				<li class="fhI">xiaofabo@xiaofabo.com.cn</li>
			</ul>
		</li>
	</ul>
</div>
      <script type="text/javascript">
      function openFileDialog()
      {
      document.getElementById("targetFile1").click();
      }

      function fileSelected(input){
      var fileName = input.files[0].name;
      var file = $("#targetFile1").val();
	  var pos=file.lastIndexOf("\\");
      document.getElementById('file').value = file.substring(pos+1);
      checkFileName(fileName);
      $("#updateBtn").attr("onclick","updateFile();");
      }

      function checkFileName(input){

      document.getElementById('updateBtn').removeAttribute('disabled');
      document.getElementById('updateBtn').setAttribute('class','btn');
      }
      function fileNameChanged(){
      var str = document.getElementById('file').value;
      if(str.length <= 3)
      document.getElementById('updateBtn').setAttribute('class','btn_grey');
      document.getElementById('updateBtn').setAttribute('disabled','disabled');
      }
      function updateFile(){
    	  if($("#targetFile1").val()==""){
    		  alert("请选择一个文件上传");
    		  $("#downloadBtn").removeAttr("onclick");//上传文件失败移除下载点击效果
    		  return;
    	  }
    	  $("#uploadName").val($("#file").val());
    	  //基金类型
    	  $("#fundType").val($(".circle1").find(".btn").eq(0).attr('data-id'));
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
          document.getElementById('btn_gongyin').className='item c_grey';
          document.getElementById('btn_huaxia').className='item c_grey';
          document.getElementById('btn_jiutai').className='item c_grey';
          document.getElementById('btn_stockType').className='item c_grey';
          document.getElementById('btn_exponentType').className='item c_grey';
          document.getElementById('btn_bondType').className='item c_grey';
          document.getElementById('btn_currencyMarket').className='item c_grey';
          
          document.getElementById('btn_gongyin').removeAttribute('onclick');
          
          if (responseText>0) {
        	  if($("#reasonColumn").val()=='0'){
        		 $("#reason_n").attr('class','btn');
         		 $("#reason_y").attr('class','item c_grey');
        	  }
        	  
        	  alert("上传成功");  
        	  if($("#fundType").val()==null || $("#fundType").val()=="" || $("#fundType").val()=="NaN"){
        		  $("#fundType").val(responseText);
        	  }else{
        		  responseText = $("#fundType").val();
        	  }
        		  if(responseText == 10){
            		  document.getElementById('btn_gongyin').setAttribute('class','btn');
            		  document.getElementById('btn_stockType').setAttribute('class','btn');
            	  }
            	  if(responseText == 11){
            		  document.getElementById('btn_gongyin').setAttribute('class','btn');
            		  document.getElementById('btn_exponentType').setAttribute('class','btn');
            	  }
            	  if(responseText == 12){
            		  document.getElementById('btn_gongyin').setAttribute('class','btn');
            		  document.getElementById('btn_bondType').setAttribute('class','btn');
            	  }
            	  if(responseText == 13){
            		  document.getElementById('btn_gongyin').setAttribute('class','btn');
            		  document.getElementById('btn_currencyMarket').setAttribute('class','btn');
            	  }
            	  
            	  if(responseText == 20){
            		  document.getElementById('btn_huaxia').setAttribute('class','btn');
            		  document.getElementById('btn_stockType').setAttribute('class','btn');
            	  }
            	  if(responseText == 21){
            		  document.getElementById('btn_huaxia').setAttribute('class','btn');
            		  document.getElementById('btn_exponentType').setAttribute('class','btn');
            	  }
            	  if(responseText == 22){
            		  document.getElementById('btn_huaxia').setAttribute('class','btn');
            		  document.getElementById('btn_bondType').setAttribute('class','btn');
            	  }
            	  if(responseText == 23){
            		  document.getElementById('btn_huaxia').setAttribute('class','btn');
            		  document.getElementById('btn_currencyMarket').setAttribute('class','btn');
            	  }
            	  
            	  if(responseText == 30){
            		  document.getElementById('btn_jiutai').setAttribute('class','btn');
            		  document.getElementById('btn_stockType').setAttribute('class','btn');
            	  }
            	  if(responseText == 31){
            		  document.getElementById('btn_jiutai').setAttribute('class','btn');
            		  document.getElementById('btn_exponentType').setAttribute('class','btn');
            	  }
            	  if(responseText == 32){
            		  document.getElementById('btn_jiutai').setAttribute('class','btn');
            		  document.getElementById('btn_bondType').setAttribute('class','btn');
            	  }
            	  if(responseText == 33){
            		  document.getElementById('btn_jiutai').setAttribute('class','btn');
            		  document.getElementById('btn_currencyMarket').setAttribute('class','btn');
            	  }
		  }else {
			  alert("上传失败");
		  }
          
          document.getElementById('downloadBtn').removeAttribute('disabled');
          document.getElementById('downloadBtn').setAttribute('class','btn');
          $("#downloadBtn").attr("onclick","downloadClicked();");//上传文件成功添加点击事件
      } else {  
    	  $("#downloadBtn").remove("onclick");
          alert("上传失败");  
      }     
  }  
      
      function downloadClicked (){  
    	  
    	  var file = $("#targetFile1").val();
    	  var pos=file.lastIndexOf("\\");
    	  $("#fileName").val($("#file").val());
    	  //alert($("#jjSelect").val() +$("#fxSelect").val());
    	 
    	  
    	  /*  $("#downloadForm").submit(); */
           
      } 
      
      
     /*  $(".wrap .circle1 li").click(function() {
    	  alert($("this").attr('id'));
			$("this").removeClass("c_grey");
    	  
	  })  */
	  
	   function clickJijin(id){
		   /* $(".circle1 li").each(function(){
			   alert($("this").html());
		   }) */
		// alert($("#"+id).removeClass("c_grey"));
		   $("#"+id).parent().find(".item").addClass('c_grey');
		  // $("#"+id).parent().find(".a").attr('class','btn');
		   //$("#"+id).parent().find(".a").eq(0).removeClass('btn');
		   $("#"+id).parent().find(".a").removeClass('c_grey');
		   $("#"+id).removeClass("c_grey");
		   $("#"+id).parent().find(".btn").attr('class','item c_grey');
		   $("#"+id).attr('class','btn');
		   $("#jjSelect").val($("#"+id).attr('data-id'));
      } 
     
     function clickFaxing(id){
    	   $("#"+id).parent().find(".item").addClass('c_grey');
		 // $("#"+id).parent().find(".a").attr('class','btn');
		   //$("#"+id).parent().find(".a").eq(0).removeClass('btn');
		   $("#"+id).parent().find(".a").removeClass('c_grey');
		   $("#"+id).removeClass("c_grey");
		   $("#"+id).parent().find(".btn").attr('class','item c_grey');
		   $("#"+id).attr('class','btn');
		   $("#fxSelect").val($("#"+id).attr('data-id'));
    	 
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

