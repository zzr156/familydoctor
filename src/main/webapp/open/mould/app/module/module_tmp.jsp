<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="moduleName"></td>
				<td pofield="moduleRemark"></td>
				<td pofield="strModuleState"></td>
				<td pofield="strModuleType"></td>
				<td pofield="strModuleAreaCode"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system appmodulerole_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system appmodulerole_sonModify" onclick="forRoleMenuTable($(this).closest('tr'));"><i class="fa fa-search"></i>设置权限</button>
					<button type="button" class="btn btn-primary btn-sm system appmodulerole_list" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button type="button" class="btn btn-danger btn-sm system appmodulerole_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
