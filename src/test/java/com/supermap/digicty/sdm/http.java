/**
 * Project Name: sdm-upgradetools
 * File Name: http
 * Package Name: com.supermap.digicty.sdm
 * Date: 2020/5/12 11:14
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm;

import org.apache.catalina.connector.ClientAbortException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 11:14 2020/5/12 
 */
public class http {
    /**
     * 下载服务器已存在的文件,支持断点续传
     *
     * @param request
     *            请求对象
     * @param response
     *            响应对象
     * @param proposeFile
     *            文件路径(绝对)
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, File proposeFile) {

        InputStream inputStream = null;
        OutputStream bufferOut = null;
        try {
            // 设置响应报头   这是本地文件的大小
            long fSize = proposeFile.length();
            response.setContentType("application/x-download");
            // Content-Disposition: attachment; filename=WebGoat-OWASP_Developer-5.2.zip
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(proposeFile.getName() ));
            // Accept-Ranges: bytes
            response.setHeader("Accept-Ranges", "bytes");
            long pos = 0, last = fSize - 1, sum = 0;//pos开始读取位置;  last最后读取位置;  sum记录总共已经读取了多少字节
            if (null != request.getHeader("Range")){
                //断点续传
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);//206
                try {
                    // 情景一：RANGE: bytes=2000070- 情景二：RANGE: bytes=2000070-2000970
                    String numRang = request.getHeader("Range").replaceAll("bytes=", "");
                    String[] strRange = numRang.split("-");
                    if (strRange.length == 2) {
                        pos = Long.parseLong(strRange[0].trim()); //pos开始读取位置
                        last = Long.parseLong(strRange[1].trim()); //last最后读取位置;
                    } else {
                        pos = Long.parseLong(numRang.replaceAll("-", "").trim());
                    }
                } catch (NumberFormatException e) {
                    pos = 0;
                }
            }
            long rangLength = last - pos + 1;// 总共需要读取的字节
            // Content-Range: bytes 10-1033/304974592
            String contentRange = new StringBuffer("bytes ").append(pos).append("-").append(last).append("/").append(fSize).toString();
            response.setHeader("Content-Range", contentRange);
            // Content-Length: 1024
            response.addHeader("Content-Length", String.valueOf(rangLength));
            //跳过已经下载的部分，进行后续下载
            bufferOut = new BufferedOutputStream(response.getOutputStream());
            inputStream = new BufferedInputStream(new FileInputStream(proposeFile));
            inputStream.skip(pos);
            byte[] buffer = new byte[1024];
            int length = 0;
            while (sum < rangLength) {
                length = inputStream.read(buffer, 0, ((rangLength - sum) <= buffer.length ? ((int) (rangLength - sum)) : buffer.length));
                sum = sum + length;
                bufferOut.write(buffer, 0, length);
            }
        } catch (Throwable e) {
            if (e instanceof ClientAbortException) {
                // 浏览器点击取消
            } else {

                e.printStackTrace();
            }
        } finally {
            try {
                if (bufferOut != null) {
                    bufferOut.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
