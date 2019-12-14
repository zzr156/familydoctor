<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="userId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="userName"></td>
				<td pofield="userNum"></td>
				<td pofield="account"></td>
				<td pofield="cdDeptUnit"></td>
				<td pofield="cdDeptName"></td>
				<td pofield="userPostname"></td>
				<td pofield="workStateName"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system users_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system users_modify" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>					
					<button type="button" class="btn btn-danger btn-sm system users_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
