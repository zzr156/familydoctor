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
					选择菜单
				</h4>
			</div>
			<div class="modal-body">
				<table id="mytable" class="table table-striped  table-hover table-left">
					<tbody id="tb">
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button> 
				<button type="button" onclick="chageMenu()" class="btn btn-primary" data-dismiss="modal">
					提交
				</button>
			</div>
		</div>
	</div>
</div>	
<jsp:include page="/open/mould/cd/menu/menu_tmp.jsp" flush="false" />
<script type="text/javascript">  
   function uiFromTmpDept(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#tb").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=pid]").text(data.pid);
		ui.find("td[pofield=mname]").html("<input type='radio' name='menuck' value='"+data.resultMenu+"'/> "+data.mname);
		
	}

</script>

<script type="text/javascript">
	function findMenu(){
		 doAjaxPost('<%=request.getContextPath()%>/menu.action?act=jsonlist',{},callDataToMenu);	
	}
	function callDataToMenu(data){
		$("#tb").html("");
	    $.each(data, function(k, v) {
	    	uiFromTmpDept("menu_tlistes", v,k);
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
	function chageMenu(){
	    var chkRadio = $('input:radio[name="menuck"]:checked').val();
        if (chkRadio == null) {
            alert("没有选中项");
            return false;
        } else {
        	var chkRadios = chkRadio.split(";;;");
        	$('#pid').val(chkRadios[0]);
        	$('#parnentMname').val(chkRadios[1]);
        }
	}
</script>
