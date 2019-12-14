package com.ylz.view.sign.servlet;

import com.ylz.packcommon.hyd.exception.BusinessException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        String filePath = req.getParameter("filePath");

        InputStream in = null;
        OutputStream os = null;

        try {
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
            URL url = new URL(filePath);
            //打开本地文件流
            in = new BufferedInputStream(url.openStream());
            //激活下载操作
            os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = in.read(b)) != -1) {
                os.write(b, 0, length);
            }
            os.close();
            in.close();
            System.out.println("成功");
        } catch (Exception e) {
            //return "下载失败"+e.toString();
            throw new BusinessException("下载失败" + e.toString());
        } finally {
            if (os != null)
                os.close();
            if (in != null) {
                in.close();
            }
        }
    }
}
