/*** 公共js ***/
var PAGE = 10;
var PAGELIST = [10,50,100];

//不为空
function necs(cs){
	if(cs!=null&&cs!=undefined&&!$.trim(cs)==""){
		return true;
	}else{
		return false;
	}
}

//为空
function iecs(cs){
	if(cs==null||cs==undefined||$.trim(cs)==""){
		return true;
	}else{
		return false;
	}
}

function comTabs(url,code,title) {
		var iframes = "iframe"+code; 
		var ifr = window.parent.document.getElementsByName(iframes);
		if(ifr.length == 0){
			var p = '<a href="javascript:;" class="active J_menuTab" data-id="' + code + '">' + title + ' <i class="fa fa-times-circle"></i></a>';
	        $(".J_menuTab",window.parent.document).removeClass("active");
	        var n = '<iframe class="J_iframe" name="iframe' + code + '" width="100%" height="100%" src="' + url + '" frameborder="0" data-id="' + code + '" seamless></iframe>';
	        $(".J_mainContent",window.parent.document).find("iframe.J_iframe").hide().parents(".J_mainContent").append(n);
	        $(".J_menuTabs .page-tabs-content",window.parent.document).append(p);
		}else{
		   $(".J_menuTab",window.parent.document).each(function() {
	            if ($(this).data("id") == code) {
	                if (!$(this).hasClass("active")) {
	                    $(this).addClass("active").siblings(".J_menuTab").removeClass("active");
	                    $(".J_mainContent .J_iframe",window.parent.document).each(function() {
	                        if ($(this).data("id") == code) {
	                            $(this).show().siblings(".J_iframe").hide();
	                            return false
	                        }
	                    })
	                }
	            }
	        });
		}
		

}

var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}   

function isCardID(sId){
    var iSum=0 ;
    var info="" ;  
    if(!/^\d{17}(\d|x)$/i.test(sId)) return "你输入的身份证长度或格式错误";   
    sId=sId.replace(/x$/i,"a");   
    if(aCity[parseInt(sId.substr(0,2))]==null) return "你的身份证地区非法";   
    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));   
    var d=new Date(sBirthday.replace(/-/g,"/")) ;  
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法";   
    for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;  
    if(iSum%11!=1) return "你输入的身份证号非法";   
    return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女")   
}   

function isCheckPhone(phone){
	 if(!(/^1[34578]\d{9}$/.test(phone))){ 
        return false; 
     }
	 return true;
}