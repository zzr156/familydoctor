//$(function() {
//	$('#centerTab').tabs({
//		tools:[{
//			iconCls:'icon-back',
//			handler: function(){
//				$.messager.confirm('注销提示', '你确定注销吗?', function(r){
//					if(r){
//						window.location = root+'/login/doLogout.jhtml';
//					}
//				});
//			}
//		}]
//	});
//});

$.extend($.fn.tabs.methods, {
	/**
	 * 加载iframe内容
	 * @param  {jq Object} jq     [description]
	 * @param  {Object} params    params.which:tab的标题或者index;params.iframe:iframe的相关参数
	 * @return {jq Object}        [description]
	 */
	loadTabIframe:function(jq,params){
		return jq.each(function(){
			var $tab = $(this).tabs('getTab',params.which);
			if($tab==null) return;

			var $tabBody = $tab.panel('body');

			//销毁已有的iframe
			var $frame=$('iframe', $tabBody);
			if($frame.length>0){
				try{//跨域会拒绝访问，这里处理掉该异常
					$frame[0].contentWindow.document.write('');
					$frame[0].contentWindow.close();
				}catch(e){
					//Do nothing
				}
				$frame.remove();
				if($.browser.msie){
					CollectGarbage();
				}
			}
			$tabBody.html('');

			$tabBody.css({'overflow':'hidden','position':'relative'});
			var $mask = $('<div style="position:absolute;z-index:2;width:100%;height:100%;background:#ccc;z-index:1000;opacity:0.3;filter:alpha(opacity=30);"><div>').appendTo($tabBody);
			var $maskMessage = $('<div class="mask-message" style="z-index:3;width:auto;height:16px;line-height:16px;position:absolute;top:50%;left:50%;margin-top:-20px;margin-left:-92px;border:2px solid #d4d4d4;padding: 12px 5px 10px 30px;background: #ffffff url(\'commonjs/jquery-easyui/themes/default/images/loading.gif\') no-repeat scroll 5px center;">' + (params.iframe.message || '正在加载数据，请稍候...  ') + '</div>').appendTo($tabBody);
			var $containterMask = $('<div style="position:absolute;width:100%;height:100%;z-index:1;background:#fff;"></div>').appendTo($tabBody);
			var $containter = $('<div style="position:absolute;width:100%;height:100%;z-index:0;"></div>').appendTo($tabBody);

			var iframe = document.createElement("iframe");
			iframe.src = params.iframe.src;
			iframe.id = params.iframe.id;
			iframe.frameBorder = params.iframe.frameBorder || 0;
			iframe.height = params.iframe.height || '100%';
			iframe.width = params.iframe.width || '100%';
			if (iframe.attachEvent){
				iframe.attachEvent("onload", function(){
					$([$mask[0],$maskMessage[0]]).fadeOut(params.iframe.delay || 'slow',function(){
						$(this).remove();
						if($(this).hasClass('mask-message')){
							$containterMask.fadeOut(params.iframe.delay || 'slow',function(){
								$(this).remove();
							});
						}
					});
				});
			} else {
				iframe.onload = function(){
					$([$mask[0],$maskMessage[0]]).fadeOut(params.iframe.delay || 'slow',function(){
						$(this).remove();
						if($(this).hasClass('mask-message')){
							$containterMask.fadeOut(params.iframe.delay || 'slow',function(){
								$(this).remove();
							});
						}
					});
				};
			}
			$containter[0].appendChild(iframe);
		});
	},
	/**
	 * 增加iframe模式的标签页
	 * @param {[type]} jq     [description]
	 * @param {[type]} params [description]
	 */
	addIframeTab:function(jq,params){
		return jq.each(function(){
			if(params.tab.href){
				delete params.tab.href;
			}
			$(this).tabs('add',params.tab);
			$(this).tabs('loadTabIframe',{'which':params.tab.title,'iframe':params.iframe});
		});
	},
	/**
	 * 更新tab的iframe内容
	 * @param  {jq Object} jq     [description]
	 * @param  {Object} params [description]
	 * @return {jq Object}        [description]
	 */
	updateIframeTab:function(jq,params){
		return jq.each(function(){
			params.iframe = params.iframe || {};
			if(!params.iframe.src){
				var $tab = $(this).tabs('getTab',params.which);
				if($tab==null) return;
				var $tabBody = $tab.panel('body');
				var $iframe = $tabBody.find('iframe');
				if($iframe.length===0) return;
				$.extend(params.iframe,{'src':$iframe.attr('src')});
			}
			$(this).tabs('loadTabIframe',params);
		});
	}
});

/**
 * 创建新选项卡
 * @param tabId    选项卡id
 * @param title    选项卡标题
 * @param url      选项卡远程调用路径
 */
function addTab(tabId,title,url){
	//如果当前id的tab不存在则创建一个tab
	if($("#"+tabId).html()==null){
//		var name = 'iframe_'+tabId;
//		$('#centerTab').tabs('add',{
//			title: title,
//			closable:true,
//			cache : false,
//			//注：使用iframe即可防止同一个页面出现js和css冲突的问题
//			content : '<iframe name="'+name+'"id="'+tabId+'"src="'+url+'" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>'
//		});
		$('#centerTab').tabs('addIframeTab',{
			tab:{title:title,closable:true},
			iframe:{src:url,id:tabId}
		});
	}else{
		$('#centerTab').tabs('select',title);
	}
}

