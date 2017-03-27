/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class ModelApostille extends Model
{
    // index
    private final IntegerProperty index = new SimpleIntegerProperty();

    public int getIndex() {
        return index.get();
    }

    public void setIndex(int value) {
        index.set(value);
    }

    public IntegerProperty indexProperty() {
        return index;
    }
    
    
    // autorite requérente
    private final StringProperty autorite = new SimpleStringProperty();
    // libelle de l'apostille
    private final StringProperty libelle = new SimpleStringProperty();
    // contenu complèt de l'apostille
    private final StringProperty contenu = new SimpleStringProperty();
    // Date In
    private final ObjectProperty<LocalDate> dateIn = new SimpleObjectProperty<>();
    // Date Out
    private final ObjectProperty<LocalDate> dateOut = new SimpleObjectProperty<>();
    // statut - en cours (false) - cloture (true)
    private final BooleanProperty statut = new SimpleBooleanProperty();

    public boolean isStatut() {
        return statut.get();
    }

    public void setStatut(boolean value) {
        statut.set(value);
    }

    public BooleanProperty statutProperty() {
        return statut;
    }
    

    public LocalDate getDateOut() {
        return dateOut.get();
    }

    public void setDateOut(LocalDate value) {
        dateOut.set(value);
    }

    public ObjectProperty dateOutProperty() {
        return dateOut;
    }
    

    public LocalDate getDateIn() {
        return dateIn.get();
    }

    public void setDateIn(LocalDate value) {
        dateIn.set(value);
    }

    public ObjectProperty dateInProperty() {
        return dateIn;
    }
    

    public String getContenu() {
        return contenu.get();
    }

    public void setContenu(String value) {
        contenu.set(value);
    }

    public StringProperty contenuProperty() {
        return contenu;
    }
    

    public String getLibelle() {
        return libelle.get();
    }

    public void setLibelle(String value) {
        libelle.set(value);
    }

    public StringProperty libelleProperty() {
        return libelle;
    }
    

    public String getAutorite() {
        return autorite.get();
    }

    public void setAutorite(String value) {
        autorite.set(value);
    }

    public StringProperty autoriteProperty() {
        return autorite;
    }
    
}
