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
				alert(data.data.list[temp]["listImg"]);
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
				   method:'post',  
				   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/rest/api/app/homepage/themeProducer",
				   data:$window.app.sign(),  
				   headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
				   transformRequest: function(obj) {  
				     var str = [];  
				     for(var p in obj){  
				       str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
				     }  
				     return str.join("&");  
				   }  
				}).success(function(data,status,headers,config){
					data.data.leftTheme["homepageImg"] = GlobalVariable.IMAGE_SERVER + data.data.leftTheme["homepageImg"]+"?x=400&w=201&h=309";
					for(var temp in data.data.rightTheme){
						data.data.rightTheme[temp]["homepageImg"] = GlobalVariable.IMAGE_SERVER + data.data.rightTheme[temp]["homepageImg"]+"?w=202&h=150";
					}
					console.log(JSON.stringify(data.data));
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
					deferred.reject(data);
        });
        return deferred.promise;
      },
      brandPurchase:function(){
				// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
				   method:'post',  
				   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/rest/api/app/homepage/brandPurchase",
				   data:$window.app.sign(),  
				   headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
				   transformRequest: function(obj) {  
				     var str = [];  
				     for(var p in obj){  
				       str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
				     }  
				     return str.join("&");  
				   }  
				}).success(function(data,status,headers,config){
					data.data.leftBrand["homepageImg"] = GlobalVariable.IMAGE_SERVER + data.data.leftBrand["homepageImg"]+"?x=400&w=201&h=309";
					for(var temp in data.data.rightBrand){
						data.data.rightBrand[temp]["homepageImg"] = GlobalVariable.IMAGE_SERVER + data.data.rightBrand[temp]["homepageImg"]+"?w=202&h=150";
					}
					console.log(JSON.stringify(data.data));
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
					deferred.reject(data);
        });
        return deferred.promise;
      },
      homeAdv:function(){
				// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
				   method:'post',  
				   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/rest/api/app/homepage/advs",
				   data:$window.app.sign({advPlacement:'app_home_adv'}),  
				   headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
				   transformRequest: function(obj) {  
				     var str = [];  
				     for(var p in obj){  
				       str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
				     }  
				     return str.join("&");  
				   }  
				}).success(function(data,status,headers,config){
        	deferred.resolve(data.data.datas);
        }).error(function(data,status,headers,config){
					deferred.reject(data);
        });
        return deferred.promise;
      }
    };
  });
