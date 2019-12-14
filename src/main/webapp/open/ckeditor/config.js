/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
    config.language = 'zh-cn'; //配置语言
    config.uiColor = '#BFEFFF'; //背景颜色
    config.width = '100%'; //宽度
    config.height = '500'; //高度
    config.skin='office2003';
    config.filebrowserBrowseUrl = '/zknews/ckfinder/ckfinder.html'; //不要写成"~/ckfinder/..."或者"/ckfinder/..."
    config.filebrowserImageBrowseUrl = '/zknews/ckfinder/ckfinder.html?type=Images';
    config.filebrowserFlashBrowseUrl = '/zknews/ckfinder/ckfinder.html?type=Flash';
    config.filebrowserUploadUrl = '/zknews/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
    config.filebrowserImageUploadUrl = '/zknews/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
    config.filebrowserFlashUploadUrl = '/zknews/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';
    config.image_previewText="图片预览框！"
    config.toolbar_Full = [
        ['Source','-','Save','NewPage','Preview','-','Templates'],
        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
        ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
        '/',
        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
        ['Link','Unlink','Anchor'],
        ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
        '/',
        ['Styles','Format','Font','FontSize'],
        ['TextColor','BGColor','Maximize', 'ShowBlocks']
    ];
    //工具栏是否可以被收缩
    config.toolbarCanCollapse = true;
    //工具栏的位置
    config.toolbarLocation = 'top';//可选：bottom/top
    //工具栏默认是否展开
    config.toolbarStartupExpanded = true;
    // 背景颜色
    config.uiColor = '#FFF';

};
