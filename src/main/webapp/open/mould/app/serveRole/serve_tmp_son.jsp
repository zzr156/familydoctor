<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="tlist">
			<tr style="display:none;text-align: center">
				<td pofield="serSettingId" style="text-align:center;" hidden="true" ></td>
				<td pofield="serRoleSonId" style="text-align:center;" hidden="true"></td>
				<td pofield="serObjectTitle"></td>
				<td pofield="serTitle"></td>
                <td><div class="row"  style="margin: 0px;"><div class="col-sm-8 col-sm-offset-2"><input id="setNum" pofield="setNum" class="form-control"><div></div></div></div></td>
				<td>
				    <div class="row"  style="margin: 0px;">
                        <div class="col-sm-4 col-sm-offset-2">
                            <input id="setSpace" pofield="setSpace" class="form-control">
                        </div>
                        <div class="col-sm-4">
                            <select id="setSpaceType" pofield="setSpaceType" class="form-control">
                                <option value="">--请选择--</option>
                                <%--<option value="5">年</option>
                                <option value="9">季度</option>--%>
                                <option value="3">月</option>
                                <%--<option value="1">日</option>--%>
                                <option value="0">其他</option>
                            </select>
                        </div>
                    </div>
				</td>
	 		</tr>
</script>
