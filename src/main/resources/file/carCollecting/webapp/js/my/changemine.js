$(function(){
	/*var obj1 = JSON.parse(sessionStorage.getItem('dd'));
	var logonToken=obj1.obj.token;
	var memberInfoId=obj1.obj.memberId;	*/
	var userInfoC={};
	var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	$("#username").val(userInfo.userName);
	$("#trueName").val(userInfo.realName);
	$("input[name='radio1'][value="+userInfo.sex+"]").attr("checked",true); 
	
	if(userInfo.card =="未认证"){
		$("#idCard").val('');
	}else{
		$("#idCard").val(userInfo.card);
	}
	if(userInfo.bankAddr =="未认证"){
		$("#bankCardNo").val('');
	}else{
		$("#bankCardNo").val(userInfo.bankCard);
	}
	if(userInfo.card =="未认证"){
		$("#bankName").val('');
	}else{
		$("#bankName").val(userInfo.bankAddr);
	}
	
	
    //点击重置
	$('.wji_btn').on('click', function() {
		
		//$(".wji_btn").prop("disabled",true);
		var name = $('#username').val();
		var trueName = $('#trueName').val();
		var idCard = $('#idCard').val();
		var bankCardNo=$('#bankCardNo').val();
		var bankName=$('#bankName').val();
		var reg = /^([1-9]{1})(\d{14,18})$/;
		var sex = $("input[name='radio1']:checked").val();	
		if(!name) {
			alert("昵称不能为空");
			return false;
		}
		
		if(!trueName) {
			alert("真实姓名不能为空");
			return false;
		}
		
		
		if(idCard.length !=15 && idCard.length !=18){
			alert("请输入正确的身份证号码");
			return false;
		}else{
			if(idCard.length ==15){
				var exp2="/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/";
				if(exp2.test(idCard)){
				}else {
					alert("请输入正确的身份证号码");
			        return false;
				}
			}else{
				if (isCardID(idCard)) {
				}else {
					alert("请输入正确的身份证号码");
			        return false;
				}
			}
		}
		
		if(!bankCardNo) {
			alert("银行账号不能为空");
			return false;
		}else{
			if(!CheckBankNo($('#bankCardNo'))){
				return false;
			}
		}
		if(!bankName) {
			alert("开户行名称不能为空");
			return false;
		}
	
		var changeUserInfo = {};
		changeUserInfo.id = userInfo.id;
		changeUserInfo.userName = name;
		changeUserInfo.realName = trueName;
		changeUserInfo.sex = sex;
		changeUserInfo.card = idCard;
		changeUserInfo.bankCard = bankCardNo;
		changeUserInfo.bankAddr = bankName;
		//console.log(changeUserInfo)
		$.ajax({
			type: "post",
			url: url+"/updateUserInfo",
			data: changeUserInfo,
			async: true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success: function(result1){
				//$(".wji_btn").prop("disabled",false);
				if(result1.msg =="SUCCESS"){
					alert("重置成功");
					userInfoC = userInfo;
					userInfoC.userName = name;
					userInfoC.realName = trueName;
					userInfoC.sex = sex;
					userInfoC.card = idCard;
					userInfoC.bankCard = bankCardNo;
					userInfoC.bankAddr = bankName;
					sessionStorage.setItem("userInfo",JSON.stringify(userInfoC));
//					location.href="change.html";
				}else{
					//Errfun1(data.msg);
				}
			},
			error:function(){
				Errfun3();
			}
		});
	})

})
