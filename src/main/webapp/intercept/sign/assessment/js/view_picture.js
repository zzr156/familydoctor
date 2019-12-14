/**
 * 初始化页面完成后立即执行
 */
$(function () {
    // showOption();
    initFunction();
    dataChange(window.parent.dataStorage());
});

/**
 * 功能初始化
 */
function initFunction() {
    var operate = getQueryString("operate");
    if (operate == "review") {
        $("#del").hide();// 审核人员不能删除图片
    }
}

/**
 * 将附件显示到页面中
 */
function dataChange(data) {
    if (data != "") {
        var jArray = JSON.parse(data);
        $.each(jArray, function (k, v) {
            if (v.indexOf('interface') != -1) {// 如果有包含工程名则不予处理，否则加上工程名interface
                // 不做处理
            } else {
                v = 'interface/' + v;
            }
            var type = v.split(".")[1];// 文件类型
            var file = v.split("/");
            var fileName = file[file.length - 1];// 文件名
            if (k == jArray.length - 1) {// 默认显示最后一张图片
                $("#del").attr("index", k);
                if (type == "mp3") {
                    $("#btns").append("<button class='layui-btn layui-btn-danger' index='" + k + "' onclick='showOption(this)' value='" + v + "'>" + (k + 1) + ".音频</button>");
                    $("#options").append("<form action='fileServlet' method='post' style='display: none;'>" +
                        "<input type='hidden' name='fileName' value='" + fileName + "'/>" +
                        "<input type='hidden' name='filePath' value='" + assessViewUrl + v + "'/>" +
                        "</form>");
                    $("#options").append("<button class='layui-btn' filePath='" + assessViewUrl + v + "' fileName='" + fileName + "' onclick='download(this)'>下载</button>");
                } else {
                    $("#btns").append("<button class='layui-btn layui-btn-danger' index='" + k + "' onclick='showOption(this)' value='" + v + "'>" + (k + 1) + ".图片</button>");
                    $("#options").append("<img width='95%' height='95%' src=" + assessViewUrl + v + " />");
                }
            } else {
                if (type == "mp3") {
                    $("#btns").append("<button class='layui-btn' fileName='" + fileName + "' filePath='" + assessViewUrl + v + "' index='" + k + "' onclick='showOption(this)' value='" + v + "'>" + (k + 1) + ".音频</button>");
                } else {
                    $("#btns").append("<button class='layui-btn' index='" + k + "' onclick='showOption(this)' value='" + v + "'>" + (k + 1) + ".图片</button>");
                }
            }
        });
    }
}

/**
 * 查看其中一个附件
 */
function showOption(obj) {
    $("button").removeClass("layui-btn-danger");
    $(obj).addClass("layui-btn-danger");
    $("#options").empty();// 清空
    if (obj.value.split(".")[1] == "mp3") {
        $("#options").append("<form action='fileServlet' method='post' style='display: none;'>" +
            "<input type='hidden' name='fileName' value='" + $(obj).attr("fileName") + "'/>" +
            "<input type='hidden' name='filePath' value='" + $(obj).attr("filePath") + "'/>" +
            "</form>");
        $("#options").append("<button class='layui-btn' filePath='" + $(obj).attr("filePath") + "' fileName='" + $(obj).attr("fileName") + "' onclick='download(this)'>下载</button>");
    } else {
        $("#options").append("<img width='95%' height='95%' src=" + assessViewUrl + obj.value + " />");
    }
    $("#del").attr("index", $(obj).attr("index"));// 删除按钮指定对应的图片索引
}

/**
 * 删除图片
 */
function delOptions(obj) {
    if (getQueryString("month") != "undefined") {
        alertMsg("团队共享的图片不允许删除!");
        return false;
    }
    var vo = {};
    vo.id = getQueryString("id");
    vo.index = $(obj).attr("index");
    vo.month = getQueryString("month");
    doAjaxPost('assessmentAction.action?act=delDetailOption', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.code != "800") {
            alertMsg("删除失败!");
        } else {// 图片消失，按钮消失
            $("#btns").find("button[index='" + data.msg + "']").remove();// 按钮消失
            var btn = $("#btns").find("button:last");
            if (btn.length == 0) {// 图片已经全部删除了
                $("#options").empty();
                $("#del").remove();
            } else {
                btn.click();// 模拟点击，查看图片
            }
            // 清空并重新加载附件列表
            $("#btns").empty();
            $("#options").empty();
            dataChange(data.result);
            window.parent.isDelStorage();// 删除后需要刷新考核表
            alertMsg("删除成功!");
        }
    })
}

/**
 * 文件下载
 */
function download(obj) {
    $(obj).prev("form").submit();
}