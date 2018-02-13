$('.log-reg-form .input-group input,.search-input input').blur(function(){	
	$(this).parent().find('.clear-text').hide();
}).focus(function(){
	if($(this).val()!=''){
		$(this).parent().find('.clear-text').show();
	}
})
$('.log-reg-form .input-group .clear-text,.search-input .clear-text').mousedown(function(e){
	$(this).parent().find('input').val('');
	var that = $(this);
	window.setTimeout (function(){that.parent().find('input').focus();},100);//ios safari里focus不生效，加上timeout解决
	that.parent().find('input').mousedown();
	$(this).hide();
	$(this).parent().parent().find('.getcode').hide();//隐藏修改邮箱里get code按钮
	$('.btn-submit input').attr('disabled','disabled');
})
$('.log-reg-form .input-group input,.search-input input').keyup(function(e){
	if($(this).val()!=''){
		$(this).parent().find('.clear-text').show();
		$(this).parent().parent().find('.getcode').show();//显示修改邮箱里get code按钮
	}else{
		$(this).parent().find('.clear-text').hide();
		$(this).parent().parent().find('.getcode').hide();//隐藏修改邮箱里get code按钮
	}
	var num=0;
	var that = $('.log-reg-form .input-group input');
	that.each(function(i,e){
		if($.trim($(e).val())==''){
			$('.btn-submit input').attr('disabled','disabled');
			return false;
		}else{
			num++;
		}
		if(num==that.length){
			$('.btn-submit input').removeAttr('disabled');
		}
	})
})
function checkLoginForm(){
	//后台查询，返回message
//	$.alertMsg({
//		alertType:'alert',
//		hidespeed:'2000',
//		content:'User name or password is incorrect'
//	})
	login();
	return false;
}
function checkRegisteForm(){
	var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;//邮箱正则表达式
	if(!emailReg.test($('#reg-email').val())){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:'Incorrect email format'
				
		})
		return false;
	}
	if($('#reg-password').val().length<6){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:'The password must be above 6'
		})
		return false;
	}
	if($('#reg-password').val()!=$('#reg-repassword').val()){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:"The two passwords don't match"
		})
		return false;
	}
	if(!$('#agreed-btn').prop('checked')){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:"Please read the terms of service"
		})
		return false;
	}
	register();
//	location.href="./login.html";
}
//修改密码提交事件
function checkChangePwdForm(){
	if($('#newpwd').val().length<6){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:'The password must be above 6'
		})
		return false;
	}
	if($('#repwd').val()!=$('#newpwd').val()){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:"The two passwords don't match"
		})
		return false;
	}
	
	location.href="./account_info.html";
			//return true;
		
	
}
//修改邮箱，获取验证码时验证邮箱是否有效
function checkEmail(){
	var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;//邮箱正则表达式
	if(!emailReg.test($('#newemail').val())){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:'Incorrect email format'
		})
	}else{
		var num = 5;
		$('#changeEmail .getcode').text(num+'s Retry');
		//发送消息提示，可能成功可能失败
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:'Verification code has been sent'
		})
		var tim = window.setInterval(function(){
			if(num>0){
				num--;
				$('#changeEmail .getcode').text(num+'s Retry');
			}else{
				num=60;
				$('#changeEmail .getcode').empty().append('<a href="javascript:void(0)" onclick="checkEmail()">Get Code</a>');
				clearInterval(tim);
			}

		},1000)
		
	}
}
//修改邮箱提交
function checkChangeEmailForm(){
	var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;//邮箱正则表达式
	if(!emailReg.test($('#newemail').val())){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:'Incorrect email format'
		})
		return false;
	}
	if($('#emailcode').val().length!=4){
		$.alertMsg({
			alertType:'alert',
			hidespeed:'2000',
			content:'Length of verification code is not correct'
		})
		return false;
	}
	
	location.href="./account_info.html";
		
	
}

//修改真实姓名提交
function changeRealName(){

	location.href="./account_info.html";
		
	
}