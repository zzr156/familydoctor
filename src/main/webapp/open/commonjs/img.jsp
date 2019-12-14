<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title></title>
</head>

<div id="img">
    <img width="800" height="800" src=""  alt="\" id="myimg" />
</div>
<div id="tool">
    <a href="#" id="arr_left" onclick="rotate('myimg','left')">左转</a>
    <a href="#" id="arr_right" onclick="rotate('myimg','right')">右转</a>
    <a href="#" id="arr_fd" onclick="PhotoSize.action(1);">放大</a>
    <a href="#" id="arr_sx" onclick="PhotoSize.action(-1);">缩小</a>
    <a href="#" id="arr_hy" onclick="PhotoSize.action(0);">还原大小</a>
</div>
<body>
</body>
</html>
<script type="text/javascript">
    function rotate(obj,arr){
        var img = document.getElementById(obj);
        if(!img || !arr) return false;
        var n = img.getAttribute('step');
        if(n== null) n=0;
        if(arr=='left'){
            (n==0)? n=3:n--;
        }else if(arr=='right'){
            (n==3)? n=0:n++;
        }
        img.setAttribute('step',n);
        if(document.all) {
            img.style.filter = 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ n +')';
            // ���ִ������д��HTML5��Ԫ�ؽ�����ת�� canvas
        }else{
            var c = document.getElementById('canvas_'+obj);
            if(c== null){
                img.style.visibility = 'hidden';
                img.style.position = 'absolute';
                c = document.createElement('canvas');
                c.setAttribute("id",'canvas_'+obj);
                img.parentNode.appendChild(c);
            }
            var canvasContext = c.getContext('2d');
            switch(n) {
                default :
                case 0 :
                    c.setAttribute('width', img.width);
                    c.setAttribute('height', img.height);
                    canvasContext.rotate(0 * Math.PI / 180);
                    canvasContext.drawImage(img, 0, 0,img.width,img.height);
                    break;
                case 1 :
                    c.setAttribute('width', img.height);
                    c.setAttribute('height', img.width);
                    canvasContext.rotate(90 * Math.PI / 180);
                    canvasContext.drawImage(img, 0, -img.height,img.width,img.height);
                    break;
                case 2 :
                    c.setAttribute('width', img.width);
                    c.setAttribute('height', img.height);
                    canvasContext.rotate(180 * Math.PI / 180);
                    canvasContext.drawImage(img, -img.width, -img.height,img.width,img.height);
                    break;
                case 3 :
                    c.setAttribute('width', img.height);
                    c.setAttribute('height', img.width);
                    canvasContext.rotate(270 * Math.PI / 180);
                    canvasContext.drawImage(img, -img.width, 0,img.width,img.height);
                    break;
            };
        }

    }
</script>

<script type="text/javascript">
    var  name='url';
    getQuery();
    function getQuery()
    {

        var reg = document.URL;
        var r = reg.split('=');
        if (r!=null){
            document.getElementById("myimg").src= '<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+r[1];
        }
    }
</script>
<script type="text/javascript">
    var PhotoSize = {
        zoom: 0, // 缩放率
        count: 0, // 缩放次数
        cpu: 0, // 当前缩放倍数值
        elem: "", // 图片节点
        photoWidth: 0, // 图片初始宽度记录
        photoHeight: 0, // 图片初始高度记录

        init: function(){
            this.elem = document.getElementById("myimg");
            this.photoWidth = this.elem.scrollWidth;
            this.photoHeight = this.elem.scrollHeight;

            this.zoom = 1.2; // 设置基本参数
            this.count = 0;
            this.cpu = 1;
        },

        action: function(x){
            if(x === 0){
                this.cpu = 1;
                this.count = 0;
            }else{
                this.count += x; // 添加记录
                this.cpu = Math.pow(this.zoom, this.count); // 任意次幂运算
            };
            this.elem.style.width = this.photoWidth * this.cpu +"px";
            this.elem.style.height = this.photoHeight * this.cpu +"px";
        }
    };
    // 启动放大缩小效果,用onload方式加载，防止第一次点击获取不到图片的宽高
    window.onload = function(){PhotoSize.init()};
</script>