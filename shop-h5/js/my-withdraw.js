$(function(){
	mui.init({
		swipeBack:true //启用右滑关闭功能
	});
	
	var accountBand;
		
	//加载页面数据
	$.ajax({
	   type: "POST",
	   url: "/rest/api/wxpublic/getBizCustomer",
	   async:false,
	   success: function(bizCustomer){
	     //可领取积分
	     $("#cashCredit").html(formatData(bizCustomer.cashCredit));
	     //用户id
	     $("#consumerId").val(bizCustomer.consumerId);
	     //微信openid
	     $("#wechatOpenid").val(bizCustomer.wechatOpenid);	   
	     
	     accountBand = bizCustomer.payeeAccount;
	   }
	});
	
	$("#withdrawAll").click(function(){
		var cashCredit = eval($("#cashCredit").html());
		$("#withdrawNumber").val(cashCredit);
	});
	
	//点击领取按钮
	$("#withdrawButton").click(function(){
		
		if(!accountBand){
			var btnArray = ['否', '是'];
            mui.confirm('您还未进行提现绑定，确认前往绑定？', '绑卡确认', btnArray, function(e) {
                if (e.index == 1) {
                	window.location.href = 'payee-band.html';	
                } else {
                    return;
                }
            });
		} else {
	//		mui.toast("系统试运营阶段，暂不支持领取",{'verticalAlign':'middle'});
	//		return false;
			var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			//获取可领取积分
			var cashCredit = eval($("#cashCredit").html());
			//领取积分
			if($("#withdrawNumber").val() == ""){
				mui.toast("请输入领取积分",{'verticalAlign':'middle'});
			} else {
				var withdrawNumber = eval($("#withdrawNumber").val());
				if(!reg.test(withdrawNumber)){
					mui.toast("请输入正确的领取金额",{'verticalAlign':'middle'});
					return false;
				}
				var remainCashCredit = eval(cashCredit-withdrawNumber);
				if(withdrawNumber <= 0){
					mui.toast("领取金额必需大于0",{'verticalAlign':'middle'});
					return false;
				} else if(remainCashCredit < 0){
					mui.toast("超过了可领取积分,无法兑换",{'verticalAlign':'middle'});
					return false;
				} else {
					$("#withdrawNumber").val('');
					//将按钮变成灰色不可点击
					$("#withdrawButton").attr("disabled",true);
					//调用领取接口(发送红包)
					$.ajax({
					   type: "POST",
					   async:false,
					   url: "/rest/api/wxpay/enchashment",
					   data:{checkCashCredit:withdrawNumber},
					   success: function(returnMessage){
					     if(returnMessage == "success"){
			                mui.alert("恭喜您提成功,金额将在稍后转入你所绑定的银行卡中,请耐心等待","领取结果","确定",function(){
								$("#cashCredit").html(remainCashCredit);
			                },'div');
					     } else {
			                mui.alert(returnMessage,"领取结果","确定",null,'div');
					     }
					   },
					  error: function(XMLHttpRequest, textStatus, errorThrown) {
						 	mui.alert("微信领取异常","领取结果","确定",null,'div');
					   },
					  complete: function(XMLHttpRequest, textStatus) {
					 		//将按钮重新变成可点击
							$("#withdrawButton").attr("disabled",false);
					   }
					});
				}		
			}
		}
	});
	
});