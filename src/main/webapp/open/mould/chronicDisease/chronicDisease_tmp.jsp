<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="patientName"></td>
				<td pofield="patientIdno"></td>
				<td pofield="HBPLabel"></td>
				<td pofield="DMLabel"></td>
				<td pofield="PMPLabel"></td>
				<td pofield="TBLabel"></td>
				<td pofield="operation">
					<button type="button" class="btn btn-info btn-sm system appNCD_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
				</td>
	 		</tr>
</script>
