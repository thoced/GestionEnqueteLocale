/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinksPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class CheckCell extends TableCell implements ChangeListener<Boolean>
{

   
    @Override
    protected void updateItem(Object item, boolean empty) 
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        
        this.setText(null);
        this.setGraphic(null);
      
        if(item != null)
        {
          CheckBox c = new CheckBox();
          c.setSelected((Boolean)item);
          c.selectedProperty().addListener(this);
          this.setGraphic(c);
        }
       
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
    {
        // modification du checked
       ((ModelAnnexeChecked)this.getTableRow().getItem()).setChecked(newValue);
    }
    
}
