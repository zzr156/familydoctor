<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="dept_tlist">
	<tr style="display:none">
				<td pofield="id"></td>
				<td pofield="sid"></td>
				<td pofield="sname"  style="text-align:left;"></td>
				<td pofield="roleName"></td>
				<td pofield="snumber"></td>
				<td pofield="deptType"></td>
				<td pofield="state"></td>
				<td>
				<button type="button" class="btn btn-primary btn-sm system dept_add" onclick="dept_ForAddZi($(this).closest('tr'));"><i class="fa fa-plus-circle"></i>添加子部门</button>
				<button type="button" class="btn btn-info btn-sm system dept_modify" onclick="dept_ForModify($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
				<button type="button" class="btn btn-danger btn-sm system dept_delete" onclick="dept_del($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>				
				</td>
	 </tr>
</script>
<script type="tmp/xmgrid" id="dept_tlistes">
	<tr style="display:none">
		<td pofield="id"></td>
		<td pofield="sid"></td>
		<td pofield="sname"></td>
	</tr>
</script>