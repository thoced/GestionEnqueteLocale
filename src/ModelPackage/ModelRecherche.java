/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 *
 * @author Thonon
 */
public class ModelRecherche extends ModelDocument
{
    private ModelDossier dossier;
    
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
        
        buttonContenu.setValue(new Button("Voir le document"));
        buttonContenu.getValue().setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                
            }
        });
    }

    public ModelDossier getDossier() {
        return dossier;
    }

    public void setDossier(ModelDossier dossier) {
        this.dossier = dossier;
    }
    
    
}
