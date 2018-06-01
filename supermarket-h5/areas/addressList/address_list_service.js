angular.module('address.list.service', [])
  .factory('AddressListFty', ['$http', '$q','$window','IndexedDBJs','GlobalVariable', function ($http, $q,$window,IndexedDBJs,GlobalVariable) {
    return {
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
