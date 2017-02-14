package com.boco.eoms.sparepart.util;

import org.apache.struts.upload.*;
import java.io.*;

public class UpLoad{
    public UpLoad(){
    }

    public static String UpLoadFile(FormFile file,String filePath){
      try{
            InputStream stream=file.getInputStream(); //���ļ�����
            OutputStream bos=new FileOutputStream(filePath+"/"+
                                                  file.getFileName()); //����һ���ϴ��ļ��������
            int bytesRead=0;
            byte[] buffer=new byte[8192];
            while((bytesRead=stream.read(buffer,0,8192))!=-1){
                bos.write(buffer,0,bytesRead); //���ļ�д�������
            }
            bos.close();
            stream.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return "UPLOADFILED";
        }
        return "UPLOADOK";
    }
}