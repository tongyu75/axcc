$(function(){
	
	/*$(".code").attr("src",url+"/api/memberCenter/getQrCode?logonToken="+logonToken+"&memberInfoId="+memberInfoId);*/
	var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	var data={};
	//data.registerUrl = url+"/carCollecting/webapp/html/loginAReg/register.html";
	//data.registerUrl = "http://192.168.31.254:8020/carCollecting/webapp/html/loginAReg/register.html";
	//data.registerUrl = "http://47.105.71.191:80/carCollecting/webapp/html/loginAReg/register.html";
	data.registerUrl = "http://www.zxgouche.cn/carCollecting/webapp/html/loginAReg/register.html";
	data.userId =userInfo.id;
	data.phone =userInfo.loginName;
	console.log(data);
	$(".memberName").html(userInfo.userName)
	$.ajax({
		type:"get",
		url:url+"/qrCode",
		data:data,
		async:true,
		/*crossDomain: true,
	    xhrFields: {withCredentials: true},*/
		success:function(result){
			console.log(result);
			$(".code").css("display","inline-block")
			//$(".code").attr("src","http://47.105.71.191:8080/qrCode?registerUrl=http://47.105.71.191:8081/carCollecting/webapp/html/loginAReg/register.html&userId=28&phone=15603311112");
	        $(".code").attr("src",url+"/qrCode?registerUrl="+data.registerUrl+"&userId="+data.userId+"&phone="+data.phone);
		},
		error:function(){
				Errfun3();
		}
	});
})
