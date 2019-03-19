    //推荐人
   var phone=GetQueryString("phone");
	function GetQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	console.log(phone);
	if(phone==null&&phone==''&&phone==undefined){
		$(".rmobile1").val("");
	}else{
		$(".rmobile1").val(phone);
	}
   


;$(function(){
	var rmobile = location.search;
	if(rmobile.indexOf("?rmobile=") != -1)
	{
		rmobile = rmobile.substring((rmobile.indexOf("rmobile=") + 8), 20);
		$(".rmobile1").val(rmobile);
	}

    //点击注册
	$(".zc_btn").on("click",function(){
		//$(".zc_btn").prop("disabled",true);
		//手机号
		var mobile1=$(".mobile1").val();
		//昵称
		var name=$(".name1").val();
		//推荐人
		var rmobile=$(".rmobile1").val();
		//验证码
		var code=$(".code1").val();
		//密码
		var password=$(".password1").val();
		//二次密码
		var password1=$(".password2").val();
		var myreg=/^[1][3,4,5,6,7,8][0-9]{9}$/;


		if(!myreg.test(mobile1)){
			alert("请输入正确的手机号");
			$(".zc_btn").prop("disabled",false);
			return false;
		}
		if(!name){
			alert("请输入会员昵称");
			$(".zc_btn").prop("disabled",false);
			return false;
		}
		if(!code){
			alert("请输入验证码");
			$(".zc_btn").prop("disabled",false);
			return false;
		}
		if(!password){
			alert("请设置登录密码");
			$(".zc_btn").prop("disabled",false);
			return false;
		}
		if(password != password1){
			alert("两次密码输入不一致");
			$(".zc_btn").prop("disabled",false);
			return false;
		}
		
		//推荐人账号验证
		var  flag = false;
		var phoneNum={};
		var phoneNumL={};
		phoneNum.loginName = rmobile;
		phoneNumL.loginName = mobile1;
		$.ajax({
			type:"post",
			url:url+"/getUserByLoginName",
			data:phoneNum,
			dataType:"JSON",
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result){
				//$(".zc_btn").prop("disabled",false);
				console.log(result);
				if(result.msg == "SUCCESS"&&result.info !=null&&result.info !=""){
			         $.ajax({
						type:"post",
						url:url+"/getUserByLoginName",
						data:phoneNumL,
						dataType:"JSON",
						crossDomain: true,
			            xhrFields: {withCredentials: true},                        
						success:function(result){
							//$(".zc_btn").prop("disabled",false);
							console.log(result);
							if(result.msg == "SUCCESS"&&result.info !=null){
						        
						          alert("号码已经注册");
						          $(".mobile1").val("")
						          return false;
						    }else if(result.info ==null){
						    	
						    	 //注册
						    	 var regData={};
									regData.phone=mobile1;
									regData.userName=name;
									regData.shortCode=code;
									regData.parentId=rmobile;
									regData.pwd=password;
									console.log(url+"/user");
									$.ajax({
										type:"post",
										url:url+"/user",
										data:regData,
										crossDomain: true,
			                            xhrFields: {withCredentials: true},
										success:function(result1){
											console.log(result1)
											console.log(result1.msg);
											if(result1.msg =="SUCCESS"){
													alert("注册成功");
													location.href="login.html?loginName="+mobile1;
											}else{
												Errfun1(result1.msg);
												if(result1.code=='2'){
													$(".code1").val("")
												}
											}
										}
									})		
						    }
					     }
					})
			    }else if(result.info ==null){
			    	 flag = false;
			    	 alert("推荐人不存在");
			    	  $(".rmobile1").val("")
			    	 return false;
			    }
		     },
		     error:function(){
				 Errfun3();
			}
		})
  })
//点击获取手机验证码
	$(".sendSMS").on("click",function(){
		console.log(1)
		$(".sendSMS").prop("disabled",true);
		var mobile1=$(".mobile1").val();
		var myreg=/^[1][3,4,5,6,7,8][0-9]{9}$/;
		console.log(mobile1)
		if(mobile1==''){
			alert("请输入手机号");
			$(".sendSMS").prop("disabled",false);
			return false;
		}else if(!myreg.test(mobile1)){
			alert("请输入正确的手机号");
			$(".sendSMS").prop("disabled",false);
			return false;
		}
		//验证图形码的有效性
		$.ajax({
			type:"post",
			url:url+"/sendSms",
			data:{"phone":mobile1},
			dataType:"JSON",
			success:function(result){
				$(".sendSMS").prop("disabled",false);
				console.log(result);
				if(result.msg=='SUCCESS'){
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
})
