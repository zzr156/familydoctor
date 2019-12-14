<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="tmp/xmgrid" id="otherslist">
	<tr style="display:none" ondblclick="forLookTable($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
        <td pofield="id" style="display:none"></td>
		<td pofield="idx"></td>
		<td pofield="baseInfo"></td>
		<td pofield="sendSampleUnitName"></td>
		<td pofield="sendSampleDate"></td>
		<td pofield="step"></td>
		<td style="width:350px;text-align:right; padding-right:20px;">
			<button type="button" pofield="modify" class="btn btn-info btn-sm system others_forAddOrEdit" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
			<button type="button" pofield="delete" class="btn btn-danger btn-sm system others_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
			<button type="button" class="btn btn-primary btn-sm system others_forLook" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
			<button type="button" pofield="print1" style="display:none" class="btn btn-warning btn-sm system others_printWrite" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>&nbsp;打印送检单</button>
			<button type="button" pofield="print2" style="display:none" class="btn btn-warning btn-sm system others_printWrite2" onclick="forPrint($(this).closest('tr'),2);"><i class="fa fa-print"></i>打印检验报告</button>
			<button type="button" pofield="print3" style="display:none" class="btn btn-warning btn-sm system others_printWrite3" onclick="forPrint($(this).closest('tr'),3);"><i class="fa fa-print"></i>打印确证报告</button>
		</td>
	</tr>
</script>

<script type="tmp/xmgrid" id="other_Sample">

	<tr style="display:none">
		<td style="display:none"><input type="text" class="form-control" pofield="ypId"></td>
		<td aa="ypName">
			<select pofield="ypName" class="form-control">
				<option value>--请选择--</option>
            </select>
			<input pofield="ypOtherName" class="form-control" placeholder='请输入其他的具体名称' style="display:none">
		</td>
		<td><input type="text" pofield="ypNum" class="form-control text-center"></td>
		<td><input type="text" pofield="ypSpecifications" class="form-control text-center"></td>
		<td><input type="text" pofield="ypPacking" class="form-control text-center"></td>
		<td><input type="text" pofield="ypSampleSource" class="form-control text-center"></td>
		<td aa="ypColdStorage">
			
		</td>
		<td>
			<button type="button" class="btn btn-sm btn-success" onclick="addYpTable()">新增</button>
			<button pofield="delete" type="button" class="btn btn-sm btn-danger" onclick="delFormSs($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="others_SampleLook">

	<tr style="display:none">
		<td><span pofield="ypName" class="text-center block"></span></td>
		<td><span pofield="ypNum" class="text-center block"></span></td>
		<td><span pofield="ypSpecifications" class="text-center block"></span></td>
		<td><span pofield="ypPacking" class="text-center block"></span></td>
		<td><span pofield="ypSampleSource" class="text-center block"></span></td>
		<td><span pofield="ypColdStorage" class="text-center block"></span></td>
		
	</tr>
	
</script>

<script type="tmp/xmgrid" id="other_file">
	<tr style="display:none" id="zzjj">
		<td style="display:none"><span pofield="clEnclosurePath"></span></td>
		<td style="display:none"><span pofield="clId"></span></td>
		<td><span pofield="idx" class="text-center block"></span></td>
		<td><input type="text" pofield="clName" class="form-control text-center block"></input></td>
		<td><input type="text" pofield="clDescription" class="form-control text-center block"></input></td>
		<td>
            <input type="file" name="image" onchange="addfile($(this).closest('tr'))">
        </td>
		<td>
			<button pofield="downLoad" type="button" class="btn btn-sm btn-primary" onclick="downLoad($(this).closest('tr'));"><i class="fa fa-download"></i>下载</button>
			<button pofield="delete" type="button" class="btn btn-sm btn-danger" onclick="delFile($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="other_addfile">
	<tr style="display:none">
		<td style="display:none"><span pofield="clEnclosurePath"></span></td>
		<td style="display:none"><span pofield="clId"></span></td>
		<td><span pofield="idx" class="text-center block"></span></td>
		<td><input type="text" pofield="clName" class="form-control text-center block"></input></td>
		<td><input type="text" pofield="clDescription" class="form-control text-center block"></input></td>
		<td>
            <input type="file" name="image" onchange="addfile($(this).closest('tr'))">
        </td>
		<td>
			<button pofield="downLoad" type="button" class="btn btn-sm btn-primary" onclick="downLoad($(this).closest('tr'));"><i class="fa fa-download"></i>下载</button>
			<button pofield="delete" type="button" class="btn btn-sm btn-danger" onclick="delFile($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>
	</tr>
</script>

<script type="tmp/xmgrid" id="others_fileLook">
	<tr style="display:none">
		<td style="display:none"><span pofield="clEnclosurePath"></span></td>
		<td style="display:none"><span pofield="clId"></span></td>
		<td><span pofield="idx" class="text-center block"></span></td>
		<td><span pofield="clName" class="text-center block"></span></td>
		<td><span pofield="clDescription" class="text-center block"></span></td>
		<td>
			<button pofield="downLoad" type="button" class="btn btn-sm btn-primary" onclick="downLoad($(this).closest('tr'));"><i class="fa fa-download"></i>下载</button>
		</td>
	</tr>
</script>

<script type="tmp/xmgrid" id="others_flowList">
	<tr style="display:none" height="35px">
		<td><span pofield="logUnitId" class="text-center block"></span></td>
        <td><span pofield="logDept" class="text-center block"></span></td>
        <td><span pofield="logUserId" class="text-center block"></span></td>
        <td><span pofield="logPhone" class="text-center block"></span></td>
        <td><span pofield="logCreateDate" class="text-center block"></span></td>
        <td><span pofield="logDescribe" class="text-center block"></span></td>
	</tr>
</script>
<script type="tmp/xmgrid" id="others_sample">
	<tr style="display:none">
		<td pofield="sampleId" style="display:none"></td>
		<td pofield="ypSpid" style="display:none"></td>
		<td pofield="ypName" style="text-align: center;"></td>
		<td pofield="ypNum" style="text-align: center;"></td>
		<td pofield="ypgg" style="text-align: center;"></td>
		<td pofield="jcxm" style="text-align: center;"></td>
	</tr>
</script>
<script type="tmp/xmgrid" id="others_jyjc">
	<tr style="display:none">
		<td pofield="jyjcId" style="display:none"></td>
		<td pofield="jyjcSpNum" style="display:none"></td>
		<td pofield="jyjcYpId" style="display:none"></td>
		<td pofield="jyjcItem" style="display:none"></td>
		<td pofield="number" style="display:none"></td>
		<td pofield="jyjcBbNum" style="text-align: center;"></td>
		<td pofield="jyjcYpName" style="text-align: center;"></td>
		<td pofield="jyjcItemName" style="text-align: center;"></td>
		<td pofield="jyjcResult" style="text-align: center;"></td>
		<td style="text-align: center;">
			<input pofield="jyjcRemarks" class="form-control text-center">
		</td>
		<td>
			
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






