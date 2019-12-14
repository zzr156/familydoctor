<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="pwTitle"></td>
				<td pofield="pwBm"></td>
				<td pofield="strPwState"></td>
				<td pofield="strPwQysj"></td>
				<td pofield="strDyb"></td>
				<td pofield="pwBbh"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-sm btn-info system cdpw_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-sm btn-primary system cdpw_modify" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button type="button" class="btn btn-sm btn-primary system cdpw_xz" onclick="forXzTable($(this).closest('tr'));"><i class="fa fa-download"></i>下载</button>					
					<button type="button" class="btn btn-sm btn-danger system cdpw_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
