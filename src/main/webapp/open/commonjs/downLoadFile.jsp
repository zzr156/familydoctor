<%@ page language="java" pageEncoding="UTF-8"%>
<%
	try
	{
		java.io.OutputStream bos = response.getOutputStream();
		String filename = (String) request.getAttribute("filename");
		String filepath = (String) request.getAttribute("filepath");
		System.out.println("filename: " + filename);
		System.out.println("filepath: " + filepath);

		com.ylz.packcommon.common.util.IoUtils ioUtil = new com.ylz.packcommon.common.util.IoUtils();

	            filename=new String(filename.getBytes("GBK"),"iso8859-1");
		if (filename == null || filepath == null)
			throw new Exception("找不到指定的要下载的文件。");

		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + "." + filepath.substring(filepath.lastIndexOf(".") + 1));

		java.io.InputStream stream = ioUtil.getFileStream(filepath);

		org.apache.commons.io.output.ByteArrayOutputStream bao = new org.apache.commons.io.output.ByteArrayOutputStream(1024000);
		org.apache.commons.io.CopyUtils.copy(stream, bos);
		bao.writeTo(bos);

                                stream.close();
		bos.close();



	}
	catch (Exception ex)
	{
		out.print("<script>alert('file not exists !');history.back();</script>");
		ex.printStackTrace();
	}
%>
