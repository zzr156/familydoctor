<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmp/xmgrid" id="carousel_tlist">
	<tr style="display:none">
				<td pofield="id" style="display: none;"></td>
				<td pofield="imgUrl" style="display: none;"></td>
				<td pofield="cjsj"></td>
				<td pofield="simage"></td>
				<td pofield="px"></td>
				<td>
				<!--<button type="button" class="btn btn-primary btn-sm system carousel_look" onclick="carousel_Forlook($(this).closest('tr'));"><i class="fa fa-search"></i>查看</button>-->
				<button type="button" class="btn btn-danger btn-sm system carousel_delete" onclick="deleteForm($(this).closest('tr'));"><i class="fa fa-times-circle"></i>删除</button>				
				</td>
	 </tr>
</script>
