// JavaScript Document
$(document).ready(function(){
    $(".acc_container:not('.acc_container:first')").hide();
    $('.acc_trigger').click(function(){
	if( $(this).next().is(':hidden') ) {
            $('.acc_trigger').removeClass('active').next().slideUp(0);
            $(this).toggleClass('active').next().slideDown(600);
	}else{
            $(this).toggleClass('active');
            $(this).next().slideUp(0);
    }
	return false;
    });
});
$(document).ready(function(){
    $(".sj_containe:not('.sj_containe:first')").hide();
    $('.sj_trigger').click(function(){
	if( $(this).next().is(':hidden') ) {
            $('.sj_trigger').removeClass('active').next().slideUp(0);
            $(this).toggleClass('active').next().slideDown(600);
	}else{
            $(this).toggleClass('active');
            $(this).next().slideUp(0);
    }
	return false;
    });
});
function control(){
	document.getElementById("controlCore").style.display = "";
	document.getElementById("testFlow").style.display = "none";
}
function resetIframeSize()
    {
    var a=document.body.clientWidth-200;
    document.getElementById("ifr").style.width=a+"px";
    }
window.onload=resetIframeSize;
window.onresize=resetIframeSize;