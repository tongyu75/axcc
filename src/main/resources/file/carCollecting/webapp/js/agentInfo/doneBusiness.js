/*已办业务查询*/
$(function(){
	var userInfo;
    var pageCur =1;
	var pageSize =7;
	showAgent(1);
	//查询按钮
	$("#searchBtn").on('click',function(){
		var searchText = $("#searchText").val();
		console.log(searchText);
		var data={};
		data.loginName = searchText;
		data.pageNum = pageCur;
		data.pageSize = pageSize;
		showAgent(1);
	});
	
	
	//显示会员列表
	function showAgent(page){
		var searchText = $("#searchText").val();
		//console.log(searchText);
		var data={};
		var loginName = JSON.parse(sessionStorage.getItem('userInfo')).loginName;
		data.loginName = loginName;
		data.phone = searchText;
		data.pageNum = page;
		data.pageSize = pageSize;
		$.ajax({
			type:"post",
			url:url+"/listBusinessByAgent",
			data:data,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				console.log(result1);
				var resultData = result1;	
				if(resultData.msg =="SUCCESS"){					
					//会员列表
					var html="";
					pageCur = page;
					console.log(resultData.info);
					var array=[];
					for(var i in resultData.info){
						var data = resultData.info[i];
						var userName = data.userName.substr(0,1)+"**";

						var userPhone = '';
						if(data.loginName!=null&&data.loginName!=''){
							 userPhone=data.loginName.substr(0,4)+"**"+data.loginName.substr(-2);						  
						}
						var buyMoney = '';
						if(data.buyMoney!=null&&data.buyMoney!=''){
							 buyMoney=data.buyMoney;
						}
						var  buyType='';
						if(data.buyType==1){
							    buyType ='十万车型';
						}else if(data.buyType==2){
							    buyType ='二十万车型';
						}else if(data.buyType==3){
							    buyType ='三十万车型';
						}else if(data.buyType==4){
							    buyType ='四十万车型';
						}else if(data.buyType==5){
							    buyType ='五十万车型';
						}
						if(data.checkStatus==2||data.checkStatus==3){
							html += '<tr><td>'+userName+'</td><td>'+userPhone+'</td><td>'+buyMoney+'</td>';
							html += '<td><button type="button" data="'+data.id+'" class="gbtn det_btn">详情</button></td></tr>';						
						}
					}
					console.log(array)
			        $(".show_List").html(html);
			        var pageTotal = result1.total;
					var pageNum = Math.ceil(pageTotal/pageSize);
					//生成页码
					$(".tcdPageCode").createPage({
							pageCount: pageNum,
							current: page,
							backFn: function(p) {}
					});
				     //会员详情
					$(".det_btn").on("click",function(){
							var delId = $(this).attr("data");
							console.log(delId);
							var data={};
							data.id = delId;
							$.ajax({
								type:"post",
								url:url+"/businessDetail",
								data:data,
								async:true,
								crossDomain: true,
			                    xhrFields: {withCredentials: true},
								success:function(result){
									var data=result;
									if(data.msg =="SUCCESS"){
										console.log(data);
										var dataInfo = data.result;
										var waitNum = '暂无';
										if(dataInfo.waitNum!=null&&dataInfo.waitNum!=''){
											 waitNum=dataInfo.waitNum;						  
										}
										var  proxyName =dataInfo.userName.substr(0,1)+"**";
										var userPhone = '';
										if(dataInfo.loginName!=null&&dataInfo.loginName!=''){
											 userPhone=dataInfo.loginName.substr(0,4)+"**"+dataInfo.loginName.substr(-2);						  
										}
							            var card = '';
										if(dataInfo.card!=null&&dataInfo.card!=''&&dataInfo.card!='未认证'){
											 card=dataInfo.card.substr(0,4)+"**"+dataInfo.card.substr(-4);						  
										}else if(dataInfo.card=='未认证'){
											 card=dataInfo.card;
										}
										var buyMoney = '';
										if(dataInfo.buyMoney!=null&&dataInfo.buyMoney!=''){
											 buyMoney=dataInfo.buyMoney+"(元)";						  
										}
										var  buyType='';
										if(dataInfo.buyType==1){
											    buyType ='五进一十万车型';
										}else if(dataInfo.buyType==2){
											    buyType ='五进一二十万车型';
										}else if(dataInfo.buyType==3){
											    buyType ='五进一三十万车型';
										}else if(dataInfo.buyType==4){
											    buyType ='五进一四十万车型';
										}else if(dataInfo.buyType==5){
											    buyType ='五进一五十万车型';
										}
										var buyStatus = '';
										if(dataInfo.buyStatus==0){
											 buyStatus="未排队";
										}else if(dataInfo.buyStatus==1){
											 buyStatus="排队中";
										}else if(dataInfo.buyStatus==2){
											 buyStatus="已出车";
										}
										var payTime = '';
										if(dataInfo.payTime!=null&&dataInfo.payTime!=''){
											 payTime=dataInfo.payTime.substr(0,10);						  
										}
							          	$("#agentwaitNum").html(waitNum);						    							         
										$("#agentName").html(proxyName);
										$("#agentPhone").html(userPhone);
										$("#agentCode").html(card);
										$("#agentMoney").html(buyMoney);
										$("#agentbuyType").html(buyType);
										$("#agentbuyStatus").html(buyStatus);
										$("#agentpayTime").html(payTime);
										
									}
								}
							});
							$("#popBox").css("display","block");
							$("#popLayer").css("display","block");
						});
						//删除当前项
						$(".del_btn").on("click",function(){
							var delId = $(this).attr("data");
						    console.log(delId)
							$.ajax({
								type:"post",
								url:url+"/member/"+delId,
						        data:{_method:"DELETE",},
								async:true,
								crossDomain: true,
			                    xhrFields: {withCredentials: true},
								success:function(result){
								  var data=result;
								  if(data.msg=='SUCCESS'){
										showAgent(page);  																
								   }
								},
								error:function(){
									Errfun3();
								}
						   });			
						});
					}else{
							Errfun1(result1.msg);
					}
					
			},
			error:function(){
				Errfun3();
			}
		})
	}
	
	//翻页
	$(".tcdPageCode").on("click",'a',function(){
        var h=$(this).html();
        var page ="";
        if(h=="下一页"){
                var t= $('.tcdPageCode  span.current').html();
                t=parseInt(t);
                page=t+1;
        }else if(h=="上一页"){
                var t= $('.tcdPageCode  span.current').html();
                t=parseInt(t);
                page=t-1;
        }else{
                h=parseInt(h);
                page=h;
        }
		showAgent(page);  
	})

    //	取消弹框
	$(".closeLayer").on("click",function(){
		$("#popBox").css("display","none");
		$("#popLayer").css("display","none");
	})
})