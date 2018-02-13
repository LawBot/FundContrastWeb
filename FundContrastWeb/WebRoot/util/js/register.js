//注册
	function register(){
		  $.ajax({
		 	    type: "post",
                url: proxyUrl+"member/regist.do",
                data: "rePassword="+$("#reg-repassword").val()+"&email="+$("#reg-email").val()+"&userName="+$("#reg-username").val()+"&password="+$("#reg-password").val()+"&time="+(new Date()).getTime(),
                dataType: "json",
                success: function(data) {
                	if(data.content=='B00000010'){
                		$.alertMsg({
                    		alertType:'alert',
                    		hidespeed:'2000',
                    		content:'email is aready exist'
                    	})
                    	return false;
                	}
                	if(data.content=='B0000001'){
                		$.alertMsg({
                    		alertType:'alert',
                    		hidespeed:'2000',
                    		content:'User name is aready exist'
                    	})
                    	return false;
                	}
                	
                	if(data.content=='B0000004'){
                		$.alertMsg({
                    		alertType:'alert',
                    		hidespeed:'2000',
                    		content:'Password is error'
                    	})
                    	return false;
                	}
                	
                	if(data.result==0){
                		console.log("注册成功");   
//                		localStorage.oldUr="member/index.html";
                		//注册成功自动登录
                		 $.ajax({
						 	    type: "post",
				                url: proxyUrl+"member/login.do",
				                data: "userName="+$("#reg-username").val()+"&password="+$("#reg-repassword").val()+"&time="+(new Date()).getTime(),
				                dataType: "json",
				                success: function(data) {
				                	if(data.result==0){
				                		console.log("登录成功");
				                		sessionStorage.userId = data.content.userId;
				                		if(localStorage.oldUrl && localStorage.oldUrl!="null"){
				                			location.href = localStorage.oldUrl;
				                		}else{
				                			location.href = "index.html";
				                		}
				                	}else{
				                		console.log("登录失败");	
				                	}
				                },error: function(xhr, type, errorThrown) {
				                		console.log("登录异常");
				                }
						 })
                	}else{
                		if(data.content=="B00000010"){
                			$(".input-box").eq(1).append('<div class="validation-advice  Emaila" id="advice-required-entry-email_address">Email address has been used</div>');
                		}
                			//wins.dd4win("Prompt","login has failed");
                	}
                },error: function(xhr, type, errorThrown) {
                		console.log("注册用户异常");
                }
		 })
	}