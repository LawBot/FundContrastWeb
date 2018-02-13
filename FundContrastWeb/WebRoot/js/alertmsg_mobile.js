//弹框插件
var msg_timer;
;(function($){
	$.extend({
		alertMsg : function(options){
			var closeMsg = function(hidecallback){
		    	$('.msg-cont').animate({'top':'0'},200)
	   			$('body .alertMsg').remove();
	   			window.clearTimeout(msg_timer);
	   			$('head link#alertmsg').remove();
	   			if(hidecallback){
	   				hidecallback();//调用户传进来的方法
	   			}
	   			
		    };
			//参数：title提示标题、content提示内容、hidespeed是否自动隐藏(毫秒数 or false),默认不自动隐藏、alertType弹出框类型（alert/confirm），
			//默认alert、confirmSure为confirm模式下点击确定后执行的回调、hidecallback为自动隐藏模式下隐藏弹框后执行的回调
			var defaults = {
				title:'tips',
				content:'this is a tips',
				hidespeed:'',
				alertType:'alert',
				confirmSure:function(){},//confirm模式下，点击确定后执行的回调
				hidecallback:function(){}//自动隐藏模式下，自动隐藏弹框后执行的回调
			}
			var _options = $.extend({},defaults,options);
			
		    var closeMsg_sure = function(){
		    	closeMsg();
		    	if(_options.alertType=='confirm'){
		    		_options.confirmSure();//点确定之后，调用用户传进来的回调函数
		    	}
		    	
		    }
			//动态加载css
			var head = document.getElementsByTagName('HEAD').item(0);
			var style = document.createElement('link');
			style.href = './css/alertmsg_mobile.css';
			style.rel = 'stylesheet';
			style.type = 'text/css';
			style.setAttribute('id','alertmsg')
			head.appendChild(style);
			//动态加载html
			var str = '<div class="alertMsg"><div class="msg-cont">';
			str+='<div class="msg-box">';
    		str+='<div class="msg-tit">'+_options.title+'</div>';
    		str+='<div class="msg-body">';
    		str+='<div><img src="../images/icon/alert-tip-img.png" width="20" height="20"></div>'
    		str+='<p>'+_options.content+'</p>';
    		if(_options.alertType=='confirm'){//confirm模式增加取消按钮
    			str+='<div class="ok"><span class="sure-close">confirm</span> <span class="cancel-close">cancel</span></div>';
    		}else{
    			str+='<div class="ok"><span class="sure-close">confirm</span></div>';
    		}
    		str+='</div>';
			str+='</div>';
			str+='</div></div>';
    		$('body').append(str);

    		$('.msg-cont').animate({'top':'24%'},200);
    		//$('.msg-cont').css('margin-left',($('.msg-cont').width()/2)*(-1)+'px');
    		//是否自动隐藏
    		if(_options.hidespeed!=''){
    			msg_timer = window.setTimeout(function(){closeMsg(_options.hidecallback());},_options.hidespeed)
    		}
    		
			$('.sure-close').click(function(){
				closeMsg_sure();
			})
		    $('.cancel-close').click(function(){
				closeMsg();
			})
    		return this;
		},
	})
})(jQuery)