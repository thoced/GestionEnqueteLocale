/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPackage;

import ModelPackage.ModelUser;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class ResetLoginViewController  implements Initializable, EventHandler<MouseEvent>
{
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField login;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField retapeNewPassword;
    @FXML
    private Label errorMessage;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       newPassword.setOnMouseClicked(this);
       retapeNewPassword.setOnMouseClicked(this);
       
    }    
    
    @FXML
    public void handleEnregistrer(ActionEvent event)
    {
        // on vérifier que les deux textfield ne sont pas vides
        if(newPassword.getText().isEmpty() || retapeNewPassword.getText().isEmpty())
        {
            errorMessage.setText("Les deux champs relatifs au nouveau password doivent être remplis");
            newPassword.clear();
            retapeNewPassword.clear();
        }
        
        if(!newPassword.getText().equals(retapeNewPassword.getText()))
        {
            errorMessage.setText("Les deux champs password doivent être les même");
            newPassword.clear();
            retapeNewPassword.clear();
        }
    }
    
    public void load(ModelUser user)
    {
        if(user != null)
        {
            login.setText(user.getLogin());
            
        }
    }

    @Override
    public void handle(MouseEvent event) 
    {
        errorMessage.setText("");
        
    }

  

   
    
    
}
