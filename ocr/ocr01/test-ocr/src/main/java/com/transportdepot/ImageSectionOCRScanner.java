package com.transportdepot;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ImageSectionOCRScanner {
  private String imagePath;

  private final File imageFile;
  private final File uznFile;

  private ImageSectionOCRScanner(){
    imageFile = null;
    uznFile = null;  
  }

  public ImageSectionOCRScanner(String filePath){
    imageFile = new File(filePath);
    uznFile = new File(filePath + ".uzn");
  }

  public String getImagePath() {
    return this.imagePath; 
  }

  public void setImagePath(String imagePath){
    this.imagePath = imagePath;
  }

  public void run(){

    Tesseract tesseract = new Tesseract();
    
    tesseract.setTessVariable("TESSDATA_PREFIX", "/usr/include/tesseract");
    tesseract.setDatapath("/usr/share/tessdata");
    tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_COLUMN);
    System.out.println("About to try");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(uznFile));
      String line = in.readLine();
      
      while( line != null ){
        line = line.trim();
        System.out.println("UZN line: " + line);
        
        String[] splitArray = line.split("\\s+");
        
        int x = Integer.parseInt( splitArray[0] );
        int y = Integer.parseInt( splitArray[1] );  
        int width = Integer.parseInt( splitArray[2] );
        int height = Integer.parseInt( splitArray[3] );
        String tag = splitArray[4];

        Rectangle r = new Rectangle(x, y, width, height);
        String ocrResult = tesseract.doOCR( imageFile, r );
        System.out.println("   Tag: " + tag + " = [" + ocrResult + "\n\n");
      }
    }
    catch(IOException e){
      System.out.println(e.getMessage());    
    }
    catch(TesseractException te){
      System.out.println( te.getMessage() );    
    }
  }
}


