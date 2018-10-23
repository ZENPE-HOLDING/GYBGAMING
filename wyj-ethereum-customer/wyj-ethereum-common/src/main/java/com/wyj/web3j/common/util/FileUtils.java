package com.wyj.web3j.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

public class FileUtils {
	public static void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {
		File f = new File(filePath);
		if (!f.exists()) {
		response.sendError(404, "File not found!");
		return;
		}
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;
	
		response.reset(); // 非常重要
		if (isOnLine) { // 在线打开方式
		URL u = new URL("file:///" + filePath);
		response.setContentType(u.openConnection().getContentType());
		response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
		// 文件名应该编码成UTF-8
		} else { // 纯下载方式
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
		}
		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
		out.write(buf, 0, len);
		br.close();
		out.close();
	}
}
