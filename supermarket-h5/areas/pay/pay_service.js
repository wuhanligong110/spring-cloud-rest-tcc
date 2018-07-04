angular.module('pay.service', [])
  .factory('PayFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
        pay: function (orderId) {
            var deferred = $q.defer();
            var prepayRequest = {};
            prepayRequest.businessId = GlobalVariable.BUSINESS_ID;
            prepayRequest.orderId = orderId;
            $http({
                method:'post',
                url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/pay/api/v1/wxpay/prepay",
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
