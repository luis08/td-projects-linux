package com.transportdepot;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
	ImageSectionOCRScanner s = new ImageSectionOCRScanner("~/projects/ocr/ocr01/pdf.pdf");
	s.run();
    }
}
