<%@ page import="java.text.SimpleDateFormat" %>
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
    <title>统计图表</title>
   <style>
        /*表格样式*/
        .pt table tr{
            border: 1px solid #ccc;
            text-align: center;
        }
        .layui-table{
            border: none !important;
        }
        .pt table th{
            text-align: center;
        }
    </style>
</head>
<body>
<div style="width: 100%;height: 100%">
    <tr>
        <td>
            <input type="text" pofield="signDateStart" id="signDateStart" autocomplete="off" class="layui-input" style="width:10%;display:inline;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/>
            ~
            <input type="text" pofield="signDateEnd" id="signDateEnd" autocomplete="off" class="layui-input" style="width:10%;display:inline;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/>
        </td>
        <input type="hidden" id="nowtime" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>" />
    </tr>
    <button class="layui-btn"  onclick="signsx();">刷新</button>
    <div  align="center" >
        <div style="margin: 15px 0;">
            <span style="font-size: 40px;text-shadow: 2px 2px #ddd" id="OrgName" name="OrgName"></span>
        </div>
        <fieldset style="border: 1px solid #ddd;"><legend  ><span   style="font-size: 20px;"> </span></legend>
          <%--  <div style="display: inline;text-align:center"> <h2 style="font-size:25px;    padding: 6px 0;">已签约人数：<span id="demoid"></span>  未签约人数 : <span id="wdemo"></span>  (管理人数 : <span id="signsum"></span> ) </h2>
            </div>--%>
            <div style="display: inline;text-align:center"> <h2 style="font-size:20px;    padding: 6px 0;">截止：<span id="demodate"></span>,  已签约人数：<span id="demoid"></span>, <span id="ycfwdiv">有偿签约人数：<span id="ycdemo"></span>,</span>   重点（基公卫）签约人数 : <span id="zddemo"></span>,  重点（经济类型）签约人数：<span id="zdjjdemo"></span> </h2>
            </div>
            <div style="font-size: 15px;text-align: left;margin-bottom: 8px;">
                <span>签约率：</span>
            </div>
            <div class="layui-progress layui-progress-big" lay-filter="demo" lay-showPercent="true" style="width: 95%;height:30px;margin-left: 30px" >
                <div class="layui-progress-bar layui-bg-green" lay-percent="0.00%" style="height:30px;">
                    <span class="layui-progress-text" ></span>
                </div>
            </div>
            <br>
            <div style="font-size: 15px;text-align: left;margin-bottom: 8px;">
            <span>重点人群（基公卫）签约率：</span>
            </div>
            <div class="layui-progress layui-progress-big" lay-filter="zddemo" lay-showPercent="true" style="width: 95%;height:30px;margin-left: 30px" >
                <div class="layui-progress-bar layui-bg-green" lay-percent="0.00%" style="height:30px;">
                    <span class="layui-progress-text"  ></span>
                </div>
            </div><br>
            <div style="font-size: 15px;text-align: left;margin-bottom: 8px;">
                <span>重点人群（经济类型）签约率：</span>
            </div>
            <div class="layui-progress layui-progress-big" lay-filter="zdjjdemo" lay-showPercent="true" style="width: 95%;height:30px;margin-left: 30px" >
                <div class="layui-progress-bar layui-bg-green" lay-percent="0.00%" style="height:30px;">
                    <span class="layui-progress-text"  ></span>
                </div>
            </div><br>
        </fieldset>
    </div>
    <br>
    <div id="pt_">
        <div style="float: left;width:50%;height: 65%">
            <div id="ptgxhbg" style="max-width: 600px;height:400px; margin-bottom: 10px;margin-left:5%">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                    <legend style="font-size: 20px">家庭医生签约服务情况汇总表</legend>
                </fieldset>
                <div class="pt">
                    <table class="layui-table" lay-even="" lay-skin="">
                        <colgroup>
                            <col width="200">
                            <col width="150">
                            <col width="150">
                            <col width="100">
                        </colgroup>
                        <thead>
                        <tr style="height: 50px;"align="center">
                            <th style="font-size: 14px;">人口经济性质</th>
                            <th style="font-size: 14px;">总  数</th>
                            <th style="font-size: 14px;">签约数</th>
                            <th style="font-size: 14px;">签约率</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr style="height: 35px;">
                            <td>户籍人口</td>
                            <td id="rksum"></td>
                            <td id="qyrk"></td>
                            <td id="qyrkl"></td>
                        </tr>
                        <tr style="height: 35px;">
                            <td>重点人群</td>
                            <td id="zdrqsum"></td>
                            <td id="qyzdrq"></td>
                            <td id="qyzdrql"></td>
                        </tr>
                        <tr style="height: 35px;">
                            <td>建档立卡</td>
                            <td id="jklmsum"></td>
                            <td id="qyjklm"></td>
                            <td id="qyjklml"></td>
                        </tr>
                        <tr style="height: 35px;">
                            <td>低保户</td>
                            <td id="dbhsum"></td>
                            <td id="qydbh"></td>
                            <td id="qydbhl"></td>
                        </tr>
                        <tr style="height: 35px;">
                            <td>特困户（五保户）</td>
                            <td id="tkhsum"></td>
                            <td id="qytkh"></td>
                            <td id="qytkhl"></td>
                        </tr>
                        <tr style="height: 35px;">
                            <td>计生特殊家庭</td>
                            <td id="jsjtsum"></td>
                            <td id="qyjsjt"></td>
                            <td id="qyjsjtl"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div style="float: right;width:50%;height: 65%">
            <div id="ptgxh" style="max-width: 600px;height:400px; margin-bottom: 20px;"></div>
        </div>
        <div id="ptLevel" style="float: left;width:90%;height: 75%">
            <fieldset class="layui-elem-field layui-field-title" style="margin-left: 3%;">
                <legend style="font-size: 20px">家庭医生签约服务情况情况表</legend>
            </fieldset>
            <div style="max-width: 1600px;max-height: 450px; margin-left: 3%;">
                <table id="ptTable" lay-filter="ptSign"></table>
            </div><br>
        </div>
    </div>
    <div style="width:90%;margin-left: 3%;" >
        <div style="float: left;width:33.3%;position: relative;top: 10px; ">
        <div id="zdrq" style="max-width: 600px;height:600px; margin-bottom: 10px;"></div>
        </div>
        <div style="float: right;width:33.3%;position: relative;top: 10px;">
            <div id="rkxz" style="max-width: 600px;height:600px; margin-bottom: 10px;"></div>
        </div>
        <div style="float: right;width:33.3%;position: relative;top: 10px;">
            <div id="sjly" style="max-width: 600px;height:600px; margin-bottom: 10px;"></div>
        </div>
    </div>
    <div style="width:90%;margin-left: 3%;display:none;" id="outpatient">
            <div style="float: right;width:50%;height: 50%">
                <div id="ybmz" style="max-width: 600px;height:400px; margin-bottom: 10px;"></div>
            </div>
            <div style="float: right;width:50%;height: 50%">
                <div id="ybnd" style="max-width: 600px;height:400px; margin-bottom: 10px;"></div>
            </div>
    </div>
</div>
<div id="fill" style="z-index:-1;display:none;float: right; max-width: 1600px;height:400px; margin-bottom: 10px;"></div>
<div id="qyzt" style="z-index:-1; max-width: 1600px;height:400px; margin-bottom: 10px;"></div>

<style>
    @media screen and (max-width: 1024px) {
        #qyzt {
            position: relative;
            top: 250px !important;
        }
        #ptgxh{
            max-width: 300px !important;
            max-height: 350px !important;
        }
        #zdrq,#rkxz,#sjly{
            position: relative;
            top: 100px !important;
        }
    }
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/echarts.common.min.js"></script>
<script type="text/javascript" src="js/echarts.js?v=1.1.5"></script>

</body>

</html>
