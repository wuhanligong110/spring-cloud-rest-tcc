/**
 * 演示程序当前的 “注册/登录” 等操作，是基于 “本地存储” 完成的
 * 当您要参考这个演示程序进行相关 app 的开发时，
 * 请注意将相关方法调整成 “基于服务端Service” 的实现。
 **/

Date.prototype.format = function(format) {
   var date = {
          "M+": this.getMonth() + 1,
          "d+": this.getDate(),
          "h+": this.getHours(),
          "m+": this.getMinutes(),
          "s+": this.getSeconds(),
          "q+": Math.floor((this.getMonth() + 3) / 3),
          "S+": this.getMilliseconds()
   };
   if (/(y+)/i.test(format)) {
          format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
   }
   for (var k in date) {
          if (new RegExp("(" + k + ")").test(format)) {
                 format = format.replace(RegExp.$1, RegExp.$1.length == 1
                        ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
          }
   }
   return format;
};

(function($, owner) {
	/**
	 * 用户登录
	 **/
	owner.login = function(loginInfo, callback) {
		callback = callback || $.noop;
		loginInfo = loginInfo || {};
		loginInfo.mobile = loginInfo.mobile || '';
		loginInfo.password = loginInfo.password || '';
		if (loginInfo.mobile.length < 5) {
			return callback('账号最短为 5 个字符');
		}
		if (loginInfo.password.length < 6) {
			return callback('密码最短为 6 个字符');
		}
		jQuery.ajax({
		   type: "POST",
		   url: siteName + '/rest/api/app/user/login',
		   async: false,
		   data:app.sign(loginInfo),
		   success: function(returnMsg){
		   	if(returnMsg.code == 0){
		   		console.log(JSON.stringify(returnMsg));
		   	    var token = returnMsg.data.token;
				owner.createState(loginInfo.mobile, token, callback);
				var users = JSON.parse(localStorage.getItem('$users') || '[]');
				users.push(loginInfo);
				localStorage.setItem('$users', JSON.stringify(users));
		   	}else{
		   		callback(returnMsg.msg);
		   	}		   	    
		   },
		   error:function(){
		   		callback('用户名或密码错误');
		   }
		});
	};
	
	owner.authenticate = function(authehInfo, callback) {
		callback = callback || $.noop;
		authehInfo = authehInfo || {};
		authehInfo.bankNumber = authehInfo.bankNumber || '';
		authehInfo.idCard = authehInfo.idCard || '';
		authehInfo.reserveMobile = authehInfo.reserveMobile || '';
		if (authehInfo.bankNumber.length < 19) {
			return callback('银行账号最短为 19 位');
		}
		if (authehInfo.idCard.length < 18) {
			return callback('身份证号码最短为 18 位');
		}
		if (authehInfo.reserveMobile.length != 11) {
			return callback('银行预留手机号码为 11 位');
		}
		jQuery.ajax({
		   type: "POST",
		   url: siteName + '/rest/api/app/user/authenticate',
		   async: false,
		   data:app.signWithToken(authehInfo),
		   success: function(returnMsg){
		   	   	console.log(JSON.stringify(returnMsg));
		   	   	callback();
		   },
		   error:function(){
		   		callback('认证失败');
		   }
		});
	};

	owner.createState = function(mobile, token, callback) {
		var state = owner.getState();
		state.mobile = mobile;
		state.token = token;
		state.vaildTime = new Date().getTime() + 24*7*60*60*1000;
		owner.setState(state);
		return callback();
	};

	/**
	 * 新用户注册
	 **/
	owner.reg = function(regInfo, callback) {
		callback = callback || $.noop;
		regInfo = regInfo || {};
		regInfo.mobile = regInfo.mobile || '';
		regInfo.password = regInfo.password || '';
		regInfo.vcode = regInfo.vcode || '';
		regInfo.recommendCode = regInfo.recommendCode || '';
		if (regInfo.mobile.length < 5) {
			return callback('用户名最短需要 5 个字符');
		}
		if (regInfo.password.length < 6) {
			return callback('密码最短需要 6 个字符');
		}
		if (regInfo.vcode.length < 4) {
			return callback('验证码最短需要4个字符');
		}
		jQuery.ajax({
		   type: "POST",
		   url: siteName + '/rest/api/app/user/register',
		   async: false,
		   data:app.sign(regInfo),
		   success: function(returnMsg){
		   	    console.log(JSON.stringify(returnMsg));
				return callback();
		   },
		   error:function(){
		   		return callback("注册失败");
		   }
		});
		
	};
	
	/**
	 * 退出登录
	 * @param {Object} callback
	 */
	owner.logOut = function(callback){
		callback = callback || $.noop;
		jQuery.ajax({
		   type: "POST",
		   url: siteName + '/rest/api/app/user/logout',
		   async: false,
		   data:app.signWithToken(),
		   success: function(returnMsg){
		   	    console.log(JSON.stringify(returnMsg));
		   	    localStorage.removeItem('$users');
				owner.setState();
				return callback();
		   },
		   error:function(){
		   		return callback("退出登录失败");
		   }
		});
	};
	
	/**
	 * 重置密码
	 * @param {Object} resetInfo
	 * @param {Object} callback
	 */
	owner.resetPassword = function(resetInfo, callback){
		callback = callback || $.noop;
		resetInfo = resetInfo || {};
		resetInfo.mobile = resetInfo.mobile || '';
		resetInfo.newPwd = resetInfo.newPwd || '';
		resetInfo.vcode = resetInfo.vcode || '';
		if (resetInfo.mobile.length < 5) {
			return callback('用户名最短需要 5 个字符');
		}
		if (resetInfo.newPwd.length < 6) {
			return callback('密码最短需要 6 个字符');
		}
		if (resetInfo.vcode.length < 4) {
			return callback('验证码最短需要4个字符');
		}
		jQuery.ajax({
		   type: "POST",
		   url: siteName + '/rest/api/app/user/resetLoginPwd',
		   async: false,
		   data:app.sign(resetInfo),
		   success: function(returnMsg){
		   	    console.log(JSON.stringify(returnMsg));
				return callback();
		   },
		   error:function(){
		   		return callback("重置密码失败");
		   }
		});
	};

	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem('$state') || "{}";
		var state = JSON.parse(stateText);
		if(state.vaildTime < new Date().getTime()){
			state = {};
			owner.setState(state);
		}
		stateText = JSON.stringify(state);
		console.log("stateText:" + stateText);
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem('$state', JSON.stringify(state));
	};

	/**
	 * 获取应用本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('$settings', JSON.stringify(settings));
	};

	/**
	 * 设置应用本地配置
	 **/
	owner.getSettings = function() {
		var settingsText = localStorage.getItem('$settings') || "{}";
		return JSON.parse(settingsText);
	};
		/**
		 * 获取本地是否安装客户端
		 **/
	owner.isInstalled = function(id) {
		if (id === 'qihoo' && mui.os.plus) {
			return true;
		}
		if (mui.os.android) {
			var main = plus.android.runtimeMainActivity();
			var packageManager = main.getPackageManager();
			var PackageManager = plus.android.importClass(packageManager)
			var packageName = {
				"qq": "com.tencent.mobileqq",
				"weixin": "com.tencent.mm",
				"sinaweibo": "com.sina.weibo"
			}
			try {
				return packageManager.getPackageInfo(packageName[id], PackageManager.GET_ACTIVITIES);
			} catch (e) {}
		} else {
			switch (id) {
				case "qq":
					var TencentOAuth = plus.ios.import("TencentOAuth");
					return TencentOAuth.iphoneQQInstalled();
				case "weixin":
					var WXApi = plus.ios.import("WXApi");
					return WXApi.isWXAppInstalled()
				case "sinaweibo":
					var SinaAPI = plus.ios.import("WeiboSDK");
					return SinaAPI.isWeiboAppInstalled()
				default:
					break;
			}
		}
	};
	/**
	 * 将请求json对象签名为带系统参数和签名的对象
	 * @param {Object} oldData
	 */
	owner.sign = function(oldData){
	    var data=JSON.parse(JSON.stringify(oldData||{})) //保存原始数据
	    var appsecret = '4D97CC43F1A644F59C5E4C6A301C2AB4';
	    data.orgNumber='App_ios';
	    data.appClient='ios';
	    data.appVersion='1.0.0';
	    data.v='App_ios';
		data.timestamp=new Date().format('yyyy-MM-dd h:m:s');
	    var keys=[];
	    for(var k in data){
	        if(data[k]===0||data[k]===false||data[k]){
	            keys.push(k);
	        }else{
	            delete data[k]; //剔除空参数
	        }
	    }
	
	    keys=keys.sort();
	    var signStr='';
	    for(var i=0; i<keys.length; i++){
	        signStr += (keys[i]+data[keys[i]]);
	    }
	
	    data.sign=md5(appsecret+signStr+appsecret).toUpperCase();
	    return data;
	};
	/**
	 * 将请求json对象签名为带系统参数和签名的对象,参数中带token
	 * @param {Object} oldData
	 */
	owner.signWithToken = function(oldData){
	    var data=JSON.parse(JSON.stringify(oldData||{})) //保存原始数据
	    var appsecret = '4D97CC43F1A644F59C5E4C6A301C2AB4';
	    data.orgNumber='App_ios';
	    data.appClient='ios';
	    data.appVersion='1.0.0';
	    data.v='App_ios';
		data.timestamp=new Date().format('yyyy-MM-dd h:m:s');
		var state = owner.getState();
		data.token = state.token;
	    var keys=[];
	    for(var k in data){
	        if(data[k]===0||data[k]===false||data[k]){
	            keys.push(k);
	        }else{
	            delete data[k]; //剔除空参数
	        }
	    }
	
	    keys=keys.sort();
	    var signStr='';
	    for(var i=0; i<keys.length; i++){
	        signStr += (keys[i]+data[keys[i]]);
	    }
	
	    data.sign=md5(appsecret+signStr+appsecret).toUpperCase();
	    return data;
	};
	
	owner.ajaxWithToken = function(url,reqestData,successCb,errorCb){
		$.ajax({
		   type: "POST",
		   url: url,
		   async: false,
		   timeout:10000,//超时时间设置为10秒；
		   data:app.signWithToken(reqestData),
		   success: successCb,
		   error:errorCb
		});
	};
	/**
	 * 获取订单列表
	 **/
	owner.getProductOrderList = function() {
		var productOrderListText = localStorage.getItem('$productOrderList') || "[]";
		return JSON.parse(productOrderListText);
	};

	/**
	 * 设置订单列表
	 **/
	owner.setProductOrderList = function(productOrderList) {
		productOrderList = productOrderList || new Array();
		localStorage.setItem('$productOrderList', JSON.stringify(productOrderList));
	};
}(mui, window.app = {}));