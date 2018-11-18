$(function(){
	
	//登陆人信息
	var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	$('#username').html(userInfo.userName);
	if(userInfo.card == "未认证" ||userInfo.bankAddr == "未认证" ||userInfo.bankCard == "未认证"   ){
		$(".isReal").html("未实名");
	 }else{
		$(".isReal").html('已实名');
	}
	$('.mobile').html(userInfo.loginName);
	if(userInfo.userRole == "1"){
		$(".dl_name").html("代理");
		$(".dl_jiang").css("display","block");
	}else if(userInfo.userRole == "2"){
		$(".dl_name").html("会员");
		$(".dl_jiang").css("display","none");
	}else if(userInfo.userRole == "0"){
		$(".dl_name").html("管理员");
		$(".dl_jiang").css("display","none");
	}
	//推荐人
	if(userInfo.parentId == "" || userInfo.parentId == null || userInfo.parentId == undefined){
		$(".meg-6").css("display","none");
	}else{
		$('.recommendMobile').html(userInfo.parentId);
	}
	//推荐人
	if(userInfo.image == "" || userInfo.image == null || userInfo.image == undefined){
		$(".img3").attr("src","../../img/my/huiyuanh.png");
	}else{
		$(".img3").attr("src",img_url+userInfo.image);
	}
});
function doUpload(a,b,c) {  
				var fileName =document.getElementById("file3").files[0];
				
				if(fileName.size>614400){
					alert("头像图片过大，应小于600kB")
					return false;
				}
				var formData = new FormData(); 
				var loginName = JSON.parse(sessionStorage.getItem('userInfo')).loginName;
				formData.append("loginName",loginName);
				formData.append("file",fileName);
				
			 	$.ajax({  
			      	url: url+'/uploadPhoto',
			  		type: 'POST',  
			        data: formData,
			        async: false,  
			        cache: false,  
			        contentType: false,  
			        processData: false,  
			        crossDomain: true,
			        xhrFields: {withCredentials: true},
			        success: function (result) {
			        	var data1=result;
			        	console.log(data1);
			            if(data1.msg=="SUCCESS"){
			            	console.log(data1.path);
			            	$(".img3").attr("src",img_url+data1.path);
			            	//登陆人信息
			            	var userInfo1 ={}
	                        var userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
	                        userInfo1 =userInfo;
	                        userInfo1.image = data1.path;
			            	sessionStorage.setItem("userInfo",JSON.stringify(userInfo1)); 
			            }
			        }
			    });  
			}
			function img_click(a){
				$("#file"+a).click();
			} 

