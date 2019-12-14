<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="mId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="smValue"></td>
				<td pofield="smName"></td>
				<td pofield="smObjectName"></td>
				<td pofield="smPkName"></td>
				<td pofield="smTotalFee"></td>
				<td pofield="smJmState"></td>
				<td pofield="smJjType"></td>
				<td pofield="smBtWay"></td>
				<%--<td pofield="smFee"></td>--%>
				<td pofield="smTimeState"></td>
				<td pofield="smBgState"></td>
				<td pofield="areaName"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system appSerMeal_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button type="button" class="btn btn-primary btn-sm system appSerMeal_list" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button type="button" class="btn btn-danger btn-sm system appSerMeal_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
					<button pofield="bj" type="button" class="btn btn-primary btn-sm system appSerMeal_list" onclick="bj($(this).closest('tr'));"><i class="fa fa-search"></i>设为快签包</button>
					<button pofield="open" type="button" class="btn btn-primary btn-sm system appSerMeal_openState" onclick="openState($(this).closest('tr'));"><i class="fa fa-search"></i>开启</button>
					<%--<button pofield="openHosp" type="button" class="btn btn-info btn-sm system appSerMeal_openObject"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser($(this).closest('tr'))">选择开放医院</button>--%>
					<button type="button" class="btn btn-info btn-sm system appSerMeal_print" onclick="print($(this).closest('tr'));"><i class="fa fa-cog"></i>打印协议</button>
				</td>
	 		</tr>
</script>
