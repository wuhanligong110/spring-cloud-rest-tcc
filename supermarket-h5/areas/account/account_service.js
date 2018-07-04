angular.module('account.service', [])
  .factory('AccountFty', function($http,$q,GlobalVariable,$window) {
      return {
          initUserInfo:function(){
              // 完整的请求服务器的步骤
              var deferred = $q.defer();
              $http({
                  method:'get',
                  url:GlobalVariable.SERVER_PATH+":"+GlobalVariable.PORT+"/account/api/v1/users",
                  params :{},
                  headers:{'Content-Type': 'application/x-www-form-urlencoded','Authorization':localStorage.getItem('userToken')},
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
                  if(data.data){
                      if(data.data.vipLevel == 1){
                          data.data.vipLevel = "初级会员";
                      }else if(data.data.vipLevel == 2){
                          data.data.vipLevel = "中级会员";
                      }else if(data.data.vipLevel == 3){
                          data.data.vipLevel = "高级会员";
                      }
                  }
                  deferred.resolve(data.data);
              }).error(function(data,status,headers,config){
                  deferred.reject(data);
              });
              return deferred.promise;
          }
      }
  });
