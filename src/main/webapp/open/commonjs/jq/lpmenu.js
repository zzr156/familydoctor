// JavaScript Document
function w(vd)
{
  var ob=document.getElementById(vd);
  if(ob.style.display=="block" || ob.style.display=="")
  {
     ob.style.display="none";
     var ob2=document.getElementById('s'+vd);

  }
  else
  {
    ob.style.display="block";
    var ob2=document.getElementById('s'+vd);
    
  }
}
function k(vd)
{
  var ob=document.getElementById(vd);  
  if(ob.style.display=="block")
  {
     ob.style.display="none";
     var ob2=document.getElementById('s'+vd);

  }
  else
  {
    ob.style.display="block";
    var ob2=document.getElementById('s'+vd);

  }
}