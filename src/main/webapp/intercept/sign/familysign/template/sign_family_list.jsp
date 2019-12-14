<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<script type="tmp/xmgrid" id="family_tlist">
	<tr style="display:none">
        <td pofield="idx" style="display:none"></td>
		<td pofield="patientName" style="text-align:center"></td>
		<td pofield="patientIdno"style="text-align:center" ></td>
		<td pofield="patientCard"style="text-align:center" ></td>
		<td pofield="patientGender"style="text-align:center" ></td>
		<td pofield="patientAge"style="text-align:center" ></td>
		<td pofield="mfFmNickName"style="text-align:center" ></td>
		<td style="text-align: center"style="text-align:center">
            <a class="layui-btn layui-btn-mini" onclick="subpageLook($(this).closest('tr'))">
                <i class=layui-icon>&#xe615;</i>查看
            </a>
            <a class="layui-btn layui-btn-warm layui-btn-mini phis sysRoleAction_delete" onclick="subpageModfiy($(this).closest('tr'))">
                <i class=layui-icon>&#xe642;</i>修改
            </a>
            <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" onclick="subpageDelete($(this).closest('tr'))">
                <i class=layui-icon>&#xe640;</i>删除
            </a>
        </td>
		<td pofield="patientTel" style="display:none"></td>
		<td  style="display:none"><input pofield="persGroup" /> </td>
		<td  style="display:none"><input pofield="sJjType" />  </td>
		<td pofield="signFromDate" style="display:none"></td>
		<td pofield="signToDate" style="display:none"></td>
		<td pofield="signzfpay" style="display:none"></td>
		<td  style="display:none"><input pofield="signpackageid" /> </td>
		<td pofield="signtext" style="display:none"></td>
	</tr>
</script>
