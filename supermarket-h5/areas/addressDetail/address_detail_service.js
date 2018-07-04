angular.module('address.detail.service', [])
  .factory('AddressDetailFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
        saveCsnForm: function (receiver) {
            receiver.type = 1;
            receiver.typeName = "邮寄地址";
            var deferred = $q.defer();
            $http({
                method:'post',
                url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/address",
                headers:{'Content-Type': 'application/json','Authorization':localStorage.getItem('userToken')},
                data:receiver
            }).success(function(data,status,headers,config){
                deferred.resolve(data.data);
            }).error(function(data,status,headers,config){
                deferred.reject(data);
            });
            return deferred.promise;
        },
        deleteCsnForm: function (addressId) {
            var deferred = $q.defer();
            $http({
                method:'delete',
                url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/address/"+addressId,
                headers:{'Content-Type': 'application/x-www-form-urlencoded','Authorization':localStorage.getItem('userToken')},
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj){
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    }
                    return str.join("&");
                }
            }).success(function(data,status,headers,config){
                deferred.resolve(data.code);
            }).error(function(data,status,headers,config){
                deferred.reject(data);
            });
            return deferred.promise;
        },
        addressDetail: function (addressId) {
            var deferred = $q.defer();
            $http({
                method:'get',
                url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users/address/detail/"+addressId,
                headers:{'Content-Type': 'application/x-www-form-urlencoded','Authorization':localStorage.getItem('userToken')},
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj){
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    }
                    return str.join("&");
                }
            }).success(function(data,status,headers,config){
                data.data.mobile = Number(data.data.mobile);
                deferred.resolve(data.data);
            }).error(function(data,status,headers,config){
                deferred.reject(data);
            });
            return deferred.promise;
        }
    }
  }]);
