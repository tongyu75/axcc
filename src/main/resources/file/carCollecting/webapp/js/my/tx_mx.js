$(function(){
	
	
	var pageSize =7;
	showAgent(1);

	//显示提现明细
	function showAgent(page){
		var data={};
		
		data.userId = JSON.parse(sessionStorage.getItem('userInfo')).id;
		//data.userId = "33";
		data.pageNum = page;
		data.pageSize = pageSize;
		$.ajax({
			type:"post",
			url:url+"/getWithdrawCashes",
			data:data,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				console.log(result1);
				var resultData = result1;	
				if(resultData.msg =="SUCCESS"){			
					    var  dataInfo =resultData.info;
					   	var html ="";
					   //会员列表
						for(var i in dataInfo){
							var  applyTime1="";
							var  applyTime2="";
							if(dataInfo[i].applyTime!=null&&dataInfo[i].applyTime!=""){
								applyTime1 = dataInfo[i].applyTime.substr(0,10);
							}
							var  createTime1="";
							var  createTime2="";
							if(dataInfo[i].createTime!=null&&dataInfo[i].createTime!=""){
								createTime1 = dataInfo[i].createTime.substr(0,10);
							}
							var  checkStatus="";
							if(dataInfo[i].checkStatus==1){
								checkStatus = "已发放";
							}else if(dataInfo[i].checkStatus==0){
								checkStatus = "审核中";
							}else if(dataInfo[i].checkStatus==2){
								checkStatus = "审核未通过";
							}
							
							html += '<tr><td>'+applyTime1+'</td><td>'+dataInfo[i].applyMoney+'</td>';
						    html += '<td>'+checkStatus+'</td><td>'+createTime1+'</td></tr>';
						}
						$(".income").html(html);
						var r = result1.total;
						r = Math.ceil(r / pageSize);
	//                  console.log(r);			
						$(".tcdPageCode").createPage({
							pageCount: r,
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
		jj(page);  
	})
})
