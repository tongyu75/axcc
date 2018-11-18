$(function(){
	
	var pageSize =7;
	showAgent(1);

	//显示代理员 提现列表
	function showAgent(page){
		var data={};		
		data.pageNum = page;
		data.pageSize = pageSize;
		$.ajax({
			type:"post",
			url:url+"/listMoneyApply",
			data:data,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				console.log(result1);
				var resultData = result1;	
				if(resultData.msg =="SUCCESS"){			
					   
					   	var html ="";
					   //会员列表
						for(var i in resultData.info){
							var dataInfo = resultData.info[i];
							
							var userName="";						
							if(dataInfo.userName!=null&&dataInfo.userName!=""&&dataInfo.userName!=undefined){
								userName=dataInfo.userName;
							}
							var applyMoney="";						
							if(dataInfo.applyMoney!=null&&dataInfo.applyMoney!=""&&dataInfo.applyMoney!=undefined){
								applyMoney=dataInfo.applyMoney;
							}
							
							var applyTime="";						
							if(dataInfo.applyTime!=null&&dataInfo.applyTime!=""&&dataInfo.applyTime!=undefined){
								applyTime=dataInfo.applyTime.substr(0,10);
							}
							
							  html += '<tr><td>'+userName+'</td><td>'+applyMoney+'</td><td>'+applyTime+'</td><td>';
						    if(dataInfo.checkStatus==0){
						      html += '<button type="button" data="'+dataInfo.id+'" class="gbtn apply_btn">审核</button>';
							}
						      html += '<button type="button" data="'+dataInfo.id+'" class="gbtn detail_btn" style="margin-left:2px">详情</button></td></tr>';
						}
						$(".apply_List").html(html);
						var r = result1.total;
						r = Math.ceil(r / pageSize);
	//                  console.log(r);			
						$(".tcdPageCode").createPage({
							pageCount: r,
							current: page,
							backFn: function(p) {}
						});
						//审核 or 详情
						$(".detail_btn").on("click",function(){
							detailInfo(2,this)
						})
						$(".apply_btn").on("click",function(){
							detailInfo(1,this)
						})
						
						 //详情
					$(".detail_btn").on("click",function(){
						
												
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
	function detailInfo(status,e){
		console.log(status)
		console.log(e)
		$("#userName").html("");
						$("#userStatus").html("");
						$("#applyMoney").html("");
                        $("#checkStatus").html("");
						$("#applyTime").html("");	
						$(".message_s_btn").attr("data-id","");
						$(".message_btn").attr("data-id","");
						
							var delId = $(e).attr("data");
							console.log(delId);
							var data={};
							data.id = delId;
							$.ajax({
								type:"post",
								url:url+"/getMoneyApplyDetail",
								async:true,
								data:data,
								crossDomain: true,
			                    xhrFields: {withCredentials: true},
								success:function(result){
									var data=result;
									if(data.msg =="SUCCESS"){
										console.log(data);
										var dataInfo = data.info;
										//var  proxyName =dataInfo.userName.substr(0,1)+"**";
										var  userName =dataInfo.userName;
																	            
										var applyMoney = '0';
										if(dataInfo.applyMoney!=null&&dataInfo.applyMoney!=''){
											 applyMoney=dataInfo.applyMoney;						  
										}
										var  checkStatus='';
										if(dataInfo.checkStatus==0){
											    checkStatus ='未审核';
										}else if(dataInfo.checkStatus==1){
											    checkStatus ='审核通过';
										}else if(dataInfo.checkStatus==2){
											    checkStatus ='审核未通过';
										}
										var userStatus = '';
										if(dataInfo.userStatus==1){
											 userStatus="代理员";
										}else if(dataInfo.userStatus==2){
											 userStatus="普通会员";
										}
										var applyTime = '';
										if(dataInfo.applyTime!=null&&dataInfo.applyTime!=''){
											 applyTime=dataInfo.applyTime;						  
										}
																          						    							         
										$("#userName").html(userName);
										$("#userStatus").html(userStatus);
										$("#applyMoney").html(applyMoney);									
										$("#checkStatus").html(checkStatus);
										$("#applyTime").html(applyTime);
										if(status==1){
											$(".message_btn_wrap").css("display","block");	
											$(".message_s_btn").attr("data-id",delId);
											$(".message_btn").attr("data-id",delId);
										}else{
											$(".message_btn_wrap").css("display","none");												
										}																		
									}
								}
							});
							$("#popBox").css("display","block");
							$("#popLayer").css("display","block");	
	}
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
	$(".message_btn").on("click",function(){
		applyInfo(2)
	})
	$(".message_s_btn").on("click",function(){
		applyInfo(1)
	})
	 //	同意和不同意
	function applyInfo(status){
		var delId ="";
		if(status==2){
			 delId = $(".message_btn").attr("data-id");
		}else if(status==1){
			 delId = $(".message_s_btn").attr("data-id");
		}
		  console.log(delId);
		  var cStatus={};
		  cStatus.id = delId;
			cStatus.checkStatus = status;
			  console.log(cStatus);
		    $.ajax({
					type:"post",
					url:url+"/approveWithdrawCashes",
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
						}else{
							Errfun1(result1.msg);
						}
					}
				})
		
	}
})
