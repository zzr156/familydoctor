<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<title>轮播图片管理主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">							                
	                <div class="ibox-content" id="form_qvo">
	                    <form method="get" class="form-horizontal">
	                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
	                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
	                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
	                        <input type="hidden" pofield="pageSize" id="pageSize" value="15">
	                 	</form>
	                 </div>       
				</div>
			</div>
		</div>
		
	
		<div class="row">
	        <div class="col-sm-12">
	            <div class="ibox float-e-margins">
	            	<div class="ibox-title">
	                    <a class="label label-success pull-right system carousel_add" onclick="newcarousel();">新增</a>
					</div>	
	                <div class="ibox-content tabble-c">
	                    <div class="table-responsive">
	                        <table class="table table-striped" id="mytable">
	                            <thead>
	                            <tr>
                                	<th style="display: none;"></th>
									<th style="text-align: center;width:20%">创建时间</th>
									<th style="text-align: center;width:50%">图片</th>
									<th style="text-align: center;width:10%">排序</th>
									<th style="text-align: center;width:20%">操作</th>
	                            </tr>
	                            </thead>
	                            <tbody id="carousel">
	
								</tbody>
	                        </table>
	                    </div>
	                    <!--分页按钮-->
	                    <div class="text-center" style="background: #fff; padding-top: 5px;">
	                        <button type="button" class="btn btn-sm" onclick="Previous();findTable();">前一页</button>
	                        <span id="qvodesc">1/1每页显示:15条,共有1条</span>&nbsp;&nbsp;
	                        <button type="button" class="btn btn-sm" onclick="Next();findTable();">下一页</button>
	                        <input style="width: 40px" class="span2" id="gotext" type="text">
	                        <button class="btn btn-sm" onclick="qvoGo();findTable();" type="button">Go!</button>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
<jsp:include page="/open/mould/cd/carousel/carousel_tmp.jsp" flush="false" />	
<script type="text/javascript">
	function uiFromTmp(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#carousel").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		if(data.cjsj==null){
			ui.find("td[pofield=cjsj]").text("");
		}else{
			ui.find("td[pofield=cjsj]").text(data.strCjsj);
		}
		ui.find("td[pofield=imgUrl]").text(data.imgUrl);
		ui.find("td[pofield=simage]").html('<a id="picFile1E" href="<%=request.getContextPath()%>/open/commonjs/img.jsp?url='+data.imgUrl+'" target="_blank" ><img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+data.imgUrl+'"/></a>');
		ui.find("td[pofield=px]").text(data.px); 
	}

</script>
<script type="text/javascript">
	var qvo={};
	var vo={};
	$(function(){
		findTable();
	})
	//查询
    function findTable(){
		qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        doAjaxPost('<%=request.getContextPath()%>/carousel.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
    	dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        $("#carousel").html("");
        $.each(data.rows, function(k, v) {
            uiFromTmp("carousel_tlist", v,k);
        });
    }
	//点击新增
	function newcarousel(){
		var url = '<%=request.getContextPath()%>/carousel.action?act=forAddOrEdit';
		defualtHref(url);
	}
	//点击删除
	function deleteForm(ui){
		layer.confirm('确认删除数据?', {
		  btn: ['确定','取消'] 
		}, function(){
			var imgId = $(ui).data("vo").id;
	        del(imgId);
		});
	}  
	function del(imgId){
     	$.post('<%=request.getContextPath()%>/carousel.action?act=delete',{'id':imgId},function(data){
             var data=eval('(' + data + ')');
             if(data.msg=="true"){
           		layer.open({
           			skin: 'layui-layer-molv',
           			closeBtn: 0,	    			  
           			title: false,
           			content:'删除成功!' ,
           			anim: 5 ,
           			btn: ['关闭']
          		});
           		findTable();
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
</script>
</body>
</html>
