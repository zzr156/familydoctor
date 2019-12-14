<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="codeRemark"></td>
				<td pofield="codeTitle"></td>
				<td pofield="codeValue"></td>
				<td pofield="codeSort"></td>
				<td pofield="stateName"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system code_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system code_modify" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>					
					<button type="button" class="btn btn-danger btn-sm system code_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
<script type="tmp/xmgrid" id="tlistJs">
			<tr style="display:none;text-align: center">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="codeTitle"></td>
				<td pofield="codeValue"></td>
				<td pofield="codeSort"></td>
				<td pofield="stateName"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system code_modifyJs" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system code_modifyJs" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button type="button" class="btn btn-danger btn-sm system code_deleteJs" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
