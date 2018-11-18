$(function(){
	var obj1 = JSON.parse(sessionStorage.getItem('dd'));
	var logonToken=obj1.obj.token;
	var memberInfoId=obj1.obj.memberId;
	var rows=7;
	var you=GetQueryString("id");
	function GetQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
	function kk(page){
		var c={};
		c.logonToken=logonToken;
		c.memberInfoId=memberInfoId;
		c.typeLevelId=you;
		c.rows=rows;
		c.page=page;
		$.ajax({
			type:"post",
			url:url+"/api/car/showList",
			data:c,
			async:true,
			crossDomain: true,
			xhrFields: {withCredentials: true},
			success:function(result){
				var data=JSON.parse(result);
				if(data.success){
					$(".title_li").html("【 "+data.obj.applyTypeLevelList[0].title+" 】");
					$(".url_li").attr("src", img_url + data.obj.photoUrl);
					
					
	//				排队列表、
					var html="";
					for(var i in data.obj.carRecordList){
						var hh=data.obj.carRecordList[i].memberMobile;
						var hh1=data.obj.carRecordList[i].memberName;
						if(data.obj.carRecordList[i].memberId == memberInfoId){
							html += '<tr class="red_tr"><td>'+data.obj.carRecordList[i].seq+'</td><td>'+hh1.replace(/^(.).*$/,"$1**")+'</td><td>'+hh.replace(/^(...).*(...)$/,"$1***$2")+'</td>';
							html += '<td><button type="button" data="'+data.obj.carRecordList[i].id+'" class="gbtn">查看详情</button></td></tr>';
						}else{
							html += '<tr><td>'+data.obj.carRecordList[i].seq+'</td><td>'+hh1.replace(/^(.).*$/,"$1**")+'</td><td>'+hh.replace(/^(...).*(...)$/,"$1***$2")+'</td>';
							html += '<td><button type="button" data="'+data.obj.carRecordList[i].id+'" class="gbtn">查看详情</button></td></tr>';
						}
						
					}
					$(".show_List").html(html);
					var r = data.obj.total;
					r = Math.ceil(r / rows);
//                  console.log(r);			
					$(".tcdPageCode").createPage({
						pageCount: r,
						current: page,
						backFn: function(p) {}
					});
					$(".gbtn").on("click",function(){
						var dd = $(this).attr("data");
						var c={};
						c.logonToken=logonToken;
						c.memberInfoId=memberInfoId;
						c.typeLevelId=you;
						c.rows=rows;
						c.page=page;
						$.ajax({
							type:"post",
							url:url+"/api/car/showList",
							data:c,
							async:true,
							crossDomain: true,
			                xhrFields: {withCredentials: true},
							success:function(result){
								var data=JSON.parse(result);
								if(data.success){
									for(var i in data.obj.carRecordList){
										if(dd == data.obj.carRecordList[i].id){
											var hh_3=data.obj.carRecordList[i].memberMobile;
											var hh_2=data.obj.carRecordList[i].memberName;
											var hh_4=data.obj.carRecordList[i].memberIdCard;
											$(".list_1").html(data.obj.carRecordList[i].seq);
											$(".list_2").html(hh_2.replace(/^(.).*$/,"$1**"));
											$(".list_3").html(hh_3.replace(/^(...).*(...)$/,"$1***$2"));
											$(".list_4").html(hh_4.replace(/^(...).*(...)$/,"$1***$2"));
											$(".list_5").html(data.obj.carRecordList[i].payMoney);
											if(data.obj.carRecordList[i].status == 1){
												var jj="排队中";
											}else if(data.obj.carRecordList[i].status == 2){
												var jj="可提车";
											}else if(data.obj.carRecordList[i].status == 6){
												var jj="已提车";
											}
											$(".list_6").html(jj);
											
										}
									}
									
								}
							},
							error:function(){
								Errfun3();
							}
						});
						$("#popBox").css("display","block");
						$("#popLayer").css("display","block");
					});
				}else{
					//Errfun1(data.msg);
				}
			},
			error:function(){
				Errfun3();
			}
		});
		
	}
	kk(1);
	$(".tcdPageCode").on("click",'a',function(){
        var h=$(this).html();
        var page ="";
        if(h=="下一页"){
                var t= $('.tcdPageCode  span.current').html();

                t=parseInt(t);
                page=t+1;
        }else if(h=="上一页"){
                var t= $('.tcdPageCode  span.current').html();

                t=parseInt(t);
                page=t-1;
        }else{
                h=parseInt(h);
                page=h;
        }
		kk(page);  
	})
//	取消弹框
	$(".closeLayer").on("click",function(){
		$("#popBox").css("display","none");
		$("#popLayer").css("display","none");
	})
	
})
