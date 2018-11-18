 $(function(){
	
	
	var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	$(".mobile1").val(userInfo.loginName);
	var card = userInfo.card;
	var bankCard = userInfo.card;
	var bankAddr = userInfo.card;
	var data1 ={};
	data1.userId = userInfo.id;
    //	排队
	$(".pd_btn").on("click",function(){
		$(".pd_btn").prop("disabled",true);
		$.ajax({
			type: "post",
			url: url+"/getMemberInfo",
			data: data1,
			async: true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success: function(result){
				console.log(result);
				if(result.msg =="SUCCESS"&&result.info!==null){
					if(result.info.checkStatus!=null){
					  alert("已参与排队");
					  $(".sel_list option[value='0']").attr("selected",true)
					}
				}else if(result.msg =="SUCCESS"&&result.info==null){
					var c={};
					c.userId=userInfo.id;
					var buyType=$(".sel_list option:selected").val();
					if(card=="未认证"||bankCard=="未认证"||bankAddr=="未认证"){
						alert("请实名认证身份信息");
						$(".pd_btn").prop("disabled",false);
						return false;
					}
					if(buyType == ""){
						alert("请选择分类");
						$(".pd_btn").prop("disabled",false);
						return false;
					}
					c.buyType=buyType;
					$.ajax({
						type:"post",
						url:url+"/memberQueue",
						data:c,
						async:true,
						crossDomain: true,
			            xhrFields: {withCredentials: true},
						success:function(result){
							console.log(result);
							if(result.msg =="SUCCESS"){
								alert("排队成功");
							}else{
								Errfun1(data.msg);
							}
						},
						error:function(){
							Errfun3();
						}
					});
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
