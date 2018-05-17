$(function(){
		mui.init({
			swipeBack:true //启用右滑关闭功能
		});
		
		var categotyId = getRequestUrlParam('categotyId');//获取产品分类id
		var categotyName = unescape(getRequestUrlParam('categotyName'));//获取产品分类名称
		$('#productCategotyListHead').html(categotyName);
		
		if(categotyId != null && categotyId !=''){
			
			//加载页面数据
			$.ajax({
			   type: "POST",
			   url: "/rest/api/wxpublic/productCategotyList",
			   async: false,
			   timeout:10000,//超时时间设置为10秒；
			   data:{categotyId:categotyId,productType:1},
			   success: function(bizProductDetailList){
			   	
				    var html = '';
				    if(bizProductDetailList != null && bizProductDetailList != ''){
				    	html += '<ul class="mui-table-view mui-grid-view">';
				    	for(var index in bizProductDetailList){
				        	html += '<li class="mui-table-view-cell mui-media mui-col-xs-12">';
						    html += '<a href="productDetail.html?productId='+bizProductDetailList[index].productId+'">';
							html += '<div class="product-wrapper">';
							html += '<img class="mui-media-object" style="border-radius: 5px;" src="'+imgSercviceUrl+bizProductDetailList[index].briefImg+'" />';
							if(bizProductDetailList[index].ifHot == 0){//hot图标
								html += '<div class="new"></div>'; 
							}
							html += '<div class="product-content">';
							html += '<div class="mui-media-body">';
							html += '<font color="white">'+bizProductDetailList[index].productName+'</font>';
							html += '<p class="price-two">¥ '+formatData(bizProductDetailList[index].price)+'</p>';							
							html += '<p class="price-one">送'+formatData(bizProductDetailList[index].price)+'积分</p>';
							html += '</div>';
							html += '</div>';
							html += '</div>';
						    html += '</a>';
						    html += '</li>';
				    	}
				    	html += '</ul>';
				    } else {
				    	html += '<img width="100%" src="'+imgSercviceUrl+'1ab9f57716be6da7014b1eb3ed2fdec2" />';
				    }
		    
			   		$('#productCategotyListDiv').html(html);
			   }
			});
		} else {
			console.log('产品分类id不存在');
		}
});
