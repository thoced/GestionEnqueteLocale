/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Thonon
 */
public class ViewPdfController 
{
    private String name;
    private Blob file;
    
    public ViewPdfController(String name,Blob file) 
    {
        this.name = name;
        this.file = file;
    }
    
    public void ShowPDFDocument() throws IOException, SQLException
    {
           File temp = File.createTempFile(this.name + new Date().getTime(), ".pdf");
           temp.deleteOnExit();
           FileOutputStream stream = new FileOutputStream(temp);
           stream.write(file.getBytes(1,(int)file.length()));
           stream.flush();
           stream.close();
          
         if(System.getProperty("os.name").contains("Windows"))
         {
            Runtime runtime = Runtime.getRuntime();
            String[] args = { "cmd.exe","/C", temp.getAbsolutePath() };
            final Process process = runtime.exec(args);
         }
         else
         {
             if(System.getProperty("os.name").contains("Linux"))
             {
                 Runtime runtime = Runtime.getRuntime();
                 String[] args = { "/bin/sh", "-c",temp.getAbsolutePath()  };
                 final Process process = runtime.exec(args);
             }

         } 
    }
    
}
