$(function(){
	
	//是否登陆   是否排队
	var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	var data1 ={};
	data1.userId = userInfo.id;
	$.ajax({
			type: "post",
			url: url+"/getMemberInfo",
			data: data1,
			async: true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success: function(result){	
				if(result.msg =="SUCCESS"&&result.info!=null){
					console.log(result);
					if(result.info.waitNum!=null){
						var  buyType='';
						if(result.info.buyType==1){
							    buyType ='【 5 进 1 - 10 万 】';
						}else if(result.info.buyType==2){
							    buyType ='【 5 进 1 - 20 万 】';
						}else if(result.info.buyType==3){
							    buyType ='【 5 进 1 - 30 万 】';
						}else if(result.info.buyType==4){
							    buyType ='【 5 进 1 - 40 万 】';
						}else if(result.info.buyType==5){
							    buyType ='【 5 进 1 - 50 万 】';
						}
					
						$(".title_ccc").html(buyType);
						$(".pm_ccc").html(result.info.waitNum);
						$(".tcpm_ccc").html(result.buyTotal);
						$(".bo-bao").css("display","block");
					}
					
				}else if(result.msg =="SUCCESS"&&result.info==null){
					$(".bo-bao").css("display","none");
					$(".title_ccc").html("");
					$(".pm_ccc").html("");
					$(".tcpm_ccc").html("");
				}else{
					Errfun1(data.msg);
				}
			},
			error:function(){
				Errfun3();
			}
		});
	
	    $(".car-img").on("click",function(){
	    	console.log(1);
	    	var typeNum = $(this).attr("data-type");
	    	location.href="List.html?typeNum="+typeNum;
	    	
	    })
})
