angular.module('cards.service', [])
  .factory('CardsFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
        getUserCardsInfo:function () {
            var deferred = $q.defer();
            $.ajax({
                type: "GET",
                url: GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/account",
                headers:{'Content-Type': 'application/x-www-form-urlencoded','Authorization':localStorage.getItem('userToken')},
                async: false,
                timeout:10000,//超时时间设置为10秒；
                success: function(data){
                    deferred.resolve(data.data.list);
                },
                error:function (e) {
                    deferred.reject(e);
                }
            });
            return deferred.promise;
        }
    }
  }]);
