/*会员查询*/
$(function(){
	var userInfo;
    var pageCur =1;
	var pageSize =7;
	showAgent(1);
	//查询按钮
	$("#searchBtn").on('click',function(){
		var searchText = $("#searchText").val();
		//console.log(searchText);
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
		data.phone = searchText;
		data.pageNum = page;
		data.pageSize = pageSize;
		$.ajax({
			type:"post",
			url:url+"/listBusinessNoMoney",
			data:data,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				//console.log(result1);
				var resultData = result1;	
				if(resultData.msg =="SUCCESS"){		
					
					//会员列表
					var html="";
					pageCur = page;
					//console.log(resultData.info);
					var array=[];
					for(var i in resultData.info){
					  	
						var data = resultData.info[i];
						var userName = data.userName;
						var parentId = '';
						if(data.parentId!=null&&data.parentId!=''){
							 parentId=data.parentId.substr(0,4)+"**"+data.parentId.substr(-2);						  
						}
						var userPhone = '';
						if(data.loginName!=null&&data.loginName!=''){
							 userPhone=data.loginName.substr(0,4)+"**"+data.loginName.substr(-2);						  
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
													
							html += '<tr><td>'+userName+'</td><td>'+userPhone+'</td><td>'+buyType+'</td>';
							html += '<td><button type="button" data="'+data.id+'" class="gbtn det_btn">办理</button></td></tr>';															
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
    function getUseVoucherInfo(id){
    	//查询优惠券
							var data1={};
							data1.userId = id;
                            console.log(data1);
							$.ajax({
								type:"post",
								url:url+"/getUseVoucherInfo",
								data:data1,
								async:true,
								crossDomain: true,
			                    xhrFields: {withCredentials: true},
								success:function(result){
								//console.log(result);
								 if(result.msg =="SUCCESS"){
									if(result.info!=null&&result.info!=""){
											var useMoney = result.info.voucherMoney;
											//voucherId = result.info.voucherId;
											$("#voucherMoneyHove").css("display","block");
											$("#voucherMoneyNo").css("display","none");
											$("#useMoney").html(useMoney);
											$("#useMoney").attr("data-id",result.info.id);
									}else{
										$("#voucherMoneyHove").css("display","none");
										$("#voucherMoneyNo").css("display","block");
									}
								  }
								},
								error:function(){
									Errfun3();
								}
							});
    }
			      			        
	//会员办理弹出框
	$(".car").on("click",".det_btn",function(){
						   
		//初始化数据
		$("#voucherMoneyHove").css("display","none");
		$("#agentName").html("");
		$("#agentPhone").html("");
		$("#agentCode").html("");										
		$("#agentbuyType").html("");
		$("#agentapplyTime").html("");
		$("#buyMoney").val("");
		$(".agentInfo .message_s_btn").attr("data-id","");
							
		var delId = $(this).attr("data");
		//console.log(delId);
		var voucherUserId = "";
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
					var  proxyName =dataInfo.userName.substr(0,1)+"**";				
					getUseVoucherInfo(dataInfo.userId)
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
						    buyType ='十万车型';
					}else if(dataInfo.buyType==2){
						    buyType ='二十万车型';
					}else if(dataInfo.buyType==3){
						    buyType ='三十万车型';
					}else if(dataInfo.buyType==4){
							buyType ='四十万车型';
					}else if(dataInfo.buyType==5){
							buyType ='五十万车型';
					}																														
					var applyTime = '';
					if(dataInfo.applyTime!=null&&dataInfo.applyTime!=''){
							applyTime=dataInfo.applyTime.substr(0,10);						  
					}															
					$("#agentName").html(proxyName);
					$("#agentPhone").html(userPhone);
					$("#agentCode").html(card);
					//$("#agentMoney").html(buyMoney);
					$(" #agentbuyType").html(buyType);
					$(" #agentapplyTime").html(applyTime);
					$(".agentInfo .message_s_btn").attr("data-id",delId);		          							    							         
										
				}
			},
			error:function(){
					Errfun3();
			}
		});
							
							$("#popBox").css("display","block");
							$("#popLayer").css("display","block");
						});
						// 返回
						$(".agentInfo .message_btn").on("click",function(){
							$("#popBox").css("display","none");
							$("#popLayer").css("display","none");
						})
						//办理
						$(".agentInfo .message_s_btn").on("click",function(){
							var delId = $(".agentInfo .message_s_btn").attr("data-id");
							var data={};
							var dataMoney={};
							//优惠券
							var useMoney="";
							var voucherId="";
		                    data.userId = delId;
		                    //console.log(data);
		                                dataMoney.id = delId;
										//dataMoney.useMoney = useMoney;
										dataMoney.buyMoney = $("#buyMoney").val();
										var  voucherUsed = 0;
										var voucherUsedC =$("input[name='radio1']:checked").val()
								
										if(voucherUsedC!=undefined&&voucherUsedC!=null&&voucherUsedC!=''){
											voucherUsed = $("input[name='radio1']:checked").val();
										}
										dataMoney.voucherUsed = voucherUsed;
										if(dataMoney.voucherUsed ==1){
											dataMoney.voucherId = $("#useMoney").attr("data-id");
										}
										dataMoney.agentId = JSON.parse(sessionStorage.getItem('userInfo')).id
										//代理员 办理缴费业务
										 $.ajax({
											type:"post",
											url:url+"/updateBuyMoney",
											data:dataMoney,
											async:true,
											crossDomain: true,
			                                xhrFields: {withCredentials: true},
											success:function(result){
											//console.log(result);
												if(result.msg =="SUCCESS"){
													showAgent(1);
													alert("办理成功")
													$("#popBox").css("display","none");
													$("#popLayer").css("display","none");
												}else{
													Errfun1(result1.msg);
												}
											},
											error:function(){
												alert("未知异常");
											}
										})
							 
							$("#popBox").css("display","none");
							$("#popLayer").css("display","none");
						})

    //	取消弹框
	$(".closeLayer").on("click",function(){
		$("#popBox").css("display","none");
		$("#popLayer").css("display","none");
	})
})
