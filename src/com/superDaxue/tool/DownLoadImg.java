package com.superDaxue.tool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class DownLoadImg {
	public boolean download(InputStream in, String path) {
		FileOutputStream out = null;
		try {
			File f = new File(path);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs(); //文件不存在就新建，新建目录
			}
			if(!f.exists()){
				f.createNewFile();
			}
			
			out = new FileOutputStream(path);
			byte b[] = new byte[1024];
			int j = 0;
			while ((j = in.read(b)) != -1) { //网络流写入图片文件
				out.write(b, 0, j);
			}
			out.flush();
			/*File file = new File(path);
			if (file.exists() && file.length() == 0)
				return false;
			 FileInputStream fis = new FileInputStream(file);  
	         JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);  
	         BufferedImage bufferedImage = decoder.decodeAsBufferedImage();  
	        
	         fis.close();  */
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if ("FileNotFoundException".equals(e.getClass().getSimpleName()))
				System.err.println("download FileNotFoundException");
			if ("SocketTimeoutException".equals(e.getClass().getSimpleName()))
				System.err.println("download SocketTimeoutException");
			else
				e.printStackTrace();
		} finally {

			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return false;
	}
}
