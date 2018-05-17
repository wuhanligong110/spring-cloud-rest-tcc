/**
 * 表单提交调用微信支付
 */
function wxpay(productOrderData){
	//加载微信支付组件
     if (typeof WeixinJSBridge == "undefined"){
           if( document.addEventListener ){  
               document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);  
           }else if (document.attachEvent){  
               document.attachEvent('WeixinJSBridgeReady', onBridgeReady);   
               document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);  
           }  
        }else{
           onBridgeReady(productOrderData);  
        }
}

function onBridgeReady(productOrderData){

       	//1：调用微信统一下单接口 获取预付商品id
		$.ajax({
			type:"post",
			url:"/rest/api/wxpay/prepay",
			async:false,
			timeout:10000,//超时时间设置为10秒；
		    contentType : 'application/json;charset=utf-8',
		    data:JSON.stringify(productOrderData),
		    success: function(returnParams){
		    	var errorMessage = returnParams['errorMessage'];
			    if(errorMessage == '' || errorMessage == null){
			    	var appId = returnParams['appId'];
			    	var timeStamp = returnParams['timeStamp'];
			    	var nonceStr = returnParams['nonceStr'];
			    	var package = returnParams['package'];
			    	var signType = returnParams['signType'];
			    	var paySign = returnParams['paySign'];
			    	console.log("调用微信统一下单接口  appId="+appId+"	 timeStamp="+timeStamp+" nonceStr="+nonceStr+"	package="+package+" signType="+signType+" paySign="+paySign);
			    	var prepayId = returnParams['prepayId'];
			    	if(prepayId == ''){
			    		mui.toast("微信支付失败,请稍后再试",{'verticalAlign':'middle'});
			    	} else {
				        WeixinJSBridge.invoke(  
				           'getBrandWCPayRequest', {
				           "appId" : appId,     //公众号名称，由商户传入
				           "timeStamp": timeStamp, //时间戳，自1970年以来的秒数
				           "nonceStr" : nonceStr, //随机串
				           "package" : package,   //预付商品id 微信格式数据包
				           "signType" : signType,  //微信签名方式
				           "paySign" : paySign   //微信签名
				       		},  
				         
					       function(res){     
					           if(res.err_msg == "get_brand_wcpay_request:ok" ) {  
					                 mui.alert("恭喜您支付成功","支付结果","确定",function(){
					                 	cleanCart();//清空购物车
							   	 	 	window.location.href = '/#homeTab';
					                 },'div')
					           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。   
					       }  
				   		);		    		
			    	}
			    } else {
	                mui.alert(errorMessage,"支付失败","确定",null,'div')	    	
			    }
		    }
		});
}

/**
 * 微信二维码扫码支付
 * @param {Object} productOrderData
 */
function wxpayQRcode(productOrderData){
	
		var btnArray = ['取消', '确定'];
		mui.confirm('<strong style="color:red">扫码付款时请备注收货人姓名</strong> \n\n <img src="/img/20170531/wxsmzfsm.jpg" style="width: 240px;border-radius: 5px;"/>', '微信扫码确认', btnArray, function(e) {
			if (e.index == 1) {
				productOrderData["type"] = 1;
				var total = productOrderData["total"];
		       	//1：调用微信统一下单接口 获取预付商品id
				$.ajax({
					type:"post",
					url:"/rest/api/wxpay/prepay",
					async:false,
					timeout:10000,//超时时间设置为10秒；
				    contentType : 'application/json;charset=utf-8',
				    data:JSON.stringify(productOrderData),
				    success: function(returnParams){
				    	var errorMessage = returnParams['errorMessage'];
					    if(errorMessage == '' || errorMessage == null){
							var backgroundHtml = '';
							backgroundHtml += '<div id="loading_wxqrcode_div" class="mui-backdrop" style="text-align: center;display: table;width: 100%;">';
							backgroundHtml += '<div class="wxqrcodetip"><div><span style="font-size: large;font-weight: bolder;color: #242EB3;">长按二维码</span>选中【识别图中二维码】进行支付</div><p style="color: red;font-size: larger;font-weight: bolder;">本次订单金额为:'+total+'</p></div>';
							backgroundHtml += '<div ><img src="../img/20170531/wxsmzf.jpg" class ="wxqrcode"/></div>';
							backgroundHtml += '<div><button type="button" class="mui-btn mui-btn-success" style="margin-top: 4em;height: 4em;width: 22em;" onclick="closedWxqrcodeDiv()"><strong>扫码支付完成</strong></button></div>';
							backgroundHtml += '</div>';
							console.log(backgroundHtml);
							var background = $(backgroundHtml);
							console.log(background);
							$(document.body).append(background);
							background.height($(window).height());
	
					    } else {
			                mui.alert(errorMessage,"支付失败","确定",null,'div'); 	
					    }
				    }
				});
			} else {
				console.log("微信扫码支付取消");
			}
		})
}


/**
 * 支付宝支付
 * @param {Object} productOrderData
 */
function alipay(productOrderData){
	
		productOrderData["type"] = 1; //无需生成微信预支付订单
		var total = productOrderData["total"];
	   	//1：调用统一下单接口 获取预付订单id
		$.ajax({
			type:"post",
			url:"/rest/api/wxpay/prepay",
			async:false,
			timeout:10000,//超时时间设置为10秒；
		    contentType : 'application/json;charset=utf-8',
		    data:JSON.stringify(productOrderData),
		    success: function(returnParams){
		    	var errorMessage = returnParams['errorMessage'];
			    if(errorMessage == '' || errorMessage == null){
					var payOrderId = returnParams['payOrderId'];//预支付订单id
					var payOrderSign = returnParams['payOrderSign'];//预支付订单id
					window.location.href = "system/aliPayTips.html?payOrderId="+payOrderId+"&payOrderSign="+payOrderSign+"&hideHeadLeft=0";
			    } else {
	                mui.alert(errorMessage,"支付失败","确定",null,'div'); 	
			    }
		    }
		});
}

/**
 * 积分兑换支付
 * @param {Object} productOrderData
 */
function jfdhpay(productOrderData){
	
		productOrderData["type"] = 1; //无需生成微信预支付订单
		var total = productOrderData["total"];
	   	//1：调用统一下单接口 获取预付订单id
		$.ajax({
			type:"post",
			url:"/rest/api/wxpay/codeConvert",
			async:false,
			timeout:10000,//超时时间设置为10秒；
		    contentType : 'application/json;charset=utf-8',
		    data:JSON.stringify(productOrderData),
		    success: function(returnMsg){
			    if(returnMsg == 'success'){
	                 mui.alert("恭喜您兑换成功","支付结果","确定",function(){
	                 	cleanCart();//清空购物车
			   	 	 	window.location.href = '/#homeTab';
	                 },'div')
			    } else {
	                mui.alert(returnMsg,"兑换失败","确定",null,'div'); 	
			    }
		    }
		});
}

/**
 * 关闭扫码div
 */
function closedWxqrcodeDiv(){
	mui.alert("<strong>支付成功后请等待确认</strong>","支付结束","确定",function(){
		$("#loading_wxqrcode_div", document.body).remove();
	},'div');
}
