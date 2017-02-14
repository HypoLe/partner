package com.boco.weixin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPhotoFromWeiXin {
	public static String downloadMedia(String accessToken, String mediaId, String savePath) {
		String filePath = savePath;
	    // 拼接请求地址
	    String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	    requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
	    System.out.println(requestUrl);
	    try {
	      URL url = new URL(requestUrl);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setDoInput(true);
	      conn.setRequestMethod("GET");

	      BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	      FileOutputStream fos = new FileOutputStream(new File(savePath));
	      byte[] buf = new byte[8096];
	      int size = 0;
	      while ((size = bis.read(buf)) != -1)
	        fos.write(buf, 0, size);
	      fos.close();
	      bis.close();

	      conn.disconnect();
	      String info = String.format("下载媒体文件成功，filePath=" + filePath);
	      System.out.println(info);
	    } catch (Exception e) {
	      filePath = null;
	      String error = String.format("下载媒体文件失败：%s", e);
	      System.out.println(error);
	    }
	    return filePath;

	}
	public  static void main(String[] args){
		String accessToken="l0FfzKYX727PWPMM9313BtFI5VpJa4Cl_cXfSi6jRKjyqVHqvdBlDw1iy2XeJEhSQkOYFQE5sFk_xu9xrzlhq3U0bMNpTfK9-iqZEzQMPW4SVa9RPjkwG5kJCBiAUtgTQETdAIAFTI";
		String mediaId="Pqo8ezHq87DbZNQ0D5mna7wGMTprG0g00qxse02U6F7b_HR1HhAKFQeJ86r5KF9y";
		String savePath="E://test1.jpg";
		try {
			downloadMedia(accessToken,mediaId,savePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
