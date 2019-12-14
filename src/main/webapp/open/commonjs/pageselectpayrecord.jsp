<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
    function winLoading(){
        parent.layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });

    }

</script>
<s:if test="recordQvo!=null">
<s:hidden name="recordQvo.pageStartNo" id="recordQvo.pageStartNo"/>
<s:hidden name="recordQvo.pageSize" id="recordQvo.pageSize"/>
<s:hidden name="recordQvo.itemCount" id="recordQvo.itemCount"/>
<s:if test="recordQvo.pageStartNo==1 || recordQvo.itemCount==0">
	<input class="btn btn-warning" type="button" value="上一页" disabled="disabled"/>     
</s:if>
<s:if test="recordQvo.pageStartNo!=1 && recordQvo.itemCount!=0">
	<input class="btn btn-warning" type="button" value="上一页" onclick="winLoading();document.getElementById('recordQvo.pageStartNo').value=${recordQvo.pageStartNo-1} ;act.value='<%=request.getParameter("act") %>';submit();"/>
</s:if>
${recordQvo.pageStartNo}/${recordQvo.pageCount} 每页显示:${recordQvo.pageSize}条共有${recordQvo.itemCount}条
<s:if test="recordQvo.pageStartNo!= recordQvo.pageCount && recordQvo.itemCount!=0">
	<input class="btn btn-warning" type="button" value="下一页" onclick="winLoading();document.getElementById('recordQvo.pageStartNo').value=${recordQvo.pageStartNo+1} ;act.value='<%=request.getParameter("act") %>';submit();"/>
</s:if>
<s:if test="recordQvo.pageStartNo== recordQvo.pageCount || recordQvo.itemCount==0">
        <input class="btn btn-warning" type="button" value="下一页" disabled="disabled"/>
</s:if>
<select id="sel" name="sel">
<s:bean name="org.apache.struts2.util.Counter" id="counter">  
   <s:param name="first" value="1" />  
   <s:param name="last" value="recordQvo.pageCount" /> 
	<s:iterator status="index">
		<option value="<s:property value='#index.index+1'/>" <s:if test="recordQvo.pageStartNo==#index.index+1">selected="selected"</s:if>><s:property value="#index.index+1"/></option>
	</s:iterator>
</s:bean>
</select>
    <s:if test="recordQvo.pageCount==1">
        <input class="btn btn-warning" type="button" value="GO" disabled="disabled"/>
    </s:if>
    <s:if test="recordQvo.pageCount>1">
        <input class="btn btn-warning" type="button" value="GO" onclick="winLoading();document.getElementById('recordQvo.pageStartNo').value=document.getElementById('sel').value ;act.value='<%=request.getParameter("act") %>';submit();"/>
    </s:if>
</s:if>
