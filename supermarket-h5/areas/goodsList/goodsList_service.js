// 商品列表服务
angular.module('goodsList.service', [])
  .factory('GoodsListFty', function ($http,$q,GlobalVariable,$window) {
    return {
      // 刷新商品列表
      refreshGoodsList: function (message) {     	
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/product/api/v1/products/catecategoty/product",
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
			for(var temp in data.data.list){
				data.data.list[temp]["listImg"] = GlobalVariable.IMAGE_SERVER + data.data.list[temp]["listImg"]+"?w=400&h=400";
			}
            console.log(JSON.stringify(data));
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
     }
    }
  });
