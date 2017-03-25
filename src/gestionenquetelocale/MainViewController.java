/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionenquetelocale;

import ModelPackage.IController;
import ModelPackage.ModelDossier;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Thonon
 */
public class MainViewController implements Initializable {
   
    
    @FXML
    private AnchorPane mainView;
    
    // data
    private ModelDossier currentDossier;
    
     @FXML
    private void handleDocument(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/EntityPackage/EntityBaseView.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setMinWidth(960);
        stage.initOwner(mainView.getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
       // stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Gestion des documents");
        // Personne
        FXMLLoader loaderPersonne = new FXMLLoader(this.getClass().getResource("/DocumentsPackage/DocumentView.fxml"));
        BorderPane panePersonne = loaderPersonne.load();
        IController controller = loaderPersonne.getController();
        controller.loadModel(currentDossier);
        pane.setCenter(panePersonne);
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleEntityNumero(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/EntityPackage/EntityBaseView.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initOwner(mainView.getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Gestion des entités : Numero");
        // Personne
        FXMLLoader loaderPersonne = new FXMLLoader(this.getClass().getResource("/EntityPackage/NumeroView.fxml"));
        BorderPane panePersonne = loaderPersonne.load();
        IController controller = loaderPersonne.getController();
        controller.loadModel(currentDossier);
        pane.setCenter(panePersonne);
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
     
    @FXML
    private void handleEntityPersonne(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/EntityPackage/EntityBaseView.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initOwner(mainView.getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Gestion des entités : Personne");
        // Personne
        FXMLLoader loaderPersonne = new FXMLLoader(this.getClass().getResource("/EntityPackage/PersonneView.fxml"));
        BorderPane panePersonne = loaderPersonne.load();
        IController controller = loaderPersonne.getController();
        controller.loadModel(currentDossier);
        pane.setCenter(panePersonne);
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleVoirDossiers(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DossiersPackage/ListDossiersView.fxml"));
        AnchorPane pane = loader.load();
        IController controller = loader.getController();
        controller.setModelDossier(currentDossier);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initOwner(mainView.getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
      
        //
        if(controller.getModelDossier() != null)
        {
            this.currentDossier = controller.getModelDossier();
            ((Stage)mainView.getScene().getWindow()).setTitle("Dossier en cours: " + controller.getModelDossier().getNomDossier());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO  
       
    }    

    
    
}
