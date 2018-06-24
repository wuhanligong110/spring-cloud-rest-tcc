angular.module('withdraw.service', [])
  .factory('WithdrawFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
        withdraw:function (withdrawRequest) {
            var deferred = $q.defer();
            $.ajax({
                type: "POST",
                url: GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/pay/api/v1/users/"+GlobalVariable.USER_ID+"/account",
                async: false,
                contentType: 'application/json',
                data:JSON.stringify(withdrawRequest),
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
