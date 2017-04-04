/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPackage;

import ModelPackage.ConnectionSQL;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class LoginController implements Initializable {

    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Label messageError;
    
    private boolean isLogin = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
      @FXML
    public void handleClose() 
    {
        isLogin = false;
        login.getScene().getWindow().hide();
    }
    
    @FXML
    public void handleConnecter()
    {
     isLogin = true;
        
        try {
            // vérification que le login n'est pas vide
            if(login.getText().isEmpty() || password.getText().isEmpty())
            {
                login.setText("");
                password.setText("");
                throw new LoginException();
            }
            
            // tentative de connection
            ConnectionSQL.setUser(login.getText());
            ConnectionSQL.setPassword(password.getText());
            ConnectionSQL.Connect();
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            isLogin = false;
            messageError.setText("Le profil n'est pas accepté par le système, veuillez contacter l'administrateur (" + ex.getMessage() +")");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             isLogin = false;
        } catch (LoginException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             isLogin = false;
              messageError.setText("Veuillez inscrire un login et/ou un password non vide");
        }
        
        if(isLogin)
        {
            login.getScene().getWindow().hide();
        }
        else
        {
            login.setText("");
            password.setText("");
        }
        
    }

    public boolean isIsLogin() {
        return isLogin;
    }
    
    
}
