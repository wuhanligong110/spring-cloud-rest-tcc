$(function(){	
		var payOrderId = getRequestUrlParam('payOrderId');//订单号
		var payOrderSign = getRequestUrlParam('payOrderSign');//订单签名
		
		//由于微信屏蔽支付宝支付,使用非微信浏览器打开
		// 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器
		var useragent = navigator.userAgent;
		if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
			window.location.href = "/rest/api/aliPay/webPay?payOrderId="+payOrderId+"&payOrderSign="+payOrderSign;
		} else {
			$("#alipayTipsImg").html('<img width="100%" src="../img/pay/alipayTips.jpg" />');
			$("#alipayTipsButton").html('<button type="button" class="mui-btn mui-btn-warning" onclick="payComplete()" style="padding: 15px;width: 80%;">支付完成</button>');
		}
});

//支付完成按钮点击
function payComplete(){
	var btnArray = ['取消', '确定'];
	mui.confirm('确定已经支付完成么', '支付结果确认', btnArray, function(e) {
		if (e.index == 1) {
			window.location.href = "/#personTab";
		} else {
			console.log("支付取消");
		}	
	});
}
