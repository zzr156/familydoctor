<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>人员选择器</title>
    <%@ include file="/open/commonjs/tld.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body onload="FindData('list')">
<!--list-->
<table id="dg" toolbar="#toolbar"  class="easyui-treegrid"
       data-options="fit:true,
                url: '<%=request.getContextPath()%>/dept.action',
                method: 'post',
                idField: 'id',
                treeField: 'sname'
            ">
    <thead>
    <tr>
        <th data-options="field:'sname'" width="20%">部门名称</th>
        <th data-options="field:'optx',align:'center',formatter:rowformaterxz" width="10%">部门选择</th>
        <th data-options="field:'opt',align:'left',formatter:rowformater" width="70%">人员</th>
    </tr>
    </thead>
</table>
<div id="toolbar" style="padding:8px;height:auto">
    <table cellspacing="0" cellpadding="0"><tr><td>
    <a href=javascript:selectAllUser()>[全选]</a><a href=javascript:selectNotAllUser()>[取消]</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="allOk()">确定</a>
        </td></tr></table>
</div>
<script type="text/javascript">
    <!-- list -->
    function FindData(actvalue){
        $('#dg').treegrid('load',{
                    act:actvalue
                }
        );
    }
    $.ajaxSetup({
        async : false
    });
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
        parent.backFindUser(userCodes,userNames);
    }
    function selectAllUser(){
        $.parser.parse();
        $('[id=users]:checkbox').prop("checked", true);
    }
    function selectNotAllUser(){
        $.parser.parse();
        $('[id=users]:checkbox').prop("checked", false);
    }
    function selectSinleDeptAllUser(vals){
        $('[name=users'+vals+']:checkbox').prop("checked", true);

    }
    function selectNotDeptAllUser(vals){
        $('[name=users'+vals+']:checkbox').prop("checked", false);

    }
    function rowformaterxz(value,row,index) {
        return "<a href=javascript:selectSinleDeptAllUser('"+row.id+"')>[全选]</a><a href=javascript:selectNotDeptAllUser('"+row.id+"')>[取消]</a>";
    }
    function rowformater(value,row,index) {
        var vars="";
        $.post('<%=request.getContextPath()%>/cdjson.action?act=jsonTreelist&type=deptuser',{'deptid':row.id},function(data){
            if(data!=null){
                var data=eval('(' + data + ')');
                vars=vars+"<div style='white-space: normal;'>";
                $.each(data,function(i,n){
                    if(n.text!="") {
                        vars=vars+"<div style='width: 80px;display: inline-block;'><input type='checkbox' name='users"+row.id+"' id='users' value=" + n.id + " attrUserNames=" + n.text + " />" + n.text+"</div>";
                    }
                });
                vars=vars+"</div>";
                return vars;
            }else{
                return vars;
            }
        });
        return vars;
    }

</script>



</body>


</html>
