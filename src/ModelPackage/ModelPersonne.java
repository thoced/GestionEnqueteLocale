/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Thonon
 */
public class ModelPersonne extends Model
{
    // nom
    private final StringProperty nom = new SimpleStringProperty();
    // prenom
    private final StringProperty prenom = new SimpleStringProperty();
    // adresse
    private final StringProperty adresse = new SimpleStringProperty();
    // date de naissance
    private final ObjectProperty<LocalDate> dateNaissance = new SimpleObjectProperty<>();
    // qualite
    private final StringProperty qualite = new SimpleStringProperty();

    public String getQualite() {
        return qualite.get();
    }

    public void setQualite(String value) {
        qualite.set(value);
    }

    public StringProperty qualiteProperty() {
        return qualite;
    }
    
    

    public LocalDate getDateNaissance() {
        return dateNaissance.get();
    }

    public void setDateNaissance(LocalDate value) {
        dateNaissance.set(value);
    }

    public ObjectProperty dateNaissanceProperty() {
        return dateNaissance;
    }
    

    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String value) {
        adresse.set(value);
    }

    public StringProperty adresseProperty() {
        return adresse;
    }
    

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String value) {
        prenom.set(value);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }
    
    

    public String getNom() {
        return nom.get();
    }

    public void setNom(String value) {
        nom.set(value);
    }

    public StringProperty nomProperty() {
        return nom;
    }
    
}
