<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
	<%@ include file="/open/commonjs/tldHtml.jsp"%>
    <base href="<%=basePath%>">
    
    <title>添加行政区划</title>
    
  </head>
  
<body class="gray-bg">
	<div class="table-responsive">
		<div class="panel panel-info">
			<form class="form-inline" role="form" id="form_vo">
			    <table class="table table-striped  table-hover">
			          <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
			          <input type="hidden" id="handle"  name="handle" value="${handle}" />
			          <tr>
			              <td align="right">行政区划全称：</td>
			              <td>
			              	<input pofield="areaName" id="areaName" maxlength="15" class="input-md form-control" style="width:200px;"/>
			              	<span style="color: red;">*</span>
			              </td>
			          </tr>
			          <tr>
			              <td align="right">编码：</td>
			              <td>
			              	<input pofield="ctcode"  id="ctcode" maxlength="15" class="input-md form-control" style="width:200px;"/>
			              	<input type="hidden" pofield="pid" id="pid" value="${addrAddzi}"/>
			              	<span style="color: red;">*</span>
			              </td>
			          </tr>
			          <tr>
			              <td align="right">行政区划简称：</td>
			              <td>
							<input type="text" id="areaSname" pofield="areaSname"   class="input-md form-control" style="width:200px;"/>
							<span style="color: red;">*</span>               		
			              </td>
			          </tr>
					<tr>
						<td align="right">行政区划别名：</td>
						<td>
							<input type="text" id="areaAlias" pofield="areaAlias"   class="input-md form-control" style="width:200px;"/>
							<span style="color: red;">*</span>
						</td>
					</tr>
			          <tr>
			          	<td colspan="2" align="center">
							<button id="handleMethod" type="button" class="btn btn-primary system address_add" onclick="saveTable();">保存</button>
							<button type="button" class="btn btn-primary system address_list" onclick="backTable();">返回</button>
			          	</td>
			          </tr>
			      </table>
		     </form>
		  </div>
	  </div>
     <script type="text/javascript">
    	var vo={};
		//页面加载完成时启动的方法
		$(document).ready(function() {
			onInit();
		});
		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			
			if($("#handle").val() == "modify"){
				$("#handleMethod").text("修改");
				if($("#id").val() != ""){
					doAjaxPost('<%=request.getContextPath()%>/address.action?act=jsonByOne',{id:$("#id").val()},callDataToAddr);
				}
			}else if($("#handle").val() == "add"){//新增顶级行政区划
				
			}else if($("#handle").val() == "addzi"){//新增子级行政区划
				//$("#pid").val($("#id").val());
			}
			
		}
		function callDataToAddr(data){
			dataToUi(data,"form_vo");
		}
		
		//点击返回
		function backTable(){
			history.go(-1);
		}
		
	    
		//保存的方法
		function saveTable() {
			vo=uiToData("form_vo",vo);
			/* var method = "add";
    		if($("#handle").val() ==  "modify"){
				method = "modify";	
			} */

			doAjaxPost('<%=request.getContextPath()%>/address.action?act=addOrEdit',{strJson:JSON.stringify(vo)},callDataSave);
		}
		
		function callDataSave(data){
			if(data.msg == 'true'){
                var url = '<%=request.getContextPath()%>/address.action?act=forList';
                defualtHref(url);
			}else{
				alert(data.msg);
			}
		}
	</script>
</body>
</html>
