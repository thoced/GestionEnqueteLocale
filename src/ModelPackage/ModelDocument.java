/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class ModelDocument extends Model
{

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
    
    // type de document
    private final StringProperty type = new SimpleStringProperty();
    // titre du document
    private final StringProperty titre = new SimpleStringProperty();
    // commentaire lié au document
    private final StringProperty commentaire = new SimpleStringProperty();
    // date du document
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    // contenu du document, pour les recheches index
    private final StringProperty contenu = new SimpleStringProperty();
    // reference liée au document
    private final StringProperty reference = new SimpleStringProperty();

    public String getReference() {
        return reference.get();
    }

    public void setReference(String value) {
        reference.set(value);
    }

    public StringProperty referenceProperty() {
        return reference;
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
    

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate value) {
        date.set(value);
    }

    public ObjectProperty dateProperty() {
        return date;
    }
    

    public String getCommentaire() {
        return commentaire.get();
    }

    public void setCommentaire(String value) {
        commentaire.set(value);
    }

    public StringProperty commentaireProperty() {
        return commentaire;
    }
    

    public String getTitre() {
        return titre.get();
    }

    public void setTitre(String value) {
        titre.set(value);
    }

    public StringProperty titreProperty() {
        return titre;
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
