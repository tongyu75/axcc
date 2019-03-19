$(function(){
	var userInfo;
	var pageCur =1;
	var pageSize =7;
	showAgent(1);
	function showAgent(page){
		//显示代理员列表
		$.ajax({
			type:"get",
			url:url+"/proxy/"+page+"/"+pageSize,
			crossDomain: true,
		    xhrFields: {
		        withCredentials: true
		    },
			success:function(result1){
				console.log(result1);
				var resultData = result1;	
				if(resultData.msg =="SUCCESS"){					
					//代理员列表
					var html="";
					pageCur = page;
					console.log(resultData.info);
					for(var i in resultData.info){
						var data = resultData.info[i];
						var proxyArea = '';
						if(data.proxyArea!=null&&data.proxyArea!=''){
							 proxyArea=data.proxyArea;						  
						}

						var userName = '';
						if(data.userName!=null&&data.userName!=''){
							userName=data.userName;	
							 //userName=data.userName.substr(0,1)+"**";						  
						}
						var userPhone = '';
						if(data.loginName!=null&&data.loginName!=''){
							 userPhone=data.loginName.substr(0,4)+"**"+data.loginName.substr(-2);						  
						}
							html += '<tr><td>'+proxyArea+'</td><td>'+userName+'</td><td>'+userPhone+'</td>';
							html += '<td><button type="button" data="'+data.id+'" class="gbtn del_btn">删除</button>';
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
				//代理员详情
				$(".det_btn").on("click",function(){
						var delId = $(this).attr("data");
						//console.log(delId);
						$.ajax({
							type:"get",
							url:url+"/proxy/"+delId,
							async:true,
							crossDomain: true,
					        xhrFields: {
					            withCredentials: true
					        },
							success:function(result){
								var data=result;
								if(data.msg =="SUCCESS"){
									console.log(data);
									var dataInfo = data.info;
									
									var  proxyArea = '';
									if(dataInfo.proxyArea!=null&&dataInfo.proxyArea!=''){
										 proxyArea=dataInfo.proxyArea;						  
									}
									var  userName = '';
									if(dataInfo.userName!=null&&dataInfo.userName!=''){
										 userName=dataInfo.userName;		
										 //userName=dataInfo.userName.substr(0,1)+"**";						  
									}
									var userPhone = '';
									if(dataInfo.loginName!=null&&dataInfo.loginName!=''){
										userPhone=dataInfo.loginName
										 //userPhone=dataInfo.loginName.substr(0,4)+"**"+dataInfo.loginName.substr(-2);						  
									}
									var card = '';
									if(dataInfo.card!=null&&dataInfo.card!=''&&dataInfo.card!='未认证'){
										card=dataInfo.card
										 //card=dataInfo.card.substr(0,4)+"**"+dataInfo.card.substr(-4);						  
									}else if(dataInfo.card=='未认证'){
										card=dataInfo.card
									}
									var bankCard = '';
									if(dataInfo.bankCard!=null&&dataInfo.bankCard!=''&&dataInfo.bankCard!='未认证'){
										bankCard=dataInfo.bankCard						  
									}else if(dataInfo.bankCard=='未认证'){
										bankCard=dataInfo.bankCard
									}
									var bankAddr = '';
									if(dataInfo.bankAddr!=null&&dataInfo.bankAddr!=''&&dataInfo.bankAddr!='未认证'){
										bankAddr=dataInfo.bankAddr						  
									}else if(dataInfo.bankAddr=='未认证'){
										bankAddr=dataInfo.bankAddr
									}
						          
						            	console.log(proxyArea);
						            $("#agentArea").html(proxyArea);
									$("#agentName").html(userName);
									$("#agentPhone").html(userPhone);
									$("#agentCode").html(card);
									$("#bankCard").html(bankCard);
									$("#bankAddr").html(bankAddr);
									
									
								}
							},
							error:function(){
								 Errfun3();
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
						url:url+"/proxy/"+delId,
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
	
	
	//添加按钮
	$("#addBtn").on('click',function(){
		location.href="addAgent.html";
	});
	//	取消弹框
	$(".closeLayer").on("click",function(){
		$("#popBox").css("display","none");
		$("#popLayer").css("display","none");
	})
	
	
})