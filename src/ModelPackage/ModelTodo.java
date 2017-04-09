/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class ModelTodo extends Model
{
    // index
    private final LongProperty index = new SimpleLongProperty();
    // libelle du todo
    private final StringProperty libelle = new SimpleStringProperty();
    // contenu du todo
    private final StringProperty contenu = new SimpleStringProperty();
    // date de création du todo
    private final ObjectProperty<LocalDate> dateCreation = new SimpleObjectProperty<>();
    // date de rappel du todo
    private final ObjectProperty<LocalDate> dateRappel = new SimpleObjectProperty<>();
    // statut du todo
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
    
    
    

    public long getIndex() {
        return index.get();
    }

    public void setIndex(long value) {
        index.set(value);
    }

    public LongProperty indexProperty() {
        return index;
    }
   
    

    public LocalDate getDateRappel() {
        return dateRappel.get();
    }

    public void setDateRappel(LocalDate value) {
        dateRappel.set(value);
    }

    public ObjectProperty dateRappelProperty() {
        return dateRappel;
    }
    

    public LocalDate getDateCreation() {
        return dateCreation.get();
    }

    public void setDateCreation(LocalDate value) {
        dateCreation.set(value);
    }

    public ObjectProperty dateCreationProperty() {
        return dateCreation;
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
    
}
