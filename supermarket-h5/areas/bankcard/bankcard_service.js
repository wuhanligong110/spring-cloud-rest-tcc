angular.module('bankcard.service', [])
  .factory('BankcardFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
        getUserBankCardInfo:function () {
            var deferred = $q.defer();
            $.ajax({
                type: "GET",
                url: GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/"+GlobalVariable.USER_ID+"/account/3",
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
        },
        bindBankCard:function (bindRequest) {
            var deferred = $q.defer();
            $.ajax({
                type: "POST",
                url: GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/"+GlobalVariable.USER_ID+"/account",
                async: false,
                contentType: 'application/json',
                data:JSON.stringify(bindRequest),
                timeout:10000,//超时时间设置为10秒；
                success: function(data){
                    deferred.resolve(data.code);
                },
                error:function (e) {
                    deferred.reject(e);
                }
            });
            return deferred.promise;
        }
    }
  }]);
