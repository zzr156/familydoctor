<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<input type="hidden" id="loginMenuId" name="loginMenuId" value="${loginMenuId}">
<script type="text/javascript"> 

$(function(){
	findRoleMenuSon();
})

function findRoleMenuSon(){
	var loginMenuId = $("#loginMenuId").val();
    if(loginMenuId == ""){
        loginMenuId = getQueryString("loginMenuId");
    }
	if(necs(loginMenuId)){
		addCSS('.system{display: none}');	
		 doAjaxPost('<%=request.getContextPath()%>/son.action?act=findCmmMenuSonRole',{loginMenuId:loginMenuId},callDataToMenuSonRole);
	}
	
}

function callDataToMenuSonRole(data){
	if(data != null){
	    $.each(data, function(k, v) {
	    	var nature = '.'+v.nature+'{display: inline-block}';
	    	addCSS(nature);	
	    });
	}
}

function addCSS(cssText){
	var nod = document.createElement("style");
	nod.type="text/css";  
	if(nod.styleSheet){         //ie下  
		nod.styleSheet.cssText = cssText;  
	} else {  
		nod.innerHTML = cssText;       //或者写成 nod.appendChild(document.createTextNode(str))  
	}  
	document.getElementsByTagName("head")[0].appendChild(nod);  
}
</script>