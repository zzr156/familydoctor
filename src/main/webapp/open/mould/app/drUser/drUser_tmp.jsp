<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="drUserId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="drName"></td>
				<td pofield="drCode"></td>
				<td pofield="drAccount"></td>
				<td pofield="hospName"></td>
				<td pofield="drJobTitle"></td>
				<td pofield="drTypeName"></td>
				<td pofield="drStateName"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system appdr_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system appdr_list" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button type="button" class="btn btn-danger btn-sm system appdr_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
