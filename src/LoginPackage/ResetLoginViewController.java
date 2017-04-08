/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelUser;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private ModelUser currentUser;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       newPassword.setOnMouseClicked(this);
       retapeNewPassword.setOnMouseClicked(this);
       
    }    
    
    @FXML
    public void handleEnregistrer(ActionEvent event)
    {
        if(currentUser == null)
            return;
        
        try {
            // on vérifier que les deux textfield ne sont pas vides
            if(newPassword.getText().isEmpty() || retapeNewPassword.getText().isEmpty())
            {
                errorMessage.setText("Les deux champs relatifs au nouveau password doivent être remplis");
                newPassword.clear();
                retapeNewPassword.clear();
                return;
            }
            
            if(!newPassword.getText().equals(retapeNewPassword.getText()))
            {
                errorMessage.setText("Les deux champs password doivent être les même");
                newPassword.clear();
                retapeNewPassword.clear();
                return;
            }
            
            if(newPassword.getText().length() < 8 || newPassword.getText().length() > 16)
            {
                errorMessage.setText("Le password doit au minimum contenir 8 caractères et maximum 16 caractères");
                newPassword.clear();
                retapeNewPassword.clear();
                return;
            }
            
            // modification du mot de password avec chiffrage
            String sql = "update t_users set password = MD5(?) where id = ?";
            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setString(1, newPassword.getText());
            ps.setLong(2,currentUser.getId());
            ps.execute();
            
            // fermeture de la vue
            newPassword.getScene().getWindow().hide();
           
        } catch (SQLException ex) {
            Logger.getLogger(ResetLoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void load(ModelUser user)
    {
        if(user != null)
        {
            login.setText(user.getLogin());
            currentUser = user;
            
        }
    }

    @Override
    public void handle(MouseEvent event) 
    {
        errorMessage.setText("");
        
    }

  

   
    
    
}
