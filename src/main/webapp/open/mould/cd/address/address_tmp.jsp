<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="address_tlist">
	<tr style="display:none">
				<td pofield="areaName"></td>
				<td pofield="ctcode"></td>
				<td pofield="areaSname"></td>
				<td>
				<button type="button" class="btn btn-sm btn-primary system address_add" onclick="address_ForAddZi($(this).closest('tr'));"><i class="fa fa-plus-circle"></i>添加子地区</button>
				<button type="button" class="btn btn-sm btn-primary system address_modify" onclick="address_ForModify($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
				<button type="button" class="btn btn-sm btn-primary system address_delete" onclick="address_del($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>				
				</td>
	 </tr>
</script>
