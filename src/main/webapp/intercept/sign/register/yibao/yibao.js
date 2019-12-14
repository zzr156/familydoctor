var $YiBao = {
    /**
     * 创建控件的实例，若创建不能成功，则需把当前的IP加入到”可信站点“，
     * 并”对未标记为可安全 执行脚本的ActiveX控件执行脚本“，设为启用
     */
    fso : (function(){
        var obj
        try{
            obj = new ActiveXObject("Scripting.FileSystemObject")
        } catch(e) {
            alert(e.description)
            obj = null;
        }
        return obj;
    })(),
    requestFileName : "request.txt", //请求的文件名
    replyFileName : "reply.txt", //应答的文件名
    sfjkPath : "c:\\", //医院收费接口的交换文件路径
    requesttype :"",//请求的类型.如mzsf
    requesturl:"yibaowait.html?a=1",//请求文件
    /**
     * 生成请求文件的路径
     * @return {}
     */
    requestFilePath : function(){return $YiBao.sfjkPath + $YiBao.requestFileName},
    /**
     * 生成应答文件的路径
     * @return {}
     */
    replyFilePath : function(){return $YiBao.sfjkPath + $YiBao.replyFileName },
    timer : null, //定时器全局标识
    replyInterval : 1000, //检索应答文件的间隔时间，单位为毫秒
    requestOverTime : 180, //请求的超时时间，单位为秒
    requestTime:0,
    /**
     * 请求成功后的回调函数
     * @param {} obj
     */
    reqCallBack : function(obj){layer.msg(obj);},
    /**
     * 请求超时的回调函数
     */
    reqOverCallBack : function(){layer.msg("与医保交互超时")},
    head_reg : /^\[(.+)\]/,
    key_reg : /(\w+)=(.*)/,
    toString : Object.prototype.toString,
    hasOwnProperty : Object.prototype.hasOwnProperty,
    child_card_div:null,
    promptTitle : '与医保接口交换中,请等待...', //提示文本
    isDebug : false, //是否启动调试模式，若启用调试模式，则会生成相应的相应请求文件和应答文件的debug文件。
    /**
     * 判断对象是否为函数
     * @param {} obj
     * @return {}
     */
    isFunction : function( obj ) {
        return $YiBao.toString.call(obj) === "[object Function]";
    },
    /**
     * 判断对象是否为数组
     * @param {} obj
     * @return {}
     */
    isArray : function( obj ) {
        return $YiBao.toString.call(obj) === "[object Array]";
    },
    /**
     * 去除字符串的左右空白
     * @param {} text
     * @return {}
     */
    trim : function( text ) {
        return (text || "").replace( /^\s+|\s+$/g, "" );
    },
    /**
     * 判断对象为简单对象，如用new Object出来的对象。
     * @param {} obj
     * @return {Boolean}
     */
    isPlainObject : function( obj ) {
        if ( !obj || $YiBao.toString.call(obj) !== "[object Object]" || obj.nodeType || obj.setInterval ) {
            return false;
        }

        if ( obj.constructor
            && !$YiBao.hasOwnProperty.call(obj, "constructor")
            && !$YiBao.hasOwnProperty.call(obj.constructor.prototype, "isPrototypeOf") ) {
            return false;
        }
        var key;
        for ( key in obj ) {}
        return key === undefined || $YiBao.hasOwnProperty.call( obj, key );
    },
    /**
     * 获得蒙板的对象
     * @return {}
     */
    getCardDiv : function() {
        var card_div = document.getElementById("card_div");
        if(!card_div) {
            card_div = document.createElement("div");
            card_div.id = 'card_div'
            card_div.className = 'parent_card_div'
            card_div.style.display = 'none'
            var applyElement = function(element, additiveElement, sWhere){
                if(sWhere=="inside"){
                    var children = element.childnodes || [];
                    element.insertBefore(additiveElement, element.firstChild);
                    for(var i=0, l=children.length; i<l; i++)
                        additiveElement.appendChild(children(i))
                }
                else{
                    element.parentNode.insertBefore(additiveElement, element);
                    additiveElement.appendChild(element);
                }
            }
            applyElement(document.body, card_div, 'inside')
            var child_card_div = document.createElement("div");
            child_card_div.className = 'child_card_div';
            child_card_div.id = "card_div_child";
            child_card_div.innerText = $YiBao.promptTitle;
            child_card_div.style.width = "300px";
            this.child_card_div = child_card_div;
            card_div.appendChild(child_card_div);
        }else{
            if (!this.child_card_div){
                this.child_card_div = document.getElementById("card_div_child");
            }
            this.child_card_div.innerText = $YiBao.promptTitle;
        }
        return card_div;
    },
    /**
     * 将obj的数据写入请求文件
     * @param {} fileObj 请求文件的句柄
     * @param {} obj 请求文件的内容, 必须为数组
     * @return {Boolean}
     */
    writeRequest : function (fileObj, obj){
        if(!fileObj) return false;
        var result = '';
        var child_result;
        var child_result2;
        var hiddenKey = false;
        var m = 0;
        var requesttype_flag = false;
        var name = 'mzghsk,mzgh,mzghcx,mzsfsk,mzsf,mzsfmx,mzsfcx,'+'yb0000,qyysxx,jtysgq,'+
            'rydjsk,rydj,rydjcx,zysfsk,zysf,zysfmx,zysfcx,cydjsk,cydj,cydjcx,'+
            'zydbzdj,dbzlsh,dbzbm0,dbzmc0,tcyy00,zydbzcx,'+
            'zybasy,zybasycx,zylsh0,cardno,yybah0,rykb00,rybs00,zkkb00,cykb00,cybs00,ryqk00,ryzdqk,cyzd00,zdmc00,cyqk00,cyzd01,zdmc01,cyqk01,cyzd02,zdmc02,cyqk02,cyzd03,zdmc03,cyqk03,cyzd04,zdmc04,cyqk04,cyzd05,zdmc05,cyqk05,blzd00,zzysxm,ssbh01,ssmc01,ssrq01,mzfs01,ssbh02,ssmc02,ssrq02,mzfs02,ssbh03,ssmc03,ssrq03,mzfs03,ssbh04,ssmc04,ssrq04,mzfs04,ssbh05,ssmc05,ssrq05,mzfs05,ssbh06,ssmc06,ssrq06,mzfs06,ssbh07,ssmc07,ssrq07,mzfs07,ssbh08,ssmc08,ssrq08,mzfs08,'+
            'mzzdxx,mzzd01,mzzd02,zdmc03,zdmc04,zdmc05,mzzd06,zdmc06,mzzd07,zdmc07,mzzd08,zdmc08,mzzd09,zdmc09,mzzd10,zdmc10,mzzz01,zzmc01,mzzz02,zzmc02,mzzz03,zzmc03,mzzz04,zzmc04,mzzz05,zzmc05,mzxxbc,mzbcmx'
        ;
        if($YiBao.isArray(obj)){
            for(var name_index in obj){
                //name_index必须在限定的name范围内
                if(name.indexOf(name_index)<0){
                    continue;
                }
                result += '['+ name_index +']' + '\n';
                //取第一个为请求类型 add by tangfs 20110119
                if (!requesttype_flag){
                    $YiBao.requesttype = name_index;
                    requesttype_flag = true;
                }
                //-----------------------------------------
                fileObj.WriteLine('['+ name_index +']')
                child_result = obj[name_index]
                hiddenKey = child_result["hiddenKey"] || false;
                if(hiddenKey){
                    child_result[0]=child_result["hiddenKey"];
                }
                if($YiBao.isArray(child_result)){
                    for(var i=0; i<child_result.length-1; i++ ){
                        child_result2 = child_result[i]
                        for(var child_index in child_result2){
                            if(hiddenKey) {
                                result += child_result2[child_index] +'\n'
                                fileObj.WriteLine(child_result2[child_index])
                            } else {
                                result += child_index+'=' + child_result2[child_index] +'\n'
                                fileObj.WriteLine(child_index+'=' + child_result2[child_index])
                            }
                        }
                        result += '\n';
                        //fileObj.WriteLine('')
                    }
                } else if($YiBao.isPlainObject(child_result)){
                    var length_child_result=0;
                    var length_child_result2=0;
                    for(var child_index in child_result){
                        length_child_result=length_child_result+1;
                    }
                    for(var child_index in child_result){
                        if(hiddenKey) {
                            length_child_result2=length_child_result2+1;
                            if(length_child_result!=length_child_result2){
                                result += child_result[child_index] +'\n'
                                fileObj.WriteLine(child_result[child_index])
                            }
                        } else {
                            result += child_index+'=' + child_result[child_index] +'\n'
                            fileObj.WriteLine(child_index+'=' + child_result[child_index])
                        }
                    }
                    result += '\n';
                    //fileObj.WriteLine('')
                }
            }
            //layer.msg(result)
            return true;
        } else {
            return false;
        }
    },
    /**
     * 应答文件的读取
     * @param {} fileObj 应答文件的句柄
     * @return {} 返回应答文件的内容，内容为数组
     */
    readReplyText : function (fileObj){
        if(fileObj && !fileObj.AtEndOfStream){
            var resultArr = [];
            var ret;
            var cur_head;
            var next_head;
            var matchs;
            var key_matchs;
            var obj;
            var pre_key;
            while(!fileObj.AtEndOfStream){
                if(!next_head) {
                    ret = $YiBao.trim(fileObj.ReadLine())
                    matchs = $YiBao.head_reg.exec(ret);
                    if(matchs && matchs[1]){
                        cur_head = matchs[1]
                        resultArr[cur_head] = []
                        //layer.msg(cur_head)
                    }
                } else {
                    cur_head = next_head;
                    next_head = null;
                    resultArr[cur_head] = []
                }
                obj = null;
                pre_key = null;
                if(cur_head){
                    while(!fileObj.AtEndOfStream){
                        ret = $YiBao.trim(fileObj.ReadLine())
                        matchs = $YiBao.head_reg.exec(ret);
                        //不是空行，并且不是头信息
                        if(ret && !matchs){
                            key_matchs = $YiBao.key_reg.exec(ret);
                            if(!obj){
                                if(key_matchs && key_matchs[1]) {
                                    obj = new Object();
                                } else {
                                    obj = []
                                }
                            }
                            if(key_matchs && key_matchs[1]){
                                pre_key = key_matchs[1];
                                obj[pre_key] = key_matchs[2]
                            } else {
                                if($YiBao.isArray(obj)) {
                                    obj.push(ret)
                                } else {
                                    if(pre_key)
                                        obj[pre_key] += '\n' + ret;
                                }
                            }
                        } else {
                            if(obj)
                                resultArr[cur_head].push(obj);
                            obj = null;
                            //空行
                            if(matchs && matchs[1]) {
                                obj = null;
                                //fileObj.Line = fileObj.Line - 1
                                next_head = matchs[1]
                                break;
                            }
                        }
                    }
                    if(obj!=null){
                        resultArr[cur_head].push(obj)
                        obj = null;
                    }
                }
            }
            return resultArr;
        } else {
            return null;
        }
    },
    /**
     * 接口讲求的入口，obj为内容，类型为数组
     * @param {} obj
     */
    postToYiBao : function (obj){
        if(!obj || !$YiBao.fso) return;
        var ret
        try{
            var requestText = $YiBao.fso.FileExists($YiBao.requestFilePath());
            var replyText = $YiBao.fso.FileExists($YiBao.replyFilePath());
            if(requestText) {
                $YiBao.fso.DeleteFile($YiBao.requestFilePath(), true)
            }
            if(replyText){
                $YiBao.fso.DeleteFile($YiBao.replyFilePath(), true)
            }
            //创建request文件
            ret = $YiBao.fso.CreateTextFile($YiBao.requestFilePath(), true);
            if(ret){
                if($YiBao.writeRequest(ret, obj)){
                    ret.close();
                    ret = null;
                    if($YiBao.isDebug){
                        $YiBao.fso.CopyFile($YiBao.requestFilePath(), $YiBao.sfjkPath + "debug_" + $YiBao.requestFileName, true)
                    }
                    var card_div = $YiBao.getCardDiv();
                    card_div.style.display = ''
                    $YiBao.requestTime = 0;
                    var url = $YiBao.requesturl + '&type=' + $YiBao.requesttype;
                    var txtStr = $YiBao.requesttype;

                    var returnArr = window.showModalDialog(url,txtStr,'help: no;scroll: no;status: no;dialogWidth: 26;dialogHeight: 7.8');//dialogWidth: 26;dialogHeight: 7.8

                    //$YiBao.timer = setInterval($YiBao.readReply, $YiBao.replyInterval)
                    $YiBao.getCardDiv().style.display = 'none';
                    if(returnArr!=null){
                        if (typeof(returnArr)=="string"){
                            layer.msg(returnArr);
                        }else{
                            if($YiBao.reqCallBack){
                                $YiBao.reqCallBack.call(this, returnArr)
                            }
                        }
                    }else
                    {
                        layer.msg("加载医保交互文件出错！");
                        HideLoading();
                    }
                }
            }
        } catch(e){
            $YiBao.getCardDiv().style.display = 'none'
            layer.msg(e.description)
        } finally {
            if(ret){
                ret.close()
                ret = null;
            }
        }
    },
    /**
     * 检测应答文件的函数
     */
    readReply : function (){
        $YiBao.requestTime += parseInt($YiBao.replyInterval / 1000);
        $YiBao.child_card_div.innerHTML = $YiBao.promptTitle + "<font color=red>" + ($YiBao.requestOverTime - $YiBao.requestTime) + "</font>";
        var replyText;
        try {
            replyText = $YiBao.fso.FileExists($YiBao.replyFilePath());
            if ((!$YiBao.fso.FileExists($YiBao.requestFilePath()))&&(replyText)){
                //modified by tangfs 20110119 ---------------------
                //成功后不删除返回文件，删除请求文件
                try{
                    replyText = $YiBao.fso.OpenTextFile($YiBao.replyFilePath());
                }catch(e){
                    return;
                }
                clearInterval($YiBao.timer);
                //$YiBao.fso.DeleteFile($YiBao.requestFilePath(), true);
                //--------------------------------------------------
                //var card_div = $YiBao.getCardDiv();
                //card_div.style.display = 'none';
                //replyText = $YiBao.fso.OpenTextFile($YiBao.replyFilePath());
                var returnArr = $YiBao.readReplyText(replyText);
                if($YiBao.isDebug){
                    $YiBao.fso.CopyFile($YiBao.replyFilePath(), $YiBao.sfjkPath + "debug_" + $YiBao.replyFileName, true);
                }
                replyText.close();
                replyText = null;

                //add by tangfs 20110119
                if (!returnArr){
                    window.returnValue = "医保返回无数据或解析失败";
                    //layer.msg("医保返回无数据或解析失败");
                }else	if (returnArr[$YiBao.requesttype][0].success.toUpperCase() != "TRUE"){
                    var errmsg = "";
                    errmsg = returnArr[$YiBao.requesttype][0].error;
                    //layer.msg("与医保请求交互失败，原因：" + errmsg);
                    window.returnValue = "与医保请求交互失败，原因：" + errmsg;
                    //layer.msg("医保返回无数据或解析失败");
                }else{
                    //layer.msg(returnArr);
                    window.returnValue = returnArr;
                    //layer.msg("1");
                }
                window.close();
                //----------------------------------------------------------------
                /**
                 if($YiBao.reqCallBack){
					$YiBao.reqCallBack.call(this, returnArr)
				}**/
            } else if($YiBao.requestTime >= $YiBao.requestOverTime){
                //layer.msg("读卡超时！")
                var card_div = $YiBao.getCardDiv();;
                card_div.style.display = 'none'
                clearInterval($YiBao.timer);
                window.returnValue = "与医保通讯超时";
                window.close();
                /**
                 if($YiBao.reqOverCallBack){
					$YiBao.reqOverCallBack.call(this)
				}**/

            }
        } catch(e){
            //$YiBao.getCardDiv().style.display = 'none'
            window.returnValue = e.description;
            //layer.msg(e.description)
            window.close();
            //window.returnValue = e.description;
        } finally {
            if(replyText){
                try{
                    replyText.close()
                    replyText = null;
                }catch(e){
                }
            }
        }
    }
};
