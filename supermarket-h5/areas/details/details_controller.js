// 详细页面控制器
angular.module('details.controller', ['details.service'])
  .controller('DetailsCtrl', function($scope,GlobalVariable,$stateParams,$ionicHistory,IndexedDBJs,$window,DetailsFty,$ionicSlideBoxDelegate) {
  	
  	productDetail();

    // 购物车徽章位置显示的数量
    $scope.obj_cartCount = {
      count: "0"
    }

    // 当详细页面激活之前获取购物车表里面有没有商品数量，如果有，我们就把他给赋值
    $scope.$on('$ionicView.beforeEnter', function (e) {
      IndexedDBJs.getAll("cart",function(data){
        for(var i =0;i<data.length;i++){
          $scope.obj_cartCount.count=parseInt($scope.obj_cartCount.count)+parseInt(data[i].number);
        }
      },null)
    });

    // 通过后台获取到的商品详细信息数据
    function productDetail(){
    	console.log(123456);
	    var promise=DetailsFty.productDetail($stateParams.productId);
      promise.then(
        function(data){
          if(data){
          	$scope.obj_productDetailImg = data.src.split(',');
          	$scope.productDetailImgSize = $scope.obj_productDetailImg.length;
          	console.log(JSON.stringify($scope.productDetailImgSize));
          	for(item in $scope.obj_productDetailImg){
          		$scope.obj_productDetailImg[item] = GlobalVariable.IMAGE_SERVER + $scope.obj_productDetailImg[item] + "?w=200&h=400";
          	}
          	data.src = $scope.obj_productDetailImg;
          	console.log(JSON.stringify($scope.obj_productDetailImg));
          	$scope.obj_goodsInfo = data;
          	// 用户选择信息
				    $scope.obj_goodsDetailInfo = {
				      productId: $scope.obj_goodsInfo.productId,
				      isFork: $scope.obj_goodsInfo.isFork,
				      description: $scope.obj_goodsInfo.description,
				      src: $scope.obj_goodsInfo.src,
				      price: $scope.obj_goodsInfo.price,
				      color: "",
				      size: "",
				      number: 1
				    }
				    $ionicSlideBoxDelegate.update();
          }else{
          	
          }
        },
        function(err){

        }).finally(function() {
          
      });
    };

    // 数量加1
    $scope.func_jia1 = function () {
      $scope.obj_goodsDetailInfo.number++;
    }

    // 数量减1
    $scope.func_jian1 = function () {
      if ($scope.obj_goodsDetailInfo.number != 1) {
        $scope.obj_goodsDetailInfo.number--;
      }
    }

    $scope.func_addToCart=function(){

      var obj_newData={};
      // 硬拷贝方法
      angular.copy($scope.obj_goodsDetailInfo,obj_newData);
      // 从新改变编号
      obj_newData.productId =obj_newData.productId + "-" + obj_newData.color + "-" + obj_newData.size;

      // 进行代码健壮性判断
      IndexedDBJs.get(obj_newData.productId,"cart",
        function(data){
          if(data==null||data==undefined){
            // 不存在商品就添加
            IndexedDBJs.add("cart", obj_newData, function () {
              // 变更购物车数量
              $scope.obj_cartCount.count=parseInt($scope.obj_cartCount.count)+parseInt($scope.obj_goodsDetailInfo.number);
              // 手动调用去更新数据绑定模型
              // 异步请求绑定到模型上的数据，不能被监听到，所以我们需要手动刷新一下
              //$scope.$apply()//在里面调用了$digest方法
              $scope.$digest();
            }, null);
          }
          else {
            // 存在商品
            // 是新增加6个数量，所以要处理一下，这个还影响下面变更购物车数量的逻辑
            obj_newData.number=parseInt(obj_newData.number)+parseInt(data.number);

            IndexedDBJs.update("cart", obj_newData, function () {
              //变更购物车数量
              $scope.obj_cartCount.count=parseInt($scope.obj_cartCount.count)+parseInt($scope.obj_goodsDetailInfo.number);
              $scope.$digest();
            }, null);
          }
        },
        null
      )

    }

    // 返回方法
    $scope.func_goBack=function(){
      $ionicHistory.goBack();
    }

  })
