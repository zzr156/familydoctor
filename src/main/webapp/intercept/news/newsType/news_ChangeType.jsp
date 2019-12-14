<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<body class="gray-bg">
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					选择类别
				</h4>
			</div>			
			<div class="modal-body">
			<div class="row">
                    <div class="col-sm-12">
                    	<table id="mytable" class="table table-striped  table-hover table-left">               
                    		<tbody id="tb"></tbody>
                    	</table>
                    	<div  class="text-center btnBar">
                    		<a class="btn btn-primary btn-sm" onclick="chageNewsType()" data-dismiss="modal">提交</a>
                    	</div>
                    </div>
                </div>
				
		</div>
	</div>
</div>	
<jsp:include page="/open/mould/news/newsType/news_change_tmp.jsp" flush="false" />
<script type="text/javascript">  
   function uiFromTmpNewsTypeTop(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmptop_idlsttr_rs(t, data,idx);
		$("#tb").append(t);
		t.fadeIn();
	}
	function dataToUiTmptop_idlsttr_rs(ui, data,idx) {
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=typePid]").text(data.typePid);
		var d=document.createElement("input");
        d.setAttribute("type","radio");
        d.setAttribute("name","changeNewsType");
        d.setAttribute("id","changeNewsType");
        d.setAttribute("value",data.resultNewsType);
		ui.find("td[pofield=typeName]").append(d);
		ui.find("td[pofield=typeName]").append(data.typeName);
	}

</script>

<script type="text/javascript">
	
	
  	//类型树结构
    function findNewsType(){
    	doAjaxPost('<%=request.getContextPath()%>/newsType.action?act=jsonTreelist',{},callDataToNewsTypeTop);	
    }
    function callDataToNewsTypeTop(data){
    	$("#tb").html();
        $.each(data, function(k, v) {
        	uiFromTmpNewsTypeTop("newsChange_tlist", v,k);
        });
        $.treetable.defaults={
   			id_col:0,//ID td列 {从0开始}
   			parent_col:1,//父ID td列
   			handle_col:2,//加上操作展开操作的 td列
   			order_col:4,
   			open_img:"open/images/minus.gif",
   			close_img:"open/images/plus.gif"
   		}
   		//$("#tb").treetable({id_col:0,parent_col:1,handle_col:2,open_img:"images/minus.gif",close_img:"images/plus.gif"});
   		//只能应用于tbody
   		$("#tb").treetable();
   		//应用样式
   		$("#tb tr:even td").addClass("alt");
   		$("#tb tr").find("td:eq(2)").addClass("spec");
   		$("#tb tr:even").find("td:eq(2)").removeClass().addClass("specalt");
   		
   		//隐藏数据列
   		$("#tb tr").find("td:eq(0)").hide();
   		$("#tb tr").find("td:eq(1)").hide();
   		$("#mytable tr:eq(0)").find("th:eq(0)").hide();
   		$("#mytable tr:eq(0)").find("th:eq(1)").hide();
    }
    
	function chageNewsType(){
        var chkRadio = null;
	 	 $("input:radio[name='changeNewsType']:checked").each(function(){ 
	 			chkRadio = $(this).attr("value");
	     }); 
       	if (chkRadio == null) {
           alert("没有选中项");
           return false;
        } else {	      
	       	var chkRadios = chkRadio.split(";;;");
	       	$('#typePid').val(chkRadios[0]);
	       	$('#typePName').val(chkRadios[1]);
       }
	}
</script>
