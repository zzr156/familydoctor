<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ylz.bizDo.register.po.HealthCareParameter" %>
<%@ page import="com.ylz.bizDo.cd.po.CdUser" %>
<%@ page import="com.ylz.packcommon.common.Constant" %>
<%@ page import="com.ylz.packaccede.allDo.SysDao" %>
<%@ page import="com.ylz.packcommon.common.bizDo.SpringHelper" %>
<%
/*String path = request.getContextPath();
Sysorg sysorg = (Sysorg)SysmanageUtil.getOrg(request);

//医保接口方式，两层或三层，两层带上医保请求应答文件路径
UtilFacade utilFacade = new UtilFacade();
Xt_xtcs00DTO YBJK = utilFacade.doFindXt_xtcs00ByName(sysorg.getOrgid(), "YBJK");
String jkfs00 = "3";//默认三层接口方式*/
 //String jklj00 = "C:\\\\sfjk\\\\";//默认接口路径在C盘根目录
	CdUser user = (CdUser)request.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
	System.out.print("有调用"+user);
	SysDao sysDao=(SysDao) SpringHelper.getBean("sysDao");
	HealthCareParameter healthCareParameter = new HealthCareParameter();
	healthCareParameter.setHospId(user.getHospId());
	healthCareParameter.setParameterName("YBJK");
	System.out.print("有调用"+healthCareParameter.getParameterName());
	HealthCareParameter hcp = sysDao.getRegisterDao().getHCParameter(healthCareParameter);
	String jklj00 = "";
	if(null != hcp){
		jklj00 = hcp.getParameterValue();
	}
	System.out.print("有调用"+jklj00);
/*if(null != YBJK && YBJK.getFlag00().equals("2")){
	jkfs00 = "2";//两层接口方式
	jklj00 = YBJK.getValue0();
}*/
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>系统信息</title>
		<STYLE> .WndFocus { PADDING-RIGHT: 1px; PADDING-LEFT: 1px; PADDING-BOTTOM: 1px; MARGIN: 1px; CURSOR: default; COLOR: #ffffff; PADDING-TOP: 1px; HEIGHT: 20px; BACKGROUND-COLOR: #31309c }
			.FrameWnd { BORDER-RIGHT: #ffffff 2px outset; BORDER-TOP: #ffffff 2px outset; FONT-SIZE: 10pt; BORDER-LEFT: #ffffff 2px outset; BORDER-BOTTOM: #ffffff 2px outset; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #d6d3ce }
			.body { FONT-SIZE: 10pt; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #396da5 }
		</STYLE>
   <script src="yibao.js" charset="UTF-8"></script>
	</head>
	<body>
		<form id="DengDaiFrm" method="post">
			<table class="FrameWnd" id="table02" height="94" cellSpacing="0" cellPadding="0" width="410" align="center" border="0">
				<TBODY>
					<tr>
						<td width="410" height="30"><font face="宋体"></font>
							<script language="javascript">
								function Progress()
								{
								    elt="0123456789ABCDEF";
									var	sTBHTMLS="";
									var	sTBHTMLE="";
									for(var	i=0;i<0xFF-0x99;i+=2)
									{
									    var	cr="";
										var	l;
										var	sTBHTML="";
										l=i+0x99;
										for(var j=0;j<2;j++)
										{
											var	n=l % 16;
											l=l >> 4;
											cr=elt.charAt(n)+cr;
										}
										l=i+0x33;
										for(var j=0;j<2;j++)
										{
											var	n=l % 16;
											l=l >> 4;
											cr=elt.charAt(n)+cr;
										}
										l=i;
										for(var j=0;j<2;j++)
										{
											var	n=l % 16;
											l=l >> 4;
											cr=elt.charAt(n)+cr;
										}
										var	w=i>(0xFF-0xA0)?8:4;
										sTBHTML= "<span style='height:20px;width:"+w+";background-color:#"+cr+";margin:0;padding:0'/></span>";
										sTBHTMLS+=sTBHTML;
										sTBHTMLE=sTBHTML+sTBHTMLE;
									}
									var	sTBHTML=sTBHTMLS+sTBHTMLE;
									document.write("<marquee scrollamount='14' direction='right'  scrolldelay='4' height='20' style='width:100%;height:20px; font-size:6px;background-color:#003399'>");
									document.write(sTBHTML);
									document.write("</marquee>");
								}
								Progress();
							</script>
						</td>
					</tr>
					<tr>
						<td width="410" height="25" align="center" ><div id="card_div"><div id="card_div_child">正在和医保通讯中，请等待...</div></div>
							<br/><font color=blue>请不要手动点击右上角关闭，以免数据不一致</font>
						</td>
					</tr>
				</TBODY>
			</table>
			 <script>
			 	$YiBao.sfjkPath = "<%=jklj00%>";
                $YiBao.sfjkPath = "C:/QZYB/医院接口/SFJK/";
                var	requesttype = window.dialogArguments;
			 	$YiBao.requesttype = requesttype;
			 	$YiBao.getCardDiv();
			 	$YiBao.timer = setInterval($YiBao.readReply, $YiBao.replyInterval);
			 </script>
		</form>
	</body>
</HTML>
