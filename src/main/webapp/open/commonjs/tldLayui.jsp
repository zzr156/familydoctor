<meta http-equiv="X-UA-Compatible"  content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/layui2/css/layui.css?v=1.1.2" media="all">
<link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/css/public.css?v=1.1.2" media="all">
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/jq/jquery-1.11.1.min.js?v=1.1.1"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/common.js?v=1.1.2"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/jquery.PrintArea.js?v=1.1.2"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/commonAjax.js?v=1.1.7"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/json2.js?v=1.1.2"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/layui2/layui.all.js?v=1.1.1"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/DatePicker/WdatePicker.js?v=1.1.2"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/ajaxfileupload.js?v=1.1.2"></script>
<script>

    var HospLabelState='<%=request.getSession().getAttribute("HospLabelState")%>'=='null'?getQueryString("HospLabelState"):'<%=request.getSession().getAttribute("HospLabelState")%>';
    var hospDissoluation='<%=request.getSession().getAttribute("HospDissoluation")%>'=='null'?getQueryString("hospDissoluation"):'<%=request.getSession().getAttribute("HospDissoluation")%>';
    var orgid='<%=request.getSession().getAttribute("ORG_ID")%>'=='null'?getQueryString("orgid"):'<%=request.getSession().getAttribute("ORG_ID")%>';
    var teamId = '<%=request.getSession().getAttribute("teamId")%>'=='null'?getQueryString("teamId"):'<%=request.getSession().getAttribute("teamId")%>';
    var userid='<%=request.getSession().getAttribute("UserId")%>'=='null'?getQueryString("userid"):'<%=request.getSession().getAttribute("UserId")%>';
    var drid='<%=request.getSession().getAttribute("DrId")%>'=='null'?getQueryString("userid"):'<%=request.getSession().getAttribute("DrId")%>';
    var username='<%=request.getSession().getAttribute("UserName")%>'=='null'?decodeURI(getQuerytoString("accName")):'<%=request.getSession().getAttribute("UserName")%>';
    var OrgName ='<%=request.getSession().getAttribute("HospName")%>'=='null'?decodeURI(getQuerytoString("orgnames")):'<%=request.getSession().getAttribute("HospName")%>';
    var HospAreaCode ='<%=request.getSession().getAttribute("HospAreaCode")%>'=='null'?decodeURI(getQuerytoString("orgAreaCode")):'<%=request.getSession().getAttribute("HospAreaCode")%>';
    var HospLevel ='<%=request.getSession().getAttribute("HospLevel")%>'=='null'?decodeURI(getQuerytoString("HospLevel")):'<%=request.getSession().getAttribute("HospLevel")%>';
    var HospName ='<%=request.getSession().getAttribute("HospName")%>'=='null'?decodeURI(getQuerytoString("HospName")):'<%=request.getSession().getAttribute("HospName")%>';
    var DrTypeRole ='<%=request.getSession().getAttribute("DrTypeRole")%>'=='null'?decodeURI(getQuerytoString("DrTypeRole")):'<%=request.getSession().getAttribute("DrTypeRole")%>';
    var upHospAreaCode = '<%=request.getSession().getAttribute("upHospAreaCode")%>'=='null'?decodeURI(getQuerytoString("upHospAreaCode")):'<%=request.getSession().getAttribute("upHospAreaCode")%>'
    var jdSourceType = '<%=request.getSession().getAttribute("jdSourceType")%>'=='null'?decodeURI(getQuerytoString("jdSourceType")):'<%=request.getSession().getAttribute("jdSourceType")%>'
    //新增加drRole，add by WangCheng
    var drRole = '<%=request.getSession().getAttribute("drRole")%>'=='null'?decodeURI(getQuerytoString("drRole")):'<%=request.getSession().getAttribute("drRole")%>';
    <!--    基卫跳转过来带读卡参数  -->
    //使用读卡卡具型号
    var dkqlx='<%=request.getSession().getAttribute("dkqlx")%>'=='null'?getQueryString("icklb0"):'<%=request.getSession().getAttribute("dkqlx")%>';
    //读卡卡具端口
    var dkqdk='<%=request.getSession().getAttribute("dkqdk")%>'=='null'?getQueryString("ickdk0"):'<%=request.getSession().getAttribute("dkqdk")%>';
    //地区编码
    var dqbm='<%=request.getSession().getAttribute("dqbm")%>'=='null'?getQueryString("linkman"):'<%=request.getSession().getAttribute("dqbm")%>';
    //网点
    var wdbm='<%=request.getSession().getAttribute("wdbm")%>'=='null'?getQueryString("shortname"):'<%=request.getSession().getAttribute("wdbm")%>';
    <!-- 福州签约实时与医保交互 调医保接口所需参数 -->
    //新农合
    var xuuser='<%=request.getSession().getAttribute("xuuser")%>'=='null'?getQueryString("xuuser"):'<%=request.getSession().getAttribute("xuuser")%>';
    var xpaw='<%=request.getSession().getAttribute("xpaw")%>'=='null'?getQueryString("xpaw"):'<%=request.getSession().getAttribute("xpaw")%>';
    //医保
    var yuuser='<%=request.getSession().getAttribute("yuuser")%>'=='null'?getQueryString("yuuser"):'<%=request.getSession().getAttribute("yuuser")%>';
    var ypaw='<%=request.getSession().getAttribute("ypaw")%>'=='null'?getQueryString("ypaw"):'<%=request.getSession().getAttribute("ypaw")%>';
</script>
