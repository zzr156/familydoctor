<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="qvo!=null">
<s:hidden name="qvo.pageStartNo" id="qvo.pageStartNo"/>
<s:hidden name="qvo.pageSize" id="qvo.pageSize"/>
<s:hidden name="qvo.itemCount" id="qvo.itemCount"/>
<s:if test="qvo.pageStartNo==1">
	<input type="button" value="上一页" disabled="disabled"/>     
</s:if>
<s:if test="qvo.pageStartNo!=1">
	<input type="button" value="上一页" onclick="document.getElementById('qvo.pageStartNo').value=${qvo.pageStartNo-1} ;act.value='<%=request.getParameter("act") %>';submit();"/>
</s:if>
${qvo.pageStartNo}/${qvo.pageCount} 每页显示:${qvo.pageSize}条共有${qvo.itemCount}条
<s:if test="qvo.pageStartNo!= qvo.pageCount">
	<input type="button" value="下一页" onclick="document.getElementById('qvo.pageStartNo').value=${qvo.pageStartNo+1} ;act.value='<%=request.getParameter("act") %>';submit();"/>
</s:if>
<s:if test="qvo.pageStartNo== qvo.pageCount">
        <input type="button" value="下一页" disabled="disabled"/>
</s:if>
<select id="sel" name="sel">
<s:bean name="org.apache.struts2.util.Counter" id="counter">  
   <s:param name="first" value="1" />  
   <s:param name="last" value="qvo.pageCount" /> 
	<s:iterator status="index">
		<option value="<s:property value='#index.index+1'/>" <s:if test="qvo.pageStartNo==#index.index+1">selected="selected"</s:if>><s:property value="#index.index+1"/></option>
	</s:iterator>
</s:bean>
</select>
    <s:if test="qvo.pageCount==1">
        <input type="button" value="GO" disabled="disabled"/>
    </s:if>
    <s:if test="qvo.pageCount>1">
        <input type="button" value="GO" onclick="document.getElementById('qvo.pageStartNo').value=document.getElementById('sel').value ;act.value='<%=request.getParameter("act") %>';submit();"/>
    </s:if>
</s:if>
