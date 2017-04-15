/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import RecherchePackage.RechercheViewController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author Thonon
 */
public class ModelRecherche extends ModelDocument
{
    private ModelDossier dossier;
    
    private RechercheViewController parent;
    
    private final ObjectProperty<Button> buttonContenu = new SimpleObjectProperty<>();

    public Button getButtonContenu() {
        return buttonContenu.get();
    }

    public void setButtonContenu(Button value) {
        buttonContenu.set(value);
    }

    public ObjectProperty buttonContenuProperty() {
        return buttonContenu;
    }
    
    

    public ModelRecherche() 
    {
        dossier = new ModelDossier();
        
        buttonContenu.setValue(new Button("Ouvrir le dossier"));
        buttonContenu.getValue().setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                // placement du dossier qui doit être affiché
                ModelRecherche.this.getParent().setDossierToView(dossier);
                // demande de fermeture du module de recherche
                ModelRecherche.this.getParent().handleQuitter(null);
            }
        });
    }

    public ModelDossier getDossier() {
        return dossier;
    }

    public void setDossier(ModelDossier dossier) {
        this.dossier = dossier;
    }

    public RechercheViewController getParent() {
        return parent;
    }

    public void setParent(RechercheViewController parent) {
        this.parent = parent;
    }
    
    
}
