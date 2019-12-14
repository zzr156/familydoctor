<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="tmp/ylz" id="logList">
			<tr>
			    <td pofield="num"></td>
				<td pofield="businessName"></td>
				<td pofield="businessTable"></td>
				<td pofield="businessDesc"></td>
				<td pofield="formatTime"></td>
				<td pofield="userName"></td>
				<td pofield="operation">
					<button type="button" class="layui-btn layui-btn-small" onclick="logView($(this).closest('tr'));">查看</button>
					<button type="button" class="layui-btn layui-btn-small" onclick="logDel($(this).closest('tr'));">删除</button>
				</td>
	 		</tr>
</script>

<script type="tmp/ylz" id="logView">
	<tr>
        <td pofield="id"></td>
        <td pofield="key"></td>
        <td pofield="oldValue"></td>
        <td pofield="newValue"></td>
    </tr>
</script>

<script type="text/html" id="barLog">
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="view"><i class=layui-icon>&#xe615;</i>查看</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del"><i class=layui-icon>&#xe640;</i>删除</a>
</script>