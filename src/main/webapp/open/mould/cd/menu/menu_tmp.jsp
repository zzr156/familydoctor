<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="menu_tlist">
	<tr style="display:none">
				<td pofield="id"></td>
				<td pofield="pid"></td>
				<td pofield="mname" style="text-align:left;"></td>
				<td pofield="sonListName"></td>
				<td pofield="onumber"></td>
				<td pofield="stateName"></td>
				<td>
				<button type="button" class="btn btn-success btn-sm system menu_add" onclick="menu_ForAddZi($(this).closest('tr'));"><i class="fa fa-plus-circle"></i>添加子菜单</button>
				<button type="button" class="btn btn-primary btn-sm system son_list" onclick="menu_ForAdd($(this).closest('tr'));"><i class="fa fa-plus-circle"></i>添加功能</button>
				<button type="button" class="btn btn-info btn-sm system menu_modify" onclick="menu_ForModify($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
				<button type="button" class="btn btn-danger btn-sm system menu_delete" onclick="menu_del($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>				
				</td>
	 </tr>
</script>
<script type="tmp/xmgrid" id="menu_tlistes">
	<tr style="display:none">
		<td pofield="id"></td>
		<td pofield="pid"></td>
		<td pofield="mname"></td>
	</tr>
</script>
<script type="tmp/xmgrid" id="son_tlist">
	<tr style="display:nonne">
		<td pofield="id"/>
		<td pofield="sname"></td>
		<td pofield="nature"></td>
		<td pofield="remark"></td>
		<td style="text-align: center;">
			<button type="button" class="btn btn-primary system son_delete" onclick="son_del($(this).closest('tr'));">删除</button>
		</td>
	</tr>

</script>