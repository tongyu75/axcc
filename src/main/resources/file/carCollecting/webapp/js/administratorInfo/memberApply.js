/*会员申请列表*/
$(function(){
	var userInfo;
    var pageCur =1;
	var pageSize =7;
	showAgent(1);
	
	//显示会员申请列表
	function showAgent(page){
		var data={};
		data.phone = '';
		data.pageNum = page;
		data.pageSize = pageSize;
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
						
						var userPhone = '';
						if(data.loginName!=null&&data.loginName!=''){
							 userPhone=data.loginName.substr(0,4)+"**"+data.loginName.substr(-2);						  
						}
							html += '<tr><td>'+userName+'</td><td>'+userPhone+'</td><td>'+buyType+'</td>';
							if(data.checkStatus==0){
							   html += '<td><button type="button" data="'+data.id+'" class="gbtn apply_btn">审核</button></td></tr>';
							}else if(data.checkStatus==1){
							   html += '<td><button type="button" data="'+data.id+'" class="gbtn detail_btn">详情</button></td></tr>';
							}else if(data.checkStatus==2){
							   html += '<td><button type="button" data="'+data.id+'" class="gbtn order_btn">排号</button></td></tr>';
							}
							
							//html += '<button type="button" data="'+data.id+'" class="gbtn det_btn">详情</button></td></tr>';						
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
				     //会员详情
					$(".detail_btn").on("click",function(){
						
						$(".agent_d_Info #agentName").html("");
						$(".agent_d_Info #agentPhone").html("");
						$(".agent_d_Info #agentCode").html("");
                        $(".agent_d_Info #agentbuyType").html("");
						$(".agent_d_Info #agentPay").html("");
						$(".agent_d_Info #agentapplyTime").html("");
						$(".agent_d_Info #agentcheckTime").html("");		
						
							var delId = $(this).attr("data");
							console.log(delId);
							var data={};
							data.id = delId;
							$.ajax({
								type:"post",
								url:url+"/businessDetail",
								async:true,
								data:data,
								crossDomain: true,
			                    xhrFields: {withCredentials: true},
								success:function(result){
									var data=result;
									if(data.msg =="SUCCESS"){
										console.log(data);
										var dataInfo = data.result;
										//var  proxyName =dataInfo.userName.substr(0,1)+"**";
										var  proxyName =dataInfo.userName;
										var userPhone = '';
										if(dataInfo.loginName!=null&&dataInfo.loginName!=''){
											 //userPhone=dataInfo.loginName.substr(0,4)+"**"+dataInfo.loginName.substr(-2);		
											 userPhone=dataInfo.loginName;						  
										}
							            var card = '';
										if(dataInfo.card!=null&&dataInfo.card!=''&&dataInfo.card!='未认证'){
											 //card=dataInfo.card.substr(0,4)+"**"+dataInfo.card.substr(-4);		
											 card=dataInfo.card;	
										}else if(dataInfo.card=='未认证'){
											 card=dataInfo.card;
										}
										/*var buyMoney = '';
										if(dataInfo.buyMoney!=null&&dataInfo.buyMoney!=''){
											 buyMoney=dataInfo.buyMoney+"(元)";						  
										}*/
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
										var checkStatus = '';
										if(dataInfo.checkStatus==2){
											 checkStatus="缴费";
										}else{
											 checkStatus="未缴费";
										}
										var applyTime = '';
										if(dataInfo.applyTime!=null&&dataInfo.applyTime!=''){
											 applyTime=dataInfo.applyTime.substr(0,10);						  
										}
										var checkTime = '';
										if(dataInfo.checkTime!=null&&checkTime.payTime!=''){
											 checkTime=dataInfo.checkTime.substr(0,10);						  
										}							          						    							         
										$(".agent_d_Info #agentName").html(proxyName);
										$(".agent_d_Info #agentPhone").html(userPhone);
										$(".agent_d_Info #agentCode").html(card);
										//$("#agentMoney").html(buyMoney);
										$(".agent_d_Info #agentbuyType").html(buyType);
										$(".agent_d_Info #agentPay").html(checkStatus);
										$(".agent_d_Info #agentapplyTime").html(applyTime);
										$(".agent_d_Info #agentcheckTime").html(checkTime);										
									}
								}
							});
							$("#popBox").css("display","block");
							$("#popLayer").css("display","block");
							$(".agent_d_Info").css("display","block");
							$(".agent_a_Info").css("display","none");
							$(".agent_o_Info").css("display","none");
						});
						//审核当前项
						$(".apply_btn").on("click",function(){
							
							$(".agent_a_Info #agentName").html("");
							$(".agent_a_Info #agentPhone").html("");
							$(".agent_a_Info #agentCode").html("");
							$(".agent_a_Info #agentbuyType").html("");
						    $(".agent_a_Info #agentTime").html("");
							$(".agent_a_Info .message_s_btn").attr("data-id","");
							$(".agent_a_Info .message_btn").attr("data-id","");
							
							var delId = $(this).attr("data");
						    console.log(delId)
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
										//var  proxyName =dataInfo.userName.substr(0,1)+"**";
										var  proxyName =dataInfo.userName;
										var userPhone = '';
										if(dataInfo.loginName!=null&&dataInfo.loginName!=''){
											 //userPhone=dataInfo.loginName.substr(0,4)+"**"+dataInfo.loginName.substr(-2);				
											 userPhone=dataInfo.loginName;	
										}
							            var card = '';
										if(dataInfo.card!=null&&dataInfo.card!=''&&dataInfo.card!='未认证'){
											//card=dataInfo.card.substr(0,4)+"**"+dataInfo.card.substr(-4);	
											card=dataInfo.card;
										}else if(dataInfo.card=='未认证'){
											card=dataInfo.card;
										}
										/*var buyMoney = '';
										if(dataInfo.buyMoney!=null&&dataInfo.buyMoney!=''){
											 buyMoney=dataInfo.buyMoney+"(元)";					  
										}*/	
										var applyTime = '';
										if(dataInfo.applyTime!=null&&dataInfo.applyTime!=''){
											 applyTime=dataInfo.applyTime.substr(0,10);						  
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
												          							    							         
										$(".agent_a_Info #agentName").html(proxyName);
										$(".agent_a_Info #agentPhone").html(userPhone);
										$(".agent_a_Info #agentCode").html(card);
										//$(".agent_a_Info #agentMoney").html(buyMoney);
										$(".agent_a_Info #agentbuyType").html(buyType);
										$(".agent_a_Info #agentTime").html(applyTime);
										$(".agent_a_Info .message_s_btn").attr("data-id",delId);
										$(".agent_a_Info .message_btn").attr("data-id",delId);
									}
								},
								error:function(){
										Errfun3();
								}
						   });
						    $("#popBox").css("display","block");
							$("#popLayer").css("display","block");
							$(".agent_d_Info").css("display","none");
							$(".agent_a_Info").css("display","block");
							$(".agent_o_Info").css("display","none");
						});
						 //会员排号
					   $(".order_btn").on("click",function(){
					   	 
					   	    $(".agent_o_Info #agentName").html("");
							$(".agent_o_Info #agentPhone").html("");
							$(" .agent_o_Info #agentCode").html("");
							$(".agent_o_Info #agentbuyType").html("");
							$(".agent_o_Info #lineNum").val("");
							$(".agent_o_Info .message_s_btn").attr("data-id","");
					   	    
							var delId = $(this).attr("data");
							console.log(delId);
							var data={};
							data.id = delId;
							$.ajax({
								type:"post",
								url:url+"/businessDetail",
								async:true,
								data:data,
								crossDomain: true,
			                    xhrFields: {withCredentials: true},
								success:function(result){
									var data=result;
									if(data.msg =="SUCCESS"){
										console.log(data);
										var dataInfo = data.result;
										//var  proxyName =dataInfo.userName.substr(0,1)+"**";
										var  proxyName =dataInfo.userName;
										var userPhone = '';
										if(dataInfo.loginName!=null&&dataInfo.loginName!=''){
											// userPhone=dataInfo.loginName.substr(0,4)+"**"+dataInfo.loginName.substr(-2);	
											 userPhone=dataInfo.loginName;
										}
							            var card = '';
										if(dataInfo.card!=null&&dataInfo.card!=''&&dataInfo.card!='未认证'){
											 //card=dataInfo.card.substr(0,4)+"**"+dataInfo.card.substr(-4);		
											 card=dataInfo.card;
										}else if(dataInfo.card=='未认证'){
											 card=dataInfo.card;
										}
										/*var buyMoney = '';
										if(dataInfo.buyMoney!=null&&dataInfo.buyMoney!=''){
											 buyMoney=dataInfo.buyMoney+"(元)";						  
										}*/
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
										var waitNum=""
										if(dataInfo.waitNum!=null&&dataInfo.waitNum!=''){
											waitNum = dataInfo.waitNum
										}
							          							    							         
										$(".agent_o_Info #agentName").html(proxyName);
										$(".agent_o_Info #agentPhone").html(userPhone);
										$(" .agent_o_Info #agentCode").html(card);
										//$("#agentMoney").html(buyMoney);
										$(".agent_o_Info #agentbuyType").html(buyType);
										$(".agent_o_Info #lineNum").val(waitNum);
										$(".agent_o_Info .message_s_btn").attr("data-id",delId);
									}
								},
								error:function(){
										Errfun3();
								}
							});
							$("#popBox").css("display","block");
							$("#popLayer").css("display","block");
							$(".agent_d_Info").css("display","none");
							$(".agent_a_Info").css("display","none");
							$(".agent_o_Info").css("display","block");
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
	//审核
	$(".agent_a_Info .message_btn").on("click",function(){
		applyInfo(4)
	})
	$(".agent_a_Info .message_s_btn").on("click",function(){
		applyInfo(1)
	})
	 //	同意和不同意
	function applyInfo(status){
		var delId ="";
		if(status==4){
			 delId = $(".agent_a_Info .message_btn").attr("data-id");
		}else if(status==1){
			 delId = $(".agent_a_Info .message_s_btn").attr("data-id");
		}
		  console.log(delId);
		  var cStatus={};
		  cStatus.id = delId;
			cStatus.checkStatus = status;
			  console.log(cStatus);
		    $.ajax({
					type:"post",
					url:url+"/updateCheckStatus",
					data:cStatus,
					async:true,
					crossDomain: true,
			        xhrFields: {withCredentials: true},
					success:function(result){
					console.log(result);
						if(result.msg =="SUCCESS"){
							showAgent(1);
							$("#popBox").css("display","none");
							$("#popLayer").css("display","none");
							$(".agent_d_Info").css("display","none");
							$(".agent_a_Info").css("display","none");
							$(".agent_o_Info").css("display","none");
						}else{
							Errfun1(result1.msg);
						}
					}
				})
		
	}
	//排号 返回
	$(".agent_o_Info .message_btn").on("click",function(){
		$("#popBox").css("display","none");
		$("#popLayer").css("display","none");
		$(".agent_d_Info").css("display","none");
		$(".agent_a_Info").css("display","none");
		$(".agent_o_Info").css("display","none");
	})
	//确定
	$(".agent_o_Info .message_s_btn").on("click",function(){
		    var delId = $(".agent_o_Info .message_s_btn").attr("data-id");
		    var waitNum = $("#lineNum").val();
		    var data={};
		    data.id = delId;
		    data.waitNum = waitNum;
	      	 $.ajax({
					type:"post",
					url:url+"/updateWaitNum",
					data:data,
					async:true,
					crossDomain: true,
			        xhrFields: {withCredentials: true},
					success:function(result){
					console.log(result);
						if(result.msg =="SUCCESS"){
							showAgent(1);
							alert("排号成功")
							$("#popBox").css("display","none");
							$("#popLayer").css("display","none");
							$(".agent_d_Info").css("display","none");
							$(".agent_a_Info").css("display","none");
							$(".agent_o_Info").css("display","none");
						}else if(result.code=="2"){
							alert("排号已存在，请重新输入");	
						}else{
							Errfun1(result1.msg);
						}
					}
				})
	})
})