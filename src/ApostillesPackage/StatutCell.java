/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApostillesPackage;

import ModelPackage.ModelApostille;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class StatutCell extends TableCell implements ChangeListener<Boolean>
{
   
    @Override
    protected void updateItem(Object item, boolean empty) 
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        
        this.setText(null);
        this.setGraphic(null);

        if(item != null)
        {
            Button button = null;
            
            if(((boolean)item) == false)
            {
                button = new Button("En cours");
                button.setStyle("-fx-background-color:lightgreen");
            }
            else
            {
                button = new Button("Cloturé");
                button.setStyle("-fx-background-color:red");
            }
            button.armedProperty().addListener(this);
            this.setGraphic(button);
        }
    }

    public StatutCell() 
    {
        
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
    { 
        if(newValue == true)
        {
            ModelApostille model = (ModelApostille) this.getTableRow().getItem();
            model.setStatut(!model.isStatut());
            
            // 
        }
    }
    
}
