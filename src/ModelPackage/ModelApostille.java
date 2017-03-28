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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

/**
 *
 * @author Thonon
 */
public class ModelApostille extends Model implements ChangeListener<LocalDate>
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

    public ModelApostille() 
    {
        // installation des listener sur les dates pour les vérifications
        dateOut.addListener(this);
        dateIn.addListener(this);
    }

    
    
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

    @Override
    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue)
    {
       if(observable == dateOut && newValue.isBefore(dateIn.getValue()))
       {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur dans la date de clôture !!!");
            alert.setContentText("La date de clôture de l'apostille ne peut être inférieur à la date d'ouverture");
            alert.showAndWait();
            dateOut.setValue(dateIn.getValue());
       }
       else
           if(observable == dateIn && newValue.isAfter(dateOut.getValue()))
            {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Erreur dans la date de d'ouverture !!!");
                 alert.setContentText("La date d'ouverture de l'apostille ne peut être supérieur à la date de clôture");
                 alert.showAndWait();
                 dateIn.setValue(dateOut.getValue());
            }
       
       
       
    }
    
}
