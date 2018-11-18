$(function(){
	

	$(".wj_btn").on('click',function(){
		$(".wji_btn").prop("disabled",true);
		var oldPassword=$("#oldPassword").val();
		var newPassword=$("#newPassword").val();
		if(!oldPassword){
			alert("请输入新密码");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		if(!newPassword){
			alert("请再次输入新密码");
			$(".wji_btn").prop("disabled",false);
			return false;
		}else if(oldPassword != newPassword){
			alert("两次密码输入不一致");
			$(".wji_btn").prop("disabled",false);
			return false;
		}
		
		var resetData={};
		resetData.phone = JSON.parse(sessionStorage.getItem('userInfo')).loginName;
		resetData.pwd = newPassword;
		//console.log(resetData)
		$.ajax({
			type:"post",
			url:url+"/resetPwd",
			data:resetData,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				console.log(result1)
				console.log(result1.msg)				
				$(".wji_btn").prop("disabled",false);
				if(result1.msg=="SUCCESS"){
					alert("密码重置成功");
					location.href="change.html";
				}else{
					Errfun1(data.msg);
				}
			},
			error:function(){
				 Errfun3();
			}
		})
	})
})
