/*会员查询*/
$(function(){
	var userInfo;
    var pageCur =1;
	var pageSize =7;
	showAgent(1);
	//查询按钮
	$("#searchBtn").on('click',function(){
		var searchText = $("#searchText").val();
		var searchType = $("#selType").val();
		console.log(searchText+"-------"+searchType);
		var data={};
		data.loginName = searchText;
		data.buyType = searchType;
		data.pageNum = pageCur;
		data.pageSize = pageSize;
//		data.buyStatus = '1';
		showAgent(1);
	});
	
	
	//显示会员列表
	function showAgent(page){
		var searchText = $("#searchText").val();
		var searchType = $("#selType").val();
		console.log(searchText+"-------"+searchType);
		var data={};
		data.phone = searchText;
		data.buyType = searchType;
		data.pageNum = page;
		data.pageSize = pageSize;
//		data.buyStatus = '1';
		$.ajax({
			type:"post",
			url:url+"/listWait",
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
					for(var i in resultData.info){
						var data = resultData.info[i];
						var userName = data.userName.substr(0,1)+"**";
						var waitNum = '';
						if(data.waitNum!=null&&data.waitNum!=''){
							 waitNum=data.waitNum;						  
						}
						var userPhone = '';
						if(data.loginName!=null&&data.loginName!=''){
							 userPhone=data.loginName.substr(0,4)+"**"+data.loginName.substr(-2);						  
						}
							html += '<tr><td>'+waitNum+'</td><td>'+userName+'</td><td>'+userPhone+'</td>';
							html += '<td><button type="button" data="'+data.id+'" class="gbtn take_btn">出车</button>&nbsp;';
							html += '<button type="button" data="'+data.id+'" class="gbtn det_btn">详情</button></td></tr>';						
					}
			        $(".show_List").html(html);
			        var pageTotal = result1.total;
					var pageNum = Math.ceil(pageTotal/pageSize);
					//生成页码
					$(".tcdPageCode").createPage({
							pageCount: pageNum,
							current: page,
							backFn: function(p) {}
					});
					//会员出车
					$(".take_btn").on("click",function(){
						var takeId = $(this).attr("data");
						console.log(takeId);
						var data={};
						data.businessId = takeId;
						$.ajax({
							type:"post",
							url:url+"/takeCar",
							data:data,
							async:true,
							crossDomain: true,
			                xhrFields: {withCredentials: true},
							success:function(result){
								var data=result;
								if(data.msg == "SUCCESS"){
									alert("出车成功！");
									showAgent(page);
								}
							},
							error:function(){
								Errfun3();
							}
						});
					});
				     //会员详情
					$(".det_btn").on("click",function(){
							var delId = $(this).attr("data");
							console.log(delId);
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
										var  dataInfo = data.result;
										var waitNum = '暂无';
										if(dataInfo.waitNum!=null&&dataInfo.waitNum!=''){
											 waitNum=dataInfo.waitNum;						  
										}										
										//var  proxyName =dataInfo.userName.substr(0,1)+"**";
										var  proxyName =dataInfo.userName;
						                var userPhone = '';
										if(dataInfo.loginName!=null&&dataInfo.loginName!=''){
											 userPhone=dataInfo.loginName;						  
										}
							            var card = '';
										if(dataInfo.card!=null&&dataInfo.card!=''&&dataInfo.card!='未认证'){
											 card=dataInfo.card;						  
										}else if(dataInfo.card=='未认证'){
											 card=dataInfo.card;
										}
							            var  buyMoney='0';
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
							          	$("#agentWaitName").html(waitNum);						    							         
										$("#agentName").html(proxyName);
										$("#agentPhone").html(userPhone);
										$("#agentCode").html(card);
										$("#agentMoney").html(buyMoney);
										$("#agentBuyType").html(buyType);
										$("#agentPay").html(buyStatus);
									}
								},
								error:function(){
										Errfun3();
								}
							});
							$("#popBox").css("display","block");
							$("#popLayer").css("display","block");
						});
					}else{
							Errfun1(result1.msg);
					}
					
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