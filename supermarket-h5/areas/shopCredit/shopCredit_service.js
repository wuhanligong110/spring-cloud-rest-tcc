angular.module('shopCredit.service', [])
  .factory('ShopCreditFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
        /*getUserCardsInfo:function () {
            var deferred = $q.defer();
            $.ajax({
                type: "GET",
                url: GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/"+GlobalVariable.USER_ID+"/account",
                async: false,
                timeout:10000,//超时时间设置为10秒；
                success: function(data){
                    deferred.resolve(data.data);
                },
                error:function (e) {
                    deferred.reject(e);
                }
            });
            return deferred.promise;
        }*/
    }
  }]);
