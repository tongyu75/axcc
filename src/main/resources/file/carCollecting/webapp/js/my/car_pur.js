$(function(){
	/*var obj1 = JSON.parse(sessionStorage.getItem('dd'));
	var logonToken=obj1.obj.token;
	var memberInfoId=obj1.obj.memberId;
	var c={};
	c.logonToken=logonToken;
	c.memberInfoId=memberInfoId;*/
	var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	var data={};
	data.userId = userInfo.id;
	$.ajax({
		type:"post",
		url:url+"/getListVoucherByUserId",
		data:data,
		async:true,
		crossDomain: true,
		xhrFields: {withCredentials: true},
		success:function(result){
			console.log(result);
			var  data =result;
			if(data.msg == 'SUCCESS'){
			
				var html="";
				var moneyTotal =0;
				for(var i in data.info){
					moneyTotal +=data.info[i].voucherMoney;
					html +='<div class="to-user-b" style="border-radius: 5px;background: #0099CC;margin-bottom:15px;">';
					
					html += '<div class="to-user-1"><div class="money-1" style="color:#fff;">￥'+data.info[i].voucherMoney+'</div>';
					html += '<div class="money-2" align="center" style="color:#fff;">抵用券</div></div>';
					html += '<div class="to-user-2"><div class="money-3" style="color:#fff;">爱心筹车购车基金</div>';
					var voucherTime=data.info[i].voucherTime.substr(0,10);
					html += '<div class="money-4" >'+voucherTime+'</div><span class="lineM">—</span>';
					html += '<div class="money-4">'+data.info[i].voucherFinish.substr(0,10)+'</div></div>';
					html += '<div class="to-user-3"></div>';
					html += '<div class="to-user-4"><span>'+userInfo.userName+'</span></div></div>';
				}
				$(".fund_list").html(html);
				$(".fundMoney").html(moneyTotal);
			}else{
				Errfun2(data.msg);
			}
		},
			error:function(){
				Errfun3();
			}
	});
})
