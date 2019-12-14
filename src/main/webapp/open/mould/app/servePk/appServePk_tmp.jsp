<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="pkId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="pkValue"></td>
				<td pofield="pkImage"></td>
				<td pofield="pkName"></td>
				<td pofield="pkState"></td>
				<td pofield="pkNum"></td>
				<td pofield="pkCreateTime"></td>
				<td pofield="operation">
					<button pofield="editid" id="editid" type="button" class="btn btn-info btn-sm system appPk_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button pofield="lookid" type="button" class="btn btn-primary btn-sm system appPk_list" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button  pofield="delectid" id="delectid" type="button" class="btn btn-danger btn-sm system appPk_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
					<button pofield="bj" type="button" class="btn btn-primary btn-sm system appPk_list" onclick="bj($(this).closest('tr'));"><i class="fa fa-search"></i>标记</button>
					<button pofield="open" type="button" class="btn btn-primary btn-sm system appPk_openState" onclick="openState($(this).closest('tr'));"><i class="fa fa-search"></i>开启</button>
				    <button pofield="openHosp" type="button" class="btn btn-info btn-sm system appPk_openObject"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser($(this).closest('tr'))">选择开放医院</button>
				</td>
	 		</tr>
</script>
