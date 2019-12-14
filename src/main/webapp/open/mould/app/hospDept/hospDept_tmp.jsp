<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="hospId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
				<td pofield="num"></td>
				<td pofield="hospName"></td>
				<td pofield="hospCode"></td>
				<td pofield="hospAddress"></td>
				<td pofield="hospTel"></td>
				<td pofield="hospLevel"></td>
				<td pofield="hospState"></td>
				<td pofield="operation">
				    <button pofield="addHosp" type="button" class="btn btn-info btn-sm system apphosp_addUploadImage" onclick="forUploadImage($(this).closest('tr'));"><i class="fa fa-cog"></i>上传公章图片</button>
                    <button pofield="addHosp" type="button" class="btn btn-info btn-sm system apphosp_addUpHosp" onclick="forAddUpHosp($(this).closest('tr'));"><i class="fa fa-cog"></i>添加上级医院</button>
					<button type="button" class="btn btn-info btn-sm system apphosp_modify" onclick="forModifyTable($(this).closest('tr'));"><i class="fa fa-cog"></i>修改</button>
                    <button type="button" class="btn btn-info btn-sm system apphosp_modify" onclick="forSetTable($(this).closest('tr'));"><i class="fa fa-cog"></i>设置</button>
					<button type="button" class="btn btn-primary btn-sm system apphosp_list" onclick="forLookTable($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>
					<button type="button" class="btn btn-danger btn-sm system apphosp_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
					<button type="button" class="btn btn-info btn-sm system apphosp_modify" onclick="signTeam($(this).closest('tr'));"><i class="fa fa-cog"></i>配制签约服务团队</button>
				</td>
	 		</tr>
</script>
<script type="tmp/xmgrid" id="ttlist">
        <tr style="display:none;text-align: center">
            <td pofield="uupId" style="text-align:center;"><input style="width:20px" type="checkbox" name="chckBox"/></td>
            <td pofield="num"></td>
            <td pofield="upName"></td>
            <td pofield="upHospName"></td>
            <td pofield="operation">
                <button pofield="state" type="button" class="btn btn-info btn-sm system apphosp_modify" onclick="forSetTable($(this).closest('tr'));"><i class="fa fa-cog"></i>设置</button>
				<button type="button" class="btn btn-danger btn-sm system apphosp_delete" onclick="delForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>
            </td>

        </tr>

</script>
