<%@page import="com.ylz.packcommon.common.Constant"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ylz.bizDo.cd.po.CdUser"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
CdUser user = (CdUser)request.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
String userName = user.getUserName();
%>
<html >
<head>
<%@ include file="/open/commonjs/tldHtmlLayUi.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/open/commonjs/css/main.css?v=1.1.5" media="all" />
    <title>家庭医生签约管理系统</title>

    <script type="text/javascript">
		function showtime(){
			var now = new Date();
		    var year = now.getFullYear();
		    var month = now.getMonth();
		    var date = now.getDate();
		    var day = now.getDay();
		    var hour = now.getHours();
		    var minu = now.getMinutes();
		    var sec = now.getSeconds();
		    var week;
		    month = month+1;
		    if(month<10)month="0"+month;
		    if(date<10)date="0"+date;
		    if(hour<10)hour="0"+hour;
		    if(minu<10)minu="0"+minu;
		    if(sec<10)sec="0"+sec;
		    var arr_week = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
		    week = arr_week[day];
		    var time = "";
		    time = year+"年"+month+"月"+date+"日"+"&nbsp;&nbsp;|&nbsp;&nbsp;"+week+"&nbsp;&nbsp;|&nbsp;&nbsp;"+hour+":"+minu+":"+sec;
			document.getElementById('time').innerHTML = time
			setTimeout("showtime();", 1000); //设定函数自动执行时间为 1000 ms(1 s)
		}

         $(document).ready(function() {
 		});
	</script>

</head>
<body class="main_body">
<div class="layui-layout layui-layout-admin">
    <!-- 顶部 -->
    <div class="layui-header header">
        <div class="layui-main">
            <a href="#" class="logo"><img height="35px" src="<%=request.getContextPath()%>/open/images/logo.png"><%--<img height="35px" src="<%=request.getContextPath()%>/open/images/img/by_logo.jpg">--%><span id="logoText"></span></a>
            <!-- 显示/隐藏菜单 -->
            <a href="javascript:;" class="iconfont hideMenu icon-menu1" id="shMenu"></a>

            <!-- 顶部右侧菜单 -->
            <ul class="layui-nav top_menu" id="menutop">
                <li class="layui-nav-item lockcms" pc>
                    <a href="javascript:;"><i class="iconfont icon-lock1"></i><cite>锁屏</cite></a>
                </li>
                <li class="layui-nav-item" pc>
                    <a href="javascript:;">
                        <cite><%=userName%></cite>
                    </a>
                    <dl class="layui-nav-child">
                        <%--<dd><a href="javascript:;" data-url="<%=request.getContextPath()%>/open/resetUserPwd.jsp"><i class="iconfont icon-shezhi1" data-icon="icon-shezhi1"></i><cite>修改密码</cite></a></dd>--%>
                        <dd><a href="javascript:;" class="changeSkin"><i class="iconfont icon-huanfu"></i><cite>更换皮肤</cite></a></dd>
                        <dd><a href="<%=request.getContextPath()%>/users.action?act=exit" class="signOut"><i class="iconfont icon-loginout"></i><cite>退出</cite></a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <!-- 左侧导航 -->
    <div class="layui-side layui-bg-black">
        <div class="navBar layui-side-scroll" style="width: 175px"></div>
    </div>
    <!-- 右侧内容 -->
    <div class="layui-body layui-form">
        <div class="layui-tab marg0" lay-filter="bodyTab" id="top_tabs_box">
            <ul class="layui-tab-title top_tab" id="top_tabs">
                <li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i> <cite>首页</cite></li>
            </ul>
            <ul class="layui-nav closeBox">
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="iconfont icon-caozuo"></i> 页面操作</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" class="closePageOther"><i class="iconfont icon-prohibit"></i> 关闭其他</a></dd>
                        <dd><a href="javascript:;" class="closePageAll"><i class="iconfont icon-guanbi"></i> 关闭全部</a></dd>
                    </dl>
                </li>
            </ul>
            <div class="layui-tab-content clildFrame">
                <div class="layui-tab-item layui-show">
                    <iframe src="<%=request.getContextPath()%>/intercept/sign/echarts/echarts.jsp"></iframe>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部 -->
    <%--<div class="layui-footer footer">--%>
    <%--<p>copyright @2017 </p>--%>
    <%--</div>--%>
</div>


<script type="text/javascript" src="<%=request.getContextPath()%>/open/commonjs/layui/layui.js?v=1.1.3"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/open/commonjs/js/leftNav.js?v=1.1.3"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/open/commonjs/js/index.js?v=1.1.5"></script>
<script type="text/javascript">
    //peControlTips('点击显示或隐藏菜单栏', 'shMenu', '', 0, 2);
</script>
</body>

</html>