/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelUser;
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
    
    private ModelUser user = null;
    
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
     isLogin = false;
        
        try {
            // vérification que le login n'est pas vide
            if(login.getText().isEmpty())
            {
                login.setText("");
                password.setText("");
                throw new LoginException();
            }
            
            // tentative de connection
            CheckLogin clogin = new CheckLogin(login.getText(),password.getText());
            
       
        } catch (LoginException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             isLogin = false;
              messageError.setText("Veuillez inscrire un login et/ou un password non vide");
              
        } catch (NoLoginException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            // mauvais Login
            isLogin = false;
            messageError.setText(ex.getMessage());
        } catch (OkLoginException ex) 
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            isLogin = true;
            user = ex.getModelUser();
            
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

    public ModelUser getUser() {
        return user;
    }
    
    
    
    
}
