<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tb_tlist">

	<tr style="display:none">
		<td style="display:none"><input type="text" class="form-control" pofield="ypId"></td>
		<td><input type="text" class="form-control" pofield="ypName"></td>
		<td><input type="text" class="form-control" pofield="ypNum"></td>
		<td><input type="text" class="form-control" pofield="ypPacking"></td>
		<td><input type="text" class="form-control" pofield="ypSpecifications"></td>
		<td><input type="text" class="form-control" pofield="ypTesting"></td>
		<td><input type="text" class="form-control" pofield="ypColdStorage"></td>
		<td>
			<button pofield="delete" type="button" class="btn btn-sm btn-danger" onclick="delFormSs($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>
	</tr>
 
</script>
<script type="tmp/xmgrid" id="tb_tlistest">
	<tr style="display:none">
		
		<td pofield="id" style="display:none"></td>
		<td pofield="idx"></td>
		<td pofield="baseXx"></td>
		<td pofield="syDwName"></td>
		<td pofield="syDate"></td>
		<td pofield="zzName"></td>
		<td >
			<button type="button" pofield="look" class="btn btn-sm btn-primary" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
			<button type="button" pofield="modify" class="btn btn-sm btn-info" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
			<button type="button" pofield="qs" class="btn btn-sm btn-primary" onclick="forQsTable($(this).closest('tr'));"><i class="fa fa-pencil-square"></i>签收</button>
			<button type="button" pofield="jc" class="btn btn-sm btn-primary" onclick="forDetection($(this).closest('tr'));"><i class="fa fa-hourglass-2"></i>检测</button>
			<button type="button" pofield="delete" class="btn btn-sm btn-danger" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
			<button type="button" pofield="print" class="btn btn-sm btn-warning" onclick="forPrintTable($(this).closest('tr'));">
<i class="fa fa-print"></i>打印</button>
		</td>
	
	</tr>
</script>

<script type="tmp/xmgrid" id="tb_tlistt">
	<tr style="display:none">
		<td pofield="clEnclosurePath" style="display:none"></td>
		<td pofield="fjId" style="display:none"></td>
		<td pofield="idx"></td>
		<td pofield="clName"></td>
		<td>
			<button pofield="downLoad" type="button" class="btn btn-sm btn-primary" onclick="downLoad($(this).closest('tr'));"><i class="fa fa-download"></i>下载</button>
			<button pofield="delete" type="button" class="btn btn-sm btn-danger" onclick="delFile($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>
	</tr>
</script>
