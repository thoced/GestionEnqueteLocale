/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class ModelEvent extends Model
{
    // index event
    private final IntegerProperty index = new SimpleIntegerProperty();
    // information event
    private final StringProperty information = new SimpleStringProperty();
    // nom du dossier qui a générer l'evenement
    private final StringProperty nameFolder = new SimpleStringProperty();

    public String getNameFolder() {
        return nameFolder.get();
    }

    public void setNameFolder(String value) {
        nameFolder.set(value);
    }

    public StringProperty nameFolderProperty() {
        return nameFolder;
    }
    
    

    public String getInformation() {
        return information.get();
    }

    public void setInformation(String value) {
        information.set(value);
    }

    public StringProperty informationProperty() {
        return information;
    }
    

    public int getIndex() {
        return index.get();
    }

    public void setIndex(int value) {
        index.set(value);
    }

    public IntegerProperty indexProperty() {
        return index;
    }
    
}
