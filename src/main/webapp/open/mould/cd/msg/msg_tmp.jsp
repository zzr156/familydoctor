<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="msg_tlist">
	<tr style="display:none">
				<td pofield="id" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="userId" style="display:none"></td>
				<td pofield="num"></td>
				<td pofield="msgTitle"></td>
				<td pofield="msgType"></td>
				<td pofield="msgUserName"></td>
				<td pofield="msgCreaterDate"></td>
				<td>
					<button type="button" class="btn btn-danger btn-sm system cdmsg_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
					<button type="button" class="btn btn-primary btn-sm system cdmsg_view" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
				</td>
	 </tr>
</script>
<script type="tmp/xmgrid" id="msg_tlistes">
	<tr style="display:none">
		<td pofield="id"></td>
		<td pofield="sid"></td>
		<td pofield="sname"></td>
		<td pofield="deptchoose"></td>
		<td pofield="userlist"></td>
	</tr>
</script>