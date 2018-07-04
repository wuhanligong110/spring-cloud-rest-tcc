
angular.module('home.controller', ['home.service'])
  .controller('HomeCtrl', function($scope,GlobalVariable,$window,HomeFty,$ionicSlideBoxDelegate) {
  	
    getHeaderSlideData();
    getHeaderTouTiao();
    getOnSaleProduct();
    getThemeProducer();
    getBrandPurchase();
    getHotProduct();
    goTop();
    countdown();
    //headerChangeColor();
    weixinUserLogin();

    // 监听页面激活事件
    $scope.$on('$ionicView.enter',function(){
      initHeaderSlide();
      initToutiaoSlide();
    });

    // 头部滚动条数据
    function getHeaderSlideData(){
	  var promise=HomeFty.headerSlideList();
      promise.then(
        function(data){
          if(data){
            $scope.obj_headerSlideData=data;
          }else{
          	
          }
        },
        function(err){

        }).finally(function() {
          // 停止广播ion-refresher
          //$scope.$broadcast('scroll.refreshComplete');
      });
    }
    
    // 初始化头部滚动条
    function initHeaderSlide(){
      var headerSwiper = new Swiper('#headerSlider', {
        slidesPerView: 1,
        paginationClickable: true,
        centeredSlides: true,
        autoplay: 2000,
        autoplayDisableOnInteraction: false,
        loop: true,
        // 如果需要分页器
        pagination: '.swiper-pagination',
        // 改变自动更新
        observer:true,
        observeParents:true
      });
    }
    
    // 头部滚动条数据
    function getHeaderTouTiao(){
	    var promise=HomeFty.headerTouTiao();
      promise.then(
        function(data){
          if(data){
            $scope.obj_headerTouTiao=data;
          }else{
          	
          }
        },
        function(err){

        }).finally(function() {
          // 停止广播ion-refresher
          //$scope.$broadcast('scroll.refreshComplete');
      });
    }
    
    // 初始化京东头条滚动条
    function initToutiaoSlide(){
      var toutiaoSwiper = new Swiper('#toutiaoSlider', {
        direction:'vertical',
        autoplay: 2000,
        loop: true
      });
    }
    
    function getOnSaleProduct(){
	    var promise=HomeFty.onSaleProduct();
      promise.then(
        function(data){
          if(data){
            $scope.product_limitTimeOnSale=data;
            //console.log(JSON.stringify(data));
          }else{
          	
          }
        },
        function(err){

        }).finally(function() {
          // 停止广播ion-refresher
          //$scope.$broadcast('scroll.refreshComplete');
      });
    }
    
    function getThemeProducer(){
      var promise=HomeFty.themeProducer();
      promise.then(
        function(data){
          if(data){
            $scope.businesses=data;
          }else{
          	
          }
        },
        function(err){

        }).finally(function() {
          // 停止广播ion-refresher
          //$scope.$broadcast('scroll.refreshComplete');
      });
    }
    
    function getBrandPurchase(){
	  var promise=HomeFty.brandPurchase();
      promise.then(
        function(data){
          if(data){
            $scope.brand=data;
          }else{
          	
          }
        },
        function(err){

        }).finally(function() {
          // 停止广播ion-refresher
          //$scope.$broadcast('scroll.refreshComplete');
      });
    }
    
    function getHotProduct(){
	  var promise=HomeFty.hotProduct();
      promise.then(
        function(data){
          if(data){
            $scope.hotProducts=data;
          }else{
          	
          }
        },
        function(err){

        }).finally(function() {
          // 停止广播ion-refresher
          //$scope.$broadcast('scroll.refreshComplete');
      });
    }

  function weixinUserLogin(){
      var promise=HomeFty.weixinUserLogin();
      promise.then(
          function(data){
              if(data){

              }else{

              }
          },
          function(err){

          }).finally(function() {

      });
  }

    // 改变头部颜色
    function headerChangeColor(){
      var bg=$window.document.getElementById('home-content');
      var nowOpacity=0;
      bg.onscroll=function(event){
        if(this.scrollTop/250<.85){
          nowOpacity=this.scrollTop/250;
        }
        document.getElementById("headerBar-bg").style.opacity=nowOpacity;
      }
    }

    //回到顶部
    function goTop(){
      var bg=window.document.getElementById('home-content');
      var goTop = document.querySelector(".back_top");

      bg.addEventListener('scroll',function(){
        var top = bg.scrollTop;
        if(top>200){
          goTop.style.opacity = 1;
        }else{
          goTop.style.opacity = 0;
        }
      },false);

      goTop.onclick = function(){
        bg.scrollTop = 0;
      }
    };


    // 秒杀计时器
    function countdown(){
      if($window.timer){
        clearInterval($window.timer);
      }
      // 倒计时
      var timeObj={
        h:1,
        m:37,
        s:13
      };

      var timeStr=toDouble(timeObj.h)+toDouble(timeObj.m)+toDouble(timeObj.s);
      var timeList=document.getElementsByClassName('time-text');
      for(var i=0;i<timeList.length;i++){
        timeList[i].innerHTML=timeStr[i];
      }
      function toDouble(num){
        if(num<10){
          return '0'+num;
        }else{
          return ''+num;
        }
      }

      $window.timer=setInterval(function(){
        timeObj.s--;
        if(timeObj.s==-1){
          timeObj.m--;
          timeObj.s=59;
        }
        if(timeObj.m==-1){
          timeObj.h--;
          timeObj.m=59;
        }
        if(timeObj.h==-1){
          timeObj.h=0;
          timeObj.m=0;
          timeObj.s=0;
          clearInterval($window.timer);
        }
        timeStr=toDouble(timeObj.h)+toDouble(timeObj.m)+toDouble(timeObj.s);
        for(var i=0;i<timeList.length;i++){
          timeList[i].innerHTML=timeStr[i];
        }
      },1000)
    }

  })
