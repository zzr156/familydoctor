//准备查看
function forLookTable(ui){
	var state = $(ui).data("vo").state;
	var type = $(ui).data("vo").type;
	if(type==2){//艾滋病
		if(state=="5"||state=="6"||state=="98"){
			var url = 'recheck.action?act=forCmmfj&handle=look&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "复检报告";
			comTabs(url,code,title);
		}else if(state=="7"||state=="8"||state=="99"){
			var url = 'recheck.action?act=forCmmqz&handle=look&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "确证报告";
			comTabs(url,code,title);
		}else{
			var url = 'aids.action?act=forLook&isBack=hide&handle=look&vo.id='+$(ui).data("vo").id;;
			var code = $(ui).data("vo").id;
			var title = "检测单";
			comTabs(url,code,title);
		}

	}else if(type==3){//通用疾病
		if(state==5||state==6||state==99){
			var url = 'recheckOther.action?act=forCmmCheck&isBack=hide&handle=look&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "检验报告";
			comTabs(url,code,title);
		}else {
			var url = 'others.action?act=forLook&isBack=hide&handle=look&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "送检单";
			comTabs(url,code,title);
		}
	}
	   
}
//准备审核
var idd="";
function forAudit(ui){
	idd=$(ui).data("vo").id;
	var type = $(ui).data("vo").type;
	if(type=="3"){
		var url = 'others.action?act=forLook&handle=audit&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = idd;
			var title = "审核";
			comTabs(url,code,title);
	}else{
		var url = 'aids.action?act=forLook&handle=audit&isBack=hide&id='+$(ui).data("vo").id;
			var code = idd+"11";
			var title = "审核";
			comTabs(url,code,title);
	  	}
	  	
 }
//准备接样
function forReceiveSample(ui){
	if($(ui).data("vo").type==3){
		var url = 'others.action?act=forLook&handle=sample&isBack=hide&vo.id='+$(ui).data("vo").id;
		var code = $(ui).data("vo").id;
		var title = "接样";
		comTabs(url,code,title);
	}else{
		idd=$(ui).data("vo").id;
    	var url = 'aids.action?act=forLook&handle=sample&isBack=hide&id='+$(ui).data("vo").id;
		var code = idd+"12";
		var title = "接样";
		comTabs(url,code,title);
	}

}


//确认退样
function confirmDropSample(ui){
	var url;
	var type = $(ui).data("vo").type;
	if(type=="3"){
		url = 'others.action?act=forLook&handle=confirm&isBack=hide&vo.id='+$(ui).data("vo").id;
	}else{
		url = 'aids.action?act=forLook&handle=confirm&isBack=hide&vo.id='+$(ui).data("vo").id;
	}
	var code = $(ui).data("vo").id+"66";
	var title = "确认退样";
	comTabs(url,code,title);
}



//准备修改
function forModifyTable(ui){
	var url;
  	var type = $(ui).data("vo").type;
  	if(type==2){
  	    url = 'aids.action?act=forAddOrEdit&handle=modify&isBack=hide&vo.id='+$(ui).data("vo").id;
  		
  	}else if(type==3){
  		url = 'others.action?act=forAddOrEdit&handle=modify&isBack=hide&vo.id='+$(ui).data("vo").id;
  	}
  	var code = $(ui).data("vo").id+"7";
	var title = "修改";
	comTabs(url,code,title);
	
}

//打印送检单
function forPrintTable(ui){	
	var url;
	var type = $(ui).data("vo").type;
	if(type=="3"){
		url = 'others.action?act=printWrite&isBack=hide&id='+$(ui).data("vo").id;
	}else{
		url = 'aids.action?act=printWrite&isBack=hide&id='+$(ui).data("vo").id;
	}
	var code = $(ui).data("vo").id+"8";
	var title = "打印送检单";
	comTabs(url,code,title);
}
//打印复检报告
function forPrintTable2(ui){
	var url;
	var title;
	var type = $(ui).data("vo").type;
	if(type=="3"){
		url = 'others.action?act=printWrite2&isBack=hide&id='+$(ui).data("vo").id;
		title = "打印检验报告";
	}else{
		url = 'aids.action?act=printWrite2&isBack=hide&id='+$(ui).data("vo").id;
		title = "打印复检报告";
	}
	var code = $(ui).data("vo").id+"9";
	comTabs(url,code,title);	
}
//打印确证报告
function forPrintTable3(ui){
    var url;
	var type = $(ui).data("vo").type;
	if(type=="3"){
		url = 'others.action?act=printWrite3&isBack=hide&id='+$(ui).data("vo").id;
	}else{
		url = 'aids.action?act=printWrite3&isBack=hide&id='+$(ui).data("vo").id;
	}	
	var code = $(ui).data("vo").id+"10";
	var title = "打印确证报告";
	comTabs(url,code,title);
}
//打印  && 次数限制
function forPrint(ui,printType){
	var id = $(ui).data("vo").id;
	var state = $(ui).data("vo").state;
	doAjaxPost('flow.action?act=printCountCmm',{"id":id,"state":state},function(data){
		if(data.msg!=null){
			layer.open({
     			skin: 'layui-layer-molv',
     			closeBtn: 0,
     			title: false,
     			content:data.msg ,
     			anim: 5 ,
     			btn: ['关闭']
   		  	});
		}else if(printType==1){
			forPrintTable(ui);
		}else if(printType==2){
			forPrintTable2(ui);
		}else if(printType==3){
		    forPrintTable3(ui);
		}
	});
}

function forLookTableTt(ui){
	var state=$(ui).data("vo").state;
	if(state=="4"||state=="5"){
		var url = 'recheck.action?act=forCmmfj&handle=look&isBack=hide&vo.id='+$(ui).data("vo").id;
		var code = $(ui).data("vo").id;
		var title = "复检报告";
		comTabs(url,code,title);
	}else if(state=="6"||state=="7"||state=="8"){
		var url = 'recheck.action?act=forCmmqz&handle=look&isBack=hide&vo.id='+$(ui).data("vo").id;
		var code = $(ui).data("vo").id;
		var title = "确证报告";
		comTabs(url,code,title);
	}
	
}
function forFjJcTable(ui){
	var url = 'recheck.action?act=forCmmfj&handle=fj&isBack=hide&vo.id='+$(ui).data("vo").id;
	var code = $(ui).data("vo").id+"1";
	var title = "复检（检测）";
	comTabs(url,code,title);
}
function forFjShTable(ui){
	doAjaxPost('aids.action?act=fjShCmm',{id:$(ui).data("vo").id},function(data){
		if(data.msg == 'true'){
			var url = 'recheck.action?act=forCmmfj&handle=fj&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id+"2";
			var title = "复检（审核）";
			comTabs(url,code,title);
		}else{
			layer.open({
     			skin: 'layui-layer-molv',
     			closeBtn: 0,
     			title: false,
     			content:data.msg ,
     			anim: 5 ,
     			btn: ['关闭']
   		  	});
		}
		
	});
	
}
function forQzJcTable(ui){
	var url = 'recheck.action?act=forCmmqz&handle=qz&isBack=hide&vo.id='+$(ui).data("vo").id;
	var code = $(ui).data("vo").id+"3";
	var title = "确证（检测）";
	comTabs(url,code,title);
}
function forQzShTable(ui){
	doAjaxPost('aids.action?act=qzFhCmm',{id:$(ui).data("vo").id},function(data){
		if(data.msg == 'true'){
			var url = 'recheck.action?act=forCmmqz&handle=qz&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id+"4";
			var title = "确证（复核）";
			comTabs(url,code,title);
		}else{
			layer.open({
     			skin: 'layui-layer-molv',
     			closeBtn: 0,
     			title: false,
     			content:data.msg ,
     			anim: 5 ,
     			btn: ['关闭']
   		  	});
		}
		
	})
}
function forQzQfTable(ui){
	doAjaxPost('aids.action?act=qzQfCmm',{id:$(ui).data("vo").id},function(data){
		if(data.msg == 'true'){
			var url = 'recheck.action?act=forCmmqz&handle=qz&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id+"5";
			var title = "确证（签发）";
			comTabs(url,code,title);
		}else{
			layer.open({
     			skin: 'layui-layer-molv',
     			closeBtn: 0,
     			title: false,
     			content:data.msg ,
     			anim: 5 ,
     			btn: ['关闭']
   		  	});
		}
		
	})
	
}

//删除
function delForm(ui){
	layer.confirm('确认删除数据?', {
		  btn: ['确定','取消']
		}, function(){
			var id = $(ui).data("vo").id;
			var num = $(ui).data("vo").num;//编号
			var type;
			if(num.indexOf('HIV')==0){
				type = 2;//艾滋病
			}else if(num.indexOf('BY')==0){
				type = 3;//通用疾病
			}
			del(id,type);
		});
}


function del(Ids,type){
	var url;
	if(type==2){
		url='aids.action?act=delete';
	}else if(type==3){
		url='others.action?act=delete';
	}
	$.post(url,{'ids':aidsIds},function(data){
        var data=eval('(' + data + ')');
      	if(data.msg=="true"){
      		layer.open({
       			skin: 'layui-layer-molv',
       			closeBtn: 0,
       			title: false,
       			content:'删除成功!' ,
       			anim: 5 ,
       			btn: ['关闭'],
       			end:function(){
       				location.reload();
       			}
     		});
        } else {
              layer.open({
         			skin: 'layui-layer-molv',
         			closeBtn: 0,
         			title: false,
         			content:data.msg ,
         			anim: 5 ,
         			btn: ['关闭']
       		  });
        }
    });
}
//待检验页面跳转
function confirmJy(ui){
	var url = 'recheckOther.action?act=forCmmCheck&handle=jy&isBack=hide&vo.id='+$(ui).data("vo").id;
	var code = $(ui).data("vo").id;
	var title = "检验";
	comTabs(url,code,title);
}
function confirmSh(ui){
	doAjaxPost('recheckOther.action?act=shCmm',{id:$(ui).data("vo").id},function(data){
		if(data.msg == 'true'){
			var url = 'recheckOther.action?act=forCmmCheck&handle=sh&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "审核";
			comTabs(url,code,title);
		}else{
			layer.open({
     			skin: 'layui-layer-molv',
     			closeBtn: 0,
     			title: false,
     			content:data.msg ,
     			anim: 5 ,
     			btn: ['关闭']
   		  	});
		}
		
	})
}
function confirmQf(ui){
	doAjaxPost('recheckOther.action?act=qfCmm',{id:$(ui).data("vo").id},function(data){
		if(data.msg == 'true'){
			var url = 'recheckOther.action?act=forCmmCheck&handle=qf&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "签发";
			comTabs(url,code,title);
		}else{
			layer.open({
     			skin: 'layui-layer-molv',
     			closeBtn: 0,
     			title: false,
     			content:data.msg ,
     			anim: 5 ,
     			btn: ['关闭']
   		  	});
		}
		
	})
}

function forMsgSj(ui){
	var id = $(ui).data("vo").id;
	if(id==null||id==""){
		layer.open({
 			skin: 'layui-layer-molv',
 			closeBtn: 0,
 			title: false,
 			content:'该记录不存在或已处理' ,
 			anim: 5 ,
 			btn: ['关闭']
		  	});
		  	return;
	}
	var url;
	var type = $(ui).data("vo").msgTitle;
	var state = $(ui).data("vo").msgSpState;
	var contentId = $(ui).data("vo").msgContentId;
	if(type.indexOf('HIV')==0){
		title = "艾滋病";
		if(state==1){//保存待提交
			url = 'aids.action?act=forAddOrEdit&handle=modify&isBack=hide&vo.id='+contentId;
		}else if(state==10){//退样跳转到确认退样页面
			url = 'aids.action?act=forLook&handle=confirm&isBack=hide&vo.id='+contentId;
			title = "确认退样";
		}else if(state==9){//审核退回修改
			url = 'aids.action?act=forAddOrEdit&handle=modify&isBack=hide&vo.id='+contentId;
		}else if(state==5||state==98){//复检审核
			url = 'recheck.action?act=forCmmfj&handle=look&isBack=hide&vo.id='+contentId;
		}else if(state==8||state==99){//确证签发
			url = 'recheck.action?act=forCmmqz&handle=look&isBack=hide&vo.id='+contentId;
		}
			
	}else if(type.indexOf('BY')==0){
		title = "其他疾病";
		if(state==1){
			url = 'others.action?act=forAddOrEdit&handle=modify&isBack=hide&vo.id='+contentId;
		}else if(state==10){
			url = 'others.action?act=forLook&handle=confirm&isBack=hide&vo.id='+contentId;
			title = "确认退样";
		}else if(state==4){
			url = 'recheckOther.action?act=forCmmCheck&handle=jy&isBack=hide&vo.id='+contentId;
			title = "检验";
		}else if(state==5){
			url = 'recheckOther.action?act=forCmmCheck&handle=sh&isBack=hide&vo.id='+contentId;
			title = "审核";
		}	
	}
	var code = id;	
	comTabs(url,code,title);
}

function delMsg(ui){
	layer.confirm('确认删除数据?', {
		  btn: ['确定','取消']
		}, function(){
			var id = $(ui).data("vo").id;
			delMsgFun(id);
		});
}
function delMsgFun(id){

	$.post('flow.action?act=deleteMsgCmm',{'id':id},function(data){
        var data=eval('(' + data + ')');
      	if(data.msg=="true"){
      		layer.open({
       			skin: 'layui-layer-molv',
       			closeBtn: 0,
       			title: false,
       			content:'删除成功!' ,
       			anim: 5 ,
       			btn: ['关闭'],
       			end:function(){
       				location.reload();
       			}
     		});
        } else {
              layer.open({
         			skin: 'layui-layer-molv',
         			closeBtn: 0,
         			title: false,
         			content:data.msg ,
         			anim: 5 ,
         			btn: ['关闭']
       		  });
        }
    });
}


