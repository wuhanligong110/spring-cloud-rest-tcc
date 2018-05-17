angular.module('category.service', [])
  .factory('CategoryFty', function($http,$q,GlobalVariable,$window) {
    return {
    	categoryData:function(){  				
				// 完整的请求服务器的步骤
        var deferred = $q.defer();
        $http({  
				   method:'post',  
				   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/rest/api/app/product/categotyList",
				   data:$window.app.sign({}),  
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
    },
    categoryDetailData:function(categotyId){  				
				// 完整的请求服务器的步骤
        var deferred = $q.defer();
        console.log(categotyId);
        $http({  
				   method:'post',  
				   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/rest/api/app/product/secondCategotyList",
				   data:$window.app.sign({categotyId:categotyId}),  
				   headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
				   transformRequest: function(obj) {  
				     var str = [];  
				     for(var p in obj){  
				       str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
				     }  
				     return str.join("&");  
				   }  
				}).success(function(data,status,headers,config){
					data.data.categoryHeader = GlobalVariable.IMAGE_SERVER + data.data.categoryHeader + "?w=510&h=170";
					for(var i in data.data.secondCategotyList){
						for(var j in data.data.secondCategotyList[i].categotyList){
							data.data.secondCategotyList[i].categotyList[j].categotyImg = GlobalVariable.IMAGE_SERVER + data.data.secondCategotyList[i].categotyList[j].categotyImg +"?w=130&h=130";
						}
					}
        	deferred.resolve(data.data);
        }).error(function(data,status,headers,config){
					deferred.reject(data);
        });
        return deferred.promise;
    }
    };
  });
