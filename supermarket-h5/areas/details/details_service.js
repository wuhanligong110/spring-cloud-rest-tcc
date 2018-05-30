
angular.module('details.service', [])
  .factory('DetailsFty', function($http,$q,GlobalVariable,$window) {
    return {
    	productDetail:function(productId){
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/product/api/v1/products/"+productId+"/detail",
		   headers:{'Content-Type': 'application/x-www-form-urlencoded'},
		   transformRequest: function(obj) {
			 var str = [];
			 for(var p in obj){
			   str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			 }
			 return str.join("&");
		   }
		}).success(function(data,status,headers,config){
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
      }
    };
  });
