/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinksPackage;

import ModelPackage.ModelAnnexe;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Thonon
 */
public class ModelAnnexeChecked extends ModelAnnexe
{

    private final ObjectProperty<Boolean> checked = new SimpleObjectProperty<>();

    public Boolean getChecked() {
        return checked.get();
    }

    public void setChecked(Boolean value) {
        checked.set(value);
    }

    public ObjectProperty checkedProperty() {
        return checked;
    }
   
    
}
