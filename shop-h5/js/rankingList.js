$(function(){
	
	mui.init({
		swipeBack:true //启用右滑关闭功能
	});
	
	//获取排行榜信息 前3名
	$.ajax({
	   type: "POST",
	   url: "/rest/api/wxpublic/getRankingList",
	   async:false,
	   success: function(rankingListInfoList){
	   	   console.log(rankingListInfoList);
	   		if(rankingListInfoList){
	   			var html = '';
	   			for(var key in rankingListInfoList){
	   				var rankingListInfo = rankingListInfoList[key];
	   				console.log(rankingListInfo);
	   				var bizCustomer = rankingListInfo.bizCustomer;
	   				console.log(bizCustomer);
				    html +='<li class="mui-table-view-cell mui-media">';
				    html +='<a href="javascript:;">';
				    html +='<div  class="divrkA">';
				    html +='<span class="spanA" >'+(eval(key)+1)+':</span>';
				    html +='</div>';
				    html +='<div class="divrkB">';
				    html +='<span class="spanB" >'+bizCustomer.wechatNickname+'</span>';
				    html +='</div>';	        	
				    html +='<div class="divrkC">';
				    html +='<span><strong>'+formatData(rankingListInfo.amount)+'</strong></span>';
				    html +='</div>';	        	
				    html +='<div>';
				    html +='<img class="mui-media-object" src="'+bizCustomer.wechatHeadimgurl+'">';
				    html +='</div>';
				    html +='</a>';
				    html +='</li>';
	   			}	
	   			$("#rankListUl").html(html);
	   		}
	   }
	});

});
