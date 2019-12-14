<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="msgId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="appName"></td>
				<td pofield="appPgName"></td>
				<td pofield="appId"></td>
				<td pofield="appMasterSecret"></td>
				<td pofield="appKey"></td>
				<td pofield="operation">
					<button pofield="editid" id="editid" type="button" class="btn btn-info btn-sm system appMsg_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
					<button pofield="lookid" type="button" class="btn btn-primary btn-sm system appMsg_modify" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button  pofield="delectid" id="delectid" type="button" class="btn btn-danger btn-sm system appMsg_modify" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
				</td>
	 		</tr>
</script>
