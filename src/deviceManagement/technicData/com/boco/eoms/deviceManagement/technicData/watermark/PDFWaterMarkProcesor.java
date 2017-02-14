package com.boco.eoms.deviceManagement.technicData.watermark;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;



import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PDFWaterMarkProcesor {

	private static String imageFilePath = "E:\\workspace_hlj\\resource\\test\\chinamobile1.jpg";
	public static void addWaterMark(String inputFile,boolean replace) throws Exception {
		
		try {
			File srcFile = new File(inputFile);
			File tempFile = new File(inputFile+".temp");
			srcFile.renameTo(tempFile);
			
			
            PdfReader reader = new PdfReader(inputFile+".temp");   
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(   
            		inputFile));   
             int total = reader.getNumberOfPages() + 1;
             
             Image image = Image.getInstance(imageFilePath);   
             image.setGrayFill(0.4f);
             image.setAlignment(Image.ALIGN_TOP);
             image.setAbsolutePosition(200, 400);  

             PdfContentByte under;   
             for (int i = 1; i < total; i++) {   
                 under = stamper.getUnderContent(i);   
                 // 添加图片   
                 under.addImage(image);  
             }   
             stamper.close();   
             reader.close();
             
             if(replace) {
            	 tempFile.delete();
             } 
             
         } catch (Exception e) {
             throw new Exception("PDF File Error!Please check the file!");
         }   
	}

	
}
