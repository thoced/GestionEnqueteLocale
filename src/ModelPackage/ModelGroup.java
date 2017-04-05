/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class ModelGroup extends Model
{

    private final StringProperty groupName = new SimpleStringProperty();

    public String getGroupName() {
        return groupName.get();
    }

    public void setGroupName(String value) {
        groupName.set(value);
    }

    public StringProperty groupNameProperty() {
        return groupName;
    }

    @Override
    public String toString() {
        return groupName.getValue(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
