// 订单列表服务
angular.module('orderDetail.service', [])
  .factory('OrderDetailFty', function ($http,$q,GlobalVariable,$window) {
    return {
      // 刷新订单列表
      orderDetail: function (orderId) {
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/order/api/v1/orders/order/detail/"+orderId,
		   headers:{'Content-Type': 'application/x-www-form-urlencoded','Authorization':localStorage.getItem('userToken')},
		   transformRequest: function(obj) {
			 var str = [];
			 for(var p in obj){
			   str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			 }
			 return str.join("&");
		   }
		}).success(function(data,status,headers,config){
			for(j in data.data.orderItems){
				data.data.orderItems[j].productImg = GlobalVariable.IMAGE_SERVER + data.data.orderItems[j].productImg+"?w=400&h=400";
			}
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
     },
     hasReceived: function (orderId) {
         // 完整的请求服务器的步骤
         var deferred = $q.defer();
         var request = {};
         request.order_id = orderId;
         $http({
             method:'post',
             url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/order/api/v1/orders/received",
             headers:{'Content-Type': 'application/json','Authorization':localStorage.getItem('userToken')},
             data:request
         }).success(function(data,status,headers,config){
             deferred.resolve(data.code);
         }).error(function(data,status,headers,config){
             deferred.reject(data);
         });
         return deferred.promise;
     },
     orderCancel: function (orderId) {
         // 完整的请求服务器的步骤
         var deferred = $q.defer();
         var request = {};
         request.order_id = orderId;
         $http({
             method:'post',
             url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/order/api/v1/orders/cancel",
             headers:{'Content-Type': 'application/json','Authorization':localStorage.getItem('userToken')},
             data:request
         }).success(function(data,status,headers,config){
             deferred.resolve(data.code);
         }).error(function(data,status,headers,config){
             deferred.reject(data);
         });
         return deferred.promise;
     }
    }
  });
