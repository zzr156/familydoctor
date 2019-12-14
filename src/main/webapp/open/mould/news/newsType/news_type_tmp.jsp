<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
	<tr style="display:none">
				<td pofield="id"></td>
				<td pofield="typePid"></td>
				<td style="text-align:left;" pofield="typeName"></td>
				<td pofield="typeNum"></td>
				<td pofield="typeStateName"></td>
				<td>
				<button type="button" class="btn btn-success btn-sm system newsType_addSon" onclick="forAddTableZi($(this).closest('tr'));"><i class="fa fa-plus-circle"></i>添加子类型</button>
				<button type="button" class="btn btn-primary btn-sm system newsType_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
				<button type="button" class="btn btn-danger btn-sm system newsType_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>				
				</td>
	 </tr>
</script>
