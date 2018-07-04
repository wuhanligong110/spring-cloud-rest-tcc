// 订单列表服务
angular.module('creditList.service', [])
  .factory('CreditListFty', function ($http,$q,GlobalVariable,$window) {
    return {
      // 刷新订单列表
      refreshCreditList: function (message) {
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/credit",
		   params:message,
           headers:{'Content-Type': 'application/x-www-form-urlencoded','Authorization':localStorage.getItem('userToken')},
		   transformRequest: function(obj) {
			 var str = [];
			 for(var p in obj){
			   str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			 }
			 return str.join("&");
		   }
		}).success(function(data,status,headers,config){
            //console.log(JSON.stringify(data));
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
     }
    }
  });
