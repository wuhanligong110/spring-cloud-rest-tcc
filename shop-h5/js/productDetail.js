$(function(){
		mui.init({
			swipeBack:true //启用右滑关闭功能
		});
		
		var productId = getRequestUrlParam('productId');//获取产品id
		
		if(productId != null && productId !=''){
			var bizProductDetail = getProductDetailByProductId(productId);
			if(bizProductDetail != null ){
				
		   		$('#productDetailHead').html(bizProductDetail.productName);//设置标题名称
		   		
		   		//设置详情内容
		   		var html = '';
		   		html += '<div>';
				html += '<img width="100%" src="'+bizProductDetail.briefImg+'"/>';
				html += '</div>';
				html += '<div>';				
				html += '<div class="mui-row cpxq">';
				html += '<div class="mui-col-sm-12 mui-col-xs-12">';
				html += '<span class="cpxq-jg-r">¥</span>';
				html += '<span class="cpxq-jg">'+formatDataN(bizProductDetail.price)+'</span>';
				html += '</div>';
				html += '<div class="mui-col-sm-12 mui-col-xs-12">';
				html += '<div class="mui-row">';
				html += '<div class="mui-col-sm-4 mui-col-xs-4 cpxq-yj">';
				html += '<span>原价¥</span>';
				html += '<span>'+formatDataN(bizProductDetail.prePrice)+'</span>';
				html += '</div>';
				html += '<div class="mui-col-sm-8 mui-col-xs-8 cpxq-yj">';
				html += '快递:免运费';
				html += '</div>';  	    
				html += '</div>';
				html += '</div>';
				html += '<div class="mui-col-sm-12 mui-col-xs-12 cpxq-xpmc">';
				html += bizProductDetail.productName;
				html += '</div>';
				html += '</div>';
				
				html += '<div class="mui-row cpxq">';
				html += '<div class="mui-col-sm-12 mui-col-xs-12">';
				html += '<span class="bk-red">&nbsp;</span>';
				html += '<span class="cpxq-cpcs">产品详情</span>';
				html += '</div>';
				html += '</div>';
				html += '<div>';
				html += '<img width="100%" src="'+bizProductDetail.detailImg+'"/>';
				html += '</div>';
				
				html += '<div class="mui-row cpxq">';
				html += '<div class="mui-col-sm-12 mui-col-xs-12">';
				html += '<span class="bk-red">&nbsp;</span>';
				html += '<span class="cpxq-cpcs">产品参数</span>';
				html += '</div>';
				html += '</div>';
						
				var productStandard = new Array();
				productStandard = bizProductDetail.specificationsList;
				for(var spec in productStandard){
					html += '<div class="prdcsxqdiv"><span class="prdcsxqdivs1">'+productStandard[spec].name+'</span><span class="prdcsxqdivs2">'+productStandard[spec].value+'</span></div>';
				}
				
				
				html += '</div>';
				
				$('#productDetailDiv').html(html);
				
				if(bizProductDetail.productType == 1){//支付购买
					$('#tjgwcgm').show();
				}
			}		
		}

	
		$('#promptBtn').on('tap', function(e) {
				var btnArray = ['取消', '确定'];
				mui.prompt('请输入你的购买数量：', 1, null, btnArray, function(e) {
					if (e.index == 1) {
						var num = e.value==""?1:e.value;
						if( isNaN(num)){
							mui.toast('请输入数字!');return;
						}
						num = (num|0);
						//增加num个
						updateShoppingCat(productId,num,0);
						mui.confirm('加入购物车成功，是否马上去结算？', '结算确认', btnArray, function(e) {
							if (e.index == 1) {
								console.log("跳转到购物车去结算");
								window.location.href = '/#cartTab';
							} else {
								console.log("nothing");
								//window.location.href = '/#cartTab';
							}
						})
					} else {
						console.log('你点了取消按钮');
					}
				},'div')
				document.querySelector('.mui-popup-input input').type='number';
			});
		 $('#checkoutBtn').on('tap', function(e) {
			updateShoppingCat(productId,1,0);
			console.log("跳转到购物车去结算");
			window.location.href = '/#cartTab';	
		});	
});
