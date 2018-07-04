// Home服务文件
angular.module('home.service', [])
  .factory('HomeFty', function($http,$q,GlobalVariable,$window) {
    return {
      headerSlideList:function(){
		// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/activity/api/v1/advs/"+GlobalVariable.BUSINESS_ID,
		   params :{pageIndex:'app_home_page'},
		   headers:{'Content-Type': 'application/x-www-form-urlencoded'},
		   transformRequest: function(obj) {
			 var str = [];
			 for(var p in obj){
                 alert(p);
                 alert(obj[p]);
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
      },
      headerTouTiao:function(){
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/activity/api/v1/advs/"+GlobalVariable.BUSINESS_ID,
		   params:{pageIndex:'app_toutiao'},
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
      },
      onSaleProduct:function(){
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/product/api/v1/products/"+GlobalVariable.BUSINESS_ID+"/onSale",
           params:{pageNo:1,pageSize:8},
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
			deferred.resolve(data.data.list);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
      },
      themeProducer:function(){
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/product/api/v1/businesses/business",
		   params:{pageNo:1,pageSize:4},
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
				data.data.list[temp]["listImg"] = GlobalVariable.IMAGE_SERVER + data.data.list[temp]["listImg"];//+"?w=202&h=150"
			}
        	deferred.resolve(data.data.list);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
      },
      brandPurchase:function(){
      	// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/product/api/v1/products/product/categoty/brandRecomend",
		   params:{pageNo:1,pageSize:4},
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
                data.data.list[temp]["categotyImg"] = GlobalVariable.IMAGE_SERVER + data.data.list[temp]["categotyImg"]+"?w=202&h=150";
            }
            deferred.resolve(data.data.list);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
      },
      hotProduct:function(){
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/product/api/v1/products/"+GlobalVariable.BUSINESS_ID+"/hot",
		   params:{pageNo:1,pageSize:10},
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
            deferred.resolve(data.data.list);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
      },
      weixinUserLogin:function(){
          var deferred = $q.defer();
          $http({
              method:'post',
              url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/weixin/login",
              headers:{'Content-Type': 'application/json'},
              transformRequest: function(obj) {
                  var str = [];
                  for(var p in obj){
                      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                  }
                  return str.join("&");
              }
          }).success(function(data,status,headers,config){
              console.log(data.token);
              localStorage.setItem("userToken",data.token);
              deferred.resolve(data.data);
          }).error(function(data,status,headers,config){
              deferred.reject(data);
          });
          return deferred.promise;
      }
    };
  });
