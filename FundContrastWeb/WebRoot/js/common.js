$(function(){
	$('#user-account').width($(window).width()*0.7);
})
//弹出公共左侧个人信息事件
$('.search-box .person-info').click(function(){
	if (parseInt($('#user-account').css('left').replace('px'))==0) {
		$('#user-account').css('left','-3000px');//隐藏菜单
		$('.mask-account').hide();
		$('body').removeClass('opened');
	}else{
		$('#user-account').css('left',0);//显示菜单
		$('.mask-account').show();
		$('body').addClass('opened');
	}
})
//公共左侧个人信息遮罩点击事件
$('.mask-account').click(function(){
	$('#user-account').css('left','-3000px');
	$(this).hide();
	
})
//公共左侧个人信息块阻止冒泡
$('#user-account').click(function(e){
	e = e || window.event;
    if (e.stopPropagation) { 
        e.stopPropagation();  
    } else {
        e.cancelBubble = true; 
    }
})
//复选框样式
$('.span-icon-check').click(function(){
	if($(this).find('i').attr('class').indexOf('empty')>0){
		$(this).find('i').attr('class','icon-check');
	}else{
		$(this).find('i').attr('class','icon-check-empty');
	}
})
$('.span-icon-radio').click(function(){
	$(this).find('i').attr('class','icon-circle');
})

//获取地址栏参数值
function getUrlName(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
};

//获取本地存储地址
function getLocalAddress(){
	if(window.localStorage){
		var addressList = localStorage.getItem('address');//顺序：firseName,lastName,mobilePhone,telephone,countryName,streetAddress,city,state,zip
		if(addressList&&addressList.length>0){//如果存在
			var addresslist=eval('('+addressList+')');//json转数组
			return addresslist;
		}else{
			return false;
		}
	}else{
		alert('您的浏览器暂不支持localStorage！');
	}
}