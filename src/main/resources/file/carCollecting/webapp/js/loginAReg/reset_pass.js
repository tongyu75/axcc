$(function(){
	$(".wji_btn").on("click",function(){
		$(".wji_btn").prop("disabled",true);
		var mobile1=$(".mobile1").val();
		var mobile2=$(this).attr("data");
		var code=$(".code1").val();
		var password=$(".password1").val();
		var password1=$(".password2").val();
		var myreg=/^[1][3,4,5,6,7,8][0-9]{9}$/;


		if(!myreg.test(mobile1)){
			alert("请输入正确的手机号");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		/*if(!code){
			alert("请输入验证码");
			$(".wji_btn").prop("disabled",false);
			return false;
		}*/
		if(!password){
			alert("请设置登录密码");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		if(!password1){
			alert("请再次输入新密码");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		if(password != password1){
			alert("两次密码输入不一致");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		var c={};
		c.mobile=mobile2;
		c.code=code;
		c.password=password;
		/*$.ajax({
			type:"post",
			url:url+"/api/index/passWordReset",
			data:c,
			success:function(result1){
				var data=JSON.parse(result1);
				$(".wji_btn").prop("disabled",false);
				if(data.success){
					alert(data.msg);
					location.href="login.html";
				}else{
					Errfun1(data.msg);
				}
			}
		})*/
		
		console.log(url+"/resetPwd");
		//重置密码
		var resetData={};
		resetData.phone = mobile1;
		resetData.pwd = password;
		console.log(resetData)
		$.ajax({
			type:"post",
			url:url+"/resetPwd",
			data:resetData,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				console.log(result1)
				console.log(result1.msg)

				//$(".wji_btn").prop("disabled",false);
				if(result1.msg=="SUCCESS"){
					alert("密码重置成功");
					location.href="login.html";
				}else{
					Errfun1(data.msg);
				}
			},
			error:function(){
				 Errfun3();
			}
		})
	});
//点击获取手机验证码
	$(".sendSMS").on("click",function(){
		$(".sendSMS").prop("disabled",true);
		var mobile1=$(".mobile1").val();
		var myreg=/^[1][3,4,5,6,7,8][0-9]{9}$/;
		if(!myreg.test(mobile1)){
			alert("请输入正确的手机号");
			$(".sendSMS").prop("disabled",false);
			return false;
		}
		//验证图形码的有效性
		$.ajax({
			type:"post",
			url:url+"/api/index/sendSMSByForget",
			data:{"mobile":mobile1},
			dataType:"JSON",
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result){
				$(".sendSMS").prop("disabled",false);
				if(result.success == true){
					var time = 60;
	                var ttime=setInterval(function () {
	                    time--;
	                    if (time === 0) {
	                        $('.sendSMS').html("获取验证码").attr('disabled',false);
	                    	clearInterval(ttime);
	                    }else if (time > 0) {
	                         $('.sendSMS').html(time + "秒后重发").attr('disabled',true);
	                    }
	                   
	                }, 1000);
					
				}else{
					Errfun1(result.msg);
				}
			}
		});
	})
//	填写验证码后验证
	$(".code1").on("blur",function(){
		var mobile1=$(".mobile1").val();
		var code=$(".code1").val();
		var c={};
		c.mobile=mobile1;
		c.code=code;
		$.ajax({
			type:"post",
			url:url+"/api/index/passwordForget",
			data:c,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				var data=JSON.parse(result1);
				if(data.success){
					$(".wji_btn").attr("data",data.obj.mobile);
				}else{
					Errfun1(data.msg);
				}
			}
		})
	})
})
