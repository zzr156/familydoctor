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
					接收人员选择
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-12">                               	
                    	<a class="label label-default pull-right" href=javascript:selectNotAllUser()>[取消]</a>
                    	<a class="label label-primary pull-right" href=javascript:selectAllUser()>[全选]</a>
                    </div> 
                    <div class="col-sm-12">
                    	<table id="mytable" class="table table-striped  table-hover table-left">
                    		<thead>
                    			<tr>
                    				<th></th>
                    				<th></th>
                    				<th>
                    					部门名称
                    				</th>
                    				<th>
                    					部门选择
                    				</th>
                    				<th>
                    					人员
                    				</th>
                    			</tr>
                    		</thead>
                    		<tbody id="tb"></tbody>
                    	</table>
                    	<div  class="text-center btnBar">
                    		<a class="btn btn-primary btn-sm" onclick="allOk()" data-dismiss="modal">保存</a>
                    	</div>
                    </div>
                </div>
				
		</div>
	</div>
</div>	
<!--list-->
<jsp:include page="/open/mould/cd/msg/msg_tmp.jsp" flush="false" />
<script type="text/javascript">
	function uiFromTmpUi(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#tb").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=sid]").text(data.sid);
		ui.find("td[pofield=sname]").text(data.sname);
		ui.find("td[pofield=deptchoose]").html("<a href=javascript:selectSinleDeptAllUser('"+data.id+"')>[全选]</a><a href=javascript:selectNotDeptAllUser('"+data.id+"')>[取消]</a>")
		doAjaxPost('<%=request.getContextPath()%>/cdjson.action?act=jsonTreelist',{'deptid':data.id,'type' :'deptuser'},function(datas){
			$.each(datas, function(k, v) {
				var i = k+1;
				if(i%5 ==0){
					var s=document.createElement("input");
			        s.setAttribute("type","checkbox");
			        s.setAttribute("name","users"+data.id);
			        s.setAttribute("id","users");
			        s.setAttribute("value",v.id);
			        s.setAttribute("attrUserNames",v.text);
			        ui.find("td[pofield=userlist]").append(s);
			        ui.find("td[pofield=userlist]").append(v.text);
			        ui.find("td[pofield=userlist]").append("&nbsp;&nbsp;");
			        ui.find("td[pofield=userlist]").append("<br/>");
				}else{
					var s=document.createElement("input");
			        s.setAttribute("type","checkbox");
			        s.setAttribute("name","users"+data.id);
			        s.setAttribute("id","users");
			        s.setAttribute("value",v.id);
			        s.setAttribute("attrUserNames",v.text);
			        ui.find("td[pofield=userlist]").append(s);
			        ui.find("td[pofield=userlist]").append(v.text);
			        ui.find("td[pofield=userlist]").append("&nbsp;&nbsp;");
				}
			});
		});
	}

</script>
<script type="text/javascript">
	var jsonvo={};
	function findUser(){
		doAjaxPost('<%=request.getContextPath()%>/dept.action?act=jsonTreelist',{},callDataToUi);	
    }
    function callDataToUi(data){
    	$("#tb").html("");
        $.each(data, function(k, v) {
        	uiFromTmpUi("msg_tlistes", v,k);
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
    function allOk(){
        users=$('[id=users]:checkbox');
        userCodes = "";
        userNames = "";
        for(i=0;i<users.length;i++)
        {
            if(users[i].checked)
            {
                if(userCodes.indexOf(users[i].value) == -1){
                    userCodes+=users[i].value+";";}
                if(userNames.indexOf(users[i].getAttribute("attrUserNames")) == -1){
                    userNames+=users[i].getAttribute("attrUserNames")+";";
                }
            }
        }
        $("#userNames").val(userNames);
        $("#userCodes").val(userCodes);
    }
    function selectAllUser(){
        $('[id=users]:checkbox').prop("checked", true);
    }
    function selectNotAllUser(){
        $('[id=users]:checkbox').prop("checked", false);
    }
    function selectSinleDeptAllUser(vals){
        $('[name=users'+vals+']:checkbox').prop("checked", true);

    }
    function selectNotDeptAllUser(vals){
        $('[name=users'+vals+']:checkbox').prop("checked", false);

    }

</script>



</body>


</html>
