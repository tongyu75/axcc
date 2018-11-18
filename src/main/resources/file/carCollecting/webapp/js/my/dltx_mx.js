$(function(){
	var obj1 = JSON.parse(sessionStorage.getItem('dd'));
	var logonToken=obj1.obj.token;
	var memberInfoId=obj1.obj.memberId;
	var rows=10;
	function jj(page){
		var c={};
		c.logonToken=logonToken;
		c.memberInfoId=memberInfoId;
		c.rows=rows;
		c.page=page;
		$.ajax({
			type:"post",
			url:url+"/api/memberCenter/agentWithdrawList",
			data:c,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result){
				var data=JSON.parse(result);
				if(data.success == true){
					var html ="";
					for(var i in data.obj.withdrawList){
						html += '<tr><td>'+data.obj.withdrawList[i].cashDate+'</td><td>'+data.obj.withdrawList[i].money+'元</td>';
						if(data.obj.withdrawList[i].status == 1){
							var hh1="待发放";
							var hh2="-";
						}else{
							var hh1="已发放";
							var hh2=data.obj.withdrawList[i].operatorDate;
						}
						html += '<td>'+hh1+'</td><td>'+hh2+'</td></tr>';
					}
					$(".income").html(html);
					var r = data.obj.total;
					r = Math.ceil(r / rows);
//                  console.log(r);			
					$(".tcdPageCode").createPage({
						pageCount: r,
						current: page,
						backFn: function(p) {}
					});
				}else{
					//Errfun2(data.msg);
				}
			},
			error:function(){
				Errfun3();
			}
		});
	}
	jj(1);
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
