/**
 * 校验微信浏览器
 */
function check_wx_b(){
	//禁止非微信浏览器打开
	// 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器
	var useragent = navigator.userAgent;
	if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
	    // 这里警告框会阻塞当前页面继续加载
	    alert('请使用微信访问本页面！');
	    // 以下代码是用javascript强行关闭当前页面
	    var opened = window.open('about:blank', '_self');
	    opened.opener = null;
	    opened.close();
	}
}
// 禁止ajax执行过程中页面的其他操作
$(document).ajaxStart(function() {
	check_wx_b();
	if (navigator.userAgent.match(/MicroMessenger/i) != 'MicroMessenger') {
		return;
	}
	showLoading();
}).ajaxStop(function() {
	hideLoading();
});

// ajax请求开始显示进度窗体
function showLoading() {
	var background = $('<div id="loading_div" class="mui-backdrop"><div class="loader">加载中...</div></div>');
	$(document.body).append(background);
	background.height($(window).height());
}
// ajax请求结束移除进度窗体
function hideLoading() {
	$("#loading_div", document.body).remove();
}

/**
 * 设置cookies
 * @param {Object} cname	名称
 * @param {Object} cvalue	值
 * @param {Object} exdays	时间（天）
 */
function setCookie(cname, cvalue, exdays) {
    setCookieS(cname, cvalue, exdays*24*60*60*1000);
}

/**
 * 设置cookies
 * @param {Object} cname	名称
 * @param {Object} cvalue	值
 * @param {Object} exdays	时间（分钟）
 */
function setCookieMins(cname, cvalue, exMins) {
    setCookieS(cname, cvalue, exMins*60*1000);
}

/**
 * 设置cookies
 * @param {Object} cname	名称
 * @param {Object} cvalue	值
 * @param {Object} exdays	时间（天）
 */
function setCookieS(cname, cvalue, timeMins) {
    var d = new Date();
    d.setTime(d.getTime() + (timeMins));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + escape(cvalue) + ";" + expires + ";path=/";
}

//取回cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return unescape(c.substring(name.length, c.length));
        }
    }
    return "";
}

//删除cookie
function delCookie(name){
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null)
	document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

function subStr(str,number){
	if(str.length > number){
		str = str.substring(0,number) + '...';
	}
	return str;
}
//--------------------js 原生控制 css--------------------
function hasClass(obj, cls) {  
    return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));  
}  
  
function addClass(obj, cls) {  
    if (!this.hasClass(obj, cls)) obj.className += " " + cls;  
}  
  
function removeClass(obj, cls) {  
    if (hasClass(obj, cls)) {  
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');  
        obj.className = obj.className.replace(reg, ' ');  
    }  
}  
  
function toggleClass(obj,cls){  
    if(hasClass(obj,cls)){  
        removeClass(obj, cls);  
    }else{  
        addClass(obj, cls);  
    }  
}

function formatData(number){
	//console.log(number/100.0);
	return (number/100.0).toFixed(2);
}

function formatDataN(number){
//	console.log(number/100);
	return Math.floor(number/100);
}

/**
 * 字符串太长了截取字符串
 * @param {Object} str  字符串
 * @param {Object} num  长度
 */
function compact(str,num){
        if(str.length>(num+3)){
                str =  str.substring(0,num).concat('...');
        }
        return str;
}

/**  
 * 获取指定的URL参数值  
 * URL:http://www.quwan.com/index?name=tyler  
 * 参数：paramName URL参数  
 * 调用方法:getParam("name")  
 * 返回值:tyler  
 */  
function getRequestUrlParam(paramName) {  
    paramValue = "", isFound = !1;  
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {  
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;  
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++  
    }  
    return paramValue == "" && (paramValue = null), paramValue  
}

function getRequestDecodeUrlParam(paramName) {  
    paramValue = "", isFound = !1;  
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {  
        arrSource = decodeURI(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;  
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++  
    }  
    return paramValue == "" && (paramValue = null), paramValue  
}

/**  
 * 获取指定的URL#后面参数值  
 * URL:http://www.quwan.com/index#name  
 * 参数：paramName URL参数  
 */  
function getRequestUrlCode() {
	var requestUrl = window.location.href;
	if(requestUrl.indexOf("#") != -1){
		return requestUrl.split("#")[1];
	} else {
		return "";
	}
}


/**
 * 根据产品id获取产品信息
 */
function getProductDetailByProductId(productId){
	var productDetail = null;
	var productIdArray = new Array();
	productIdArray.push(productId);
	var productDetailList = getProductDetailListByproductIdStr(productIdArray);
	productDetail = productDetailList[0];
	return productDetail;
}

/**
 * 根据产品id获取简介版产品信息并存入cookie
 */
function getProductBriefDetailForCookies(productId){
	var productBriefDetail = null;
	
	if(getCookie("productDetail_"+productId) == ''){
		console.log("【数据库】根据产品id获取产品信息 productId="+productId);
		//加载页面数据
		$.ajax({
		   type: "POST",
		   url: "/rest/api/wxpublic/productBriefDetail",
		   async: false,
		   timeout:10000,//超时时间设置为10秒；
		   data:{"productId":productId},
		   success: function(productDetail){
		   		productBriefDetail = productDetail;
		   		setCookieMins("productDetail_"+productId, JSON.stringify(productDetail), 5);
		   },
		   error: function (XMLHttpRequest, textStatus, errorThrown) {
		   		alert(根据id获取产品信息失败,请联系管理员);
			}
		})
	} else {
		console.log("【缓存】从缓存中获取产品信息 productId="+productId);
		productBriefDetail = JSON.parse(getCookie("productDetail_"+productId));
	}
	return productBriefDetail;
}

/**
 * 根据产品id查询产品列表  多个产品id用","分割
 */
function getProductDetailListByproductIdStr(productIdArray){
	
	var returnBizProductDetailList = null;
	
	//加载页面数据
	$.ajax({
	   type: "POST",
	   url: "/rest/api/wxpublic/productList",
	   contentType: "application/json; charset=utf-8",
	   async: false,
	   timeout:10000,//超时时间设置为10秒；
	   data:JSON.stringify(productIdArray),
	   success: function(bizProductDetailList){
	   		returnBizProductDetailList = bizProductDetailList;
	   },
	   error: function (XMLHttpRequest, textStatus, errorThrown) {
	   		alert(根据id获取产品信息失败,请联系管理员);
		}
	})
	
	return returnBizProductDetailList;
}

/**
 * 更新购物车
 * productId  产品id
 * orderNumber  数量
 * type 类型  0-增加  1-减少
 * productOrderList 购物车
 */
function updateShoppingCat(productId,orderNumber,type,productOrderList){
	
	if(productOrderList == null){
		productOrderList = getCookie('productOrderList') == ''? new Array():JSON.parse(getCookie('productOrderList'));
	}
	
	if(orderNumber <= 0) return;//无论是增加还是减少 orderNumber必需大于0
	
	//判断产品是否存在
	var ifproductExist = false;
	var existIndex = null;//若存在 记录在数组中的位置
	for(var index in productOrderList){
		if(productOrderList[index].productId == productId){
			ifproductExist = true;
			existIndex = index;
			break;
		}
	}
	
	if(ifproductExist){//如果存在
		if(type == 0 ){//新增
			productOrderList[existIndex].orderNumber = eval(productOrderList[existIndex].orderNumber + orderNumber);
		} else {//减少
			if(eval(productOrderList[existIndex].orderNumber - orderNumber) > 0){
				productOrderList[existIndex].orderNumber = eval(productOrderList[existIndex].orderNumber - orderNumber);
			} else {
				productOrderList.splice(existIndex,1);
			}
		}
	} else {//不存在
		if(type == 0 ){//新增
			productOrderList.push({productId:productId,orderNumber:orderNumber});
		}
	}
	
	setCookie('productOrderList',JSON.stringify(productOrderList),7);//将购物车存入cookie
	//购物车更新后刷新页面
}

/**
 * 将购物车内容显示在页面上
 */
function shoppingCatShow(productOrderList){
	
	//展示购物车
	if(productOrderList.length > 0){
		
		/**
		 * 清除购物车中不存在的产品或者无库存的产品
		 */
		//获取购物车中所有产品的信息并更新
		var productIdArray = new Array();
		for(var indexp in productOrderList){
			productIdArray.push(productOrderList[indexp].productId);
		}
		var productDetailOrderList = getProductDetailListByproductIdStr(productIdArray);
		
		//清除购物车中已售罄产品
		for(var index = productOrderList.length - 1; index >= 0; index--){
			if(productOrderList[index].orderNumber > 0){
				var productDetail = null;//获取产品详情
				for(var indexo in productDetailOrderList){
					if(productOrderList[index].productId == productDetailOrderList[indexo].productId){
						productDetail = productDetailOrderList[indexo];
						break;
					}
				}
				if(productDetail == null){
					console.log('购物车中产品已售罄,清除购物车中该产品,productId='+productOrderList[index].productId);
					productOrderList.splice(index,1);
				}
			}
		}
		
		/**
		 * 展示购物车详情
		 */
		var html = '';
		for(var index in productOrderList){
			var productDetail = getProductBriefDetailForCookies(productOrderList[index].productId);//获取产品详情
			if(productOrderList[index].orderNumber > 0){
				if(productDetail != null){				
				    html += '<div id="productOrder_'+productDetail.productId+'" class="mui-row tcart-tj-ddcp-row">';
				    html += '<div class="mui-col-sm-2 mui-col-xs-2 tcart-tj-ddcp-row-tp">';
				    html += '<img src="'+productDetail.briefImg+'" />';
				    html += '</div>';
				    html += '<div class="mui-col-sm-6 mui-col-xs-6">';
				    html += '<div class="mui-row">';
				    html += '<div class="mui-col-sm-12 mui-col-xs-12 tcart-tj-ddcp-row-cpmc">';
				    html += productDetail.productName;
				    html += '</div>';
				    html += '<div class="mui-col-sm-12 mui-col-xs-12 tcart-tj-ddcp-row-cpjg">';
				    html += '¥'+formatData(productDetail.price);
				    html += '</div>';
				    html += '</div>';
				    html += '</div>';
				    html += '<div class="mui-col-sm-4 mui-col-xs-4 tcart-tj-ddcp-row-numbox ">';
					html += '<div class="mui-numbox" data-numbox-step="1" data-numbox-min="0">';
					html += '<button class="mui-btn mui-numbox-btn-minus" type="button">-</button>';
					html += '<input id="orderNumber_'+productDetail.productId+'" onchange="changeShoppingCatOrderNumber('+productDetail.productId+')" class="mui-numbox-input tcart-tj-ddcp-sp" min="0" type="number" value="'+productOrderList[index].orderNumber+'"/>';
					html += '<button class="mui-btn mui-numbox-btn-plus" type="button">+</button>';
					html += '</div>';	
				    html += '</div>';
				    html += '</div>';	
				}			
			}
		}
		
		$('#tabbarcart-orderList').html(html);//购物车
		mui('div.mui-numbox').numbox();	//初始化numbox	
		calculateShoppingCat(productOrderList);//计算购物车总价格 更新购物车数量
		
		//展示购物车
		$("#tabbar-with-cart-hava").show();
		$("#tabbar-with-cart-null").hide();
	} else {
		//展示空购物车
		$("#tabbar-with-cart-hava").hide();
		$("#tabbar-with-cart-null").show();
	}
}

/**
 * 购物车数量变动
 */
function changeShoppingCatOrderNumber(productId){	
	updateShoppingCatOrderNumber(productId,eval($("#orderNumber_"+productId).val()));//
}

/**
 * 将购物车的产品数量更新成指定数量
 * productId  产品id
 * orderNumber  数量
 */
function updateShoppingCatOrderNumber(productId,orderNumber){
	
//	console.log(productId);
//	console.log(orderNumber);
	if(orderNumber == 0){//购物数量为0时  不展示该产品
		$("#productOrder_"+productId).remove();
	}
		
	var	productOrderList = getCookie('productOrderList') == ''? new Array():JSON.parse(getCookie('productOrderList'));
	
	//判断产品是否存在
	var ifproductExist = false;
	var existIndex = null;//若存在 记录在数组中的位置
	for(var index in productOrderList){
		if(productOrderList[index].productId == productId){
			ifproductExist = true;
			existIndex = index;
			break;
		}
	}
	
	if(ifproductExist){//如果存在
		if(orderNumber != 0 ){//更新orderNumber
			productOrderList[existIndex].orderNumber = orderNumber;
		} else {//若最终更新为0  直接删除该订单
			productOrderList.splice(existIndex,1);
		}		
	}
	
	calculateShoppingCat(productOrderList);//计算购物车总价格 更新购物车数量
}

/**
 * 计算购物车总价格 更新购物车数量
 * productOrderList 产品订单List
 */
function calculateShoppingCat(productOrderList){
	console.log("刷新购物车总数量和总价格")
//	console.log(productOrderList)
	if(productOrderList == null){
		productOrderList = getCookie('productOrderList') == ''? new Array():JSON.parse(getCookie('productOrderList'));
	}
	var totalPrice = 0;
	var productCartItermNmber = 0;
	for(var index in productOrderList){
		if(productOrderList[index].orderNumber > 0){
			var productDetail = getProductBriefDetailForCookies(productOrderList[index].productId);
			totalPrice += formatData(productDetail.price) * productOrderList[index].orderNumber;
			productCartItermNmber += productOrderList[index].orderNumber;
		}
	}
	$('#totalPrice').html(totalPrice < 0?0:totalPrice +'.00');//更新总价格
	$('#productCartItermNmber').html(productCartItermNmber);//更新购物车产品数量
	productCartItermNmber <= 0?$('#productCartItermNmber').hide():$('#productCartItermNmber').show();
	
	if(totalPrice <= 0){
		//展示空购物车
		$("#tabbar-with-cart-hava").hide();
		$("#tabbar-with-cart-null").show();
	} else {
		//展示购物车
		$("#tabbar-with-cart-hava").show();
		$("#tabbar-with-cart-null").hide();
	}
	
	//刷新cookie中订单信息
	setCookie('productOrderList',JSON.stringify(productOrderList),7);//将购物车存入cookie
}


/**
 * 清空购物车
 */
function cleanCart(){
	console.log('清空购物车');
	delCookie('productOrderList');
}

function consumerPhoneChange(obj){
	var pattern = new RegExp("^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$");
	var value = $(obj).val();
	if(!pattern.test(value)){
		$('#consumerErrorMsg').html('手机号码格式不正确');
	} else {
		if($('#consumerErrorMsg').html() != ''){
				$('#consumerErrorMsg').html('');
		}
	}
}

function consumerPasswordChange(obj){
	var pattern = new RegExp("[0-9a-zA-Z]{4,23}");
	var value = $(obj).val();
	if(!pattern.test(value)){
		$('#consumerErrorMsg').html('密码为6-20位的字母或者数字');
	} else {
		if($('#consumerErrorMsg').html() != ''){
				$('#consumerErrorMsg').html('');
		}
	}
}
