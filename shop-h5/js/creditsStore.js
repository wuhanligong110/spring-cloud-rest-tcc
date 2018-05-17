/**
 * 表单提交调用微信支付
 */
function wxpay_mshopConvert(productOrderData){
	//加载微信支付组件
     if (typeof WeixinJSBridge == "undefined"){
           if( document.addEventListener ){  
               document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);  
           }else if (document.attachEvent){  
               document.attachEvent('WeixinJSBridgeReady', onBridgeReady);   
               document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);  
           }  
        }else{  
           onBridgeReady_mshopConvert(productOrderData);  
        }
}

function onBridgeReady_mshopConvert(productOrderData){

       	//1：调用微信统一下单接口 获取预付商品id
		$.ajax({
			type:"post",
			url:"/rest/api/wxpay/mshopConvert",
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
					                 mui.alert("恭喜您兑换成功","兑换结果","确定",function(){
							   	 	 	location.reload();
					                 },'div')
					           }// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。   
					       }
				   		);
			    	}
			    } else {
	                mui.alert(errorMessage,"支付失败","确定",null,'div')	    	
			    }
		    }
		});
}