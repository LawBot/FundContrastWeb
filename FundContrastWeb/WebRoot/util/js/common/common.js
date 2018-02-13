proxyUrl = "/proxy/";


/* @ session   校验
		 *  1.进入需要登录的页面监测未登录跳转 login.html	
		 *  3.session  过期访问敏感页面 自动跳转到 login.html
		 * */
		function getSession(type,fn){
				 var d = new Date();
				 var timezone = -d.getTimezoneOffset()/60;
				 conf.$.ajax({
			 	    type: "get",
			 	    data:{timezone:timezone},
	                url: conf.itsUrl+"getSession.do",	              
	                dataType: "JSON",
	                async:false,
	                success: function(data) {
	                	    if(JSON.stringify(data)!="{}"){
	                	    	if(data.content.userId){
	                	    		u.setUserData(data.content);
	                	    		fn();
	                	    	}	                     	   
	                     	}else{
	                     		if(type){
	                	    			switch(type){
	                	    				case 1:
	                	    				   if(!localStorage.oldUrl){
	                	    				   		localStorage.oldUrl = location.href;
	                	    				   }
	                	    				 	if(common.GetQueryString("showView")==0){
	                	    				 		parent.location.href=conf.hrefUrl+"login.html";
	                	    				 	}else{
	                	    				 		location.href=conf.hrefUrl+"login.html";
	                	    				 	}	                	    				  
	                	    			    break;                	    		   
	                	    				default:
	                	    			//	 window.open(conf.hrefUrl+"404.html");
	                	    			}
	                	    		}
	                     	}
	                },error: function(xhr, type, errorThrown) {
	                		console.log("getSession请求异常");
	                }
			 	})
			
		}