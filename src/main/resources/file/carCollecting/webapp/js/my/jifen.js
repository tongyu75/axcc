$(function() {

	var pageSize = 7;
	showAgent(1);

	//显示分享来院会员列表
	function showAgent(page) {
		var data = {};
		data.userId = JSON.parse(sessionStorage.getItem('userInfo')).id;
		//data.userId = "33";
		data.pageNum = page;
		data.pageSize = pageSize;
		$.ajax({
			type: "post",
			url: url + "/listShareMoney",
			data: data,
			async: true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success: function(result1) {
				console.log(result1);
				var resultData = result1;
				if(resultData.msg == "SUCCESS") {
					var dataInfo = resultData.info;
					var sumShareMoney = "0.00元"
					if(dataInfo.sumShareMoney != 0) {
						sumShareMoney = dataInfo.sumShareMoney + "元";
					}
					$(".balance").text(sumShareMoney);
					var html = "";
					//会员列表
					for(var i in dataInfo.listShareMoney) {
						var level = "";
						if(dataInfo.listShareMoney[i].level == 1) {
							level = "直推会员"
						} else if(dataInfo.listShareMoney[i].level == 2) {
							level = "间推会员"
						}
						var applyStatus = "";
						if(dataInfo.listShareMoney[i].applyStatus == 0) {
							applyStatus = "未提现"
						} else if(dataInfo.listShareMoney[i].applyStatus == 1) {
							applyStatus = "已提现"
						}

						html += '<tr><td>' + level + '</td><td>' + dataInfo.listShareMoney[i].userName + '</td>';
						html += '<td>' + dataInfo.listShareMoney[i].buyMoney + '</td><td>' + dataInfo.listShareMoney[i].checkTime + '</td></tr>';
					}
					$(".income").html(html);
					var r = result1.total;
					r = Math.ceil(r / pageSize);
					//                  console.log(r);			
					$(".tcdPageCode").createPage({
						pageCount: r,
						current: page,
						backFn: function(p) {}
					});
				} else {
					Errfun1(result1.msg);
				}
			},
			error: function() {
				Errfun3();
			}
		})
	}

	$(".tcdPageCode").on("click", 'a', function() {
		var h = $(this).html();
		var page = "";
		if(h == "下一页") {
			var t = $('.tcdPageCode  span.current').html();
			t = parseInt(t);
			page = t + 1;
		} else if(h == "上一页") {
			var t = $('.tcdPageCode  span.current').html();
			t = parseInt(t);
			page = t - 1;
		} else {
			h = parseInt(h);
			page = h;
		}
		showAgent(page);
	})
	//	申请提现
	$(".tx_btn").on("click", function() {
		var data1 = {};
		data1.userId = JSON.parse(sessionStorage.getItem('userInfo')).id;
		//data1.userId = "33";
		$.ajax({
			type:"post",
			url:url+"/countLevel1",
			data:data1,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				console.log(result1);
				var resultData = result1;	
				if(resultData.msg =="SUCCESS"){			
					   // console.log(resultData.info); 
					    if(resultData.info>=20){
					    	$(".shareNum").css("display","inline-block")
					    }else{
					    	$(".shareNum").css("display","none");
					    }
					}else{
					    Errfun1(result1.msg);
					}
			},
			error:function(){
					Errfun3();
			}
		})
		//1代理员；2普通会员
		var dataT = {};
		dataT.userId = JSON.parse(sessionStorage.getItem('userInfo')).id;
		dataT.userStatus = "2";
		$.ajax({
			type: "post",
			url: url + "/withdrawCashes",
			data: dataT,
			async: true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success: function(result1) {
				console.log(result1);
				var resultData = result1;
				if(resultData.msg == "SUCCESS") {	
					   // console.log(resultData.info);
					    $(".account_jf").html($(".balance").html());
						//$(".balance_jf").html($(".balance").html());					
						//$(".rate_jf").html($(".balance").html());
						$(".account_jf").html($(".balance").html());
						$("#aaa").css("display", "block");
	                 	$("#ddd").css("display", "block");
				} else if(resultData.code=="2"){
					alert("会员提现于每周周一申请，公司审核并发放");
					$("#aaa").css("display", "none");
	                $("#ddd").css("display", "none");		
				}else{
					Errfun1(result1.msg);
				}
			},
			error: function() {
				Errfun3();
			}
		})
	})
	//	取消弹框
	$(".close1").on("click", function() {
		$("#aaa").css("display", "none");
		$("#ddd").css("display", "none");
	})
	$("#aaa").on("click", function() {
		$("#aaa").css("display", "none");
		$("#ddd").css("display", "none");
	})

	//	确认提现
	$(".Modify_confirm").on("click", function() {
		$("#aaa").css("display", "none");
		$("#ddd").css("display", "none");	
	})

})