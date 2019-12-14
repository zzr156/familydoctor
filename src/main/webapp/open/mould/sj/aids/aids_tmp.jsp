<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="aids_tlist">

	<tr style="display:none">
		<td style="display:none"><input type="text" class="form-control" pofield="ypId"></td>
		<td>
			<select pofield="ypName" class="form-control">
				<option value>--请选择--</option>
            </select>
			<input pofield="ypOtherName" class="form-control" placeholder='请输入其他的具体名称' style="display:none">
		</td>
		<td><input type="text" class="form-control" pofield="ypNum"></td>
		<td aa="ypHemolysis">
			
		</td>
		<td>
			<button type="button" class="btn btn-success" onclick="addYpTable()">新增</button>
			<button pofield="delete" type="button" class="btn btn-danger" onclick="delFormSs($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>
	</tr>
	
</script>
<script type="tmp/xmgrid" id="aids_shYplist">

	<tr style="display:none">
		<td><span pofield="ypName" class="text-center block"></span></td>
		<td><span pofield="ypNum" class="text-center block"></span></td>
		<td><span pofield="ypHemolysis" class="text-center block"></span></td>
	</tr>
	
</script>
<script type="tmp/xmgrid" id="aids_tlistest">
	<tr style="display:none">
		<td style="display:none"><input type="text" class="form-control" pofield="jlId"></td>
		<td>
			<select pofield="jlTestRecord" class="form-control">
				<option value>--请选择--</option>
            </select>
		</td>
		<td>
			<select pofield="jlTestMethod" class="form-control">
				<option value>--请选择--</option>
            </select>
		</td>
		<td><input type="text" class="form-control" pofield="jlTestDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
		<td><input type="text" class="form-control" pofield="jlReagentManufacturers"></td>
		<td><input type="text" class="form-control" pofield="jlBatchNumber"></td>
		<td><input type="text" class="form-control" pofield="jlEffectiveDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
		<td>
			<select pofield="jlTestResult" class="form-control">
				<option value>--请选择--</option>
            </select>
		</td>
		<td >
			<button pofield="delete" type="button" class="btn btn-danger system aids_deleteJc" onclick="delFormRecord($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>

	</tr>
</script>


<script type="tmp/xmgrid" id="aids_fileList">
	<tr style="display:none">
		<td pofield="clEnclosurePath" style="display:none"></td>
		<td pofield="clId" style="display:none"></td>
		<td pofield="idx"></td>
		<td pofield="clName"></td>
		<td>
			<button pofield="downLoad" type="button" class="btn btn-primary" onclick="downLoad($(this).closest('tr'));"><i class="fa fa-download"></i>下载</button>
			<button pofield="delete" type="button" class="btn btn-danger system aids_deleteFile" onclick="delFile($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>
	</tr>
</script>

<script type="tmp/xmgrid" id="aidslist">
	<tr style="display:none" ondblclick="forLookTable($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
        <td pofield="id" style="display:none"></td>
		<td pofield="idx"></td>
		<td pofield="baseInfo"></td>
		<td pofield="sendSampleUnitName"></td>
		<td pofield="sendSampleDate"></td>
		<td pofield="step"></td>
		<td style="width:350px;text-align:right; padding-right:20px;">
			<button type="button" pofield="modify" class="btn btn-info btn-sm system aids_forAddOrEdit" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
			<button type="button" pofield="delete" class="btn btn-danger btn-sm system aids_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
			<button type="button" class="btn btn-primary btn-sm system aids_forLook" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
			<button type="button" pofield="print1" style="display:none" class="btn btn-warning btn-sm system aids_printWrite" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>&nbsp;打印送检单</button>
			<button type="button" pofield="print2" style="display:none" class="btn btn-warning btn-sm system aids_printWrite2" onclick="forPrint($(this).closest('tr'),2);"><i class="fa fa-print"></i>打印复检报告</button>
			<button type="button" pofield="print3" style="display:none" class="btn btn-warning btn-sm system aids_printWrite3" onclick="forPrint($(this).closest('tr'),3);"><i class="fa fa-print"></i>打印确证报告</button>
		</td>
	</tr>
</script>

<script type="tmp/xmgrid" id="aids_contactList">
	<tr style="display:none" height="35px">
		<td><input type="checkbox" pofield="jcsId"></td>
		<td pofield="jcsTitle"></td>
		<td pofield="jcsValue">
			<input type="text" class="form-control input-sm" style="margin:0 auto;width:120px">
		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="aids_flowList">
	<tr style="display:none" height="35px">
		<td><span pofield="logUnitId" class="text-center block"></span></td>
        <td><span pofield="logDept" class="text-center block"></span></td>
        <td><span pofield="logUserId" class="text-center block"></span></td>
        <td><span pofield="logPhone" class="text-center block"></span></td>
        <td><span pofield="logCreateDate" class="text-center block"></span></td>
        <td><span pofield="logDescribe" class="text-center block"></span></td>
	</tr>
</script>
