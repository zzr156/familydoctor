<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2017/11/12
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <%-- <script type="text/javascript" src="<%=request.getContextPath() %>/open/sign/echarts/css/main.css"></script>--%>
    <%--   <script type="text/javascript" src="<%=request.getContextPath() %>/open/sign/echarts/css/global.css"></script>--%>
    <title>统计图表</title>
        <style type="text/css">
            div#loadbar{
                width:95%;
                background-color: silver;
               /* border:1px solid salmon;*/
                text-align: left;
                border-radius:8px ;
            }
            #bar{
                display: block;
                font-family: arial;
                font-size: 12px;
                background-color: #009688;
                text-align: left;
                padding: 5px;
                border-radius:5px ;

            }
            #bars{
                display: block;
                font-family: arial;
                font-size: 12px;
                background-color: #009688;
                text-align: left;
                padding: 5px;
                border-radius:5px ;

            }
        </style>

</head>
<body>


<div style="width: 100%;height: 80%">
    <tr>
        <td>
            <input type="text" id="signDateStart" name="signDateStart" pofield="signDateStart"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" >

            ~<input type="text" id="signDateEnd" name="signDateEnd" pofield="signDateEnd"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})" >
        </td>
    </tr>

    <button class="layui-btn"  onclick="signsx();">刷新</button>

    <div  align="center" >
        <span style="font-size: 30px;" id="OrgName" name="OrgName"></span>
        <fieldset ><legend  ><span   style="font-size: 20px;"> </span></legend>
            <div style="display: inline;text-align:center"> <h2 style="font-size:25px;    padding: 6px 0;">截止：<span id="demodate"></span>,  已签约人数：<span id="demoid"></span>,  有偿签约人数 : <span id="ycdemo"></span>,  重点签约人数 : <span id="zddemo"></span>  </h2>
            </div>
            <div style="text-align: left">
                <span style="font-size: 15px;">签约率：</span>
            </div>
               <div id="loadbar">
                   <span id="bar"  style="height:30px;"></span>
               </div>

            <br>
            <div style="text-align: left">
                <span style="font-size: 15px;text-align: left">重点人群签约率：</span>
            </div>
            <div id="loadbar">
                <span id="bars"  style="height:30px;"></span>
            </div>

        </fieldset>
    </div>
    <div style="float: left;width:50%;height: 50%">
        <div id="zdrq" style="max-width: 600px;height:400px; margin-bottom: 10px;"></div>
    </div>
    <div style="float: right;width:50%;height: 50%">
        <div id="rkxz" style="max-width: 600px;height:400px; margin-bottom: 10px;"></div>
    </div>

</div>
<div id="qyzt" style="max-width: 1600px;height:400px; margin-bottom: 10px;"></div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/echarts.common.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/intercept/sign/echarts/js/echarts_admin.js"></script>
</html>
