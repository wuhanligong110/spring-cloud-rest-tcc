angular.module('order.service', [])
  .factory('OrderFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
      getDefaultConsignee: function () {
          var deferred = $q.defer();
          $http({
              method:'get',
              url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/address/default",
              headers:{'Content-Type': 'application/x-www-form-urlencoded','Authorization':localStorage.getItem('userToken')},
              transformRequest: function(obj) {
                  var str = [];
                  for(var p in obj){
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
        submitOrder: function (order) {
            var deferred = $q.defer();
            var prepayRequest = {};
            var orderItems = new Array();
            for(var i in order.items){
                var orderItem = {};
                orderItem.business_id = order.items[i].businessId;
                orderItem.product_id = order.items[i].productId;
                orderItem.product_count = order.items[i].number;
                orderItems.push(orderItem);
            }
            prepayRequest.orderItems = orderItems;
            prepayRequest.total = order.total;
            prepayRequest.addressId = order.addressId;
            prepayRequest.businessId = GlobalVariable.BUSINESS_ID;
            $http({
                method:'post',
                url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/pay/api/v1/pay/prepay",
                headers:{'Content-Type': 'application/json','Authorization':localStorage.getItem('userToken')},
                data:prepayRequest
            }).success(function(data,status,headers,config){
                deferred.resolve(data);
            }).error(function(data,status,headers,config){
                deferred.reject(data);
            });
            return deferred.promise;
        }
    }
  }]);
