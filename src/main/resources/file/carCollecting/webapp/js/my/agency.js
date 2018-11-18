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
			url:url+"/api/memberCenter/agentIncome",
			data:c,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result){
				var data=JSON.parse(result);
				if(data.success == true){
					$(".balance").html((data.obj.balance).toFixed(2)+"元");
					if(Number(data.obj.balance)<0){
						$(".tx_btn").prop("disabled",true).css("background","#808080");
					}
					if(data.obj.withdrawStatus == 1){
						$(".tx_btn").html("已提现").prop("disabled",true).css("background","#808080");
					}
					var html ="";
					for(var i in data.obj.incomeList){
						html += '<tr><td>'+data.obj.incomeList[i].recommendType+'级VIP</td><td>'+data.obj.incomeList[i].fromMemberName+'</td>';
						html += '<td>'+data.obj.incomeList[i].totalMoney+'</td><td>'+data.obj.incomeList[i].addTime+'</td></tr>';
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
//	申请提现
	$(".tx_btn").on("click",function(){
		$(".tx_btn").prop("disabled",true);
		var c={};
		c.logonToken=logonToken;
		c.memberInfoId=memberInfoId;
		$.ajax({
			type:"post",
			url:url+"/api/memberCenter/agentWithdraw",
			data:c,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result){
				var data=JSON.parse(result);
				$(".tx_btn").prop("disabled",false);
				if(data.success == true){
					$(".account_jf").html(data.obj.account+"元");
					$(".balance_jf").html(data.obj.balance+"元");
					$(".remark_jf").html(","+data.obj.remark);
					$(".rate_jf").html(data.obj.rate);
					$("#aaa").css("display","block");
					$("#ddd").css("display","block");
				}else{
					Errfun1(data.msg);
				}
			},
			error:function(){
				Errfun3();
			}
		});
	})
//	取消弹框
	$(".close1").on("click",function(){
		$("#aaa").css("display","none");
		$("#ddd").css("display","none");
	})
	$("#aaa").on("click",function(){
		$("#aaa").css("display","none");
		$("#ddd").css("display","none");
	})
	
//	确认提现
	$(".Modify_confirm").on("click",function(){
		$(".Modify_confirm").prop("disabled",true);
		var c={};
		c.logonToken=logonToken;
		c.memberInfoId=memberInfoId;
		$.ajax({
			type:"post",
			url:url+"/api/memberCenter/confirmAgentWithdraw",
			data:c,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result){
				var data=JSON.parse(result);
				$(".Modify_confirm").prop("disabled",false);
				if(data.success == true){
					alert(data.msg);
					$("#aaa").css("display","none");
					$("#ddd").css("display","none");
				}else{
					Errfun1(data.msg);
				}
			},
			error:function(){
				Errfun3();
			}
		});
	})
})
