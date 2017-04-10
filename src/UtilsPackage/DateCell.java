/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsPackage;

import ModelPackage.ModelApostille;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class DateCell extends TableCell
{

    @Override
    protected void updateItem(Object item, boolean empty) 
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        this.setText(null);
        this.setGraphic(null);
        
        if(item != null)
        {
            LocalDate date = (LocalDate) item;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.setText(date.format(format));
        }
    }
    
}
