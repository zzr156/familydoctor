<%@ page contentType="text/html; charset=UTF-8" %>
<script>
//select模糊查询，参数说明selectName2:下拉框名称 、inputText:文本值
//调用例子：<input name="tx" type="text" onkeyup="txtOnChange('select.name',this.value);" />
function txtOnChange(selectName2,inputText)
{
  var selectName=document.all(selectName2);
  if (selectName.selectedIndex!=-1)
  {
  	 selectName.options(selectName.selectedIndex).selected = false;
  }
  for (i=0;i<selectName.options.length;i++){
		if (selectName.options(i).text == inputText)
		{
			selectName.options(i).selected = true;
			return;
		}
   }
   for (i=0;i<selectName.options.length;i++){
		if (selectName.options(i).text.indexOf(inputText)!=-1)
		{
			selectName.options(i).selected = true;
			return;
   		}
	}
}

//超链接删除 参数说明form2:form名称 、id:值
//调用例子：<a href="javascript:del('ybqkStudentInfActionForm','${item.id }')">删除</a>
function del(form2,id)
{
  if(window.confirm("是否确定删除！！"))
  {
    var form=document.all(form2)
    action=form.action;
    form.target="self";
    form.action="<%=request.getScheme()%>://<%=request.getHeader("host")%>"+action+"?act=delete&vo.id=" + id ;
    form.submit();
  }
} 
function delId(form2,id)
{
  if(window.confirm("是否确定删除！！"))
  {
    var form=document.all(form2);
    action=form.action;
    document.getElementById('act').value="delete";
    document.getElementById('vo.id').value=id;
    form.submit();
  }
}

</script>