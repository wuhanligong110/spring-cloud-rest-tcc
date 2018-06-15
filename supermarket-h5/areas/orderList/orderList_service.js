// 订单列表服务
angular.module('orderList.service', [])
  .factory('OrderListFty', function ($http,$q,GlobalVariable,$window) {
    return {
      // 刷新订单列表
      refreshOrderList: function (message) {
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/order/api/v1/orders/"+GlobalVariable.USER_ID+"/order",
		   params:message,
		   headers:{'Content-Type': 'application/x-www-form-urlencoded'},
		   transformRequest: function(obj) {
			 var str = [];
			 for(var p in obj){
			   str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			 }
			 return str.join("&");
		   }
		}).success(function(data,status,headers,config){
			/*for(var temp in data.data.list){
				data.data.list[temp]["listImg"] = GlobalVariable.IMAGE_SERVER + data.data.list[temp]["listImg"]+"?w=400&h=400";
			}*/
            //console.log(JSON.stringify(data));
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
     }
    }
  });
