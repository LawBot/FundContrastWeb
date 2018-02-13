//登录
	function login(){
		 $.ajax({
		 	    type: "post",
                url: proxyUrl +"member/login.do",
                data: "userName="+$("#login_username").val()+"&password="+$("#login_password").val()+"&time="+(new Date()).getTime(),
                dataType: "json",
                success: function(data) {
                	if(data.content=='B0000003'){
                		$.alertMsg({
                    		alertType:'alert',
                    		hidespeed:'2000',
                    		content:'User name or password is not exist'
                    	})
                    	return false;
                	}
                	
                	if(data.content=='B0000004'){
                		$.alertMsg({
                    		alertType:'alert',
                    		hidespeed:'2000',
                    		content:'User name or password is incorrect'
                    	})
                    	return false;
                	}
                	
                	if(data.result==0){
                		if(data.originalUrl!=undefined){
            				location.href = data.originalUrl;
            			}else{
            				location.href = "index.html";
            			}
                	}else{
//                		$("#tip").html("Login failed");
                		alert("Login failed");
                	}
                },error: function(xhr, type, errorThrown) {
                		console.log("登录异常");
                }
		 })
	}