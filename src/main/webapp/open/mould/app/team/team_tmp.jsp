<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="teamId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="teamHospName"></td>
				<td pofield="teamCode"></td>
				<td pofield="teamName"></td>
				<td pofield="teamDrName"></td>
				<%--<td pofield="teamDrCode"></td>
				<td pofield="teamTel"></td>--%>
				<td pofield="teamStateName"></td>
				<td pofield="teamCreateTime"></td>
				<td pofield="operation">
				    <button type="button" class="btn btn-info btn-sm system appteam_add" onclick="forAddMemTable($(this).closest('tr'));">添加成员</button>
					<button type="button" class="btn btn-info btn-sm system appteam_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system appteam_list" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button type="button" class="btn btn-danger btn-sm system appteam_list" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
<script type="tmp/xmgrid" id="mem_tlist">
			<tr style="display:none;text-align: center">
				<td pofield="memId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="teamName"></td>
				<td pofield="memName"></td>
				<td pofield="workType"></td>
				<td pofield="hospName"></td>
				<td pofield="operation">
				<%--	<button type="button" class="btn btn-info btn-sm system appteam_modify" onclick="saveTable($(this).closest('tr'));"><i class="fa fa-cog"></i>保存</button>--%>
					<button type="button" class="btn btn-danger btn-sm system appteam_list" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>

