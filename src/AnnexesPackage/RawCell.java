/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnnexesPackage;

import ModelPackage.ModelAnnexe;
import UtilsPackage.ViewPdfController;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;

/**
 *
 * @author Thonon
 */
public class RawCell extends TableCell<ModelAnnexe,Blob> implements EventHandler
{

    @Override
    protected void updateItem(Blob item, boolean empty)
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        
        this.setText(null);
        this.setGraphic(null);
        
        if(item != null)
        {
           Button b = new Button("Voir fichier");
           b.addEventHandler(ActionEvent.ACTION, this);
           b.setStyle("-fx-background-color:lightgreen");
           this.setGraphic(b);
        }
    }

    @Override
    public void handle(Event event)
    {
        if(event != null)
        {
            ModelAnnexe model = (ModelAnnexe) this.getTableRow().getItem();
            if(model != null)
            {
                try {
                    ViewPdfController pdf = new ViewPdfController("test",model.getRaw());
                    pdf.ShowPDFDocument();
                } catch (IOException ex) {
                    Logger.getLogger(RawCell.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(RawCell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
