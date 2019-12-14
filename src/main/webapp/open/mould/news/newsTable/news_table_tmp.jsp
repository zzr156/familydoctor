<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
	<tr style="display:none">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="tableTitle"></td>
				<td pofield="strTableType"></td>
				<td pofield="tableCjrxm"></td>
				<td pofield="strTableCjsj"></td>
				<td>
				<button type="button" class="btn btn-sm btn-info system newsTable_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
				<button type="button" class="btn btn-sm btn-primary system newsTable_modify" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
				<button type="button" class="btn btn-sm btn-danger system newsTable_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>				
				<button type="button" class="btn btn-sm btn-primary system newsTable_set" onclick = "setTs($(this).closest('tr'))">设置推送</button>
				</td>
	 </tr>
</script>

<script type="tmp/xmgrid" id="hosp_tlist">
	<tr style="display:none">
		<td pofield="userId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBoxx"/></td>
        <td pofield="num"></td>
        <td pofield="patientName"></td>
        <td pofield="sex"></td>
        <td pofield="age"></td>
        <td pofield="orgName"></td>
        <td pofield="drName"></td>
        <td pofield="signTime"></td>
	</tr>
</script>
