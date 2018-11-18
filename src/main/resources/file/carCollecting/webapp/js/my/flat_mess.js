/*会员查询*/
$(function(){
	var userInfo;
    var pageCur =1;
	var pageSize =7;
	showMessage(1);

	
	//显示会员列表
	function showMessage(page){
		
		var data={};
		data.pageNum = page;
		data.pageSize = pageSize;
		$.ajax({
			type:"post",
			url:url+"/listPageMessageByBean",
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
					
					/*resultData.info.forEach(function(item,index){
						console.log(item)
						
						var title = '';
						if(item.title!=null&&item.title!=''){
							console.log(item.title.length)
							if(item.title.length>12){
								title=item.title.substr(0,6)+"**"+item.title.substr(-2);		
							}else{
								title=item.title;
							}
							 				  
						}
						
							html += '<tr><td>'+(index+1)+'</td><td>'+title+'</td>';
							html += '<td><button type="button" data="'+item.id+'" class="gbtn del_btn">删除</button>';
							html += '<button type="button" data="'+item.id+'" class="gbtn det_btn">详情</button></td></tr>';						
					})
			        $(".show_List").html(html);*/
			        
			        var html ="";
					for(var i in resultData.info){
						html += '<div class="message-c"><div><div class="message-co"><img src="../../img/my/message.png" style="width:65%;"></div>';
		  				html += '<div style="text-align: center;font-size:18px;display: inline-block;line-height: 30px;font-weight: bold;">'+resultData.info[i].title+'</div></div>';
	  					html += '<div style="text-align: right;font-size:13px;margin-bottom:5px;">'+ resultData.info[i].createTime.substr(0,10)+'</div>';
	  					html += '<div style="color:#808080;font-size:14px;text-indent: 28px;word-break:break-all;word-wrap:break-word">'+resultData.info[i].contents+'</div></div>';
					}
					$(".message-con").html(html);
			        
			        
			        var pageTotal = result1.count;
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
								url:url+"/getMessageById",
								data:data,
								async:true,
								crossDomain: true,
			                    xhrFields: {withCredentials: true},
								success:function(result){
									var data=result;
									if(data.msg =="SUCCESS"){
										console.log(data);
										var dataInfo = data.info;
										var  title =dataInfo.title;
										var createTime = '';
										if(dataInfo.createTime!=null&&dataInfo.createTime!=''){
											 createTime=dataInfo.createTime.substr(0,10);						  
										}
										var contents = '';
										if(dataInfo.contents!=null&&dataInfo.contents!=''){
											 contents=dataInfo.contents;						  
										}
							          				    							         
										$(".message-title").html(title);
										$(".message-time").html(createTime);
										$(".message-content").html(contents);
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
								url:url+"/deleteMessageById",
						        data:{_method:"DELETE",},
								async:true,
								crossDomain: true,
		                     	xhrFields: {withCredentials: true},
								success:function(result){
								  var data=result;
								  if(data.msg=='SUCCESS'){
										showMessage(page);  																
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
})