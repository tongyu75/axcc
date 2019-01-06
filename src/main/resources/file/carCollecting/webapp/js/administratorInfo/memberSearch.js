/*会员查询*/
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
		console.log(searchText);
		var data={};
		data.phone = searchText;
		data.pageNum = page;
		data.pageSize = pageSize;
		$.ajax({
			type:"post",
			url:url+"/getAllUserList",
			//url:url+"/listWait",
			data:data,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
			//	console.log(result1);
				var resultData = result1;	
				if(resultData.msg =="SUCCESS"){					
					//会员列表
					var html="";
					pageCur = page;
				//	console.log(resultData.info);
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
							html += '<tr><td>'+userName+'</td><td>'+userPhone+'</td><td>'+parentId+'</td>';
							html += '<td><button type="button" data="'+data.loginName+'" class="gbtn del_btn">删除</button>';
							html += '<button type="button" data-userName="'+data.userName+'" data-userPhone="'+data.loginName+'" data-parentId="'+data.parentId+'"  data-card="'+data.card+'" data-status="'+data.buyStatus+'" data="'+data.loginName+'" class="gbtn det_btn">详情</button></td></tr>';						
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
					$(".det_btn").on("click",function(){
							var loginName = $(this).attr("data");
							var userName1 = $(this).parent().parent().find("td").eq(2).text()
						//	console.log(userName1);
						//	console.log(loginName);
						//	console.log($(this).attr("data-status"));
							$("#agentName").html($(this).attr("data-userName"));
							$("#agentPhone").html($(this).attr("data-userPhone"));
							$("#agentCode").html($(this).attr("data-card"));
							$("#parentPhone").html($(this).attr("data-parentId"));
							$("#agentPay").html($(this).attr("data-status"));
							
							/*var data={};
							data.loginName = loginName;
							$.ajax({
								type:"post",
								url:url+"/memberByLoginName",
								data:data,
								async:true,
								success:function(result){
									var data=result;
									if(data.msg =="SUCCESS"){
										console.log(data);
										var dataInfo = data.info;
										var  proxyName =dataInfo.userName.substr(0,1)+"**";
										var userPhone = '';
										if(dataInfo.loginName!=null&&dataInfo.loginName!=''){
											 userPhone=dataInfo.loginName.substr(0,4)+"**"+dataInfo.loginName.substr(-2);						  
										}
							            var card = '';
										if(dataInfo.card!=null&&dataInfo.card!=''&&dataInfo.card!='未认证'){
											 card=dataInfo.card.substr(0,4)+"**"+dataInfo.card.substr(-4);						  
										}else if(dataInfo.card=='未认证'){
											 card=dataInfo.card
										}
										var buyMoney = '';
										if(dataInfo.buyMoney!=null&&dataInfo.buyMoney!=''){
											 buyMoney=dataInfo.buyMoney+"(元)";						  
										}
										var buyStatus = '';
										if(dataInfo.buyStatus==0){
											 buyStatus="未排队";
										}else if(dataInfo.buyStatus==1){
											 buyStatus="排队中";
										}else if(dataInfo.buyStatus==2){
											 buyStatus="已出车";
										}
										
							          							    							         
										$("#agentName").html(proxyName);
										$("#agentPhone").html(userPhone);
										$("#agentCode").html(card);
										//$("#agentMoney").html(buyMoney);
										$("#agentPay").html(buyStatus);
										
									}
								},
								error:function(){
										Errfun3();
								}
							});*/
							$("#popBox").css("display","block");
							$("#popLayer").css("display","block");
						});
						//删除当前项
						$(".del_btn").on("click",function(){
							//var delId = $(this).attr("data");
							/*$.ajax({
								type:"post",
								url:url+"/member/"+delId,
						        data:{_method:"DELETE",},
								async:true,
								success:function(result){
								  var data=result;
								  if(data.msg=='SUCCESS'){
										showAgent(page);  																
								   }
								},
								error:function(){
										Errfun3();
								}
						   });	*/	
						   var loginName = $(this).attr("data");
						   $.ajax({
								type:"post",
								url:url+"/delUserByLoginName?loginName="+loginName,
						        data:{_method:"DELETE",},
								async:true,
								crossDomain: true,
			                    xhrFields: {withCredentials: true},
								success:function(result){
								  var data=result;
								  if(data.msg=='SUCCESS'){
								  	   alert("删除成功");
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