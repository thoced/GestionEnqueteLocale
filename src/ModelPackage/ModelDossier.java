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
public class ModelDossier extends Model
{

    // nom du dossier
    private final StringProperty nomDossier = new SimpleStringProperty();
    // nom du owner
    private final StringProperty nomOwner = new SimpleStringProperty();

    public String getNomOwner() {
        return nomOwner.get();
    }

    public void setNomOwner(String value) {
        nomOwner.set(value);
    }

    public StringProperty nomOwnerProperty() {
        return nomOwner;
    }
    

    public String getNomDossier() {
        return nomDossier.get();
    }

    public void setNomDossier(String value) {
        nomDossier.set(value);
    }

    public StringProperty nomDossierProperty() {
        return nomDossier;
    }
    // nom du dossier
    
}
