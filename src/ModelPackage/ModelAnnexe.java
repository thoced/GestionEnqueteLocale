/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.sql.Blob;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class ModelAnnexe extends Model
{
    // index
    private final IntegerProperty index = new SimpleIntegerProperty();
    // libelle de l'annexe
    private final StringProperty libelle = new SimpleStringProperty();
    // commentaire éventuel lié à l'annexe
    private final StringProperty commentaire = new SimpleStringProperty();
    // document raw blob 
    private final ObjectProperty<Blob> raw = new SimpleObjectProperty<>();

    public Blob getRaw() {
        return raw.get();
    }

    public void setRaw(Blob value) {
        raw.set(value);
    }

    public ObjectProperty rawProperty() {
        return raw;
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
    
    

    public String getLibelle() {
        return libelle.get();
    }

    public void setLibelle(String value) {
        libelle.set(value);
    }

    public StringProperty libelleProperty() {
        return libelle;
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
