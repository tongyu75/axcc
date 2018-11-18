    var pageCur =1;
	var pageSize =7;
	//车类型
	var buyType=GetQueryString("typeNum");
	function GetQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
;$(function(){
	
	showCarList(1);
   
	console.log(buyType);
	var carType =""
	if(buyType==1){ 
		carType ='【五进一  10万车型】';
	}else if(buyType==2){
		carType ='【五进一 20万车型】';
	}else if(buyType==3){
		carType ='【五进一  30万车型】';
	}else if(buyType==4){
		carType ='【五进一  40万车型】';
	}else if(buyType==5){
		carType ='【五进一  50万车型】';
	}
	$(".title_li").html(carType);
	//显示会员列表
	function showCarList(page){
		
		var data={};
		data.buyType = buyType;
		data.pageNum = page;
		data.pageSize = pageSize;
		//console.log(data)
		$.ajax({
			type:"post",
			url:url+"/listBuyTypeUser",
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
					console.log(resultData.info);
					for(var i in resultData.info){
						var data = resultData.info[i];
						var waitNum = '';
						if(data.waitNum!=null&&data.waitNum!=''){
							waitNum=data.waitNum;						  
						}
						var userName = data.userName.substr(0,1)+"**";
						
						var userPhone = '';
						if(data.loginName!=null&&data.loginName!=''){
							 userPhone=data.loginName.substr(0,4)+"**"+data.loginName.substr(-2);						  
						}
							html += '<tr><td>'+waitNum+'</td><td>'+userName+'</td><td>'+userPhone+'</td>';
							
							html += '<td><button type="button" data="'+data.id+'" class="gbtn det_btn">查看详情</button></td></tr>';						
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
						
					    	$("#agentwaitNum").html("");						    							         
							$("#agentName").html("");
							$("#agentPhone").html("");
							$("#agentCode").html("");
							$("#agentbuyStatus").html("");
						
							var delId = $(this).attr("data");						
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
										//console.log(data);
										var dataInfo = data.result;
										var waitNum = '';
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
										
							          	$("#agentwaitNum").html(waitNum);						    							         
										$("#agentName").html(proxyName);
										$("#agentPhone").html(userPhone);
										$("#agentCode").html(card);
										//$("#agentMoney").html(buyMoney);
										$("#agentbuyStatus").html(buyStatus);
										
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
		showCarList(page);  
	})

    //	取消弹框
	$(".closeLayer").on("click",function(){
		$("#popBox").css("display","none");
		$("#popLayer").css("display","none");
	})

})
