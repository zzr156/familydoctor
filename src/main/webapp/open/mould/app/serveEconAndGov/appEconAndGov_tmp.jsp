<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="gId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="econTitle"></td>
				<td pofield="govTitle"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system appEag_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system appEag_list" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button type="button" class="btn btn-danger btn-sm system appEag_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
					<%--<button type="button" class="btn btn-primary btn-sm system appEag_modify" onclick="bj($(this).closest('tr'));"><i class="fa fa-search"></i>标记</button>--%>
				    <button pofield="open" type="button" class="btn btn-primary btn-sm system appEag_openState" onclick="openState($(this).closest('tr'));"><i class="fa fa-search"></i>开启</button>
                    <button pofield="openHosp" type="button" class="btn btn-info btn-sm system appEag_openObject"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser($(this).closest('tr'))">选择开放医院</button>
				</td>
	 		</tr>
</script>
