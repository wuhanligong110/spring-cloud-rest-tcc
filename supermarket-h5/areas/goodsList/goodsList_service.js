// 商品列表服务
angular.module('goodsList.service', [])
  .factory('GoodsListFty', function ($http,$q,GlobalVariable,$window) {
    return {
      // 刷新商品列表
      refreshGoodsList: function (message) {     	
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
				   method:'post',  
				   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/rest/api/app/product/pageList",
				   data:$window.app.sign(message),  
				   headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
				   transformRequest: function(obj) {  
				     var str = [];  
				     for(var p in obj){  
				       str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
				     }  
				     return str.join("&");  
				   }  
				}).success(function(data,status,headers,config){
					console.log(JSON.stringify(data));
					for(var temp in data.data.datas){
						data.data.datas[temp]["src"] = GlobalVariable.IMAGE_SERVER + data.data.datas[temp]["src"]+"?w=400&h=400";
					}
        	deferred.resolve(data.data.datas);
        }).error(function(data,status,headers,config){
					deferred.reject(data);
        });
        return deferred.promise;
     }
    }
  });
