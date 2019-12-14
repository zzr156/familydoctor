var d = document,w = window;
var isIE=navigator.userAgent.indexOf("MSIE")!=-1&&!window.opera;
d.write("<style>.sman_selectedStyle{background-Color:#102681;color:#FFFFFF}</style>");
var _style = "display:block;background:#E8F7EB;border: 1px solid #CCCCCC;font-size:14px;cursor: default;";
function G(A){
	return d.getElementById(A)
}
//dwr分页调用方法
//<%--设置按钮状态--%>//..........1
function setBtnStatus(){
	  if(dwr.util.getValue("pageStartNo")==1)
	    G("prevPage").disabled=true;
	  else
	    G("prevPage").disabled=false;

          var k=new Number(dwr.util.getValue("pageStartNo"));
          var z=new Number(dwr.util.getValue("pageCount"));
            if(k<z)
              G("nextPage").disabled=false;
            else
              G("nextPage").disabled=true;

	  if(dwr.util.getValue("pageCount")<2)
		G("shipPageBtn").disabled = true;
	  else
		G("shipPageBtn").disabled = false;
}
//<%--上一页--%>
function prev(s){
  dwr.util.setValue("pageStartNo",parseInt(dwr.util.getValue("pageStartNo"))-1);
  findDataFromDwr();
}
//<%--下一页--%>
function next(x){
  dwr.util.setValue("pageStartNo",parseInt(dwr.util.getValue("pageStartNo"))+1);
  findDataFromDwr();
}
//<%--跳转页--%>
function skip(t){
  dwr.util.setValue("pageStartNo",parseInt(dwr.util.getValue("skipPage")));
  findDataFromDwr();
}

/**
 * findDataFromDwr();
 *
 * <input onClick="prev(this);" id="prevPage" type="button" value="上一页" />
 *<label id="flable"></label>
 *<input onClick="next(this);" id="nextPage" type="button" value="下一页" />
 *<select id="skipPage">
 *</select>
 *<input onClick="skip(this);" id="shipPageBtn" type="button" value="Go" />
 */

