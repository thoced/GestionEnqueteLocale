/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionenquetelocale;

import LoginPackage.LoginController;
import LoginPackage.ResetLoginViewController;
import ModelPackage.ConnectionSQL;
import ModelPackage.ModelDossier;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Thonon
 */
public class GestionEnqueteLocale extends Application implements EventHandler<WindowEvent>
{

    // class static de connection
    public static ConnectionSQL connectionSQL;
    
    private FXMLLoader loaderLogin;
    
    private Stage stageLogin;
    
    private Stage primaryStage;
   
    public GestionEnqueteLocale() throws SQLException 
    {
        try {
            try {
                // creation de l'objet static
                connectionSQL = new ConnectionSQL();
                
                
            } catch (SQLException ex) {
                Logger.getLogger(GestionEnqueteLocale.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GestionEnqueteLocale.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GestionEnqueteLocale.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            ConnectionSQL.Connect();
           
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionEnqueteLocale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        primaryStage = stage;
        // chargement de la vue de login
        loaderLogin = new FXMLLoader(getClass().getResource("/LoginPackage/LoginView.fxml"));
        AnchorPane pane =  loaderLogin.load();
        Scene sceneLogin = new Scene(pane);
        stageLogin = new Stage();
        stageLogin.initStyle(StageStyle.UNDECORATED);
        stageLogin.setScene(sceneLogin);
        stageLogin.setOnHidden(this);
        stageLogin.showAndWait();
        
        
        
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(WindowEvent event) 
    {
       if(event.getSource() == stageLogin)
       {
           if(((LoginController)loaderLogin.getController()).isIsLogin())
           {
              
               
               try {
                   FXMLLoader loaderPrimary = new FXMLLoader(getClass().getResource("MainView.fxml"));
                   Scene scene = new Scene(loaderPrimary.load());
                   MainViewController mvc = loaderPrimary.getController();
                   mvc.setCurrentUser(((LoginController)loaderLogin.getController()).getUser());
                   primaryStage.setScene(scene);
                   primaryStage.setTitle("Gestion d'enquête locale");
                   primaryStage.setMaximized(true);
                   primaryStage.show();
                   
                   
               // si le password est vide alors cela veut dire qu'il faut faire un reset
               if(((LoginController)loaderLogin.getController()).getUser().getPassword().isEmpty())
               {
                   // il faut faire un reset
                   FXMLLoader loaderReset = new FXMLLoader(this.getClass().getResource("/LoginPackage/ResetLoginView.fxml"));
                   AnchorPane paneReset = loaderReset.load();
                   ResetLoginViewController controller = loaderReset.getController();
                   controller.load(((LoginController)loaderLogin.getController()).getUser());
                   Scene sceneReset = new Scene(paneReset);
                   Stage stageReset = new Stage();
                   stageReset.setScene(sceneReset);
                   stageReset.setTitle("Modification du password");
                   stageReset.showAndWait();
               }
               
               System.out.println("MVC : " +  ((LoginController)loaderLogin.getController()).getUser().getLogin());
               // on place le user
                mvc.setCurrentUser(((LoginController)loaderLogin.getController()).getUser());
                   
               } catch (IOException ex) {
                   Logger.getLogger(GestionEnqueteLocale.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }
    }
    
}
