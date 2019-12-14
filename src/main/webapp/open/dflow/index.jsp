<%@ page contentType="text/html; charset=UTF-8" %>
<html xmlns:v="urn:schemas-microsoft-com:vml" >
<head>
    <title>流程设计器</title>
    <style type="text/css">
        v\:*{BEHAVIOR: url(#default#VML)}
    </style>
    <link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="design.css" />
    <script type="text/javascript" src="ext/ext-base.js"></script>
    <script type="text/javascript" src="ext/ext-all.js"></script>
    <script type="text/javascript" src="designConfig.js"></script>
    <script>
        Ext.onReady(function(){
            DesignConfig.init();
        });
    </script>
</head>
<body onContextMenu="return false">
<div id="west" style="display:none">
    <div title="select" class="btn btn_down"><span class="icon_select">选择</span></div>
    <div title="transition" class="btn btn_up_split"><span class="icon_transtion">转换</span></div>
    <!--<div title="grid" class="btn btn_up_split"><span class="icon_grid">栅格</span></div>-->
    <div title="start" class="btn btn_up"><span class="icon_start">开始</span></div>
    <div title="end" class="btn btn_up_split"><span class="icon_stop">结束</span></div>
    <div title="fork" class="btn btn_up"><span class="icon_fork">分支</span></div>
    <div title="join" class="btn btn_up"><span class="icon_join">合并</span></div>
    <div title="bool" class="btn btn_up"><span class="icon_bool">决策</span></div>
    <div title="task" class="btn btn_up"><span class="icon_task">任务</span></div>
    <div title="mail" class="btn btn_up_split"><span class="icon_mail">邮件</span></div>
</div>
<div id="center"></div>
<div id="south_key" style="display:none">
    <div title="base" class="key_btn key_btn_up key_btn_down"><span class="icon_base">基本信息</span></div>
    <div title="change" class="key_btn key_btn_up"><span class="icon_change">转换模式</span></div>
    <div title="delegate" class="key_btn key_btn_up"><span class="icon_delegate">任务委派</span></div>
    <div title="autoDelegate" class="key_btn key_btn_up"><span class="icon_delegate">动态委派</span></div>
    <div title="method" class="key_btn key_btn_up"><span class="icon_method">任务策略</span></div>
    <div title="form" class="key_btn key_btn_up"><span class="icon_form">业务表单</span></div>
    <div title="case" class="key_btn key_btn_up"><span class="icon_case">转换条件</span></div>
    <div title="swimlane" class="key_btn key_btn_up"><span class="icon_swimlane">泳道定义</span></div>
    <div title="sql" class="key_btn key_btn_up"><span class="icon_sql">SQL脚本</span></div>
    <div title="notice" class="key_btn key_btn_up"><span class="icon_notice">消息提醒</span></div>
    <div title="mailto" class="key_btn key_btn_up"><span class="icon_mailto">邮件发送</span></div>
    <div title="base" class="key_split"></div>
</div>
<div id="south_value"></div>
</body>
</html>