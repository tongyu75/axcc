$(function(){
	
    //消息类别
	$(".message_btn").on("click",function(){
        location.href="messageList.html";
	})
	//提交消息
	
	$(".message_s_btn").on('click',function(){
		$(".message_s_btn").prop("disabled",true);
		var messageT=$("#messageT").val();
		var messageC=$("#messageC").val();
		
		if(!messageT){
			alert("请输入消息标题");
			$(".message_s_btn").prop("disabled",false);
			return false;
		}
		if(!messageC){
			alert("请输入消息内容");
			$(".message_s_btn").prop("disabled",false);
			return false;
		}
		
		var c={};
		c.title=messageT;
		c.contents=messageC;		
	
		console.log(url+"/insertMessageByBean");
		$.ajax({
			type:"post",
			url:url+"/insertMessageByBean",
			data:c,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result1){
				var data=result1
				$(".message_s_btn").prop("disabled",false);
				if(data.msg == "SUCCESS"){
					alert("添加成功！");
					location.href="my.html";
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
