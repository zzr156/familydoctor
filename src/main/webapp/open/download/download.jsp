<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
    response.setContentType("text/html");
    javax.servlet.ServletOutputStream ou = response.getOutputStream();
    String filepath="download/";
    String filename=new String(request.getParameter("filename").getBytes("ISO8859_1"),"GB2312").toString();
    System.out.println("DownloadFile filepath:" + filepath);
    System.out.println("DownloadFile filename:" + filename);
    java.io.File file = new java.io.File(request.getRealPath("/")+"/"+filepath + filename);
    System.out.println(file.getAbsolutePath());
    if (!file.exists()) {
        System.out.println(file.getAbsolutePath() + " �ļ�������!");
        return;
    }
// ��ȡ�ļ���
    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
// �����ļ�
// ������Ӧͷ�����ر�����ļ���
    if (filename != null && filename.length() > 0) {
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("gb2312"),"iso8859-1") + "");
        if (fileInputStream != null) {
            int filelen = fileInputStream.available();
//�ļ�̫��ʱ�ڴ治��һ�ζ���,Ҫѭ��
            byte a[] = new byte[filelen];
            fileInputStream.read(a);
            ou.write(a);
            out.clear(); 
            out = pageContext.pushBody();
        }
        fileInputStream.close();
        ou.close();
    }
%>
