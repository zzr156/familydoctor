<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="content_tlist">
	 <tr style="display:none;text-align: center" ondblclick="forMsgSj($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
		<td pofield="id" style="display:none;"></td>
		<td pofield="contentId" style="display:none;"></td>
		<td pofield="title" style="display:none;"></td>
		<td pofield="idx"></td>
		<td pofield="content"></td>
		<td pofield="createTime"></td>
		<td style="text-align:right; padding-right:20px;">
			<button class="btn btn-sm btn-primary " onclick="forMsgSj($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
			<button pofield="delete" class="btn btn-danger btn-sm" onclick="delMsg($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
		</td>
	 </tr>
</script>
<script type="tmp/xmgrid" id="msg_list">
		<p style="display:none;"  ondblclick=forMsgSj($(this)); onmouseover=this.style.cursor='hand';this.style.color='blue'; onmouseout=this.style.cursor='normal';this.style.color='black';>
			<i class='fa fa-caret-right'></i>
			<span pofield="msgText"></span>
		</p>
</script>
<script type="tmp/xmgrid" id="sources_tlist">
	<tr style="display:none;text-align: center"  ondblclick="forLookTable($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
		<td pofield="id" style="display:none;"></td>
		<td pofield="idx"></td>
		<td pofield="num"></td>
		<td pofield="name"></td>
		<td pofield="age"></td>
		<td pofield="sex"></td>
		<td pofield="sendUnitId"></td>
		<td pofield="sendUserName"></td>
		<td pofield="sendDate"></td>
		<td pofield="state"></td>
		<td style="text-align:right; padding-right:20px;">
			<button pofield="delete" class="btn btn-danger btn-sm" style="display:none" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
			<button pofield="confirm" class="btn btn-sm btn-default system aids_confirmDropSample" style="display:none" onclick="confirmDropSample($(this).closest('tr'));"><i class="fa fa-reply-all"></i>确认退样</button>
			<button pofield="receiveSample" class="btn btn-sm btn-info system aids_forReceiveSample" style="display:none" onclick="forReceiveSample($(this).closest('tr'));"><i class="fa fa-share-alt"></i>接样</button>			
            <button pofield="inspect" class="btn btn-sm btn-info" style="display:none" onclick="forInspect($(this).closest('tr'));"><i class="fa fa-reply-all"></i>检验</button>
			<button pofield="audit" class="btn btn-sm btn-info aids_forAudit" style="display:none" onclick="forAudit($(this).closest('tr'));"><i class="fa fa-get-pocket"></i>审核</button>
			<button pofield="sign" class="btn btn-sm btn-info" style="display:none" onclick="confirmQf($(this).closest('tr'));"><i class="fa fa-pencil-square"></i>签发</button>
			<button pofield="fjJc" class="btn btn-sm btn-info" style="display:none" onclick="forFjJcTable($(this).closest('tr'));"><i class="fa fa-hourglass-2"></i>复检(检测)</button>
			<button pofield="fjSh" class="btn btn-sm btn-info" style="display:none" onclick="forFjShTable($(this).closest('tr'));"><i class="fa fa-get-pocket"></i>复检(审核)</button>
			<button pofield="qzJc" class="btn btn-sm btn-success"  style="display:none" onclick="forQzJcTable($(this).closest('tr'));"><i class="fa fa-hourglass-2"></i>确证(检测)</button>
			<button pofield="qzSh" class="btn btn-sm btn-success" style="display:none" onclick="forQzShTable($(this).closest('tr'));"><i class="fa fa-get-pocket"></i>确证(复核)</button>
			<button pofield="qzQf" class="btn btn-sm btn-success"  style="display:none" onclick="forQzQfTable($(this).closest('tr'));"><i class="fa fa-pencil-square"></i>确证(签发)</button>
			<button pofield="modify" class="btn btn-info btn-sm system aids_forAddOrEdit" style="display:none" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
			<button pofield="look" class="btn btn-sm btn-primary system aids_forLook" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
			<button pofield="print1" class="btn btn-sm btn-warning system aids_printWrite" style="display:none" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>&nbsp;打印送检单 </button>
			<button pofield="print2" class="btn btn-sm btn-warning system aids_printWrite2" style="display:none" onclick="forPrint($(this).closest('tr'),2);"><i class="fa fa-print"></i>打印复检报告</button>
			<button pofield="print3" class="btn btn-sm btn-warning system aids_printWrite3" style="display:none" onclick="forPrint($(this).closest('tr'),3);"><i class="fa fa-print"></i>打印确证报告</button>
		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="wait_tlist">
	<tr style="display:none;text-align: center" ondblclick="forLookTable($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
		<td pofield="id" style="display:none;"></td>
		<td pofield="idx"></td>
		<td pofield="num"></td>
		<td pofield="name"></td>
		<td pofield="age"></td>
		<td pofield="sex"></td>
		<td pofield="sendUnitId"></td>
		<td pofield="sendUserName"></td>
		<td pofield="sendDate"></td>
		<td style="text-align:right; padding-right:20px;">
			<button class="btn btn-sm btn-info system aids_forReceiveSample" onclick="forReceiveSample($(this).closest('tr'));"><i class="fa fa-share-alt"></i>接样</button>
			<button class="btn btn-sm btn-primary system aids_forLook" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
			<button class="btn btn-sm btn-warning system aids_printWrite" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>打印送检单</button>
		</td>
	</tr>
</script>

<script type="tmp/xmgrid" id="audit_tlist">
	<tr style="display:none;text-align: center" ondblclick="forLookTable($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
		<td pofield="id" style="display:none;"></td>
		<td pofield="idx"></td>
		<td pofield="num"></td>
		<td pofield="name"></td>
		<td pofield="age"></td>
		<td pofield="sex"></td>
		<td pofield="sendUnitId"></td>
		<td pofield="sendUserName"></td>
		<td pofield="sendDate"></td>
		<td style="text-align:right; padding-right:20px;">
			<button class="btn btn-sm btn-info aids_forAudit" onclick="forAudit($(this).closest('tr'));"><i class="fa fa-get-pocket"></i>审核</button>
			<button class="btn btn-sm btn-primary aids_forLook" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
			<button class="btn btn-sm btn-warning aids_printWrite" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>打印送检单</button>
		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="waitModify_tlist">
	<tr style="display:none;text-align: center" ondblclick="forLookTable($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
		<td pofield="id" style="display:none;"></td>
		<td pofield="idx"></td>
		<td pofield="num"></td>
		<td pofield="name"></td>
		<td pofield="age"></td>
		<td pofield="sex"></td>
		<td pofield="sendUnitId"></td>
		<td pofield="sendUserName"></td>
		<td pofield="sendDate"></td>
		<td style="text-align:right; padding-right:20px;">
			<button pofield="modify" class="btn btn-sm btn-info system aids_forAddOrEdit" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-spin fa-cog"></i>修改</button>
			<button class="btn btn-sm btn-primary aids_forLook" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
			<button class="btn btn-sm btn-warning aids_printWrite" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>打印送检单</button>
		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="common_tlist">
	<tr style="display:none;text-align: center" ondblclick="forLookTable($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
		<td pofield="id" style="display:none;"></td>
		<td pofield="idx"></td>
		<td pofield="num"></td>
		<td pofield="name"></td>
		<td pofield="age"></td>
		<td pofield="sex"></td>
		<td pofield="sendUnitId"></td>
		<td pofield="sendUserName"></td>
		<td pofield="sendDate"></td>
		<td style="text-align:right; padding-right:20px;">
			<button pofield="inspect" class="btn btn-sm btn-info" style="display:none" onclick="forInspect($(this).closest('tr'));"><i class="fa fa-reply-all"></i>检验</button>
			<button pofield="confirm" class="btn btn-sm btn-info aids_confirmDropSample" style="display:none" onclick="confirmDropSample($(this).closest('tr'));"><i class="fa fa-reply-all"></i>确认退样</button>
			<button pofield="look" class="btn btn-sm btn-primary aids_forLook" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>			
            <button pofield="print1" class="btn btn-sm btn-warning aids_printWrite" style="display:none" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>打印送检单</button>
			<button pofield="print2" class="btn btn-sm btn-warning aids_printWrite2" style="display:none" onclick="forPrint($(this).closest('tr'),2);"><i class="fa fa-print"></i>打印复检报告</button>
			<button pofield="print3" class="btn btn-sm btn-warning aids_printWrite3" style="display:none" onclick="forPrint($(this).closest('tr'),3);"><i class="fa fa-print"></i>打印确证报告</button>			
		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="gzt_tlist">
	<li style="display:none">
		<a href="#" class="check-link">
			<i class="fa fa-check-square">
				<input type="hidden" pofield="platformItemNum">
			</i> 
		</a>
        <span class="m-l-xs" pofield="platformItem"></span>
	</li>
</script>
<script type="tmp/xmgrid" id="aids_tlist">
	<tr style="display:none;text-align: center"  ondblclick="forLookTableTt($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
		<td pofield="id" style="display:none;"></td>
		<td pofield="idx"></td>
		<td pofield="spNum"></td>
		<td pofield="name"></td>
		<td pofield="age"></td>
		<td pofield="sex"></td>
		<td pofield="sjdw"></td>
		<td pofield="syr"></td>
		<td pofield="sysj"></td>
		<td style="text-align:right; padding-right:20px;">
			
			<button pofield="fjJc" class="btn btn-sm btn-info" onclick="forFjJcTable($(this).closest('tr'));"><i class="fa fa-hourglass-2"></i>复检(检测)</button>
			<button pofield="fjSh" class="btn btn-sm btn-info" onclick="forFjShTable($(this).closest('tr'));"><i class="fa fa-get-pocket"></i>复检(审核)</button>
			<button pofield="qzJc" class="btn btn-sm btn-success" onclick="forQzJcTable($(this).closest('tr'));"><i class="fa fa-hourglass-2"></i>确证(检测)</button>
			<button pofield="qzSh" class="btn btn-sm btn-success" onclick="forQzShTable($(this).closest('tr'));"><i class="fa fa-get-pocket"></i>确证(复核)</button>
			<button pofield="qzQf" class="btn btn-sm btn-success" onclick="forQzQfTable($(this).closest('tr'));"><i class="fa fa-pencil-square"></i>确证(签发)</button>
			<button pofield="look" class="btn btn-sm btn-primary aids_forLook" onclick="forLookTableTt($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
 			<button pofield="print1" class="btn btn-sm btn-warning aids_printWrite" style="display:none" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>打印送检单</button>
			<button pofield="print2" class="btn btn-sm btn-warning aids_printWrite2" style="display:none" onclick="forPrint($(this).closest('tr'),2);"><i class="fa fa-print"></i>打印复检报告</button>
			<button pofield="print3" class="btn btn-sm btn-warning aids_printWrite3" style="display:none" onclick="forPrint($(this).closest('tr'),3);"><i class="fa fa-print"></i>打印确证报告</button>	


		</td>
	</tr>
</script>
<script type="tmp/xmgrid" id="waiJy_tlist">
	<tr style="display:none;text-align: center" ondblclick="forLookTable($(this));" onmouseover="this.style.cursor='hand';this.style.color='blue';" onmouseout="this.style.cursor='normal';this.style.color='black';">
		<td pofield="id" style="display:none;"></td>
		<td pofield="idx"></td>
		<td pofield="num"></td>
		<td pofield="name"></td>
		<td pofield="age"></td>
		<td pofield="sex"></td>
		<td pofield="sendUnitId"></td>
		<td pofield="sendUserName"></td>
		<td pofield="sendDate"></td>
		<td style="text-align:right; padding-right:20px;">
			<button pofield="confirm1" class="btn btn-sm btn-info aids_confirmDropSample" style="display:none" onclick="confirmJy($(this).closest('tr'));"><i class="fa fa-hourglass-2"></i>检验</button>
			<button pofield="confirm2" class="btn btn-sm btn-info aids_confirmDropSample" style="display:none" onclick="confirmSh($(this).closest('tr'));"><i class="fa fa-get-pocket"></i>审核</button>
			<button pofield="confirm3" class="btn btn-sm btn-info aids_confirmDropSample" style="display:none" onclick="confirmQf($(this).closest('tr'));"><i class="fa fa-pencil-square"></i>签发</button>
			<button pofield="look" class="btn btn-sm btn-primary aids_forLook" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>			
            <button pofield="print1" class="btn btn-sm btn-warning aids_printWrite" style="display:none" onclick="forPrint($(this).closest('tr'),1);"><i class="fa fa-print"></i>打印送检单</button>
			<button pofield="print2" class="btn btn-sm btn-warning aids_printWrite2" style="display:none" onclick="forPrint($(this).closest('tr'),2);"><i class="fa fa-print"></i>打印检验报告</button>
			<button pofield="print3" class="btn btn-sm btn-warning aids_printWrite3" style="display:none" onclick="forPrint($(this).closest('tr'),2);"><i class="fa fa-print"></i>打印审核报告</button>
			<button pofield="print4" class="btn btn-sm btn-warning aids_printWrite4" style="display:none" onclick="forPrint($(this).closest('tr'),2);"><i class="fa fa-print"></i>打印签发报告</button>			
		</td>
	</tr>
</script>