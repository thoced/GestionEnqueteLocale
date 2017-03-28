/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApostillesPackage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class DateOutViewController implements Initializable {

    
    @FXML 
    private DatePicker dateOutField; 
    
    private LocalDate dateOut = null;
    
    @FXML
    public void handleConfimer(ActionEvent event)
    {
        if(dateOutField.getValue() != null)
            dateOut = dateOutField.getValue();
        else
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Probleme de date");
            alert.setContentText("Aucune date n'a été choisie, voulez-vous appliquer la date d'aujourd'hui ?");
            ButtonType bOui = new ButtonType("Oui");
            ButtonType bNon = new ButtonType("Non");
            alert.getButtonTypes().setAll(bNon,bOui);
            Optional<ButtonType> ret =  alert.showAndWait();
            if(ret.get() == bOui)
            {
                dateOut = LocalDate.now();
            }
           
            
        }
       // fermeture 
        dateOutField.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

    public LocalDate getDateOut() {
        return dateOut;
    }
    
    
    
}
