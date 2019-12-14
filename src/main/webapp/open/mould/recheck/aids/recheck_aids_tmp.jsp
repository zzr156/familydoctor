<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 样品模板 -->
<script type="tmp/xmgrid" id="yp_tlist">
	<tr style="display:none">
		<input type="hidden" pofield="ypId"/>
		<input type="hidden" pofield="ypTypeNum"/>
		<td style="text-align: center;" pofield="ypName"></td>
		<td style="text-align: center;" pofield="ypNum"></td>
		<td style="text-left: center;">
			<table pofield="jyItemTable"></table>
		</td>
	</tr>
</script>
<!-- 送检单位检测记录模板 -->
<script type="tmp/xmgrid" id="jcjl_tlist">
	<tr style="display:none">
		<input type="hidden" pofield="jlId">
		<td style="text-align: center;" pofield="jlTestRecord"></td>
		<td style="text-align: center;" pofield="jlTestMethod"></td>
		<td style="text-align: center;" pofield="jlTestDate"></td>
		<td style="text-align: center;" pofield="jlReagentManufacturers"></td>
		<td style="text-align: center;" pofield="jlBatchNumber"></td>
		<td style="text-align: center;" pofield="jlEffectiveDate"></td>
		<td style="text-align: center;" pofield="jlTestResult"></td>
	</tr>
</script>
<!-- 检验/检测结果模板 -->
<script type="tmp/xmgrid" id="jcResult_tlist">
	<tr style="display:none">
		<input type="hidden" pofield="jcId"/>
		<input type="hidden" pofield="jcSpNum"/>
		<input type="hidden" pofield="Itemtype"/>
		<td style="text-align: center;" pofield="bbNum"></td>
		<td style="text-align: center;" pofield="brName"></td>
		<td style="text-align: center;">
			<input type="hidden" pofield="jyItemCode">
			<input type="hidden" pofield="jcjl">
			<span pofield="jyItem"></span>
		</td>
		<td style="text-align: center; width:15%;">
			<div class="form-group">
				 <div class="input-group">  
        			<div class="input-group-btn">  
            			<select class="form-control" pofield="jcMethods" style="width: auto;" onChange="GaiBian(this)"></select>
        			</div>  
        				<input class="form-control" style="width:80;display:none"  pofield="jlbh" placeholder="记录编号" >  
   		 		</div>
				<input class="form-control" style="display:none;width:100" pofield="other"/>  
			</div>
		</td>
		<td style="text-align: center;">
			<select pofield="sjcj" class="form-control"></select>
		</td>
		<td style="text-align: center;">
			<input pofield="ph" class="form-control"/>
		</td>
		<td style="text-align:center;">
			<input pofield="yxDate" class="input-md form-control" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</td>
		<td style="text-align: center;">
			<select pofield="jyResult" class="form-control"></select>
		</td>
		<td pofield="bbz" style="text-align: center;">
			<input style="text-align: center;" pofield="bz" class="form-control">
		</td>
	</tr>
</script>
<!-- 确证检验/检测结果模板 -->
<script type="tmp/xmgrid" id="QzjcResult_tlist">
	<tr style="display:none">
		<input type="hidden" pofield="jcId"/>
		<input type="hidden" pofield="jcSpNum"/>
		<input type="hidden" pofield="Itemtype"/>
		<td style="text-align: center;" pofield="bbNum"></td>
		<td style="text-align: center;" pofield="brName"></td>
		<td style="text-align: center;">
			<input type="hidden" pofield="jyItemCode">
			<input type="hidden" pofield="jcjl">
			<span pofield="jyItem"></span>
		</td>
		<td style="text-align: center; width:15%;">
			<div class="form-group">
				 <div class="input-group">  
        			<div class="input-group-btn">  
            			<select class="form-control" pofield="jcMethods" style="width: auto;" onChange="GaiBian(this)"></select>
        			</div>  
        				<input class="form-control" style="width:80;display:none"  pofield="jlbh" placeholder="记录编号">  
   		 		</div>
				<input class="form-control" style="display:none;width:100" pofield="other"/>  
			</div>
		</td>
		<td style="text-align: center;">
			<select pofield="sjcj" class="form-control"></select>
		</td>
		<td style="text-align: center;">
			<input pofield="ph" class="form-control"/>
		</td>
		<td style="text-align:center;">
			<input pofield="yxDate" class="input-md form-control" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</td>
		<td style="text-align: center;">
			<div class="input-group">
            	<input type="text" class="form-control" name="jyResultName" pofield="jyResultName" type="text" readonly="readonly"> 
                <input type="hidden" pofield="jyResult"/>
           		<span class="input-group-btn"> 
            		<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalCdUserDept" onclick="ChangeDateToResult(this)">选择结果</button>
            	</span>
            </div>
		</td>
		<td pofield="bbz" style="text-align: center;">
			<input style="text-align: center;" pofield="bz" class="form-control">
		</td>
	</tr>
</script>
<!-- 多选检验项目模板 -->
<script type="tmp/xmgrid" id="itemInsp_tlist">
	<label style="display:none">
		<input style="width:20px" type="checkbox" name="chckBox"/>
		<label pofield="itemName"></label>
		<input type="hidden" pofield="xxypId">
	</label>
</script>
<script type="tmp/xmgrid" id="item_tlist">
	<tr style="display:none">
		<input type="hidden" pofield="jcItem">
		<td><input onclick="chooseThis(this)" type="checkbox" pofield="itemF" value="">
			<label pofield="itemNameF"></label>
			<input type="hidden" pofield="xxypId">
		</td>
		<td><input onclick="chooseThis(this)" type="checkbox" pofield="itemS" value="">
			<label pofield="itemNameS"></label>
		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="item_tlists">
	<tr style="display:none">
		<input type="hidden" pofield="jcItem">
		<td><input onclick="chooseThis(this)" type="checkbox" pofield="itemF" value="">
			<label pofield="itemNameF"></label>
		</td>
	</tr>

</script>
<script type="tmp/xmgrid" id="jyResult_tlist">
	<tr style="display:none">
		<td pofield="id"></td>
		<td pofield="resultName"></td>
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
