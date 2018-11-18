$(function(){
	var pageSize =7;
	showAgent(1);

	//显示我的会员列表
	function showAgent(page){
		var data={};
		data.userId = JSON.parse(sessionStorage.getItem('userInfo')).id;
		$.ajax({
			type:"post",
			url:url+"/myMember",
			data:data,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				console.log(result1);
				var resultData = result1;	
				if(resultData.msg =="SUCCESS"){			
					    var  dataInfo =resultData.info;
					    console.log(dataInfo.level2Data);
					    $(".zt_hy").html(dataInfo.level1Count);
				        $(".qj_hy").html(dataInfo.level3Count);
					   	var html ="";
					   //直推会员列表
						for(var i in dataInfo.level1Data){
							html += '<div class="am-u-sm-12" style="margin-bottom:5px;padding:3px 1rem;border-bottom:1px solid #ddd;">'
							html +='<div class="am-u-sm-3" style="padding:0;">'+dataInfo.level1Data[i].userName+'</div>';
					        html += '<div class="am-u-sm-4 jjl"  style="line-height:30px;">'+dataInfo.level1Data[i].loginName+'</div><div class="am-u-sm-5 jjl" style="line-height:24px;">'+dataInfo.level1Data[i].time+'</div></div>';
						}
						$(".oneLevel_List").html(html);
						//区间列表列表
						var htm2 ="";
						for(var i in dataInfo.level3Data){
						    htm2 += '<div class="am-u-sm-12" style="margin-bottom:5px;padding:3px 1rem;border-bottom:1px solid #ddd;">'
							htm2 +='<div class="am-u-sm-3" style="padding:0;">'+dataInfo.level3Data[i].userName+'</div>';
					        htm2 += '<div class="am-u-sm-4 jjl" style="line-height:30px;">'+dataInfo.level3Data[i].loginName+'</div><div class="am-u-sm-5 jjl" style="line-height:24px;">'+dataInfo.level3Data[i].time+'</div></div>';
						}		
						$(".otherLevel_List").html(htm2);
						//间推会员列表
						var htm3 ="";
						for(var i in dataInfo.level2Data){
							htm3 += '<div class="am-u-sm-12" style="margin-bottom:5px;padding:3px 1rem;border-bottom:1px solid #ddd;">'
							htm3 +='<div class="am-u-sm-3" style="padding:0;">'+dataInfo.level2Data[i].userName+'</div>';
					        htm3 += '<div class="am-u-sm-4 jjl" style="line-height:30px;">'+dataInfo.level2Data[i].loginName+'</div><div class="am-u-sm-5 jjl" style="line-height:24px;">'+dataInfo.level2Data[i].time+'</div></div>';
						}		
						$(".level2Data_List").html(htm3);
						
					}else{
							Errfun1(result1.msg);
					}
					
			},
			error:function(){
					Errfun3();
			}
		})
		
		/*var data1 = {};
		data1.userId = JSON.parse(sessionStorage.getItem('userInfo')).original;
		//直推人数 和分享金额
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
					    	$(".balance").text(5000);
					    }else{
					    	$(".balance").text("0.00");
					    }
					}else{
							Errfun1(result1.msg);
					}
					
			},
			error:function(){
					Errfun3();
			}
		})*/
		
	}
})