   //用户名
   var loginName=GetQueryString("loginName");
	function GetQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	//console.log(loginName);
	if(loginName==null&&loginName==''&&loginName==undefined){
		 $(".mobile1").val("");
	}else{
		 $(".mobile1").val(loginName);
	}
   
$(function(){
	$(".login_btn").on("click",function(){
		$(".login_btn").prop("disabled",true);
		var mobile1=$(".mobile1").val();
		var password=$(".password1").val();
		var myreg=/^[1][3,4,5,6,7,8][0-9]{9}$/;
		if(!myreg.test(mobile1)){
			alert("请输入正确的手机号");
			$(".login_btn").prop("disabled",false);
			return false;
		}
		if(!password){
			alert("请设置登录密码");
			$(".login_btn").prop("disabled",false);
			return false;
		}
		
		var userInfo={};
		/*var data1={}
		data1.success=true;
		data1.obj={token: "10c57e77-e48a-4ddb-b92a-94b63e321cc6", name: "李翠平", memberId: 107, mobile: "13671150807"};
		data1.status=null;
		sessionStorage.setItem("dd",JSON.stringify(data1));*/
		$.ajax({
									type:"get",
									url:url+"/login/"+mobile1+"/"+password,
									crossDomain: true,
			                        xhrFields: {withCredentials: true},
									success:function(result1){
										//console.log(result1);
										var resultData = result1
										$(".login_btn").prop("disabled",false);				
										if(resultData.code =="0"){
											userInfo = resultData.result;	
									        sessionStorage.setItem("userInfo",JSON.stringify(userInfo));
										  if(resultData.result.userRole=='2'){		   //普通会员登陆			
											location.href="../home/home.html";
										  }else if(resultData.result.userRole=='0'){   //管理员	
											location.href="../administratorInfo/agent.html";	
										  }else if(resultData.result.userRole=='1'){   //代理员
											//location.href="../agentInfo/agent.html";	
											location.href="../agentInfo/waitBusiness.html";	
										  }
										}else{
											//alert("密码错误")
											Errfun1(result1.msg);
										}
									
									},
									error:function(){
										 Errfun3();
									}
								})
		var data={};
		data.loginName = mobile1;
		 /*$.ajax({
						type:"post",
						url:url+"/getUserByLoginName",
						data:data,
						dataType:"JSON",
						crossDomain: true,
			            xhrFields: {withCredentials: true},
						success:function(result){
							//$(".zc_btn").prop("disabled",false);
							//console.log(result);
							if(result.msg == "SUCCESS"&&result.info !=null){						        
						        $.ajax({
									type:"get",
									url:url+"/login/"+mobile1+"/"+password,
									crossDomain: true,
			                        xhrFields: {withCredentials: true},
									success:function(result1){
										//console.log(result1);
										var resultData = result1
										$(".login_btn").prop("disabled",false);				
										if(resultData.msg =="SUCCESS"){
											userInfo = resultData.result;	
									        sessionStorage.setItem("userInfo",JSON.stringify(userInfo));
										  if(resultData.result.userRole=='2'){		   //普通会员登陆			
											location.href="../home/home.html";
										  }else if(resultData.result.userRole=='0'){   //管理员	
											location.href="../administratorInfo/agent.html";	
										  }else if(resultData.result.userRole=='1'){   //代理员
											//location.href="../agentInfo/agent.html";	
											location.href="../agentInfo/waitBusiness.html";	
										  }
										}else{
											alert("密码错误")
											//Errfun1(result1.msg);
										}
									
									},
									error:function(){
										 Errfun3();
									}
								})
						    }else if(result.info ==null){
						    	alert("该用户未注册");
						    	$(".login_btn").prop("disabled",false);
						    	$(".mobile1").val("");
						    }
					     }
					})	*/
	})
})
