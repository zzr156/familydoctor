Ext.namespace("DesignConfig");
var testProcess = '<?xml version="1.0"?>'
			+'<process name="借款流程" description="借款流程" xmlns="http://jbpm.org/4.4/jpdl">'
			+'<swimlane name="测试" candidate-users="wind" candidate-groups="开发部,管理员" candidate-depts="开发部" candidate-roles="管理员" />'
			+'<swimlane name="测试1" candidate-users="autumn" candidate-groups="销售部" candidate-depts="销售部" />'
			+'<start name="流程开始" g="478,3,110,50">'
				+'<transition name="借款发起" to="填写借款申请" g="499,65"/>'
			+'</start>'
			+'<task name="填写借款申请" candidate-users="wind,autumn" g="479,127,110,50">'
				+'<transition name="提交申请" to="部门经理审批" g="498,185"/>'
			+'</task>'
			+'<task name="部门经理审批" g="477,243,110,50" candidate-users="经理" swimlane="测试">'
				+'<transition name="部门经理审批通过" to="金额判断" g="313,243"/>'
				+'<transition name="部门经理驳回" to="流程结束2" g="657,311"/>'
			+'</task>'
			+'<task name="总经理审批" g="108,374,110,50" swimlane="测试">'
				+'<transition name="总经理审批通过" to="财务拨款" g="314,376"/>'
			+'</task>'
			+'<task name="财务拨款" candidate-depts="财务部" candidate-roles="财务" candidate-groups="财务部,财务" g="480,378,110,50">'
				+'<transition name="邮件通知" to="流程结束2" g="655,379"/>'
			+'</task>'
			+'<decision name="金额判断" g="108,242,110,50">'
				+'<transition name="&gt;5000总经理审批" to="总经理审批" g="128,308"/>'
				+'<transition name="&lt;=5000财务拨款" to="财务拨款" g="314,310"/>'
			+'</decision>'
			+'<end name="流程结束2" g="789,380,110,50"/>'
			+'</process>';
var DesignConfig = {
		//初始化布局
		init : function(){		
			tip = "提示";
		//全局变量
		currentBtn = "select";
		currentParam = "base";
		//--- 图形化设计 ---
		lineFlag = false;
		dragable = true;
		eventsrc = null;//eventsrc是当前触发事件的对象（节点对象）
		presrc = null; //前一个选中的对象
		xmlNode = null;//当前选中的XML节点
		x = 0,y = 0;  
		popeventsource = "";
		temp1 = 0;
		temp2 = 0;
		//--- 画线 ---
		//连线的源和目标
		srcRect=null,desRect=null;
		x0=0,x1=0,y0=0,y1=0;   //连线的头尾坐标
		fontX=0,fontY=0;	   //文字的坐标
		xml = null;//xml对象
		//各个节点所拥有的属性
		nodeParams = {
			base:["base"],//文本节点
			mail:["base","mailto"],//mail节点
			process:["base","swimlane","sql","notice","mailto"],//流程定义
			start:["base","sql","notice","mailto"],//start、end节点
			task:["base","sql","notice","mailto","change","delegate","form","method"],//任务节点
			transition:["base","sql","notice","mailto","autoDelegate","case"]//transition
		}
		//虚线
		dashLine = null;
		//显示xml窗口
		xmlWin = null;
		designWin = null;
		var elePanel = new Ext.Panel({
			title: '工具栏',
			region: 'west',	
			iconCls: 'picon05',
			width: 150,
			split: true,
			minSize: 150,
			maxSize: 150,
			contentEl: 'west',
			collapsible: true			
		});
		var mainPanel = new Ext.Panel({
			region: 'center',
			autoScroll: true,
			contentEl: 'center',
			tbar: new Ext.Toolbar({
				height: 31,
				border: false,
				items: [
					{text: '&nbsp;新建流程',iconCls: 'picon17',handler: function(){
						Ext.getDom("center").innerHTML = "";
						xml = null;
						initProcess();
					}},'-',
					{text: '&nbsp;导入流程',iconCls: 'picon15',handler: function(){
						XmlToProcess(testProcess);
					}},'-',
					{text: '&nbsp;导出流程',iconCls: 'picon16',handler: ''},'-',
					{text: '&nbsp;栅格',iconCls: 'picon21',handler: function(){
						if(Ext.fly(Ext.getDom("center").parentNode).hasClass("center"))
							Ext.fly(Ext.getDom("center").parentNode).removeClass("center");
						else
							Ext.fly(Ext.getDom("center").parentNode).addClass("center");
					}},'-',
					{text: '&nbsp;查看XML',iconCls: 'picon18',handler: showXml},'->',
					{text: '&nbsp;发布流程',iconCls: 'picon20',handler: ''},'-',
					{text: '&nbsp;保存流程',iconCls: 'picon19',handler: ''}
				]
			})
		});
		var keyPanel = new Ext.Panel({
			x: 0,
			y: 0,
			layout: 'fit',
			border: false,
			anchor: '0 100%',
			contentEl: 'south_key'
		});
		var valuePanel = new Ext.Panel({
			border: false,
			layout: 'card',
			id: 'paramCard',
			activeItem: 0,
			x: 155,
			y:0,
			anchor: '0 100%',
			items: new Ext.form.FormPanel({
				id: 'base',
				border: false,
				bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
				labelAlign: 'left',
				labelWidth: 80,
				defaults: {width: '85%'},
				defaultType: 'textfield',
				items:[{
					fieldLabel: '名称',
					id: 'baseName',
					enableKeyEvents: true,
					listeners: {
						'keyup': function(e){
							if(presrc != null){
								if(presrc.tagName.toLowerCase()=="roundrect"){
									presrc.lastChild.lastChild.data = e.getValue();
									modifyXmlNodeAttr(presrc,"name",e.getValue());
									//修改指向该节点的transition的to属性
									var lines = Ext.DomQuery.select("line[project="+presrc.id+"]");
									for(var i=0;i<lines.length;i++)
										modifyXmlNodeAttr(lines[i],"to",e.getValue());
								}else if(presrc.tagName.toLowerCase()=="line"){
									Ext.DomQuery.select("span[title="+presrc.id+"]")[0].innerHTML = e.getValue();
									modifyXmlNodeAttr(presrc,"name",e.getValue());
								}else{
									presrc.innerHTML = e.getValue();
									modifyXmlNodeAttr(Ext.getDom(presrc.title),"name",e.getValue());
								}	
								if(presrc.tagName.toLowerCase() != "span")
									presrc.title = e.getValue();
							}else{//为空时表明是流程定义属性
								modifyXmlNodeAttr("process","name",e.getValue());
							}
						}
					}
				},{
					xtype: 'textarea',
					fieldLabel: '描述',
					id: 'baseDescrip',
					enableKeyEvents: true,
					listeners: {
						'keyup': function(e){
							if(presrc != null){
								//修改对应的XML
								if(presrc.tagName.toLowerCase() == "span"){
									modifyXmlNodeAttr(Ext.getDom(presrc.title),"description",e.getValue());
								}
								modifyXmlNodeAttr(presrc,"description",e.getValue());
							}else{
								modifyXmlNodeAttr("process","description",e.getValue());
							}
						}
					}
				}]
			})
		});
		var paraPanel = new Ext.Panel({
			title: '流程定义',
			region: 'south',
			id: 'paraPanel',
			iconCls: 'picon01',
			layout: 'absolute',
			collapsible: true,
			autoScroll: true,
			split: true,
			height: 180,
			items: [keyPanel,valuePanel],
			collapseFirst: false,
			tools:[{
				id: 'maximize',
				qtip: '最大化',
				handler: function(){
					paraPanel.setHeight(400);
					designWin.doLayout();
				}
			},{
				id: 'restore',
				qtip: '还原',
				handler: function(){
					paraPanel.setHeight(180);
					designWin.doLayout();
				}
			}]
		});
		designWin = new Ext.Window({
			title: '流程设计器',
			layout: 'border',
			iconCls: 'picon06',
			width: 900,
	        height: 600,
	  		autoScroll: false,
	        plain: false,
	  		modal: true,
	  		//maximizable: true,
			maximized: true,
	  		closeAction: 'hide',
			items:[elePanel,mainPanel,paraPanel]
		});
		designWin.show();
		Ext.getDom("west").style.display = "block";
		Ext.getDom("south_key").style.display = "block";
		//初始流程属性
		initProcess();
		//为工具栏按钮注册事件
		Ext.get(Ext.DomQuery.select(".btn")).on("click",function(){
			if(!Ext.fly(this).hasClass("btn_down")){
				currentBtn = this.title;
				Ext.get(Ext.DomQuery.select(".btn_down")).removeClass("btn_down");
				Ext.fly(this).addClass("btn_down");
				setParams();
			}
		});
		//为属性栏按钮注册事件
		Ext.get(Ext.DomQuery.select(".key_btn")).on("click",function(){
			if(!Ext.fly(this).hasClass("key_btn_down")){
				currentParam = this.title;
				Ext.get(Ext.DomQuery.select(".key_btn_down")).removeClass("key_btn_down");
				Ext.fly(this).addClass("key_btn_down");
				//转换面板
				changeCard();
				//设置值
				if(this.title != "base"){
					if(presrc == null)
						XmlSetParams("process");
					else if(presrc.tagName.toLowerCase() == "span")
						XmlSetParams(Ext.getDom(presrc.title));
					else
						XmlSetParams(presrc);				
				}	
			}
		});
		//为流程区添加右键菜单
		var target = null;
		var contextmenu = new Ext.menu.Menu({
			items:[{
				text: '删除',	
				iconCls: 'picon13',
				handler: function(){
					if(presrc == target)
						initProcess();
					//删除与之相关的文本节点
					if(target.tagName.toLowerCase() == "line")
						Ext.get(Ext.DomQuery.select("span[title="+target.id+"]")).remove();
					//删除与之相关的连线
					if(target.tagName.toLowerCase() == "roundrect"){
						var sources = Ext.DomQuery.select("line[source="+target.id+"]");
						var projects = Ext.DomQuery.select("line[project="+target.id+"]");			
						//删除与之对应的文本节点
						var lines = sources.concat(projects);
						for(var i=0;i<lines.length;i++){
							deleteXmlNode(lines[i]);
							Ext.get(Ext.DomQuery.select("span[title="+lines[i].id+"]")).remove();	
						}
						Ext.get(lines).remove();
					}
					if(target.tagName.toLowerCase() == "span"){
						target.innerHTML = "";
						modifyXmlNodeAttr(Ext.getDom(target.title),"name","");
						Ext.getDom(target.title).title = "";	
						target.style.border = 0;
						if(presrc != null && presrc.id == target.title)
							Ext.getCmp("baseName").setValue("");	
					}else{
						Ext.fly(target).remove();
						deleteXmlNode(target);
					}
				}
			}]
		});
		Ext.getBody().on('mousedown',function(e){
			//判断是否是右键
			if(e.button != "2")
				return false;
			
			target = e.target;
			//判断是否在center区域
			if(!Ext.get(target).findParent("div[id=center]",Ext.getBody()))
				return false;
			if (e.target.tagName.toLowerCase() == 'textbox') 
				target = event.srcElement.parentElement;
			if (e.target.tagName.toLowerCase() == 'b') 
				target = event.srcElement.parentElement.parentElement;			
			var tagName = target.tagName.toLowerCase();
			if (tagName != "line" && tagName != "roundrect" && tagName != "span")
				return false;
			contextmenu.showAt(e.getXY());
		});
		//document.onselectstart = function(){return false;}//禁用复制事件
		document.onmousedown = downAction;  //开始移动
		document.onmouseup = upAction;  //结束移动
	},
	winShow : function(){
		designWin.show();
	}
};
//初始设置流程定义
function initProcess(){	
	presrc = null;
	if(xml == null){
		//节点计数器
		taskNum = 1;
		lineNum = 1;
		endNum = 1;
		boolNum = 1;
		joinNum = 1;
		forkNum = 1;
		mailNum = 1;
		//树列表计数器
		swimlaneNum = 1;
	}
	createDashLine();
	showNodeParams(nodeParams.process,"流程定义","picon01");
	//创建xml对象
	createXml();
	//设置显示属性
	XmlSetParams("process");
}
//根据点击的按钮设置参数
function setParams(){
	switch(currentBtn){
		case "select":
			dragable = true;
			break
		case "grid":
			break
		case "transition":
			lineFlag = true;
			break
		default:
	}
}
//左键按下时方法
function downAction(){
	//判断是否是左键被按下
	if(event.button != 1)
		return;
	switch(currentBtn){
		case "select":		
			//拖动
			drags();
			//显示属性
			showParams();
			break
		case "transition":
			createLine();
			break
		default:
			createNode();
	}
}
//左键释放时方法
function upAction(){
	if(!Ext.get(eventsrc).findParent("div[id=center]",Ext.getBody()))
		return false;
	switch(currentBtn){
		case "select":
			dragable = false;
			break
		case "transition":
			drawLine();
			break
		default:
	}
}
//创建节点
function createNode(){
	if(!nodeOrNot())
		return false;
	var node = document.createElement("v:roundrect");
	node.inset = '2pt,2pt,2pt,2pt';
	node.style.pixelLeft = event.x + Ext.getDom("center").parentNode.scrollLeft;
	node.style.pixelTop = event.y + Ext.getDom("center").parentNode.scrollTop;
	node.style.zIndex = 1;
	node.style.pixelWidth = 110;
	node.style.pixelHeight = 50;
	node.strokeColor = "#27548d";
	node.fillcolor= '#EEEEEE';
	Ext.fly(node).addClass("node");
	switch(currentBtn){
		case "start":
			node.id = "start";
			node.title = "流程开始";
			node.flowtype = "start";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_start' inset='1pt,2pt,1pt,1pt'><b>Start</b><br />流程开始</v:textbox>";
			break
		case "task":		
			node.id = "task" + taskNum;
			node.title = "任务节点" + taskNum;
			node.flowtype = "task";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_task' inset='1pt,2pt,1pt,1pt'><b>TaskNode</b><br />任务节点"+taskNum+"</v:textbox>";
			taskNum ++;
			break
		case "end":
			node.id = "end" + endNum;
			node.title = "流程结束" + endNum;
			node.flowtype = "end";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_end' inset='1pt,2pt,1pt,1pt'><b>End</b><br />流程结束"+endNum+"</v:textbox>";
			endNum ++;
			break;
		case "bool":
			node.id = "bool" + boolNum;
			node.title = "决策" + boolNum;
			node.flowtype = "decision";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_bool' inset='1pt,2pt,1pt,1pt'><b>Decision</b><br />决策"+boolNum+"</v:textbox>";
			boolNum ++;
			break;
		case "mail":
			node.id = "mail" + mailNum;
			node.title = "邮件" + mailNum;
			node.flowtype = "mail";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_mail' inset='1pt,2pt,1pt,1pt'><b>Mail</b><br />邮件"+mailNum+"</v:textbox>";
			mailNum ++;
			break;
		case "join":
			node.style.pixelWidth = 50;
			node.id = "join" + joinNum;
			node.title = "合并" + joinNum;
			node.flowtype = "join";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_join' inset='1pt,2pt,1pt,1pt'><br /></v:textbox>";
			joinNum ++;
			break;
		case "fork":
			node.style.pixelWidth = 50;
			node.id = "fork" + forkNum;
			node.title = "分支" + forkNum;
			node.flowtype = "fork";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_fork' inset='1pt,2pt,1pt,1pt'><br /></v:textbox>";
			forkNum ++;
			break;
		default:
	}
	document.getElementById("center").appendChild(node);
	addXmlNode(node);
}
//判断是否创建节点
function nodeOrNot(){
	//点击事件事是否发生在工作区
	//防止右键菜单弹出时点击阴影出错
	if(event.srcElement == null || event.srcElement.firstChild == null) return false;
	if(event.srcElement.firstChild.id != "center" && event.srcElement.id != "center")
		return false;
	//如果是start节点判断是否已经存在
	if(currentBtn == "start" && document.getElementById("start") != null){
		return false;
	}
	return true;
}
//将当前触发事件的节点内对象转为节点对象
function selectNode(){
	eventsrc = event.srcElement;
	//如果事件对象是textbox，将事件对象变为它的父对象
	if (event.srcElement.tagName.toLowerCase() == 'textbox') 
		eventsrc = event.srcElement.parentElement;
	//如果事件对象是b，将事件对象变为它的父对象的父对象
	if (event.srcElement.tagName.toLowerCase() == 'b') 
		eventsrc = event.srcElement.parentElement.parentElement;
	//如果是选择并且在center区域，执行下面的选中
	if(currentBtn == "select" && !!Ext.get(eventsrc).findParent("div[id=center]",Ext.getBody())){
		//如果前次选择与当前选中一致，不执行以下语句
		//alert(presrc.id + "," + eventsrc.id);
		if(presrc == eventsrc)
			return false;	
		if(presrc != null){
			if(presrc.tagName.toLowerCase() == "span"){
				presrc.style.border = "0";
			}else{
				presrc.strokeColor = "#27548d";
				presrc.strokeWeight = "1pt";
				presrc.style.zIndex = 1;
			}
		}
		switch(eventsrc.tagName.toLowerCase()){
			case "roundrect":
				eventsrc.strokeColor = "#ff0000";
				eventsrc.strokeWeight = "2pt";
				eventsrc.style.zIndex = 2;
				break
			case "line":
				eventsrc.strokeColor = "#ff0000";
				eventsrc.strokeWeight = "2pt";
				break
			case "span":
				eventsrc.style.border = "2px solid #ff0000";
				break
		}
		//将当前节点赋值给presrc
		presrc = eventsrc;
	}else if(currentBtn == "select"){
		if(presrc!=null&&eventsrc.tagName.toLowerCase()=="div"&&eventsrc.firstChild.id=="center"||eventsrc.id =="center"){
			if(presrc.tagName.toLowerCase() == "span"){
				presrc.style.border = "0";
			}else{
				presrc.strokeColor = "#27548d";
				presrc.strokeWeight = "1pt";
				presrc.style.zIndex = 1;
			}
			presrc = null;
		}
	}
}
function move(){
	if (event.button == 1 && dragable){
		var newleft = temp1 + event.x - x;
		var newtop = temp2 + event.y - y;
		//重新设置节点的坐标
		modifyXmlNodeAttr(eventsrc,"g",newleft+","+newtop+","+eventsrc.style.pixelWidth+","+eventsrc.style.pixelHeight);
		eventsrc.style.pixelLeft = newleft;
		eventsrc.style.pixelTop = newtop;
		//重画与节点相关的线和文字节点
		reDrawLine();
		return false;
	}
}
function lineMove(){
	//移动时的虚线随鼠标移动
	if(lineFlag){
		//判断是否有滚动条，有的话加上滚动条的滚动长度
		dashLine.to = (event.x+Ext.getDom("center").parentNode.scrollLeft) + "," + (event.y+Ext.getDom("center").parentNode.scrollTop);
		return false;
	}
}
//查看选择节点的属性
function showParams(){
	if(presrc==null&&eventsrc.tagName.toLowerCase()=="div"&&eventsrc.firstChild.id=="center"||eventsrc.id =="center"){
		//如果前次选中为null的话，显示流程定义属性
		initProcess();
		return false;
	}
	if(!Ext.get(eventsrc).findParent("div[id=center]",Ext.getBody()))
		return false;
	switch(eventsrc.flowtype){
		case "start":
			showNodeParams(nodeParams.start,"开始节点","picon08");
			break
		case "end":
			showNodeParams(nodeParams.start,"结束节点","picon09");
			break
		case "decision":
			showNodeParams(nodeParams.base,"决策节点","picon04");
			break		
		case "mail":
			showNodeParams(nodeParams.mail,"邮件节点","picon10");
			break	
		case "join":
			showNodeParams(nodeParams.base,"合并","picon11");
			break
		case "fork":
			showNodeParams(nodeParams.base,"分支","picon12");
			break	
		case "task":			
			showNodeParams(nodeParams.task,"任务节点","picon02");
			break
		case "transition":		
			showNodeParams(nodeParams.transition,"转换","picon03");
			break
		default:
			//当选中的是文本节点的话，转换成对应的连线
			eventsrc = Ext.getDom(eventsrc.title);
			showNodeParams(nodeParams.transition,"转换","picon03");
			break
	}
	//设置显示属性
	XmlSetParams(eventsrc);
}
//显示各节点对应的属性
function showNodeParams(params,title,icon){
	Ext.getCmp("paraPanel").setTitle(title);
	Ext.getCmp("paraPanel").setIconClass(icon);
	//隐藏所有属性
	Ext.get(Ext.DomQuery.select(".key_btn")).setDisplayed("none");
	//显示对应属性
	for(var i=0;i<params.length;i++){
		var param = Ext.DomQuery.select("div[title="+params[i]+"]")[0];
		param.style.display = "block";
		if(params[i] == "base"){
			param.fireEvent('onclick');
		}
	}
}
function drags(){
	if (event.button != 1)
		return;
	selectNode();
	if (eventsrc.tagName.toLowerCase() == 'roundrect'){
		dragable = true;
		temp1 = eventsrc.style.pixelLeft;
		temp2 = eventsrc.style.pixelTop;
		x = event.x;
		y = event.y;
		document.onmousemove = move;
	}
}
//创建虚线
function createDashLine(){
	if(document.getElementById("dashLine") == null){
		dashLine = document.createElement("v:line"); 
		dashLine.style.display = "none";
		dashLine.style.position = "absolute";
		dashLine.id = "dashLine";
		dashLine.strokeWeight = "2pt";
		dashLine.fillcolor = "#f441ff";
		dashLine.strokeColor = "#f441ff";
		dashLine.innerHTML = "<v:stroke dashstyle='longDash'/><v:shadow on='t' type='single' color='#cccccc' offset='1px,1px'/>";
		document.getElementById("center").appendChild(dashLine);
	}
}
function createLine() {
	selectNode();
	if (eventsrc.tagName == 'roundrect' && eventsrc.flowtype != "end"){
		srcRect = eventsrc;
		//将虚线显示，并将虚线的起点和终点设为点击事件对象的中心
		var dx = srcRect.style.pixelLeft + srcRect.style.pixelWidth / 2;
		var dy = srcRect.style.pixelTop + srcRect.style.pixelHeight / 2;
		dashLine.from =  dx + "," +	dy;
		dashLine.to =  dx + "," +	dy;
		dashLine.style.pixelLeft = dx + 'px';
        dashLine.style.pixelTop = dy + 'px';
		dashLine.style.display = "block";
		document.onmousemove = lineMove;
	}else{
		srcRect = null;
	}
}
function drawLine(){
	if(srcRect == null)
		return;
	selectNode();
	if (eventsrc.tagName == 'roundrect' && srcRect != eventsrc){
		desRect = eventsrc;
		//创建线
		//判断是否画线
		if(drawOrNot()){
			var line = document.createElement("v:line");
			direction();
			line.from = x0+","+y0;
			line.to = x1+","+y1;
			line.style.pixelLeft = x0 + 'px';
			line.style.pixelTop = y0 + 'px';
			line.style.position = "absolute";
			line.style.display = "block";
			line.id = "line" + lineNum;
			line.flowtype = "transition";
			line.strokeWeight = "1pt";
			line.style.cursor = "pointer";
			line.strokeColor = "#27548d";
			line.source = srcRect.id;
			line.project = desRect.id;
			//创建箭头
			line.innerHTML = "<v:stroke endarrow='Classic' />";
			document.getElementById("center").appendChild(line);	
			//在连线上生成文字
			var font = createFont();
			line.title = font.innerHTML;
			lineNum ++;
			addXmlNode(line,srcRect);
		}
	}
	//onmouseup事件后隐藏虚线和取消移动事件
	dashLine.style.display = "none";
	document.onmousemove = null;
}
function reDrawLine(){
	var lines = Ext.DomQuery.select("line[project="+eventsrc.id+"]").concat(Ext.DomQuery.select("line[source="+eventsrc.id+"]"));
	for(var i=0;i<lines.length;i++){
		if(eventsrc.id == lines[i].source){
			//将源与目的赋值为线的源与目的
			srcRect = document.getElementById(lines[i].source);
			desRect = document.getElementById(lines[i].project);
			direction();
			lines[i].to = x1 + "," + y1;
			lines[i].from = x0 + "," + y0;
			//重新设置文本位置
			fontLocation();
			var font = Ext.DomQuery.select("span[title="+lines[i].id+"]")[0];
			if(font != null){
				modifyXmlNodeAttr(lines[i],"g",fontX+","+fontY);
				font.style.pixelLeft = fontX;
				font.style.pixelTop = fontY;
			}
		}
		if(eventsrc.id == lines[i].project){
			//将源与目的赋值为线的源与目的
			srcRect = document.getElementById(lines[i].source);
			desRect = document.getElementById(lines[i].project);
			var locations = direction();
			lines[i].to = x1 + "," + y1;
			lines[i].from = x0 + "," + y0;
			//重新设置文本位置
			fontLocation();
			var font = Ext.DomQuery.select("span[title="+lines[i].id+"]")[0];
			if(font != null){
				//在修改坐标前先修改XML中的坐标，否则改完后找不到相应的节点
				modifyXmlNodeAttr(lines[i],"g",fontX+","+fontY);
				font.style.pixelLeft = fontX;
				font.style.pixelTop = fontY;
			}	
		}		
	}
}
//判断是否画线
function drawOrNot(){
	//目的地址不能是start
	if(desRect.flowtype == "start")
		return false;
	//是否已存在
	var lines = document.getElementsByTagName('line');
	for(var i=0;i<lines.length;i++){
		if(srcRect.id==lines[i].source&&desRect.id==lines[i].project)
			return false;
		if(srcRect.id == "start" && lines[i].source == "start")
			return false;
	}
	return true;
}
//在横线上生成文字
function createFont(){
	var textNode = document.createElement("span");
	fontLocation();
	textNode.style.pixelLeft = fontX;
	textNode.style.pixelTop = fontY;
	textNode.innerHTML = "to " + desRect.title;
	Ext.fly(textNode).addClass("font_node");
	textNode.title = "line" + lineNum;
	textNode.id = "text" + lineNum;
	document.getElementById("center").appendChild(textNode);
	return textNode;
}
//判断文字的位置
function fontLocation(){
	fontX = Math.round(x0+(x1-x0)/2 - 30);
	fontY = Math.round(y0+(y1-y0)/2 - 25);
}
//箭头方向判断
function direction(){
	if (srcRect.style.pixelLeft > desRect.style.pixelLeft){
		if ((srcRect.style.pixelLeft - desRect.style.pixelLeft) <= desRect.style.pixelWidth){
			x0 = srcRect.style.pixelLeft + srcRect.style.pixelWidth / 2;
			x1 = desRect.style.pixelLeft + desRect.style.pixelWidth / 2;
			if (srcRect.style.pixelTop >  desRect.style.pixelTop){
				y0 = srcRect.style.pixelTop;
				y1 = desRect.style.pixelTop  + desRect.style.pixelHeight;
			}else{
				y0 = srcRect.style.pixelTop + srcRect.style.pixelHeight;
				y1 = desRect.style.pixelTop;
			}
		}else{
			x0 = srcRect.style.pixelLeft;
			x1 = desRect.style.pixelLeft + desRect.style.pixelWidth;
			y0 = srcRect.style.pixelTop + srcRect.style.pixelHeight / 2;
			y1 = desRect.style.pixelTop + desRect.style.pixelHeight / 2;
		}
	}else{
		if ((desRect.style.pixelLeft - srcRect.style.pixelLeft) <= desRect.style.pixelWidth){
			x0 = srcRect.style.pixelLeft + srcRect.style.pixelWidth / 2;
			x1 = desRect.style.pixelLeft + desRect.style.pixelWidth / 2;
			if (srcRect.style.pixelTop >  desRect.style.pixelTop){
			   y0 = srcRect.style.pixelTop;
			   y1 = desRect.style.pixelTop  + desRect.style.pixelHeight;
			}else{
			   y0 = srcRect.style.pixelTop + srcRect.style.pixelHeight;
			   y1 = desRect.style.pixelTop;
			}
		}else{
			x0 = srcRect.style.pixelLeft + srcRect.style.pixelWidth;
			x1 = desRect.style.pixelLeft;
			y0 = srcRect.style.pixelTop + srcRect.style.pixelHeight / 2;
			y1 = desRect.style.pixelTop + desRect.style.pixelHeight / 2;
		}
	}
}

//------------	xml操作    -------------
//创建xml
function createXml(){
	if(xml == null){
		xml = new ActiveXObject("Microsoft.XMLDOM");
		var p = xml.createProcessingInstruction("xml","version='1.0' encoding='UTF-8'");
		xml.appendChild(p);
		var root = xml.createElement("process");
		root.setAttribute("name","新建流程");
		root.setAttribute("xmlns","http://jbpm.org/4.4/jpdl");
		xml.appendChild(root);
	}
}
//查看xml
function showXml(){
	var str = xml.xml.replace(/></g,'>\n\r<');
	str = str.replace(/xmlns=\"\"/g,'');
	if(xmlWin !=null){
		Ext.getCmp("xmlTextArea").setValue(str);
		xmlWin.show();
		return false;
	}
	xmlWin = new Ext.Window({
		title: '查看XML',
		width: 700,
		layout: 'fit',
		iconCls: 'picon18',
		height: 450,
		modal: true,
		closeAction: 'hide',
		maximizable: true,
		items: new Ext.form.TextArea({
			id: 'xmlTextArea',
			value: str
		}),
		buttons:[{
			text: '保存',
			iconCls: 'picon19',
			handler: function(){
				XmlToProcess(Ext.getCmp("xmlTextArea").getValue());
				xmlWin.hide();
			}
		},{
			text: '取消',
			iconCls: 'picon09',
			handler: function(){
				xmlWin.hide();
			}
		}]
	}).show();
}
//添加xml节点
function addXmlNode(node,parentNode){
	var newNode = null;//新节点
	var attr = null;//属性
	var parent = findXmlNode(parentNode);
	if(parent == null)
		parent = xml.documentElement;//指向根节点
	switch(node.flowtype){
		case "start":
			newNode = xml.createElement("start");
			break
		case "end":
			newNode = xml.createElement("end");
			break
		case "task":
			newNode = xml.createElement("task");
			break
		case "decision":
			newNode = xml.createElement("decision");
			break
		case "mail":
			newNode = xml.createElement("mail");
			break
		case "join":
			newNode = xml.createElement("join");
			break
		case "fork":
			newNode = xml.createElement("fork");	
			break
		case "transition":
			newNode = xml.createElement("transition");
			//当选择节点的文本值为null，包括的节点有(join,fork)
			addXmlAttribute(newNode,"name","to "+desRect.title);
			addXmlAttribute(newNode,"to",desRect.title);
			//设置transition节点的g属性的值为对应文本节点的位置
			var font = Ext.DomQuery.select("span[title="+node.id+"]")[0];
			addXmlAttribute(newNode,"g",font.style.pixelLeft+","+font.style.pixelTop);
			parent.appendChild(newNode);
			break
	}
	if(node.flowtype != "transition"){
		addXmlAttribute(newNode,"name",node.title);
		addXmlAttribute(newNode,"g",node.style.pixelLeft+","+node.style.pixelTop+","+node.style.pixelWidth+","+node.style.pixelHeight);
		parent.appendChild(newNode);
	}
}
//添加xml属性
function addXmlAttribute(node,attr,value){
	var attribute = xml.createAttribute(attr);
	attribute.value = value;
	node.setAttributeNode(attribute);
}
//通过XML节点查找流程图节点，当前只用到通过name查找roundrect节点
function findNodeXml(xmlNode){	
	var nodes = [];
	var sameNodes = [];
	//如果不是节点的话通过title查找，如果是节点的话通过flowtype查找
	if(typeof xmlNode == "object"){	
		nodes = Ext.DomQuery.select("[flowtype="+xmlNode.tagName+"]");
		for(var i=0;i<nodes.length;i++){
			if(xmlNode.getAttribute("name") == nodes[i].title)
				sameNodes.push(nodes[i]);
		}
		if(sameNodes.length == 1){
			return sameNodes[0];
		}else{
			for(i=0;i<sameNodes.length;i++){
				if(sameNodes[i].getAttribute("g") == xmlNode.style.pixelLeft+","+xmlNode.style.pixelTop+","+xmlNode.style.pixelWidth+","+xmlNode.style.pixelHeight)
					return sameNodes[i];
			}	
		}
	}else{
		//如果有多个title相同的节点，只能返回第一个
		return Ext.DomQuery.select("roundrect[title="+xmlNode+"]")[0];
	}
	return null;
}
//通过流程图节点查找XML节点
function findXmlNode(node){
	if(node == "process")
		return xml.documentElement;//返回根节点
	if(node == null || node.flowtype == null)
		return null;
	var nodes = xml.getElementsByTagName(node.flowtype);
	var sameNodes = [];
	
	for(var i=0;i<nodes.length;i++){
		//判断是连线还是节点，是节点的话用节点坐标比较，是连线的话，用连线对应的文本节点坐标比较
		if(node.flowtype == "transition"){
			var font = Ext.DomQuery.select("span[title="+node.id+"]")[0];
			for(i=0;i<nodes.length;i++){
				if(nodes[i].getAttribute("g") == font.style.pixelLeft+","+font.style.pixelTop)
					sameNodes.push(nodes[i]);
			}
		}else{
			for(i=0;i<nodes.length;i++){
				if(nodes[i].getAttribute("g") == node.style.pixelLeft+","+node.style.pixelTop+","+node.style.pixelWidth+","+node.style.pixelHeight)
					sameNodes.push(nodes[i]);
			}	
		}
	}
	if(sameNodes.length == 1){
		return sameNodes[0];
	}else{
		for(var i=0;i<sameNodes.length;i++){
			if(sameNodes[i].getAttribute("name") == node.title){
				return sameNodes[i];
			}
		}
	}
	/*
	 *下面是先通过title查找,再通过坐标查找,这样查找的次数比较多【sameNodes的长度比较小】
	 *上面面先通过坐标查找，再通过title查找，坐标相同的几率比较小
	 *因为移动的时候文本节点的坐标改变了，是用上面的方法无法定位到对应的transition
	 *【通过修改重画reDrawLine方法，在修改坐标前先修改XML这样就能定位到相应的transition】
	 *最终使用上面的方法
	 */
	
	//先用名字进行查找，如果找到相同的，在用坐标进行匹配，如果还有相同的，返回第一个
	/*for(var i=0;i<nodes.length;i++){
		if(nodes[i].getAttribute("name") == node.title){
			sameNodes.push(nodes[i]);
		}
	}
	if(sameNodes.length == 1){
		return sameNodes[0];
	}else{
		//判断是连线还是节点，是节点的话用节点坐标比较，是连线的话，用连线对应的文本节点坐标比较
		if(node.flowtype == "transition"){
			var font = Ext.DomQuery.select("span[title="+node.id+"]")[0];
			for(i=0;i<sameNodes.length;i++){
				if(sameNodes[i].getAttribute("g") == font.style.pixelLeft+","+font.style.pixelTop)
					return sameNodes[i];
			}
		}else{
			for(i=0;i<sameNodes.length;i++){
				if(sameNodes[i].getAttribute("g") == node.style.pixelLeft+","+node.style.pixelTop+","+node.style.pixelWidth+","+node.style.pixelHeight)
					return sameNodes[i];
			}	
		}
	}
	/*
	//通过一个code进行查找，每个节点必须带code，不通用，不能适用于其他的XML
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].getAttribute("code") == node.id)
			return nodes[i];
	}
	*/
	return null;
}
//删除节点
function deleteXmlNode(node){
	//如果是文本节点，获取对应的transition节点，清空name属性
	if(node.tagName.toLowerCase() == "span"){
		node = findXmlNode(Ext.getDom(node.title));
		node.setAttribute("name","");
		return false;
	}
	//如果不是文本节点，获取父节点，通过父节点删除自己
	node = findXmlNode(node);
	if(node != null)
		node.parentNode.removeChild(node);
}
//修改节点对应的属性
function modifyXmlNodeAttr(node,param,value){
	node = findXmlNode(node);
	if(node != null)
		node.setAttribute(param,value);
}
//XML的逆向转换，生成节点
function XmltoNode(child){
	var showNode = true;//是否创建节点
	var locations = child.getAttribute("g")!=null?child.getAttribute("g").split(","):[0,0,110,50];
	var node = document.createElement("v:roundrect");
	node.inset = '2pt,2pt,2pt,2pt';
	node.style.pixelLeft = locations[0];
	node.style.pixelTop = locations[1];
	node.style.pixelWidth = locations[2];
	node.style.pixelHeight = locations[3];
	node.strokeColor = "#27548d";
	node.fillcolor= '#EEEEEE';
	Ext.fly(node).addClass("node");
	switch(child.tagName){
		case "start":
			node.id = "start";
			node.title = child.getAttribute("name");
			node.flowtype = "start";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_start' inset='1pt,2pt,1pt,1pt'><b>Start</b><br />"+child.getAttribute("name")+"</v:textbox>";
			break
		case "task":	
			var title = "TaskNode";
			//标识泳道
			if(child.getAttribute("swimlane") != null&&child.getAttribute("swimlane") != "")
				title += "<span title='"+child.getAttribute("swimlane")+"' class='sign'>泳</span>";
			node.id = "task" + taskNum;
			node.title = child.getAttribute("name");
			node.flowtype = "task";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_task' inset='1pt,2pt,1pt,1pt'><b>"+title+"</b><br />"+child.getAttribute("name")+"</v:textbox>";
			taskNum ++;
			break
		case "end":
			node.id = "end" + endNum;
			node.title = child.getAttribute("name");
			node.flowtype = "end";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_end' inset='1pt,2pt,1pt,1pt'><b>End</b><br />"+child.getAttribute("name")+"</v:textbox>";
			endNum ++;
			break;
		case "decision":
			node.id = "decision" + boolNum;
			node.title = child.getAttribute("name");
			node.flowtype = "decision";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_bool' inset='1pt,2pt,1pt,1pt'><b>Decision</b><br />"+child.getAttribute("name")+"</v:textbox>";
			boolNum ++;
			break;
		case "mail":
			node.id = "mail" + mailNum;
			node.title = child.getAttribute("name");
			node.flowtype = "mail";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_mail' inset='1pt,2pt,1pt,1pt'><b>Mail</b><br />"+child.getAttribute("name")+"</v:textbox>";
			mailNum ++;
			break;
		case "join":
			node.style.pixelWidth = 50;
			node.style.pixelHeight = 50;
			node.id = "join" + joinNum;
			node.title = child.getAttribute("name");
			node.flowtype = "join";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_join' inset='1pt,2pt,1pt,1pt'><br /></v:textbox>";
			joinNum ++;
			break;
		case "fork":
			node.style.pixelWidth = 50;
			node.style.pixelHeight = 50;
			node.id = "fork" + forkNum;
			node.title = child.getAttribute("name");
			node.flowtype = "fork";
			node.innerHTML = "<v:shadow on='T' type='single' color='#b3b3b3' offset='2px,2px' />"
			+"<v:textbox class='node_fork' inset='1pt,2pt,1pt,1pt'><br /></v:textbox>";
			forkNum ++;
			break;
		default:
			showNode = false;
	}
	if(showNode)
		document.getElementById("center").appendChild(node);
}
//XML的逆向转换，生成连线及文本节点
function XmltoLine(child){
	srcRect = findNodeXml(child.parentNode);
	desRect = findNodeXml(child.getAttribute("to"));
	var locations = child.getAttribute("g")!=null?child.getAttribute("g").split(","):[0,0];
	var line = document.createElement("v:line");
	direction();
	line.title = child.getAttribute("name");
	line.from = x0+","+y0;
	line.to = x1+","+y1;
	line.style.pixelLeft = x0 + 'px';
	line.style.pixelTop = y0 + 'px';
	line.style.position = "absolute";
	line.style.display = "block";
	line.id = "line" + lineNum;
	line.flowtype = "transition";
	line.strokeWeight = "1pt";
	line.style.cursor = "pointer";
	line.strokeColor = "#27548d";
	line.source = srcRect.id;
	line.project = desRect.id;
	//创建箭头
	line.innerHTML = "<v:stroke endarrow='Classic' />";
	document.getElementById("center").appendChild(line);

	//生成文本节点
	var textNode = document.createElement("span");
	textNode.style.pixelLeft = locations[0];
	textNode.style.pixelTop = locations[1];
	textNode.innerHTML = child.getAttribute("name");
	Ext.fly(textNode).addClass("font_node");
	textNode.title = "line" + lineNum;
	textNode.id = "text" + lineNum;
	document.getElementById("center").appendChild(textNode);
	lineNum ++;
}
//XML的逆向转换，将XML转换成为流程图
function XmlToProcess(loadXml){
	//清空已有的流程图
	Ext.getDom("center").innerHTML = "";
	xml.loadXML(loadXml);
	//初始化流程信息
	initProcess();

	var root = findXmlNode("process");
	var rootChilds = root.childNodes;
	for(var i=0;i<rootChilds.length;i++)
		XmltoNode(rootChilds[i]);
	var lines = xml.getElementsByTagName("transition");
	for(i=0;i<lines.length;i++)
		XmltoLine(lines[i]);
}
//根据节点获取对应的XML节点，并对相应的属性页面赋值
function XmlSetParams(node){
	xmlNode = findXmlNode(node);
	if(xmlNode == null)
		return false;
	switch(currentParam){
		case "base":
			if(node != null){
				Ext.getCmp("baseName").setValue(xmlNode.getAttribute("name"));
				Ext.getCmp("baseDescrip").setValue(xmlNode.getAttribute("description"));
			}
			break
		case "delegate":
			//formClear("delegate");
			//初始化泳道下拉框
			var swimlanes = xml.getElementsByTagName("swimlane");
			var arrays = [];
			for(var i=0;i<swimlanes.length;i++){
				arrays.push([swimlanes[i].getAttribute("name"),swimlanes[i].getAttribute("name")]);
			}
			Ext.getCmp("delegateSwimlaneCombo").store.loadData(arrays);
			//判断是否有泳道，有的话进行赋值
			if(xmlNode.getAttribute("swimlane")!=""&&xmlNode.getAttribute("swimlane")!=null){
				Ext.getCmp("delegateSwimlaneCombo").setValue(xmlNode.getAttribute("swimlane"));
				Ext.getCmp("delegateSwimlaneCombo").enable();
				Ext.getCmp("delegateSwimlaneCheck").checked = true;
				Ext.getDom("delegateSwimlaneCheck").checked = true;
			}else{
				Ext.getCmp("delegateSwimlaneCombo").setValue("");
				Ext.getCmp("delegateSwimlaneCombo").disable();
				Ext.getCmp("delegateSwimlaneCheck").checked = false;
				Ext.getDom("delegateSwimlaneCheck").checked = false;
			}
			//为候选人赋值
			if(xmlNode.getAttribute("candidate-users")!=""&&xmlNode.getAttribute("candidate-users")!=null){
				Ext.getCmp("delegateUserCheck").checked = true;
				Ext.getDom("delegateUserCheck").checked = true;
				Ext.getCmp("delegateUserBox").enable();
				Ext.getCmp("delegateUserBox").setValue(xmlNode.getAttribute("candidate-users"));
			}else{
				Ext.getCmp("delegateUserCheck").checked = false;
				Ext.getDom("delegateUserCheck").checked = false;
				Ext.getCmp("delegateUserBox").disable();
				Ext.getCmp("delegateUserBox").setValue("");
			}
			//候选角色赋值
			if(xmlNode.getAttribute("candidate-roles")!=""&&xmlNode.getAttribute("candidate-roles")!=null){
				Ext.getCmp("delegateRoleCheck").checked = true;
				Ext.getDom("delegateRoleCheck").checked = true;
				Ext.getCmp("delegateRoleBox").enable();
				Ext.getCmp("delegateRoleBox").setValue(xmlNode.getAttribute("candidate-roles"));
			}else{
				Ext.getCmp("delegateRoleCheck").checked = false;
				Ext.getDom("delegateRoleCheck").checked = false;
				Ext.getCmp("delegateRoleBox").disable();
				Ext.getCmp("delegateRoleBox").setValue("");
			}
			//候选部门赋值
			if(xmlNode.getAttribute("candidate-depts")!=""&&xmlNode.getAttribute("candidate-depts")!=null){
				Ext.getCmp("delegateDeptCheck").checked = true;
				Ext.getDom("delegateDeptCheck").checked = true;
				Ext.getCmp("delegateDeptBox").enable();
				Ext.getCmp("delegateDeptBox").setValue(xmlNode.getAttribute("candidate-depts"));
			}else{
				Ext.getCmp("delegateDeptCheck").checked = false;
				Ext.getDom("delegateDeptCheck").checked = false;
				Ext.getCmp("delegateDeptBox").disable();
				Ext.getCmp("delegateDeptBox").setValue("");
			}
			break
		case "swimlane":
			var xmlSwimlanes = xml.getElementsByTagName("swimlane");
			//因为泳道是属于整个流程的，所以只有页面列表和XML节点数目不同的时候加入
			if(Ext.DomQuery.select(".swimlane_btn").length != xmlSwimlanes.length){
				Ext.getDom("swimlaneList").innerHTML = "";
				for(var i=0;i<xmlSwimlanes.length;i++){
					addTreeList(Ext.getDom("swimlaneList"),"S"+(i+1),xmlSwimlanes[i].getAttribute("name"),"swimlane");
					swimlaneNum ++;
				}
			}
			break
		default:
			//alert(node.tagName);
	}
}
//根据节点名字和类型查找XML节点
function findXmlNodeByName(nodeType,nodeName){
	var nodes = xml.getElementsByTagName(nodeType);
	for(var i=0;i<nodes.length;i++)
		if(nodes[i].getAttribute("name") == nodeName)
			return nodes[i];
}
function deleteXmlNodeByType(nodeType){
	var treeNode = Ext.getDom(currentTreeNode);
	if(treeNode == null)
		return false;
	var node = findXmlNodeByName(nodeType,treeNode.title);
	node.parentNode.removeChild(node);
	Ext.fly(treeNode).remove();
}
//根据节点名字和类型添加节点
function addXmlNodeByName(nodeType,nodeName,parentNode){
	if(parentNode == null)
		parentNode = xml.documentElement;

	var newNode = xml.createElement(nodeType);
	parentNode.appendChild(newNode);
	addXmlAttribute(newNode,"name",nodeName);
	switch(nodeType){
		case "swimlane":
			var groups = "";
			if(!Ext.getCmp("swimlaneUserBox").disabled){
				addXmlAttribute(newNode,"candidate-users",Ext.getCmp("swimlaneUserBox").getValue());
			}
			if(!Ext.getCmp("swimlaneRoleBox").disabled){
				groups += Ext.getCmp("swimlaneRoleBox").getValue()==""?"":Ext.getCmp("swimlaneRoleBox").getValue();
				addXmlAttribute(newNode,"candidate-roles",Ext.getCmp("swimlaneRoleBox").getValue());
			}
			if(groups != ""&&!Ext.getCmp("swimlaneDeptBox").disabled&&Ext.getCmp("swimlaneDeptBox").getValue()!="")
				groups += ",";
			if(!Ext.getCmp("swimlaneDeptBox").disabled){
				groups += Ext.getCmp("swimlaneDeptBox").getValue()==""?"":Ext.getCmp("swimlaneDeptBox").getValue();
				addXmlAttribute(newNode,"candidate-depts",Ext.getCmp("swimlaneDeptBox").getValue());
			}	
			if(groups != "")
				addXmlAttribute(newNode,"candidate-groups",groups);
			break
		default:
	}
}

//------------  属性表单操作   -----------
//重置表单
function formClear(formId){
	Ext.getCmp(formId).getForm().getEl().dom.reset();
}
//为表单赋值
function formFill(type){
	var node = findXmlNodeByName(type,Ext.getDom(currentTreeNode).title);
	switch(type){
		case "swimlane":
			Ext.getCmp("swimlaneDescriptionBox").setValue(node.getAttribute("name"));
			Ext.getCmp("swimlaneUserBox").setValue(node.getAttribute("candidate-users"));
			Ext.getCmp("swimlaneRoleBox").setValue(node.getAttribute("candidate-roles"));
			Ext.getCmp("swimlaneDeptBox").setValue(node.getAttribute("candidate-depts"));
			if(node.getAttribute("candidate-users")!=""&&node.getAttribute("candidate-users")!=null){
				/*
				 *为什么需要两次为checked赋值？
				 *elements的checked会改变checkbox的checked值，才能触发check事件，但是不会改变勾选的显示
				 *dom的checked不会改变checkbox的checked值，但是会改变勾选的显示
				 *所以需要两个同时作用
				*/
				Ext.getDom("swimlaneUserCheck").checked = true;
				Ext.getCmp("swimlaneUserCheck").checked = true;
				Ext.getCmp("swimlaneUserBox").enable();
			}else{
				Ext.getDom("swimlaneUserCheck").checked = false;
				Ext.getCmp("swimlaneUserCheck").checked = false;
				Ext.getCmp("swimlaneUserBox").disable();
			}
			if(node.getAttribute("candidate-roles")!=""&&node.getAttribute("candidate-roles")!=null){
				Ext.getDom("swimlaneRoleCheck").checked = true;
				Ext.getCmp("swimlaneRoleCheck").checked = true;
				Ext.getCmp("swimlaneRoleBox").enable();
			}else{
				Ext.getDom("swimlaneRoleCheck").checked = false;
				Ext.getCmp("swimlaneRoleCheck").checked = false;
				Ext.getCmp("swimlaneRoleBox").disable();
			}
			if(node.getAttribute("candidate-depts")!=""&&node.getAttribute("candidate-depts")!=null){
				Ext.getDom("swimlaneDeptCheck").checked = true;
				Ext.getCmp("swimlaneDeptCheck").checked = true;
				Ext.getCmp("swimlaneDeptBox").enable();
			}else{
				Ext.getDom("swimlaneDeptCheck").checked = false;
				Ext.getCmp("swimlaneDeptCheck").checked = false;
				Ext.getCmp("swimlaneDeptBox").disable();
			}
			break
		default:
	}
}
//添加树列表
function addTreeList(tree,id,title,type){
	var treeNode = "<div title='" + title + "'"
	+" id='" + id + "' class='" + type + "_btn'>"
	+"<span class='icon_" + type + "'>" + title
	+"</span></div>";
	tree.innerHTML += treeNode;
	//注册事件
	Ext.get(Ext.DomQuery.select("."+type+"_btn")).on("click",function(){
		if(!Ext.fly(this).hasClass(type+"_btn_down")){
			Ext.get(Ext.DomQuery.select("."+type+"_btn_down")).removeClass(type+"_btn_down");
			Ext.fly(this).addClass(type+"_btn_down");
			currentTreeNode = this.id;
			formFill(type);
		}
	});
}

//------------	属性操作   -------------
//根据属性按钮转换card布局面板
function changeCard(){
	var currentCard = Ext.getCmp(currentParam);
	if(currentCard == null){
		switch(currentParam){
			case "sql":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',
					items: [{
						layout: 'column',
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							layout: 'fit',
							width: 180,
							height: 350,
							border: false,
							items: [{
								xtype: 'panel',
								id: 'sqlListPanel',
								tbar: new Ext.Toolbar({
									height: 31,
									border: false,
									items: [
										'->',
										{text: '&nbsp;添加',iconCls: 'picon23',handler: ''},'-',
										{text: '&nbsp;删除',iconCls: 'picon13',handler: ''}
									]
								})
							}]
						},{
							border: false,
							bodyStyle: {background:"#dfe7f4",padding:"0 0 0 10"},
							items: [{
								layout: 'form',
								defaultType: 'textfield',
								labelWidth: 80,
								defaults: {width: '85%'},
								border: false,
								bodyStyle: {background:"#dfe7f4"},
								items: [{
									id: 'sqlDescriptionBox',
									fieldLabel: 'SQL描述'
								},{
									xtype: 'combo',
									fieldLabel: '事件类型',
									id: 'sqlCaseTypeCombo',
									width: 100,
									valueField: 'value',
									emptyText: '请选择',
									displayField: 'text',
									triggerAction: 'all',
									editable: false,
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','流程启动'],
											['2','流程结束']								
										]
									})
								}]	
							},{
								layout: 'column',
								border: false,
								bodyStyle: {background:"#dfe7f4"},
								labelWidth: 80,
								items: [{
									layout: 'form',
									border: false,
									bodyStyle: {background:"#dfe7f4"},
									width: 200,
									items: [{
										xtype: 'combo',
										fieldLabel: '脚本类型',
										id: 'sqlScriptTypeCombo',
										width: 100,
										editable: false,
										valueField: 'value',
										displayField: 'text',
										triggerAction: 'all',
										emptyText: '请选择',
										mode: 'local',
										store: new Ext.data.SimpleStore({
											fields: ['value','text'],
											data:[
												['1','查询'],
												['2','更新'],
												['3','删除']
											]
										})
									}]
								},{
									layout: 'form',	
									border: false,
									width: 300,
									labelWidth: 55,
									bodyStyle: {background:"#dfe7f4"},
									items: [{
										id: 'sqlVarNameBox',
										xtype: 'textfield',
										width: 200,
										fieldLabel: '变量名称'
									}]
								}]					
							},{
								layout: 'form',
								border: false,
								labelWidth: 80,
								bodyStyle: {background:"#dfe7f4"},
								items: [{
									id: 'sqlScriptBox',
									xtype: 'textarea',
									fieldLabel: '脚本',
									width: '85%',
									id: 'script'
								}]			
							},{
								layout: 'fit',
								border: false,
								bodyStyle: {background:"#dfe7f4",padding:"0 0 0 88"},
								items: [{
									xtype: 'panel',
									border: false,
									height: 16,
									bodyStyle: {background:"#dfe7f4"},
									html: '<span title="添加参数" style="margin-top:0;" class="img_btn picon23" />'
								}]
							},{
								layout: 'column',
								border: false,
								bodyStyle: {background:"#dfe7f4",padding:"0 0 0 88"},
								items: [{
									layout: 'form',
									border: false,
									width: 20,
									items: [{
										xtype: 'panel',
										border: false,
										height: 20,
										bodyStyle: {background:"#dfe7f4"},
										html: '<span title="删除参数" style="margin-top:4;" class="img_btn picon13" />'
									}]
								},{
									layout: 'form',
									border: false,
									width: 175,
									bodyStyle: {background:"#dfe7f4"},
									labelWidth: 55,
									defaultType: 'textfield',
									items: [{
										id: 'sqlParamNameBox1',
										fieldLabel: '参数名称',
										width: 100
									}]
								},{
									layout: 'form',
									border: false,
									width: 175,
									bodyStyle: {background:"#dfe7f4"},
									labelWidth: 55,
									defaultType: 'combo',
									items: [{
										fieldLabel: '参数类型',
										id: 'sqlParamTypeCombo1',
										width: 100,
										editable: false,
										valueField: 'value',
										displayField: 'text',
										triggerAction: 'all',
										emptyText: '请选择',
										mode: 'local',
										store: new Ext.data.SimpleStore({
											fields: ['value','text'],
											data:[
												['1','字符串'],
												['2','数字型'],
												['3','浮点型'],
												['4','布尔型']
											]
										})
									}]
								},{
									layout: 'form',
									border: false,
									width: 165,
									bodyStyle: {background:"#dfe7f4"},
									labelWidth: 45,
									defaultType: 'textfield',
									items: [{
										id: 'sqlParamValueBox1',
										fieldLabel: '参数值',
										width: 100
									}]				
								},{
									layout: 'form',
									border: false,
									width: 20,
									items: [{
										xtype: 'panel',
										border: false,
										height: 22,
										bodyStyle: {background:"#dfe7f4"},
										html: '<span title="引用变量" style="margin-top:4;" class="img_btn picon22" />'
									}]			
								}]			
							}]	
						}]
					}]
				});
				break
			case "swimlane":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',									
					items: [{
						layout: 'column',
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							layout: 'fit',
							width: 180,
							height: 350,
							border: false,
							items: [{
								xtype: 'panel',
								id: 'swimlaneListPanel',
								html: '<div class="panel_list" id="swimlaneList"></div>',
								tbar: new Ext.Toolbar({
									height: 31,
									border: false,
									items: [
										'->',
										{text: '&nbsp;添加',iconCls: 'picon23',handler: function(){
											if(Ext.getCmp("swimlaneDescriptionBox").getValue() == ""){
												Ext.MessageBox.alert(tip,'泳道描述不能为空！');
												return false;
											}
											if(findXmlNodeByName("swimlane",Ext.getCmp("swimlaneDescriptionBox").getValue()) != null){
												Ext.MessageBox.alert(tip,'泳道描述不能重复！');
												return false;
											}
											//添加泳道节点
											addXmlNodeByName("swimlane",Ext.getCmp("swimlaneDescriptionBox").getValue());
											//移除事件
											Ext.get(Ext.DomQuery.select(".swimlane_btn")).un("click");
											//添加树列表
											var swimlineId = "S" + swimlaneNum;
											swimlaneNum ++;
											addTreeList(Ext.getDom("swimlaneList"),swimlineId,Ext.getCmp("swimlaneDescriptionBox").getValue(),"swimlane");
											//触发事件
											Ext.getDom(swimlineId).fireEvent('onclick');
									}},'-',
										{text: '&nbsp;删除',iconCls: 'picon13',handler: function(){
											deleteXmlNodeByType("swimlane");		
									}}
									]
								})
							}]
						},{
							border: false,
							bodyStyle: {background:"#dfe7f4",padding:"0 0 0 10"},
							items: [{
								layout: 'form',
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								labelWidth: 80,
								items: [{
									id: 'swimlaneDescriptionBox',
									xtype: 'textfield',
									width: '85%',
									fieldLabel: '泳道描述'
								}]
							},{
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								layout: 'column',	
								items: [{
									width: 170,
									labelWidth: 80,
									border: false,
									layout: 'form',
									defaults: {height: 22},
									bodyStyle: {background:"#dfe7f4"},
									defaultType: 'checkbox',
									items: [{
										id: 'swimlaneSponsorCheck',
										fieldLabel: '参与者',
										boxLabel: '流程发起者',
										listeners: {
											"check": function(){
												Ext.getCmp("swimlaneSponsorCombo").disabled?Ext.getCmp("swimlaneSponsorCombo").enable():Ext.getCmp("swimlaneSponsorCombo").disable();
											}
										}	
									},{
										id: 'swimlaneExecutorCheck',
										boxLabel: '流程执行者',
										listeners: {
											"check": function(){
												Ext.getCmp("swimlaneExecutorCombo").disabled?Ext.getCmp("swimlaneExecutorCombo").enable():Ext.getCmp("swimlaneExecutorCombo").disable();
												Ext.getCmp("swimlaneExecutorTypeCombo").disabled?Ext.getCmp("swimlaneExecutorTypeCombo").enable():Ext.getCmp("swimlaneExecutorTypeCombo").disable();
												if(Ext.getCmp("swimlaneExecutorTypeCombo").getValue() == 2 && !Ext.getCmp("swimlaneExecutorTypeCombo").disabled)
													Ext.getCmp("swimlaneExecutorAreaCombo").enable();
												else
													Ext.getCmp("swimlaneExecutorAreaCombo").disable();
											}
										}	
									},{
										id: 'swimlanePartnerCheck',
										boxLabel: '流程参与者',
										listeners: {
											"check": function(){
												Ext.getCmp("swimlanePartnerCombo").disabled?Ext.getCmp("swimlanePartnerCombo").enable():Ext.getCmp("swimlanePartnerCombo").disable();
												Ext.getCmp("swimlanePartnerTypeCombo").disabled?Ext.getCmp("swimlanePartnerTypeCombo").enable():Ext.getCmp("swimlanePartnerTypeCombo").disable();
												if(Ext.getCmp("swimlanePartnerTypeCombo").getValue() == 2 && !Ext.getCmp("swimlanePartnerTypeCombo").disabled)
													Ext.getCmp("swimlanePartnerAreaCombo").enable();
												else
													Ext.getCmp("swimlanePartnerAreaCombo").disable();
											}
										}	
									},{
										boxLabel: '用户',
										id: 'swimlaneUserCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("swimlaneUserBox").disabled?Ext.getCmp("swimlaneUserBox").enable():Ext.getCmp("swimlaneUserBox").disable();
												var node = findXmlNodeByName("swimlane",Ext.getCmp("swimlaneDescriptionBox").getValue());
												if(node != null){
													if(Ext.getCmp("swimlaneUserBox").disabled){
														node.removeAttribute("candidate-users");
													}else{
														node.setAttribute("candidate-users",Ext.getCmp("swimlaneUserBox").getValue());
													}
												}
											}
										}	
									},{
										boxLabel: '角色',
										id: 'swimlaneRoleCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("swimlaneRoleBox").disabled?Ext.getCmp("swimlaneRoleBox").enable():Ext.getCmp("swimlaneRoleBox").disable();
												var node = findXmlNodeByName("swimlane",Ext.getCmp("swimlaneDescriptionBox").getValue());
												if(node != null){
													if(Ext.getCmp("swimlaneRoleBox").disabled){
														node.removeAttribute("candidate-roles");
														node.setAttribute("candidate-groups",Ext.getCmp("swimlaneDeptBox").disabled?"":Ext.getCmp("swimlaneDeptBox").getValue());
														if(node.getAttribute("candidate-groups") == "")
															node.removeAttribute("candidate-groups");
													}else{
														node.setAttribute("candidate-roles",Ext.getCmp("swimlaneRoleBox").getValue());
														if(node.getAttribute("candidate-groups")!=""&&node.getAttribute("candidate-groups")!=null){
															if(Ext.getCmp("swimlaneRoleBox").getValue() != "")
																node.setAttribute("candidate-groups",node.getAttribute("candidate-groups")+","+Ext.getCmp("swimlaneRoleBox").getValue());
														}else{
															node.setAttribute("candidate-groups",Ext.getCmp("swimlaneRoleBox").getValue());
														}
													}
												}
											}
										}
									},{
										boxLabel: '部门',
										id: 'swimlaneDeptCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("swimlaneDeptBox").disabled?Ext.getCmp("swimlaneDeptBox").enable():Ext.getCmp("swimlaneDeptBox").disable();
												var node = findXmlNodeByName("swimlane",Ext.getCmp("swimlaneDescriptionBox").getValue());
												if(node != null){
													if(Ext.getCmp("swimlaneDeptBox").disabled){
														node.removeAttribute("candidate-depts");
														node.setAttribute("candidate-groups",Ext.getCmp("swimlaneRoleBox").disabled?"":Ext.getCmp("swimlaneRoleBox").getValue());
														if(node.getAttribute("candidate-groups") == "")
															node.removeAttribute("candidate-groups");
													}else{
														node.setAttribute("candidate-depts",Ext.getCmp("swimlaneDeptBox").getValue());
														if(node.getAttribute("candidate-groups")!=""&&node.getAttribute("candidate-groups")!=null){
															if(Ext.getCmp("swimlaneRoleBox").getValue() != "")
																node.setAttribute("candidate-groups",node.getAttribute("candidate-groups")+","+Ext.getCmp("swimlaneDeptBox").getValue());
														}else{
															node.setAttribute("candidate-groups",Ext.getCmp("swimlaneDeptBox").getValue());
														}
													}
												}
											}
										}
									}]
								},{
									layout: 'column',
									border: false,
									bodyStyle: {background:"#dfe7f4"},
									items: [{
										layout: 'form',
										labelWidth: 10,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 130,
										defaultType: 'combo',
										items:[{
											id: 'swimlaneSponsorCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										},{
											id: 'swimlaneExecutorCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										},{
											id: 'swimlanePartnerCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										}]			
									},{
										layout: 'form',
										labelWidth: 33,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 170,
										defaultType: 'combo',
										items:[{
											xtype: 'label',
											height: 25
										},{
											id: 'swimlaneExecutorTypeCombo',
											fieldLabel: '类型',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','整个流程执行者'],
													['2','某个节点执行者']								
												]
											}),
											scope: this,
											listeners: {
												"select": function(){
													if(this.getValue() == 1){
														Ext.getCmp("swimlaneExecutorAreaCombo").disable();
													}else{
														//获取所有XML任务节点
														var tasks = xml.getElementsByTagName("task");
														var arrays = [];
														for(var i=0;i<tasks.length;i++){
															arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
														}
														var store = new Ext.data.SimpleStore({
															fields: ['value','text'],
															data: arrays
														});
														Ext.getCmp("swimlaneExecutorAreaCombo").store = store;
														Ext.getCmp("swimlaneExecutorAreaCombo").enable();
													}
												},
												"beforerender": function(){
													this.setValue(1);								
												}
											}
										},{
											id: 'swimlanePartnerTypeCombo',
											fieldLabel: '类型',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','整个流程参与者'],
													['2','某个节点参与者']								
												]
											}),
											scope: this,
											listeners: {
												"select": function(){
													if(this.getValue() == 1){
														Ext.getCmp("swimlanePartnerAreaCombo").disable();
													}else{
														//获取所有XML任务节点
														var tasks = xml.getElementsByTagName("task");
														var arrays = [];
														for(var i=0;i<tasks.length;i++){
															arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
														}
														var store = new Ext.data.SimpleStore({
															fields: ['value','text'],
															data: arrays
														});
														Ext.getCmp("swimlanePartnerAreaCombo").store = store;
														Ext.getCmp("swimlanePartnerAreaCombo").enable();
													}
												},
												"beforerender": function(){
													this.setValue(1);								
												}
											}	
										}]
									},{
										layout: 'form',
										labelWidth: 33,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 170,
										defaultType: 'combo',
										items:[{
											xtype: 'label',
											height: 25
										},{
											id: 'swimlaneExecutorAreaCombo',
											fieldLabel: '节点',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local'	
										},{
											id: 'swimlanePartnerAreaCombo',
											fieldLabel: '节点',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local'	
										}]				
									}]
								},{
									layout: 'form',
									labelWidth: 10,
									bodyStyle: {background:"#dfe7f4"},
									border: false,
									defaults: {width: '83%'},
									items: [{
										xtype: 'textfield',
										id: 'swimlaneUserBox',
										disabled: true
									},{
										xtype: 'textfield',
										id: 'swimlaneRoleBox',
										disabled: true
									},{
										xtype: 'textfield',
										id: 'swimlaneDeptBox',
										disabled: true
									}]
								}]							
							}]
						}]
					}]
				});		
				break
			case "notice":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',									
					items: [{
						layout: 'column',
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							layout: 'fit',
							width: 180,
							height: 350,
							border: false,
							items: [{
								xtype: 'panel',
								id: 'noticeListPanel',
								tbar: new Ext.Toolbar({
									height: 31,
									border: false,
									items: [
										'->',
										{text: '&nbsp;添加',iconCls: 'picon23',handler: ''},'-',
										{text: '&nbsp;删除',iconCls: 'picon13',handler: ''}
									]
								})
							}]
						},{
							border: false,
							bodyStyle: {background:"#dfe7f4",padding:"0 0 0 10"},
							items: [{
								layout: 'form',
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								labelWidth: 80,
								items: [{
									id: 'noticeDescriptionBox',
									xtype: 'textfield',
									width: '85%',
									fieldLabel: '消息描述'
								},{
									xtype: 'combo',
									fieldLabel: '事件类型',
									id: 'noticeCaseTypeCombo',
									width: 100,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','流程启动'],
											['2','流程结束']								
										]
									})
								}]
							},{
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								layout: 'column',	
								items: [{
									width: 170,
									labelWidth: 80,
									border: false,
									layout: 'form',
									defaults: {height: 22},
									bodyStyle: {background:"#dfe7f4"},
									defaultType: 'checkbox',
									items: [{
										id: 'noticeSponsorCheck',
										fieldLabel: '接收者',
										boxLabel: '流程发起者',
										listeners: {
											"check": function(){
												Ext.getCmp("noticeSponsorCombo").disabled?Ext.getCmp("noticeSponsorCombo").enable():Ext.getCmp("noticeSponsorCombo").disable();
											}
										}	
									},{
										id: 'noticeExecutorCheck',
										boxLabel: '流程执行者',
										listeners: {
											"check": function(){
												Ext.getCmp("noticeExecutorCombo").disabled?Ext.getCmp("noticeExecutorCombo").enable():Ext.getCmp("noticeExecutorCombo").disable();
												Ext.getCmp("noticeExecutorTypeCombo").disabled?Ext.getCmp("noticeExecutorTypeCombo").enable():Ext.getCmp("noticeExecutorTypeCombo").disable();
												if(Ext.getCmp("noticeExecutorTypeCombo").getValue() == 2 && !Ext.getCmp("noticeExecutorTypeCombo").disabled)
													Ext.getCmp("noticeExecutorAreaCombo").enable();
												else
													Ext.getCmp("noticeExecutorAreaCombo").disable();
											}
										}	
									},{
										id: 'noticePartnerCheck',
										boxLabel: '流程参与者',
										listeners: {
											"check": function(){
												Ext.getCmp("noticePartnerCombo").disabled?Ext.getCmp("noticePartnerCombo").enable():Ext.getCmp("noticePartnerCombo").disable();
												Ext.getCmp("noticePartnerTypeCombo").disabled?Ext.getCmp("noticePartnerTypeCombo").enable():Ext.getCmp("noticePartnerTypeCombo").disable();
												if(Ext.getCmp("noticePartnerTypeCombo").getValue() == 2 && !Ext.getCmp("noticePartnerTypeCombo").disabled)
													Ext.getCmp("noticePartnerAreaCombo").enable();
												else
													Ext.getCmp("noticePartnerAreaCombo").disable();
											}
										}	
									},{
										boxLabel: '用户',
										id: 'noticeUserCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("noticeUserBox").disabled?Ext.getCmp("noticeUserBox").enable():Ext.getCmp("noticeUserBox").disable();
											}
										}	
									},{
										boxLabel: '角色',
										id: 'noticeRoleCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("noticeRoleBox").disabled?Ext.getCmp("noticeRoleBox").enable():Ext.getCmp("noticeRoleBox").disable();
											}
										}
									},{
										boxLabel: '部门',
										id: 'noticeDeptCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("noticeDeptBox").disabled?Ext.getCmp("noticeDeptBox").enable():Ext.getCmp("noticeDeptBox").disable();
											}
										}
									}]
								},{
									layout: 'column',
									border: false,
									bodyStyle: {background:"#dfe7f4"},
									items: [{
										layout: 'form',
										labelWidth: 10,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 130,
										defaultType: 'combo',
										items:[{
											id: 'noticeSponsorCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										},{
											id: 'noticeExecutorCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										},{
											id: 'noticePartnerCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										}]			
									},{
										layout: 'form',
										labelWidth: 33,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 170,
										defaultType: 'combo',
										items:[{
											xtype: 'label',
											height: 25
										},{
											id: 'noticeExecutorTypeCombo',
											fieldLabel: '类型',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','整个流程执行者'],
													['2','某个节点执行者']								
												]
											}),
											scope: this,
											listeners: {
												"select": function(){
													if(this.getValue() == 1){
														Ext.getCmp("noticeExecutorAreaCombo").disable();
													}else{
														//获取所有XML任务节点
														var tasks = xml.getElementsByTagName("task");
														var arrays = [];
														for(var i=0;i<tasks.length;i++){
															arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
														}
														var store = new Ext.data.SimpleStore({
															fields: ['value','text'],
															data: arrays
														});
														Ext.getCmp("noticeExecutorAreaCombo").store = store;
														Ext.getCmp("noticeExecutorAreaCombo").enable();
													}
												},
												"beforerender": function(){
													this.setValue(1);								
												}
											}
										},{
											id: 'noticePartnerTypeCombo',
											fieldLabel: '类型',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','整个流程参与者'],
													['2','某个节点参与者']								
												]
											}),
											scope: this,
											listeners: {
												"select": function(){
													if(this.getValue() == 1){
														Ext.getCmp("noticePartnerAreaCombo").disable();
													}else{
														//获取所有XML任务节点
														var tasks = xml.getElementsByTagName("task");
														var arrays = [];
														for(var i=0;i<tasks.length;i++){
															arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
														}
														var store = new Ext.data.SimpleStore({
															fields: ['value','text'],
															data: arrays
														});
														Ext.getCmp("noticePartnerAreaCombo").store = store;
														Ext.getCmp("noticePartnerAreaCombo").enable();
													}
												},
												"beforerender": function(){
													this.setValue(1);								
												}
											}	
										}]
									},{
										layout: 'form',
										labelWidth: 33,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 170,
										defaultType: 'combo',
										items:[{
											xtype: 'label',
											height: 25
										},{
											id: 'noticeExecutorAreaCombo',
											fieldLabel: '节点',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local'	
										},{
											id: 'noticePartnerAreaCombo',
											fieldLabel: '节点',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local'	
										}]				
									}]
								},{
									layout: 'form',
									labelWidth: 10,
									bodyStyle: {background:"#dfe7f4"},
									border: false,
									defaults: {width: '83%'},
									items: [{
										xtype: 'textfield',
										id: 'noticeUserBox',
										disabled: true
									},{
										xtype: 'textfield',
										id: 'noticeRoleBox',
										disabled: true
									},{
										xtype: 'textfield',
										id: 'noticeDeptBox',
										disabled: true
									}]
								}]							
							},{
								layout: 'form',
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								labelWidth: 80,
								items: [{
									xtype: 'textarea',
									fieldLabel: '消息内容',
									id: 'noticeContentBox',
									width: '85%'
								}]
							},{
								layout: 'fit',
								border: false,
								bodyStyle: {background:"#dfe7f4",padding:"0 0 0 88"},
								items: [{
									xtype: 'panel',
									border: false,
									height: 16,
									bodyStyle: {background:"#dfe7f4"},
									html: '<span title="插入变量" style="margin-top:0;" class="img_btn picon22" />'
								}]		
							}]
						}]
					}]
				});		
				break
			case "mailto":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',
					items: [{
						layout: 'column',
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							layout: 'fit',
							width: 180,
							height: 350,
							border: false,
							items: [{
								xtype: 'panel',
								id: 'mailtoListPanel',
								tbar: new Ext.Toolbar({
									height: 31,
									border: false,
									items: [
										'->',
										{text: '&nbsp;添加',iconCls: 'picon23',handler: ''},'-',
										{text: '&nbsp;删除',iconCls: 'picon13',handler: ''}
									]
								})
							}]
						},{
							border: false,
							bodyStyle: {background:"#dfe7f4",padding:"0 0 0 10"},
							items: [{
								layout: 'form',
								defaultType: 'checkbox',
								labelWidth: 80,
								defaults: {width: '85%'},
								border: false,
								bodyStyle: {background:"#dfe7f4"},
								items: [{
									id: 'mailtoDescriptionBox',
									xtype: 'textfield',
									fieldLabel: '邮件描述'
								},{
									xtype: 'combo',
									fieldLabel: '事件类型',
									id: 'mailtoCaseTypeBox',
									width: 100,
									valueField: 'value',
									displayField: 'text',
									editable: false,
									emptyText: '请选择',
									triggerAction: 'all',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','流程启动'],
											['2','流程结束']								
										]
									})
								}]
							},{
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								layout: 'column',	
								items: [{
									width: 170,
									labelWidth: 80,
									border: false,
									layout: 'form',
									defaults: {height: 22},
									bodyStyle: {background:"#dfe7f4"},
									defaultType: 'checkbox',
									items: [{
										id: 'mailtoSponsorCheck',
										fieldLabel: '接收者',
										boxLabel: '流程发起者',
										listeners: {
											"check": function(){
												Ext.getCmp("mailtoSponsorCombo").disabled?Ext.getCmp("mailtoSponsorCombo").enable():Ext.getCmp("mailtoSponsorCombo").disable();
											}
										}	
									},{
										id: 'mailtoExecutorCheck',
										boxLabel: '流程执行者',
										listeners: {
											"check": function(){
												Ext.getCmp("mailtoExecutorCombo").disabled?Ext.getCmp("mailtoExecutorCombo").enable():Ext.getCmp("mailtoExecutorCombo").disable();
												Ext.getCmp("mailtoExecutorTypeCombo").disabled?Ext.getCmp("mailtoExecutorTypeCombo").enable():Ext.getCmp("mailtoExecutorTypeCombo").disable();
												if(Ext.getCmp("mailtoExecutorTypeCombo").getValue() == 2 && !Ext.getCmp("mailtoExecutorTypeCombo").disabled)
													Ext.getCmp("mailtoExecutorAreaCombo").enable();
												else
													Ext.getCmp("mailtoExecutorAreaCombo").disable();
											}
										}	
									},{
										id: 'mailtoPartnerCheck',
										boxLabel: '流程参与者',
										listeners: {
											"check": function(){
												Ext.getCmp("mailtoPartnerCombo").disabled?Ext.getCmp("mailtoPartnerCombo").enable():Ext.getCmp("mailtoPartnerCombo").disable();
												Ext.getCmp("mailtoPartnerTypeCombo").disabled?Ext.getCmp("mailtoPartnerTypeCombo").enable():Ext.getCmp("mailtoPartnerTypeCombo").disable();
												if(Ext.getCmp("mailtoPartnerTypeCombo").getValue() == 2 && !Ext.getCmp("mailtoPartnerTypeCombo").disabled)
													Ext.getCmp("mailtoPartnerAreaCombo").enable();
												else
													Ext.getCmp("mailtoPartnerAreaCombo").disable();
											}
										}	
									},{
										boxLabel: '用户',
										id: 'mailtoUserCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("mailtoUserBox").disabled?Ext.getCmp("mailtoUserBox").enable():Ext.getCmp("mailtoUserBox").disable();
											}
										}	
									},{
										boxLabel: '角色',
										id: 'mailtoRoleCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("mailtoRoleBox").disabled?Ext.getCmp("mailtoRoleBox").enable():Ext.getCmp("mailtoRoleBox").disable();
											}
										}
									},{
										boxLabel: '部门',
										id: 'mailtoDeptCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("mailtoDeptBox").disabled?Ext.getCmp("mailtoDeptBox").enable():Ext.getCmp("mailtoDeptBox").disable();
											}
										}
									},{
										boxLabel: '邮箱地址',
										id: 'mailtoAddressCheck',
										listeners: {
											"check": function(){
												Ext.getCmp("mailtoAddressBox").disabled?Ext.getCmp("mailtoAddressBox").enable():Ext.getCmp("mailtoAddressBox").disable();
											}
										}
									}]
								},{
									layout: 'column',
									border: false,
									bodyStyle: {background:"#dfe7f4"},
									items: [{
										layout: 'form',
										labelWidth: 10,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 130,
										defaultType: 'combo',
										items:[{
											id: 'mailtoSponsorCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										},{
											id: 'mailtoExecutorCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										},{
											id: 'mailtoPartnerCombo',
											width: 100,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','包含'],
													['2','除外']								
												]
											}),
											scope: this,
											listeners: {
												"beforerender": function(){
													this.setValue(1);
												}
											}
										}]			
									},{
										layout: 'form',
										labelWidth: 33,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 170,
										defaultType: 'combo',
										items:[{
											xtype: 'label',
											height: 25
										},{
											id: 'mailtoExecutorTypeCombo',
											fieldLabel: '类型',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','整个流程执行者'],
													['2','某个节点执行者']								
												]
											}),
											scope: this,
											listeners: {
												"select": function(){
													if(this.getValue() == 1){
														Ext.getCmp("mailtoExecutorAreaCombo").disable();
													}else{
														//获取所有XML任务节点
														var tasks = xml.getElementsByTagName("task");
														var arrays = [];
														for(var i=0;i<tasks.length;i++){
															arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
														}
														var store = new Ext.data.SimpleStore({
															fields: ['value','text'],
															data: arrays
														});
														Ext.getCmp("mailtoExecutorAreaCombo").store = store;
														Ext.getCmp("mailtoExecutorAreaCombo").enable();
													}
												},
												"beforerender": function(){
													this.setValue(1);								
												}
											}
										},{
											id: 'mailtoPartnerTypeCombo',
											fieldLabel: '类型',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local',
											store: new Ext.data.SimpleStore({
												fields: ['value','text'],
												data:[
													['1','整个流程参与者'],
													['2','某个节点参与者']								
												]
											}),
											scope: this,
											listeners: {
												"select": function(){
													if(this.getValue() == 1){
														Ext.getCmp("mailtoPartnerAreaCombo").disable();
													}else{
														//获取所有XML任务节点
														var tasks = xml.getElementsByTagName("task");
														var arrays = [];
														for(var i=0;i<tasks.length;i++){
															arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
														}
														var store = new Ext.data.SimpleStore({
															fields: ['value','text'],
															data: arrays
														});
														Ext.getCmp("mailtoPartnerAreaCombo").store = store;
														Ext.getCmp("mailtoPartnerAreaCombo").enable();
													}
												},
												"beforerender": function(){
													this.setValue(1);								
												}
											}	
										}]
									},{
										layout: 'form',
										labelWidth: 33,
										bodyStyle: {background:"#dfe7f4"},
										border: false,
										width: 170,
										defaultType: 'combo',
										items:[{
											xtype: 'label',
											height: 25
										},{
											id: 'mailtoExecutorAreaCombo',
											fieldLabel: '节点',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local'	
										},{
											id: 'mailtoPartnerAreaCombo',
											fieldLabel: '节点',
											width: 120,
											disabled: true,
											editable: false,
											valueField: 'value',
											displayField: 'text',
											triggerAction: 'all',
											emptyText: '请选择',
											mode: 'local'	
										}]				
									}]
								},{
									layout: 'form',
									labelWidth: 10,
									bodyStyle: {background:"#dfe7f4"},
									border: false,
									defaults: {width: '83%'},
									items: [{
										xtype: 'textfield',
										id: 'mailtoUserBox',
										disabled: true
									},{
										xtype: 'textfield',
										id: 'mailtoRoleBox',
										disabled: true
									},{
										xtype: 'textfield',
										id: 'mailtoDeptBox',
										disabled: true
									},{
										xtype: 'textfield',
										id: 'mailtoAddressBox',
										disabled: true
									}]
								}]							
							},{
								layout: 'column',
								bodyStyle: {background:"#dfe7f4"},
								border: false,	
								items: [{
									layout: 'form',
									border: false,
									bodyStyle: {background:"#dfe7f4"},
									labelWidth: 80,
									width: 500,
									items: [{
										xtype: 'textfield',
										fieldLabel: '邮件标题',
										id: 'mailtoTitleBox',
										width: '400'
									}]
								},{
									layout: 'fit',
									border: false,
									bodyStyle: {background:"#dfe7f4"},
									items: [{
										xtype: 'panel',
										border: false,
										height: 20,
										bodyStyle: {background:"#dfe7f4"},
										html: '<span title="插入变量" class="img_btn picon22" />'
									}]	
								}]
							},{
								layout: 'column',
								bodyStyle: {background:"#dfe7f4"},
								border: false,	
								items: [{
									layout: 'form',
									bodyStyle: {background:"#dfe7f4"},
									border: false,
									labelWidth: 80,
									width: 600,
									items: [{
										xtype: 'textarea',
										fieldLabel: '邮件内容',
										id: 'mailtoContentBox',
										width: '500'
									}]
								},{
									layout: 'fit',
									border: false,
									bodyStyle: {background:"#dfe7f4"},
									items: [{
										xtype: 'panel',
										border: false,
										height: 20,
										bodyStyle: {background:"#dfe7f4"},
										html: '<span title="插入变量" class="img_btn picon22" />'
									}]	
								}]
							}]
						}]
					}]
				});		
				break
			case "change":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',
					labelWidth: 80,
					defaultType: 'radio',
					items:[{
						fieldLabel: '转换模式',
						boxLabel: '手动流转',
						name: 'changeMethodRadio',
						value: '1',
						checked: true,
						width: 'auto'					
					},{
						boxLabel: '自动流转',
						name: 'changeMethodRadio',
						value: '2',
						width: 'auto'
					}]
				});		
				break
			case "delegate":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',							
					items:[{
						bodyStyle: {background:"#dfe7f4"},
						border: false,
						layout: 'column',	
						items: [{
							width: 170,
							labelWidth: 80,
							border: false,
							layout: 'form',
							defaults: {height: 22},
							bodyStyle: {background:"#dfe7f4"},
							defaultType: 'checkbox',
							items: [{
								id: 'delegateSponsorCheck',
								fieldLabel: '任务委派',
								boxLabel: '流程发起者',
								listeners: {
									"check": function(){
										Ext.getCmp("delegateSponsorCombo").disabled?Ext.getCmp("delegateSponsorCombo").enable():Ext.getCmp("delegateSponsorCombo").disable();
									}
								}	
							},{
								id: 'delegateExecutorCheck',
								boxLabel: '流程执行者',
								listeners: {
									"check": function(){
										Ext.getCmp("delegateExecutorCombo").disabled?Ext.getCmp("delegateExecutorCombo").enable():Ext.getCmp("delegateExecutorCombo").disable();
										Ext.getCmp("delegateExecutorTypeCombo").disabled?Ext.getCmp("delegateExecutorTypeCombo").enable():Ext.getCmp("delegateExecutorTypeCombo").disable();
										if(Ext.getCmp("delegateExecutorTypeCombo").getValue() == 2 && !Ext.getCmp("delegateExecutorTypeCombo").disabled)
											Ext.getCmp("delegateExecutorAreaCombo").enable();
										else
											Ext.getCmp("delegateExecutorAreaCombo").disable();
									}
								}	
							},{
								id: 'delegatePartnerCheck',
								boxLabel: '流程参与者',
								listeners: {
									"check": function(){
										Ext.getCmp("delegatePartnerCombo").disabled?Ext.getCmp("delegatePartnerCombo").enable():Ext.getCmp("delegatePartnerCombo").disable();
										Ext.getCmp("delegatePartnerTypeCombo").disabled?Ext.getCmp("delegatePartnerTypeCombo").enable():Ext.getCmp("delegatePartnerTypeCombo").disable();
										if(Ext.getCmp("delegatePartnerTypeCombo").getValue() == 2 && !Ext.getCmp("delegatePartnerTypeCombo").disabled)
											Ext.getCmp("delegatePartnerAreaCombo").enable();
										else
											Ext.getCmp("delegatePartnerAreaCombo").disable();
									}
								}	
							},{
								boxLabel: '用户',
								id: 'delegateUserCheck',
								listeners: {
									"check": function(){
										Ext.getCmp("delegateUserBox").disabled?Ext.getCmp("delegateUserBox").enable():Ext.getCmp("delegateUserBox").disable();
										if(xmlNode != null){
											if(Ext.getCmp("delegateUserBox").disabled){
												xmlNode.removeAttribute("candidate-users");
											}else{
												xmlNode.setAttribute("candidate-users",Ext.getCmp("delegateUserBox").getValue());
											}
										}
									}
								}	
							},{
								boxLabel: '角色',
								id: 'delegateRoleCheck',
								listeners: {
									"check": function(){
										Ext.getCmp("delegateRoleBox").disabled?Ext.getCmp("delegateRoleBox").enable():Ext.getCmp("delegateRoleBox").disable();
										if(xmlNode != null){
											if(Ext.getCmp("delegateRoleBox").disabled){
												xmlNode.removeAttribute("candidate-roles");
												xmlNode.setAttribute("candidate-groups",Ext.getCmp("delegateDeptBox").disabled?"":Ext.getCmp("delegateDeptBox").getValue());
												if(xmlNode.getAttribute("candidate-groups") == "")
													xmlNode.removeAttribute("candidate-groups");
											}else{
												xmlNode.setAttribute("candidate-roles",Ext.getCmp("delegateRoleBox").getValue());
												if(xmlNode.getAttribute("candidate-groups")!=""&&xmlNode.getAttribute("candidate-groups")!=null){
													if(Ext.getCmp("delegateRoleBox").getValue() != "")
														xmlNode.setAttribute("candidate-groups",xmlNode.getAttribute("candidate-groups")+","+Ext.getCmp("delegateRoleBox").getValue());
												}else{
													xmlNode.setAttribute("candidate-groups",Ext.getCmp("delegateRoleBox").getValue());
												}
											}
										}
									}
								}
							},{
								boxLabel: '部门',
								id: 'delegateDeptCheck',
								listeners: {
									"check": function(){
										Ext.getCmp("delegateDeptBox").disabled?Ext.getCmp("delegateDeptBox").enable():Ext.getCmp("delegateDeptBox").disable();
										if(xmlNode != null){
											if(Ext.getCmp("delegateDeptBox").disabled){
												xmlNode.removeAttribute("candidate-depts");
												xmlNode.setAttribute("candidate-groups",Ext.getCmp("delegateRoleBox").disabled?"":Ext.getCmp("delegateRoleBox").getValue());
												if(xmlNode.getAttribute("candidate-groups") == "")
													xmlNode.removeAttribute("candidate-groups");
											}else{
												xmlNode.setAttribute("candidate-depts",Ext.getCmp("delegateDeptBox").getValue());
												if(xmlNode.getAttribute("candidate-groups")!=""&&xmlNode.getAttribute("candidate-groups")!=null){
													if(Ext.getCmp("delegateDeptBox").getValue() != "")
														xmlNode.setAttribute("candidate-groups",xmlNode.getAttribute("candidate-groups")+","+Ext.getCmp("delegateDeptBox").getValue());
												}else{
													xmlNode.setAttribute("candidate-groups",Ext.getCmp("delegateDeptBox").getValue());
												}
											}
										}
									}
								}
							},{
								boxLabel: '泳道',
								id: 'delegateSwimlaneCheck',
								listeners: {
									"check": function(){
										Ext.getCmp("delegateSwimlaneCombo").disabled?Ext.getCmp("delegateSwimlaneCombo").enable():Ext.getCmp("delegateSwimlaneCombo").disable();
										if(this.checked){
											xmlNode.setAttribute("swimlane",Ext.getCmp("delegateSwimlaneCombo").getValue());
											if(Ext.getCmp("delegateSwimlaneCombo").getValue() != "")
												presrc.lastChild.firstChild.innerHTML = "TaskNode<span title='"+Ext.getCmp("delegateSwimlaneCombo").getValue()+"' class='sign'>泳</span>";
										}else{
											xmlNode.removeAttribute("swimlane");
											presrc.lastChild.firstChild.innerHTML = "TaskNode";
										}
									}
								}
							}]
						},{
							layout: 'column',
							border: false,
							bodyStyle: {background:"#dfe7f4"},
							items: [{
								layout: 'form',
								labelWidth: 10,
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								width: 130,
								defaultType: 'combo',
								items:[{
									id: 'delegateSponsorCombo',
									width: 100,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','包含'],
											['2','除外']								
										]
									}),
									scope: this,
									listeners: {
										"beforerender": function(){
											this.setValue(1);
										}
									}
								},{
									id: 'delegateExecutorCombo',
									width: 100,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','包含'],
											['2','除外']								
										]
									}),
									scope: this,
									listeners: {
										"beforerender": function(){
											this.setValue(1);
										}
									}
								},{
									id: 'delegatePartnerCombo',
									width: 100,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','包含'],
											['2','除外']								
										]
									}),
									scope: this,
									listeners: {
										"beforerender": function(){
											this.setValue(1);
										}
									}
								}]			
							},{
								layout: 'form',
								labelWidth: 33,
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								width: 170,
								defaultType: 'combo',
								items:[{
									xtype: 'label',
									height: 25
								},{
									id: 'delegateExecutorTypeCombo',
									fieldLabel: '类型',
									width: 120,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','整个流程执行者'],
											['2','某个节点执行者']								
										]
									}),
									scope: this,
									listeners: {
										"select": function(){
											if(this.getValue() == 1){
												Ext.getCmp("delegateExecutorAreaCombo").disable();
											}else{
												//获取所有XML任务节点
												var tasks = xml.getElementsByTagName("task");
												var arrays = [];
												for(var i=0;i<tasks.length;i++){
													arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
												}
												var store = new Ext.data.SimpleStore({
													fields: ['value','text'],
													data: arrays
												});
												Ext.getCmp("delegateExecutorAreaCombo").store = store;
												Ext.getCmp("delegateExecutorAreaCombo").enable();
											}
										},
										"beforerender": function(){
											this.setValue(1);								
										}
									}
								},{
									id: 'delegatePartnerTypeCombo',
									fieldLabel: '类型',
									width: 120,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','整个流程参与者'],
											['2','某个节点参与者']								
										]
									}),
									scope: this,
									listeners: {
										"select": function(){
											if(this.getValue() == 1){
												Ext.getCmp("delegatePartnerAreaCombo").disable();
											}else{
												//获取所有XML任务节点
												var tasks = xml.getElementsByTagName("task");
												var arrays = [];
												for(var i=0;i<tasks.length;i++){
													arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
												}
												var store = new Ext.data.SimpleStore({
													fields: ['value','text'],
													data: arrays
												});
												Ext.getCmp("delegatePartnerAreaCombo").store = store;
												Ext.getCmp("delegatePartnerAreaCombo").enable();
											}
										},
										"beforerender": function(){
											this.setValue(1);								
										}
									}	
								}]
							},{
								layout: 'form',
								labelWidth: 33,
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								width: 170,
								defaultType: 'combo',
								items:[{
									xtype: 'label',
									height: 25
								},{
									id: 'delegateExecutorAreaCombo',
									fieldLabel: '节点',
									width: 120,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local'	
								},{
									id: 'delegatePartnerAreaCombo',
									fieldLabel: '节点',
									width: 120,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local'	
								}]				
							}]
						},{
							layout: 'form',
							labelWidth: 10,
							bodyStyle: {background:"#dfe7f4"},
							border: false,
							defaults: {width: '85%'},
							items: [{
								xtype: 'textfield',
								id: 'delegateUserBox',
								disabled: true
							},{
								xtype: 'textfield',
								id: 'delegateRoleBox',
								disabled: true
							},{
								xtype: 'textfield',
								id: 'delegateDeptBox',
								disabled: true
							},{
								xtype: 'combo',
								id: 'delegateSwimlaneCombo',	
								width: 150,
								disabled: true,
								editable: false,
								valueField: 'value',
								displayField: 'text',
								triggerAction: 'all',
								emptyText: '请选择',
								mode: 'local',
								store: new Ext.data.SimpleStore({
									id: 'delegateSwimlaneStore',
									fields: ['value','text'],
									data: []
								}),
								listeners: {
									"select": function(e){
										xmlNode.setAttribute("swimlane",e.getValue());
										presrc.lastChild.firstChild.innerHTML = "TaskNode<span title='"+e.getValue()+"' class='sign'>泳</span>";
									}
								}
							}]
						}]							
					}]
				});		
				break
			case "autoDelegate":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',	
					items:[{
						layout: 'form',
						defaultType: 'radio',
						labelWidth: 80,
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items:[{
							xtype: 'radio',
							fieldLabel: '运行时委派',
							boxLabel: '是',
							value: '1',
							name: 'autoDeleRunRadion',
							width: 'auto'
						},{
							xtype: 'radio',
							boxLabel: '否',
							value: '2',
							checked: true,
							name: 'autoDeleRunRadion',
							width: 'auto'
						}]							
					},{
						bodyStyle: {background:"#dfe7f4"},
						border: false,
						layout: 'column',	
						items: [{
							width: 170,
							labelWidth: 80,
							border: false,
							layout: 'form',
							defaults: {height: 22},
							bodyStyle: {background:"#dfe7f4"},
							defaultType: 'checkbox',
							items: [{
								id: 'autoDeleSponsorCheck',
								fieldLabel: '范围',
								boxLabel: '流程发起者',
								listeners: {
									"check": function(){
										Ext.getCmp("autoDeleSponsorCombo").disabled?Ext.getCmp("autoDeleSponsorCombo").enable():Ext.getCmp("autoDeleSponsorCombo").disable();
									}
								}	
							},{
								id: 'autoDeleExecutorCheck',
								boxLabel: '流程执行者',
								listeners: {
									"check": function(){
										Ext.getCmp("autoDeleExecutorCombo").disabled?Ext.getCmp("autoDeleExecutorCombo").enable():Ext.getCmp("autoDeleExecutorCombo").disable();
										Ext.getCmp("autoDeleExecutorTypeCombo").disabled?Ext.getCmp("autoDeleExecutorTypeCombo").enable():Ext.getCmp("autoDeleExecutorTypeCombo").disable();
										if(Ext.getCmp("autoDeleExecutorTypeCombo").getValue() == 2 && !Ext.getCmp("autoDeleExecutorTypeCombo").disabled)
											Ext.getCmp("autoDeleExecutorAreaCombo").enable();
										else
											Ext.getCmp("autoDeleExecutorAreaCombo").disable();
									}
								}	
							},{
								id: 'autoDelePartnerCheck',
								boxLabel: '流程参与者',
								listeners: {
									"check": function(){
										Ext.getCmp("autoDelePartnerCombo").disabled?Ext.getCmp("autoDelePartnerCombo").enable():Ext.getCmp("autoDelePartnerCombo").disable();
										Ext.getCmp("autoDelePartnerTypeCombo").disabled?Ext.getCmp("autoDelePartnerTypeCombo").enable():Ext.getCmp("autoDelePartnerTypeCombo").disable();
										if(Ext.getCmp("autoDelePartnerTypeCombo").getValue() == 2 && !Ext.getCmp("autoDelePartnerTypeCombo").disabled)
											Ext.getCmp("autoDelePartnerAreaCombo").enable();
										else
											Ext.getCmp("autoDelePartnerAreaCombo").disable();
									}
								}	
							},{
								boxLabel: '用户',
								id: 'autoDeleUserCheck',
								listeners: {
									"check": function(){
										Ext.getCmp("autoDeleUserBox").disabled?Ext.getCmp("autoDeleUserBox").enable():Ext.getCmp("autoDeleUserBox").disable();
									}
								}	
							},{
								boxLabel: '角色',
								id: 'autoDeleRoleCheck',
								listeners: {
									"check": function(){
										Ext.getCmp("autoDeleRoleBox").disabled?Ext.getCmp("autoDeleRoleBox").enable():Ext.getCmp("autoDeleRoleBox").disable();
									}
								}
							},{
								boxLabel: '部门',
								id: 'autoDeleDeptCheck',
								listeners: {
									"check": function(){
										Ext.getCmp("autoDeleDeptBox").disabled?Ext.getCmp("autoDeleDeptBox").enable():Ext.getCmp("autoDeleDeptBox").disable();
									}
								}
							}]
						},{
							layout: 'column',
							border: false,
							bodyStyle: {background:"#dfe7f4"},
							items: [{
								layout: 'form',
								labelWidth: 10,
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								width: 130,
								defaultType: 'combo',
								items:[{
									id: 'autoDeleSponsorCombo',
									width: 100,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','包含'],
											['2','除外']								
										]
									}),
									scope: this,
									listeners: {
										"beforerender": function(){
											this.setValue(1);
										}
									}
								},{
									id: 'autoDeleExecutorCombo',
									width: 100,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','包含'],
											['2','除外']								
										]
									}),
									scope: this,
									listeners: {
										"beforerender": function(){
											this.setValue(1);
										}
									}
								},{
									id: 'autoDelePartnerCombo',
									width: 100,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','包含'],
											['2','除外']								
										]
									}),
									scope: this,
									listeners: {
										"beforerender": function(){
											this.setValue(1);
										}
									}
								}]			
							},{
								layout: 'form',
								labelWidth: 33,
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								width: 170,
								defaultType: 'combo',
								items:[{
									xtype: 'label',
									height: 25
								},{
									id: 'autoDeleExecutorTypeCombo',
									fieldLabel: '类型',
									width: 120,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','整个流程执行者'],
											['2','某个节点执行者']								
										]
									}),
									scope: this,
									listeners: {
										"select": function(){
											if(this.getValue() == 1){
												Ext.getCmp("autoDeleExecutorAreaCombo").disable();
											}else{
												//获取所有XML任务节点
												var tasks = xml.getElementsByTagName("task");
												var arrays = [];
												for(var i=0;i<tasks.length;i++){
													arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
												}
												var store = new Ext.data.SimpleStore({
													fields: ['value','text'],
													data: arrays
												});
												Ext.getCmp("autoDeleExecutorAreaCombo").store = store;
												Ext.getCmp("autoDeleExecutorAreaCombo").enable();
											}
										},
										"beforerender": function(){
											this.setValue(1);								
										}
									}
								},{
									id: 'autoDelePartnerTypeCombo',
									fieldLabel: '类型',
									width: 120,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local',
									store: new Ext.data.SimpleStore({
										fields: ['value','text'],
										data:[
											['1','整个流程参与者'],
											['2','某个节点参与者']								
										]
									}),
									scope: this,
									listeners: {
										"select": function(){
											if(this.getValue() == 1){
												Ext.getCmp("autoDelePartnerAreaCombo").disable();
											}else{
												//获取所有XML任务节点
												var tasks = xml.getElementsByTagName("task");
												var arrays = [];
												for(var i=0;i<tasks.length;i++){
													arrays.push([tasks[i].getAttribute("name"),tasks[i].getAttribute("name")]);
												}
												var store = new Ext.data.SimpleStore({
													fields: ['value','text'],
													data: arrays
												});
												Ext.getCmp("autoDelePartnerAreaCombo").store = store;
												Ext.getCmp("autoDelePartnerAreaCombo").enable();
											}
										},
										"beforerender": function(){
											this.setValue(1);								
										}
									}	
								}]
							},{
								layout: 'form',
								labelWidth: 33,
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								width: 170,
								defaultType: 'combo',
								items:[{
									xtype: 'label',
									height: 25
								},{
									id: 'autoDeleExecutorAreaCombo',
									fieldLabel: '节点',
									width: 120,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local'	
								},{
									id: 'autoDelePartnerAreaCombo',
									fieldLabel: '节点',
									width: 120,
									disabled: true,
									editable: false,
									valueField: 'value',
									displayField: 'text',
									triggerAction: 'all',
									emptyText: '请选择',
									mode: 'local'	
								}]				
							}]
						},{
							layout: 'form',
							labelWidth: 10,
							bodyStyle: {background:"#dfe7f4"},
							border: false,
							defaults: {width: '85%'},
							items: [{
								xtype: 'textfield',
								id: 'autoDeleUserBox',
								disabled: true
							},{
								xtype: 'textfield',
								id: 'autoDeleRoleBox',
								disabled: true
							},{
								xtype: 'textfield',
								id: 'autoDeleDeptBox',
								disabled: true
							}]
						}]													
					}]
				});		
				break;
			case "method":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',				
					items: [{
						layout: 'column',
						border: false,		
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							width: 210,
							layout: 'form',
							defaultType: 'radio',
							defaults: {height: 22},
							bodyStyle: {background:"#dfe7f4"},
							border: false,
							labelWidth: 80,	
							items: [{
								fieldLabel: '分配策略',
								boxLabel: '分配给所有人',
								value: '1',
								name: 'methodDistributionRadio'
							},{
								boxLabel: '任意分配给一个人',
								value: '2',
								name: 'methodDistributionRadio'
							},{
								fieldLabel: '完成策略',
								boxLabel: '任意完成一个',
								value: '1',
								name: 'methodCompleteRadio'
							},{
								boxLabel: '必须完成所有',
								value: '2',
								name: 'methodCompleteRadio'
							},{
								boxLabel: '按完成个数',
								value: '3',
								name: 'methodCompleteRadio',
								listeners: {
									"check": function(){
										Ext.getCmp("methodNumBox").disabled?Ext.getCmp("methodNumBox").enable():Ext.getCmp("methodNumBox").disable();
									}
								}
							},{
								boxLabel: '按完成百分比',
								value: '4',
								name: 'methodCompleteRadio',
								listeners: {
									"check": function(){
										Ext.getCmp("methodPecentBox").disabled?Ext.getCmp("methodPecentBox").enable():Ext.getCmp("methodPecentBox").disable();
									}
								}
							}]
						},{
							width: 100,
							layout: 'form',
							bodyStyle: {background:"#dfe7f4"},
							border: false,
							defaultType: 'textfield',
							labelWidth: 5,
							items: [{
								xtype: 'label',
								height: 113
							},{
								width: 80,
								id: 'methodNumBox',
								disabled: true
							},{
								width: 80,
								id: 'methodPecentBox',
								disabled: true
							}]
						},{
							width: 60,
							layout: 'form',
							bodyStyle: {background:"#dfe7f4"},
							border: false,
							defaultType: 'label',
							items: [{
								height: 140
							},{
								text: '个',
								height: 30,
								width: 60
							},{
								text: '%',
								height: 30,
								width: 60
							}]
						}]
					}]	
				});		
				break
			case "form":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',
					items: [{
						layout: 'column',
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							layout: 'fit',
							width: 180,
							height: 350,
							border: false,
							items: [{
								xtype: 'panel',
								id: 'formListPanel',
								tbar: new Ext.Toolbar({
									height: 31,
									border: false,
									items: [
										'->',
										{text: '&nbsp;添加',iconCls: 'picon23',handler: ''},'-',
										{text: '&nbsp;删除',iconCls: 'picon13',handler: ''}
									]
								})
							}]
						},{
							border: false,
							bodyStyle: {background:"#dfe7f4",padding:"0 0 0 10"},
							items: [{
								layout: 'fit',
								html: "业务表单",
								bodyStyle: {background:"#dfe7f4"},
								border: false,
								labelWidth: 80		
							}]				
						}]
					}]
				});		
				break
			case "case":
				currentCard = new Ext.form.FormPanel({
					id: currentParam,
					border: false,
					bodyStyle: {background:"#dfe7f4",padding:"10 0 0 20"},
					labelAlign: 'left',
					labelWidth: 80,
					items: [{
						layout: 'column',
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							layout: 'form',
							width: 220,
							border: false,
							bodyStyle: {background:"#dfe7f4"},
							defaultType: 'textfield',
							items: [{
								id: 'caseLeftValueBox1',
								fieldLabel: '判断条件',
								width: 120
							}]
						},{
							layout: 'fit',
							xtype: 'panel',
							border: false,
							height: 20,
							width: 20,
							bodyStyle: {background:"#dfe7f4"},
							html: '<span title="引用变量" class="img_btn picon22" />'
						},{
							layout: 'form',
							width: 135,
							border: false,
							labelWidth: 5,
							bodyStyle: {background:"#dfe7f4"},
							items: [{
								id: 'caseCompareCombo1',
								xtype: 'combo',
								width: 120,
								valueField: 'value',
								editable: false,
								displayField: 'text',
								triggerAction: 'all',
								emptyText: '请选择',
								mode: 'local',
								store: new Ext.data.SimpleStore({
									fields: ['value','text'],
									data:[
										['1','小于'],
										['2','大于'],
										['3','等于'],
										['4','不等于'],
										['5','小于等于'],
										['6','大于等于']
									]
								})
							}]		
						},{
							layout: 'form',
							width: 145,
							border: false,
							labelWidth: 5,
							bodyStyle: {background:"#dfe7f4"},
							items: [{
								id: 'caseRightValueBox1',
								xtype: 'textfield',
								width: 120
							}]				
						},{
							layout: 'fit',
							xtype: 'panel',
							border: false,
							height: 20,
							width: 20,
							bodyStyle: {background:"#dfe7f4"},
							html: '<span title="引用变量" class="img_btn picon22" />'			
						}]
					},{
						layout: 'column',
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							layout: 'form',
							border: false,
							width: 220,
							bodyStyle: {background:"#dfe7f4"},
							items: [{
								id: 'caseRelationCombo',
								xtype: 'combo',
								width: 120,
								editable: false,
								valueField: 'value',
								displayField: 'text',
								triggerAction: 'all',
								emptyText: '请选择',
								mode: 'local',
								store: new Ext.data.SimpleStore({
									fields: ['value','text'],
									data:[
										['1','并且(and)'],
										['2','或者(or)']
									]
								})
							}]
						},{
							layout: 'fit',
							xtype: 'panel',
							border: false,
							height: 20,
							width: 20,
							bodyStyle: {background:"#dfe7f4"},
							html: '<span title="添加" class="img_btn picon23" />'			
						}]
					},{
						layout: 'form',
						border: false,
						bodyStyle: {background:"#dfe7f4"},
						items: [{
							id: 'caseExpressionBox',
							xtype: 'textarea',
							fieldLabel: '表达式',
							width: '85%'
						}]
					}]
				});		
				break
		}
		Ext.getCmp('paramCard').add(currentCard);
	}
	Ext.getCmp('paramCard').layout.setActiveItem(currentParam);
}