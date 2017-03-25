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
public class ModelNumero extends Model
{
    // type de numero
    private final StringProperty type = new SimpleStringProperty();
    // numero
    private final StringProperty numero = new SimpleStringProperty();
    // nationalite
    private final StringProperty nationalite = new SimpleStringProperty();
    // owner
    private final StringProperty owner = new SimpleStringProperty();

    public String getOwner() {
        return owner.get();
    }

    public void setOwner(String value) {
        owner.set(value);
    }

    public StringProperty ownerProperty() {
        return owner;
    }
    

    public String getNationalite() {
        return nationalite.get();
    }

    public void setNationalite(String value) {
        nationalite.set(value);
    }

    public StringProperty nationaliteProperty() {
        return nationalite;
    }
    

    public String getNumero() {
        return numero.get();
    }

    public void setNumero(String value) {
        numero.set(value);
    }

    public StringProperty numeroProperty() {
        return numero;
    }
    

    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }
    
}
