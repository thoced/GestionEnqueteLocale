/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionenquetelocale;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelDossier;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Thonon
 */
public class GestionEnqueteLocale extends Application 
{

    // class static de connection
    public static ConnectionSQL connectionSQL;
   
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
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
