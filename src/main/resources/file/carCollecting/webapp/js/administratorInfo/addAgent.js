$(function(){
	/*var obj1 = JSON.parse(sessionStorage.getItem('dd'));
	var logonToken=obj1.obj.token;
	var memberInfoId=obj1.obj.memberId;*/

	$(".wj_btn").on('click',function(){
		$(".wji_btn").prop("disabled",true);
		var agentArea=$("#agentArea").val();
		var agentName=$("#agentName").val();
		var agentPhone=$("#agentPhone").val();
		if(!agentArea){
			alert("请输入代理员区域");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		if(!agentName){
			alert("请输入代理员姓名");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		if(!agentPhone){
			alert("请输入代理员手机号");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		var phoneNumL={};
		phoneNumL.loginName = agentPhone;
		 $.ajax({
				type:"post",
				url:url+"/getUserByLoginName",
				data:phoneNumL,
				dataType:"JSON",
				crossDomain: true,
		        xhrFields: {
		            withCredentials: true
		        },
				success:function(result){
				console.log(result);
				  if(result.msg == "SUCCESS"&&result.info !=null&&result.info !=""){	
					 alert("号码已经注册");
					 $("#agentPhone").val("");
				  }else  if(result.info ==null){
				  	
				  	    var c={};
						c.proxyArea=agentArea;
						c.userName=agentName;		
						c.phone=agentPhone;
				
						console.log(url+"/proxy");
						$.ajax({
							type:"post",
							url:url+"/proxy",
							data:c,
							crossDomain: true,
					        xhrFields: {
					            withCredentials: true
					        },
							success:function(result1){
								var data=result1
								$(".wji_btn").prop("disabled",false);
								if(data.msg == "SUCCESS"){
									alert("添加成功！");
									location.href="agent.html";
								}else{
									Errfun1(data.msg);
								}
							},
							error:function(){
								 Errfun3();
							}
						})
				  }
				}
		})
		
		
	})
	
})
