
	//服务器路径
	var url="http://47.105.71.191:8080";
	//var url="http://47.105.71.191:8081";
	//图片路径
	var img_url="http://47.105.71.191:8081";

//	操作才有
	function Errfun1(err){
		if(err == "登录超时，请重新登录！"){
			alert(err);
			location.href="login.html";
		}else{
			alert(err);
		}
	}
//	进入就有
	function Errfun2(err){
		if(err == "登录超时，请重新登录！"){
			alert(err);
			location.href="login.html";
		}
	}
	//ajax报错提示
	function Errfun3(){
		alert("服务器异常")
	};
