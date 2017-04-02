/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionenquetelocale;

import ModelPackage.IController;
import ModelPackage.ModelDossier;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class MainViewController implements Initializable, ChangeListener<Boolean>,EventHandler<WindowEvent>{

    @FXML
    private AnchorPane mainView;
    @FXML
    private Label labelDossier;
    // data
    private ModelDossier currentDossier;
    
    private ObservableList<Stage> oStages;
    
    @FXML
    private void handleAnnexe(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/EntityPackage/EntityBaseView.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setMinWidth(960);
        stage.initOwner(mainView.getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
       // stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Gestion des annexes");
        // Personne
        FXMLLoader loaderPersonne = new FXMLLoader(this.getClass().getResource("/AnnexesPackage/AnnexeView.fxml"));
        BorderPane panePersonne = loaderPersonne.load();
        IController controller = loaderPersonne.getController();
        controller.loadModel(currentDossier);
        pane.setCenter(panePersonne);
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleApostille(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/EntityPackage/EntityBaseView.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setMinWidth(960);
        stage.initOwner(mainView.getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
       // stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Gestion des apostilles");
        // Personne
        FXMLLoader loaderPersonne = new FXMLLoader(this.getClass().getResource("/ApostillesPackage/ApostilleView.fxml"));
        BorderPane panePersonne = loaderPersonne.load();
        IController controller = loaderPersonne.getController();
        controller.loadModel(currentDossier);
        pane.setCenter(panePersonne);
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
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
            // label
            labelDossier.setText("Dossier en cours: " +  controller.getModelDossier().getNomDossier());
            
            // chargement du stage folderstab
            loader = new FXMLLoader(this.getClass().getResource("/gestionenquetelocale/MainFolderTabView.fxml"));
            BorderPane bp = loader.load();
            MainFolderTabViewController mftvc = loader.getController();
            mftvc.load(currentDossier);
            //IController controller = loader.getController();
            //controller.setModelDossier(currentDossier);
            scene = new Scene(bp);
            stage = new Stage();
            stage.setTitle(controller.getModelDossier().getNomDossier());
           // stage.initOwner(mainView.getScene().getWindow());
            stage.initStyle(StageStyle.DECORATED);
            stage.setMaxHeight(700);
            stage.setMaxWidth(1024);
            stage.setResizable(true);
            stage.setScene(scene);
            stage.focusedProperty().addListener(this);
            stage.setOnHidden(this);
            oStages.add(stage);
            stage.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       oStages = FXCollections.observableArrayList();
       
    }    

    

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
    {
        if(newValue)
        {
           // on parse les stages et on regarde celui qui a le focus
            for(Stage s: oStages)
            {
                if(s.isFocused())
                {
                    labelDossier.setText(s.getTitle());
                    return;
                }
            }
   
        }
        else 
            labelDossier.setText("");
    }

    @Override
    public void handle(WindowEvent event) 
    {
        // suppression du stage à sa fermeture
        oStages.remove((Stage)event.getSource());
    }

    

    
    
}
