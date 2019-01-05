$(function(){
	/*var obj1 = JSON.parse(sessionStorage.getItem('dd'));
	var logonToken=obj1.obj.token;
	var memberInfoId=obj1.obj.memberId;
	var c={};
	c.logonToken=logonToken;
	c.memberInfoId=memberInfoId;*/
/*	$.ajax({
		type:"post",
		url:url+"/api/memberCenter/info",
		data:c,
		async:true,
		success:function(result){
			var data=JSON.parse(result);
			if(data.success == true){
				$(".tx_li").attr("src",head_img+data.obj.memberInfo.headImageUrl);
				if(data.obj.memberInfo.sex == 1){
					$(".xb_li").html("男");
				}else{
					$(".xb_li").html("女");
				}
				
				$(".sjh_li").html(data.obj.memberInfo.mobile);
				if(data.obj.memberInfo.idCard == null || data.obj.memberInfo.idCard == ""){
					$(".sfz_li").html("未绑定");
				}else{
					$(".sfz_li").html(data.obj.memberInfo.idCard);
				}
				if(data.obj.memberInfo.bankName == null || data.obj.memberInfo.bankName ==  ""){
					$(".khx_li").html("未绑定");
				}else{
					$(".khx_li").html(data.obj.memberInfo.bankName);
				}
				if(data.obj.memberInfo.bankCardNo == null || data.obj.memberInfo.bankCardNo ==  ""){
					$(".yhkh_li").html("未绑定");
				}else{
					$(".yhkh_li").html(data.obj.memberInfo.bankCardNo);
				}
			}else{
				//Errfun2(data.msg);
			}
		}
	});*/
	
	var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
  
	var sex="";
	if(userInfo.sex =="1"&&userInfo.sex!=null&&userInfo.sex!=""){
		sex="男"
	}else if(userInfo.sex =="2"&&userInfo.sex!=null&&userInfo.sex!=""){
		sex="女"
	}
	var card = '';
	if(userInfo.card!=null&&userInfo.card!=''){
		card=userInfo.card;						  
	}
	var bankAddr = '';
	if(userInfo.bankAddr!=null&&userInfo.bankAddr!=''&&userInfo.bankAddr!="未认证"){
		bankAddr=userInfo.bankAddr;						  
	}else if(userInfo.card =="未认证"){
		bankAddr= '未绑定';
	}
	var bankCard = '';
	if(userInfo.bankCard!=null&&userInfo.bankCard!=''&&userInfo.bankCard!="未认证"){
		bankCard=userInfo.bankCard;						  
	}else if(userInfo.bankCard =="未认证"){
		bankCard= '未绑定';
	}
	
	
	$(".xb_li").html(sex);
	$(".sjh_li").html(userInfo.loginName);
	$(".sfz_li").html(card);
	$(".khx_li").html(bankAddr);
	$(".yhkh_li").html(bankCard);

})
