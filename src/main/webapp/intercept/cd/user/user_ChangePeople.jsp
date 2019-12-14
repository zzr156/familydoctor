<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<body class="gray-bg">
<div class="modal fade" id="myModalCdUserDept" tabindex="-1" role="dialog" aria-labelledby="myModalLabelCdUserDept" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabelCdUserDept">
					选择部门
				</h4>
			</div>
			<div class="modal-body">
				<table id="mytableCdUserDept" class="table table-striped  table-hover table-left">
					<tbody id="tbCdUserDept">
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button> 
				<button type="button" onclick="chageCdUserDept()" class="btn btn-primary" data-dismiss="modal">
					提交
				</button>
			</div>
		</div>
	</div>
</div>	
<jsp:include page="/open/mould/cd/user/user_change_tmp.jsp" flush="false" />
<script type="text/javascript">  
   function uiFromTmpDept(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#tbCdUserDept").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=sid]").text(data.sid);
		var d=document.createElement("input");
        d.setAttribute("type","radio");
        d.setAttribute("name","changeCdUserDept");
        d.setAttribute("id","changeCdUserDept");
        d.setAttribute("value",data.resultDept);
		ui.find("td[pofield=sname]").append(d);
		ui.find("td[pofield=sname]").append(data.sname);
	}

</script>

<script type="text/javascript">
	
	
  	//部门
    function findDept(){
    	doAjaxPost('<%=request.getContextPath()%>/dept.action?act=jsonCmmList',{},callDataToDept);	
    }
    function callDataToDept(data){
    	$("#tbCdUserDept").html("");
        $.each(data, function(k, v) {
        	 uiFromTmpDept("dept_tlist", v,k);
        });
        if(data.length=="1"&&data[0].deptType=="0"){
	    		//$("#sjdwSelect").attr("value",data[0].id);
	    		$("#deptName").val(data[0].sname);
	    		$("#deptId").val(data[0].id);
	    		$("#deptId").attr("disabled",true);
	    }
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
   		$("#tbCdUserDept").treetable();
   		//应用样式
   		$("#tbCdUserDept tr:even td").addClass("alt");
   		$("#tbCdUserDept tr").find("td:eq(2)").addClass("spec");
   		$("#tbCdUserDept tr:even").find("td:eq(2)").removeClass().addClass("specalt");
   		
   		//隐藏数据列
   		$("#tbCdUserDept tr").find("td:eq(0)").hide();
   		$("#tbCdUserDept tr").find("td:eq(1)").hide();
   		$("#mytableCdUserDept tr:eq(0)").find("th:eq(0)").hide();
   		$("#mytableCdUserDept tr:eq(0)").find("th:eq(1)").hide();
   		
   		findTable();
    }
    
	function chageCdUserDept(){
	     var chkRadio = null;
	 	 $("input:radio[name='changeCdUserDept']:checked").each(function(){ 
	 			chkRadio = $(this).attr("value");
	     }); 
        if (chkRadio == null) {
            alert("没有选中项");
            return false;
        } else {	      
        	var chkRadios = chkRadio.split(";;;");
        	$('#deptId').val(chkRadios[0]);
        	$('#deptName').val(chkRadios[1]);
        }
	}
</script>
