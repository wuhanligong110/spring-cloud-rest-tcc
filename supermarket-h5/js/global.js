// 全局变量模块
angular.module('global', [])
  .constant("GlobalVariable",{
    'SERVER_PATH':'http://remotewh.supermarket.miget.com',
    'HTTP':'http://',
    'PORT':'80',
    'VERSION':"0.0.1",
    "IMAGE_SERVER":"http://wh.jiuguishangcheng.com/images/",
    "BUSINESS_ID":1,
    "USER_ID":1000001
  });
