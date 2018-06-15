angular.module('cart.service', [])
  .factory('CartFty', ['$http', '$q','$window','IndexedDBJs', function ($http, $q,$window,IndexedDBJs) {
    return {
      getAllData: function () {
        var deferred = $q.defer();
        IndexedDBJs.getAll("cart",function(data){
          deferred.resolve(data);
        },function(e){
          deferred.reject(e);
        })
        return deferred.promise;
      },
      get: function (id) {
        var deferred = $q.defer();
        IndexedDBJs.get(id,"cart",function(data){
          deferred.resolve(data);
        },function(e){
          deferred.reject(e);
        })
        return deferred.promise;
      },
      updateData: function (data) {
        var deferred = $q.defer();
        IndexedDBJs.update("cart",data,function(){
          deferred.resolve();
        },function(e){
          deferred.reject(e);
        })
        return deferred.promise;
      },
      delete: function (id) {
        var deferred = $q.defer();
        IndexedDBJs.delete(id,"cart",function(data){
          deferred.resolve(data);
        },function(e){
          deferred.reject(e);
        })
        return deferred.promise;
      },
      clearPay:function (order) {
        for(var i in order.items){
            var businessId = order.items[i].businessId;
            IndexedDBJs.get(businessId,"cart",function(data){
              for(var j in order.items){
                  var productId = order.items[j].productId;
                  for(var i in data.businessProductList){
                      if(data.businessProductList[i].productId == productId){
                          data.businessProductList = data.businessProductList.slice(0,i).concat(data.businessProductList.slice(i+1,data.businessProductList.length));
                          break;
                      }
                  }
              }
              IndexedDBJs.update("cart",data);
              if(data.businessProductList.length == 0){
                  IndexedDBJs.delete(data.businessId,"cart");
              }
            });
        }
      }
    }
  }]);
