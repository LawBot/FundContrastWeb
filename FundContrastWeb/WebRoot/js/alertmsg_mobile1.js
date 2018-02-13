
//弹框插件
var msg_timer;
;(function($){
	$.fn.alertMsg = function (options) {
        this.each(function () {
            new alertMsg(this);
        });
    };
	var alertMsg = function (options) {	
		//四个参数：提示标题、提示内容、是否自动隐藏(毫秒数 or false),默认不自动隐藏、弹出框类型（alert/confirm），默认alert
		var defaults = {
			title:'tips',
			content:'this is a tips',
			hidespeed:'',
			alertType:'alert',
			boxWidth:'280px',
			boxHeight:'auto',
			confirmSure:function(){}//回调
		}
		this._options = $.extend({},defaults,options);
		this.alertM();	
		(function (alertMsg) {//自执行
		  $('.sure-close').click(function () { alertMsg.closeMsg_sure(); })
		  $('.cancel-close').click(function () { alertMsg.closeMsg(); })
		} (this))
	};
	alertMsg.prototype={
		init:function(){
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
    		str+='<div class="msg-tit">'+this._options.title+'</div>';
    		str+='<div class="msg-body">';
    		str+='<p>'+this._options.content+'</p>';
    		if(this._options.alertType=='confirm'){//confirm模式增加取消按钮
    			str+='<div class="ok"><span class="sure-close">confirm</span> <span class="cancel-close">cancel</span></div>';
    		}else{
    			str+='<div class="ok"><span class="sure-close">confirm</span></div>';
    		}
    		str+='</div>';
			str+='</div>';
			str+='</div></div>';
    		$('body').append(str);
		},
		closeMsg:function(){//关闭事件
			$('.msg-cont').anim({'top':'0'},0.8)
   			$('body .alertMsg').remove();
   			window.clearTimeout(msg_timer);
   			$('head link#alertmsg').remove();
		},
		closeMsg_sure : function(){//确定事件，调用户传进来的回调函数
			this.closeMsg();
	    	if(this._options.alertType=='confirm'){
	    		this._options.confirmSure();//点确定之后，调用用户传进来的回调函数
	    	}
		},
		alertM:function(){
			this.init();
    		$('.msg-cont').anim({'top':'24%'},0.8,'fast');
    		//是否自动隐藏
    		if(this._options.hidespeed!=''){
    			msg_timer = window.setTimeout(function(){this.closeMsg();},this._options.hidespeed)
    		}

		}
		
	};

})(Zepto)