angular.module('category.service', [])
  .factory('CategoryFty', function($http,$q,GlobalVariable,$window) {
    return {
    	categoryData:function(){
        var deferred = $q.defer();
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/product/api/v1/products/product/categoty",
		   params:{level:1},
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
    categoryDetailData:function(categotyId){
        var deferred = $q.defer();
        console.log(categotyId);
        $http({  
		   method:'get',
		   url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/product/api/v1/products/product/categoty/"+categotyId,
		   headers:{'Content-Type': 'application/x-www-form-urlencoded'},
		   transformRequest: function(obj) {
			 var str = [];
			 for(var p in obj){
			   str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			 }
			 return str.join("&");
		   }
		}).success(function(data,status,headers,config){
			data.categoryHeader = GlobalVariable.IMAGE_SERVER + data.categoryHeader + "?w=510&h=170";
			for(var i in data.secondCategotyList){
				for(var j in data.secondCategotyList[i].categotyList){
					data.secondCategotyList[i].categotyList[j].categotyImg = GlobalVariable.IMAGE_SERVER + data.secondCategotyList[i].categotyList[j].categotyImg +"?w=130&h=130";
				}
			}
        	deferred.resolve(data);
        }).error(function(data,status,headers,config){
        	deferred.reject(data);
        });
        return deferred.promise;
    }
    };
  });
