$(function(){
	
	var initPage = function(){
		//校验微信浏览器
		check_wx_b();
		//设置内容滚动区高度=可见区高度-底部导航栏高度50px
		$("#tabbar-with-homeTab,#tabbar-with-cartTab,#tabbar-with-usercenter,#tabbar-with-personTab,#tabbar-with-infoTab").css('height',(document.documentElement.clientHeight - 50)+"px");
		//设置购物车为空居中偏上 购物车为空高度200px
		$("#tabbar-with-cart-null").css('margin-top',(document.documentElement.clientHeight - 400)/2+"px");
		//启用右滑关闭功能
		mui.init({swipeBack:true });
	}

	//初始化首页页面数据
	var initIndexData = function(){
		
		console.log('初始化首页页面数据');
		//加载页面数据
		$.ajax({
		   type: "POST",
		   url: "/rest/api/wxpublic/index",
		   async: false,
		   timeout:10000,//超时时间设置为10秒；
		   data:{code:getRequestUrlParam('code')},
		   success: function(indexResponse){

		   }
		});
		
		//首页热门推荐酒
		$.ajax({
		   type: "POST",
		   url: "/rest/api/wxpublic/productCategotyList",
		   async: false,
		   timeout:10000,
		   success: function(bizProductDetailList){  	
			    var html = '';
			    if(bizProductDetailList != null && bizProductDetailList != ''){
			    	for(var index in bizProductDetailList){
			        	html += '<div class="mui-row sy-rmtjcp">';
				        html += '<a href="/product/productDetail.html?productId='+bizProductDetailList[index].productId+'">';
					    html += '<div class="mui-row">';
					    html += '<div class="mui-col-sm-3 mui-col-xs-3">';
					    html += '<img src="'+bizProductDetailList[index].briefImg+'" class="sy-rmtjcp-tp">';
					    html += '</div>';
					    html += '<div class="mui-col-sm-9 mui-col-xs-9 sy-rmtjcp-nr">';
					    html += '<div class="mui-row">';
					    html += '<div class="mui-col-sm-12 mui-col-xs-12 sy-rmtjcp-nr-bt">'+bizProductDetailList[index].productName+'</div>';
					    html += '<div class="mui-col-sm-12 mui-col-xs-12 sy-rmtjcp-nr-fbt">'+bizProductDetailList[index].subName+'</div>';
					    html += '<div class="mui-col-sm-12 mui-col-xs-12 ">';
					    html += '<div class="mui-row">';
					    
					    if(bizProductDetailList[index].tag != null && bizProductDetailList[index].tag != ""){
					    	var tags = bizProductDetailList[index].tag.split("#");
					    	for(var indext in tags){
					    		html += '<div class="mui-col-sm-3 mui-col-xs-3 sy-rmtjcp-nr-bq"><span>'+tags[indext]+'</span></div>';
					    	}
					    }
					    html += '</div>';
					    html += '</div>';
					    html += '<div class="mui-col-sm-12 mui-col-xs-12 sy-rmtjcp-nr-jgkc">';
					    html += '<div class="mui-row">';
					    html += '<div class="mui-col-sm-4 mui-col-xs-4 sy-rmtjcp-nr-jgkc-jg">';
					    html += '<span>¥</span>';
					    html += '<span class="sy-rmtjcp-nr-jgkc-jgsz">'+formatData(bizProductDetailList[index].price)+'</span>';
					    html += '</div>';
					    html += '<div class="mui-col-sm-5 mui-col-xs-5 mui-text-center">';
					    html += '<span class="sy-rmtjcp-nr-spxx">原价:¥'+formatData(bizProductDetailList[index].prePrice)+'</span>';
					    html += '</div>';
					    html += '<div class="mui-col-sm-3 mui-col-xs-3">';
					    html += '<span class="sy-rmtjcp-nr-spxx">已售'+bizProductDetailList[index].havaInventory+'</span>';
					    html += '</div>';
					    html += '</div>';
					    html += '</div>';
					    html += '</div>';
					    html += '</div>';			        			
					    html += '</div>';
				        html += '</a>';		        	    
			        	html += '</div>';
			    	}
			    }
			    $("#sy-rmtjwz-sp").html(html);
		   }
		});	
		
		var ui = {
			sliderList: document.querySelector('#indexSlider'),
		};
		
		var bindSliderList = function() {
			$.ajax({
			   type: "POST",
			   url: "/rest/api/wxpublic/advs",
			   async: false,
			   timeout:10000,//超时时间设置为10秒；
			   data:{advPlacement:'app_home_page'},
			   success: function(result){
//				   	console.log(JSON.stringify(result));
				   	var record = result.data.datas;
					//绑定数据:
					ui.sliderList.innerHTML = template('banner-template', {
						"record": record
					});			
			   },
			   error:function(e){
			   	console.log("出错了！"+e);
			   }
			});
		};
		bindSliderList();
	}
	
	//从cookie中获取订单  初始化购物车
	var initcartData = function(){
		console.log('初始化购物车数据');
	    shoppingCatShow(getCookie('productOrderList') == ''? new Array():JSON.parse(getCookie('productOrderList')));			
	}

	//初始化个人中心页面数据
	var initUserCenterData = function(){
		
		console.log('初始化个人中心页面数据');		
		//加载页面数据
		$.ajax({
		   type: "POST",
		   url: "/rest/api/wxpublic/userCenter",
		   async: false,
		   timeout:10000,//超时时间设置为10秒；
		   success: function(userCenterResp){
			     var bizCustomer = userCenterResp.bizCustomer;
			     var bizCustomerParent = userCenterResp.bizCustomerParent;
			     
			     //校验是否是禁止用户
			     //check_customer_status(bizCustomer.status);
			     		     
			     //微信头像
			     $("#wechatHeadimgurl").attr("src",bizCustomer.wechatHeadimgurl);
			     //微信昵称
			     $("#wechatNickname").html(bizCustomer.wechatNickname); 
			     //用户ID
			     $("#consumerId").html("会员ID:"+bizCustomer.consumerId);
			     //会员等级
			     var vipLevel = "客户";
			     if(bizCustomer.vipLevel == 0){
			     	vipLevel = "客户";
			     }else if(bizCustomer.vipLevel == 1){
			     	vipLevel = "VIP导购";
			     }else if(bizCustomer.vipLevel == 2){
			     	vipLevel = "销售总监";
			     }
			     $("#vipLevel").html("会员等级:"+vipLevel);//
			     
			     if(bizCustomer.address != null && bizCustomer.address != ""){
			     	$("#ad-username").html(bizCustomer.username);
			     	$("#ad-phone").html(bizCustomer.phone);
			     	$("#ad-address").html(bizCustomer.area+bizCustomer.address);
			     	$("#tabbarcart-yjdz").show();
			     	$("#tabbarcart-tjyjdz").hide();
			     }
			     
			     //活动推广
		     	 $("#myRcode").attr("href","person/my-rcode.html?openId="+userCenterResp.openId+"&nickName="+encodeURI(bizCustomer.wechatNickname)+"&wechatImg="+bizCustomer.wechatHeadimgurl);
		     	 //我的积分
		     	 $("#myPoints").attr("href","person/my-points.html?txjf="+bizCustomer.cashCredit+"&xfjf="+bizCustomer.mshopCredit);
		     	 //账号绑定
		     	 $("#accountBand").attr("href","person/account-band.html?payeeBand="+userCenterResp.payeeBand+"&addressBand="+userCenterResp.addressBand);
			     		    
		   }
		});
	}
	
	//模拟点击
	var monitabNameClick = function(tabName){
		mui.trigger($("#"+tabName)[0], 'tap');//模拟点击
	}
	
	var  moniClick = function(){
		//公众号外页面直接跳转个人中心-模拟点击
		if(getRequestUrlCode()=='personTab'){
		    monitabNameClick('personTab');
		} else if(getRequestUrlCode()=='cartTab'){
		    monitabNameClick('cartTab');
		} else if(getRequestUrlCode()=='infoTab'){
		    monitabNameClick('infoTab');
		} else {
			monitabNameClick('homeTab');
		}		
	}
	
	initPage();
	initIndexData();
	initcartData();
	initUserCenterData();
	
	mui("#nav-div").on('tap','a.mui-tab-item',function(){
		var tapId = $(this).attr("id");
//		console.log($(this));
//		console.log(tapId);
		$(".mui-bar-tab>.mui-tab-item.mui-active").removeClass('mui-active');
		$(".mui-control-content.mui-active").removeClass('mui-active');
		$(this).addClass('mui-active');
	    $("#tabbar-with-"+tapId).addClass("mui-active");
		history.pushState("","",window.location.href.split("#")[0]+"#"+tapId);
		if(tapId == 'homeTab'){
			//滚动栏开始滚动
			mui("#indexSlider").slider({interval: 2000});
		}
	});
	
	moniClick();
		
	//点击添加地址跳转到添加地址页面
	$("#tabbarcart-tjdz").click(function(){
		window.location.href = "person/address-add.html";
	});
	
	//点击跳转到订单页面
	$("#allOrders").click(function(){
		window.location.href = "person/my-orders.html";
	});
	
	//点击跳转到未发货订单页面
	$("#payedOrders").click(function(){
		window.location.href = "person/my-orders.html?isSend=0";
	});
	
	//点击跳转到已发货订单页面
	$("#sendedOrders").click(function(){
		window.location.href = "person/my-orders.html?isSend=1";
	});
	
	//购物车为空点击去逛逛跳转首页
	$("#tabbar-cart-null-bt").click(function(){
		monitabNameClick('homeTab');//模拟点击
	});
	
	//我的粉丝点击跳转
	$("#personTab-wdfs").click(function(){
		window.location.href = "person/my-team.html";
	});
	
	//最终支付确定
	$("#payBtnFinal").click(function(){	
//		commonToast("系统试运营阶段，暂不支持支付");
//		return false;
		var totalPrice = eval($('#totalPrice').html());
		if(totalPrice<=0){
			commonToast("亲,购物车为空,快去选择产品吧");
			return false;
		}

		//微信5.0版本后才加入微信支付模块，低版本用户调用微信支付功能将无效
		var wechatInfo = navigator.userAgent.match(/MicroMessenger\/([\d\.]+)/i) ;
		if( !wechatInfo ) {
		    mui.alert("本页面仅支持微信","提示","确定",null)
		} else if ( wechatInfo[1] < "5.0" ) {
		    mui.alert("请使用微信5.0以上版本","提示","确定",null)
		} else {
			//地址校验
			if($("#ad-username").html() == "...." || $("#ad-username").html() == "" ||
				$("#ad-phone").html() == "...." || $("#ad-phone").html() == "" ||
				$("#ad-address").html() == "...." || $("#ad-address").html() == "" ){
				commonToast("收货地址为空,亲,快去录入地址吧");
				return false;
			}
			
			var payradioValue = $("input[type='radio'][name='payradio']:checked").val();
			console.log(payradioValue);
			if(payradioValue == '' || payradioValue == undefined){
				commonToast("请勾选支付方式");
				return false;
			}
			commonToast("金额:&yen;"+(totalPrice)+",正在支付");
					
			//拼接数据报文
			var productOrderData = {};
			productOrderData["productOrderList"] = getCookie('productOrderList') == ''? new Array():JSON.parse(getCookie('productOrderList'));
			productOrderData["total"] = totalPrice;
			
			//调用不同的接口进行支付
			if(payradioValue == "wxpay"){//微信支付
				console.log("微信支付API");
				wxpay(productOrderData);
			}
//			} else if(payradioValue == "alipay"){//支付宝
//				console.log("支付宝支付API");
//				alipay(productOrderData);
//			} else if(payradioValue == "wxsmpay"){//微信扫码支付
//				console.log("微信扫码支付API");
//				wxpayQRcode(productOrderData);			
//			} else if(payradioValue == "jfdhpay"){//领取积分兑换
//				console.log("领取积分兑换支付API");
//				jfdhpay(productOrderData);
//			}
		}
	});

});

/**
 * 校验是否禁止用户
 */
function check_customer_status(status){
	if (status == 1) {
	    // 这里警告框会阻塞当前页面继续加载
	    alert('亲,访问失败请联系管理员！');
	    // 以下代码是用javascript强行关闭当前页面
	    var opened = window.open('about:blank', '_self');
	    opened.opener = null;
	    opened.close();
	}
}

/**
 * 弹出窗口
 */
function commonToast(msg){
	mui.toast(msg,{'verticalAlign':'middle'});
}