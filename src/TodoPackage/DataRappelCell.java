/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TodoPackage;

import ModelPackage.ModelTodo;
import UtilsPackage.DateCell;
import java.time.LocalDate;
import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class DataRappelCell extends DateCell
{

    @Override
    protected void updateItem(Object item, boolean empty) 
    {
        // appel au super constructeur de DateCell (la modification de la date se fait déja dans la superclass
        super.updateItem(item, empty); 
        
       /* this.setGraphic(null);
        this.setText(null);
        this.getTableRow().setStyle(null);*/
       
       // date de rappel des cell
        this.getTableRow().setStyle(null);
        
        if(item != null)
        {
            LocalDate date = (LocalDate)item;
          
            if(date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()))
            {
                // on change la couleur du row puisque la date de rappel vient d'être arrivée et que le statut n'est pas TRUE
                if(!((ModelTodo)this.getTableRow().getItem()).isStatut())
                     this.getTableRow().setStyle("-fx-background-color:Salmon");
            }
          
        }
    }

   
    
}
