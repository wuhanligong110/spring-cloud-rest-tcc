
angular.module('details.service', [])
  .factory('DetailsFty', function($http,$q,GlobalVariable,$window) {
    return {
    	productDetail:function(productId){  				
				// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
				   method:'post',  
				   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/rest/api/app/product/detail",
				   data:$window.app.sign({productId:productId}),  
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
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
					deferred.reject(data);
        });
        return deferred.promise;
      }
    };
  });
