/**
 * Created by cjw on 2018/4/10.
 */
var id = getQueryString("signId");
var index = parent.layer.getFrameIndex(window.name);
// 拒签保存
function signrefuse_add() {
    debugger
    if(necs(id)){
        var reason=$("#reason").val();
        doAjaxPost('signAction.action?act=refuseadd',{id:id,reason:reason},function(data){
            if(data.vo!=null && data.code=="800"){
                parent.refusevo="false";
                parent.layer.close(index);
            }

        });
    }
}
// 返回
function Goto(){
    parent.layer.close(index);
}
