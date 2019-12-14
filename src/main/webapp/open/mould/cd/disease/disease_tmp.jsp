<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="itemIcd"></td>
				<td pofield="itemName"></td>
				<td pofield="itemInspectName"></td>
				<td pofield="itemStateName"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system disease_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-default btn-sm system disease_blockDisease" onclick="forBlockTable($(this).closest('tr'));"><i class="fa fa-minus-circle"></i>停用</button>
					<button type="button" class="btn btn-primary btn-sm system disease_modify" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>					
					<button type="button" class="btn btn-danger btn-sm system disease_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
