<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
    function winLoading(){
        parent.layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });

    }

</script>
<s:if test="newsMsgQvo!=null">
<s:hidden name="newsMsgQvo.pageStartNo" id="newsMsgQvo.pageStartNo"/>
<s:hidden name="newsMsgQvo.pageSize" id="newsMsgQvo.pageSize"/>
<s:hidden name="newsMsgQvo.itemCount" id="newsMsgQvo.itemCount"/>
<s:if test="newsMsgQvo.pageStartNo==1">
	<input class="btn btn-warning" type="button" value="上一页" disabled="disabled"/>     
</s:if>
<s:if test="newsMsgQvo.pageStartNo!=1">
	<input class="btn btn-warning" type="button" value="上一页" onclick="winLoading();document.getElementById('newsMsgQvo.pageStartNo').value=${newsMsgQvo.pageStartNo-1} ;act.value='<%=request.getParameter("act") %>';submit();"/>
</s:if>
${newsMsgQvo.pageStartNo}/${newsMsgQvo.pageCount} 每页显示:${newsMsgQvo.pageSize}条共有${newsMsgQvo.itemCount}条
<s:if test="newsMsgQvo.pageStartNo!= newsMsgQvo.pageCount">
	<input class="btn btn-warning" type="button" value="下一页" onclick="winLoading();document.getElementById('newsMsgQvo.pageStartNo').value=${newsMsgQvo.pageStartNo+1} ;act.value='<%=request.getParameter("act") %>';submit();"/>
</s:if>
<s:if test="newsMsgQvo.pageStartNo== newsMsgQvo.pageCount">
        <input class="btn btn-warning" type="button" value="下一页" disabled="disabled"/>
</s:if>
<select id="sel" name="sel">
<s:bean name="org.apache.struts2.util.Counter" id="counter">  
   <s:param name="first" value="1" />  
   <s:param name="last" value="newsMsgQvo.pageCount" /> 
	<s:iterator status="index">
		<option value="<s:property value='#index.index+1'/>" <s:if test="newsMsgQvo.pageStartNo==#index.index+1">selected="selected"</s:if>><s:property value="#index.index+1"/></option>
	</s:iterator>
</s:bean>
</select>
    <s:if test="newsMsgQvo.pageCount==1">
        <input class="btn btn-warning" type="button" value="GO" disabled="disabled"/>
    </s:if>
    <s:if test="newsMsgQvo.pageCount>1">
        <input class="btn btn-warning" type="button" value="GO" onclick="winLoading();document.getElementById('newsMsgQvo.pageStartNo').value=document.getElementById('sel').value ;act.value='<%=request.getParameter("act") %>';submit();"/>
    </s:if>
</s:if>
