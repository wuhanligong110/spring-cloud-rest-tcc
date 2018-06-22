angular.module('category.controller', ['category.service'])
    .controller('CategoryCtrl', function($scope, GlobalVariable, $ionicHistory,CategoryFty) {

        $scope.$on('$ionicView.enter', function(e) {
            getCategoryData();
            $scope.getCategoryDetailData(1);
        });

        function getCategoryData() {
	      var promise=CategoryFty.categoryData();
	      promise.then(
	        function(data){
	          if(data){
	            $scope.categoryData = data;
	          }else{
	          	
	          }
	        },
	        function(err){
	
	        }).finally(function() {
	          
	      });

        }

        $scope.getCategoryDetailData = function(categotyId) {
          var promise=CategoryFty.categoryDetailData(categotyId);
	      promise.then(
	        function(data){
	          if(data){
	            $scope.categoryDetailData = data;
	          }else{
	          	
	          }
	        },
	        function(err){
	
	        }).finally(function() {
	          
	      });
        };

        // 左侧分类单击样式修改
        $scope.categoryLeftClick = function(e) {
        	console.log("this is a e");
            e.target.className = 'nav-current';
            $(e.target).siblings().removeClass().addClass('nav-blur');
        };
    });
