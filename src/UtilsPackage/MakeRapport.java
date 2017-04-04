/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsPackage;

import ModelPackage.ModelNice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author Thonon
 */
public class MakeRapport 
{
    private XWPFDocument document;
    
    public void createNewDocument(File file,ObservableList<ModelNice> oNices)
    {
        FileOutputStream out= null;
        try {
            //Blank Document
            document= new XWPFDocument();
            //Write the Document in file system
            out = new FileOutputStream(
                    file);
            
            // creation du rapport
            this.createRapport(oNices);
            
                       document.write(out);
            out.close();
          
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MakeRapport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MakeRapport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(MakeRapport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void createRapport(ObservableList<ModelNice> oNices)
    {
        if(document == null)
            return;
        
           
            
            int numPage = 1;
            for(ModelNice model : oNices)
            {
                 XWPFParagraph paragraph = document.createParagraph();
                 XWPFRun run=paragraph.createRun();
                 
                
                run.setFontSize(18);
                
                run.setText("Page: " + numPage);
                run.addBreak();
                run.addBreak();
                run.setText("Event Id: " + model.getEventId().toString());
                run.addBreak();
                run.setText("Event Type: " + model.getEventType());
                run.addBreak();
                run.setText("Categorie: " + model.getCategorie());
                run.addBreak();
                run.setText("Numéro appelant: " + model.getNumCaller());
                run.addBreak();
                run.setText("Numéro appelé: " + model.getNumCalled());
                run.addBreak();
                run.addBreak();
                run.setText("Synopsis:");
                run.addBreak();
                
                paragraph = document.createParagraph();
                run=paragraph.createRun();
                
                run.setFontSize(14);
                run.setText(model.getSynopsis());
                run.addBreak();

                paragraph = document.createParagraph();
                run=paragraph.createRun();
                
                run.setFontSize(18);
                run.setText("Content:");
                run.addBreak();
                
                paragraph = document.createParagraph();
                run=paragraph.createRun();
                
                run.setFontSize(14);
                run.setText(model.getSmsContent());
                run.addBreak(BreakType.PAGE);
                
                // page +1
                numPage++;
            }
            
            
            
    }
}
