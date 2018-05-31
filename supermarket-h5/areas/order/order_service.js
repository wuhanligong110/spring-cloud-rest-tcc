angular.module('order.service', [])
  .factory('OrderFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
      getDefaultConsignee: function () {
          var deferred = $q.defer();
          $http({
              method:'get',
              url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/"+GlobalVariable.USER_ID+"/address/default",
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
              console.log("default consignee");
              console.log(data.data);
              deferred.resolve(data.data);
          }).error(function(data,status,headers,config){
              deferred.reject(data);
          });
          return deferred.promise;
      },
      getSelectedProduct: function () {
        var deferred = $q.defer();
        IndexedDBJs.getAll("cart",function(data){
            var order ={
                items:[],
                total:0
            };
            for(var i=0;i<data.length;i++){
                for(var j=0;j<data[i].businessProductList.length;j++){
                    if(data[i].businessProductList[j].selected){
                        order.total=order.total+parseFloat(data[i].businessProductList[j].price)*data[i].businessProductList[j].number*1.0;
                        order.items.push(data[i].businessProductList[j]);
                    }
                }
            }
            console.log(order);
            deferred.resolve(order);
        },function(e){
            deferred.reject(e);
        });
        return deferred.promise;
      },
        getConsigneeList: function () {
            var deferred = $q.defer();
            $http({
                method:'get',
                url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/"+GlobalVariable.USER_ID+"/address",
                params:{pageNum:1,pageSize:10},
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
                console.log("default consignee");
                console.log(data.data);
                deferred.resolve(data.data.list);
            }).error(function(data,status,headers,config){
                deferred.reject(data);
            });
            return deferred.promise;
        }
    }
  }]);
