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
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/order/api/v1/orders/order",
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
			for(var i in data.data.list){
				for(j in data.data.list[i].orderItems){
					data.data.list[i].orderItems[j].productImg = GlobalVariable.IMAGE_SERVER + data.data.list[i].orderItems[j].productImg+"?w=400&h=400";
				}
			}
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
     }
    }
  });
