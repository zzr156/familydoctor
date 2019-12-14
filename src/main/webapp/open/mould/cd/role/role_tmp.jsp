<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="rname"></td>
				<td pofield="rnum"></td>
				<td pofield="remark"></td>
				<td pofield="stateName"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system role_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system roleMenu_modify" onclick="forRoleMenuTable($(this).closest('tr'));"><i class="fa fa-exclamation-triangle"></i>权限管理</button>					
					<button type="button" class="btn btn-danger btn-sm system role_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
