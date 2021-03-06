/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionenquetelocale;

import DossiersPackage.FolderCreateViewController;
import DossiersPackage.ListDossiersViewController;
import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelDossier;
import ModelPackage.ModelEvent;
import ModelPackage.ModelGroup;
import ModelPackage.ModelUser;
import RecherchePackage.RechercheViewController;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private TableView tableEvenements;
    @FXML
    private TableColumn<ModelEvent,String> columnInformation;
    @FXML
    private TableColumn<ModelEvent,String> columnNameDossier;
    @FXML
    private ImageView logoPolice;
    // data
    private ModelDossier currentDossier;
    // date user
    private ModelUser currentUser;
    
    private ObservableList<Stage> oStages;
    
    private ObservableList<ModelEvent> oEvents;
    
     @FXML
    private void handleHelp(ActionEvent event) throws IOException 
    {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/HelpPackage/HelpView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            stage.setMinHeight(600);
            stage.setMinWidth(960);
            stage.setMaximized(false);
            stage.setScene(scene);
            stage.setTitle("About");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
    }
    
    @FXML
    private void handleRecherche(ActionEvent event) throws IOException 
    {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/RecherchePackage/RechercheView.fxml"));
            AnchorPane pane = loader.load();
            RechercheViewController controller = loader.getController();
            controller.load(this.getCurrentUser());
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            stage.setMinHeight(600);
            stage.setMinWidth(960);
            stage.setMaximized(false);
            stage.setScene(scene);
            stage.setTitle("Recherche de contenu");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            // le module de recherche est fermé, on teste si un document doit être ouvert
            if(controller.getDossierToView() != null)
            {
                // preparation des données liées au dossier
                controller.getDossierToView().prepareData();
                // Affichage du dossier
                this.viewDossier(controller.getDossierToView());
            }
            
    }
   
    @FXML
    private void handleCreationDossier(ActionEvent event) throws IOException 
    {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DossiersPackage/FolderCreateView.fxml"));
            AnchorPane pane = loader.load();
            FolderCreateViewController controller = loader.getController();
            controller.load(this.getCurrentUser());
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setTitle("Création d'un dossier");
            stage.initOwner(mainView.getScene().getWindow());
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            
            if(controller.isCreateFolder())
            {
                ModelDossier model = controller.getDossier();
                // création du nouveau dossier
                String sql = "insert into t_folders (nom,commentaire,owner,visible) values (?,?,?,true)";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, model.getNomDossier());
                ps.setString(2, model.getCommentaire());
                ps.setLong(3, currentUser.getId());
                ps.execute();
                ResultSet result = ps.getGeneratedKeys();
                result.first();
                model.setId(result.getLong(1));
                // liens des groupes
                sql = "insert into t_link_group_folders (ref_id_group,ref_id_folders) values (?,?)";
                ps = ConnectionSQL.getCon().prepareStatement(sql);
                for(ModelGroup group : model.getoGroups())
                {
                    ps.setLong(1, group.getId());
                    ps.setLong(2, model.getId());
                    ps.execute();
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void viewDossier(ModelDossier dossier)
    {
        try {
            this.currentDossier = dossier;
            ((Stage)mainView.getScene().getWindow()).setTitle("Dossier en cours: " + dossier.getNomDossier());
            // label
            labelDossier.setText("Dossier en cours: " +  dossier.getNomDossier());
            
            // chargement du stage folderstab
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/gestionenquetelocale/MainFolderTabView.fxml"));
            BorderPane bp = loader.load();
            MainFolderTabViewController mftvc = loader.getController();
            mftvc.load(currentDossier);
            Scene scene = new Scene(bp);
            Stage stage = new Stage();
            stage.setTitle(dossier.getNomDossier());
            stage.initOwner(mainView.getScene().getWindow());
            stage.initStyle(StageStyle.DECORATED);
            stage.setMaxHeight(700);
            stage.setMaxWidth(1024);
            stage.setResizable(true);
            stage.setScene(scene);
            stage.focusedProperty().addListener(this);
            stage.setOnHidden(this);
            oStages.add(stage);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @FXML
    private void handleVoirDossiers(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DossiersPackage/ListDossiersView.fxml"));
        AnchorPane pane = loader.load();
        ListDossiersViewController controller = loader.getController();
        controller.setModelDossier(currentDossier);
        controller.load(currentUser);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Selection du dossier");
        stage.initOwner(mainView.getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
      
        //
        if(controller.getModelDossier() != null)
        {
           this.viewDossier(controller.getModelDossier());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
            // instance de la liste des stages
            oStages = FXCollections.observableArrayList();
            // instance de la liste des evenements
            oEvents = FXCollections.observableArrayList();
            tableEvenements.setItems(oEvents);

            tableEvenements.focusedProperty().addListener(new ChangeListener<Boolean>() 
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
                {
                    MainViewController.this.loadEvent();
                }
            });
            
            // cellfactoryvalue
            columnInformation.setCellValueFactory(cellData->cellData.getValue().informationProperty());
            columnNameDossier.setCellValueFactory(cellData->cellData.getValue().nameFolderProperty());
           
            // logo police
           Image ima = new Image(this.getClass().getResourceAsStream("/AssetsPackage/logo_police.png"));
           logoPolice.setImage(ima);
            
           
    }    
    
    private void loadEvent()
    {
      
        try {
            // recherche d'évenements eventuelles sur les todos
            String sql_event = "select * from t_todo inner join t_folders on t_todo.ref_id_folders = t_folders.id where statut = FALSE AND date_rappel <= CURRENT_DATE() AND t_todo.ref_id_folders IN "
                    + "(select ref_id_folders from t_link_group_folders where ref_id_group IN (select ref_id_group from " +
                       "t_link_group_users where ref_id_users = ?))";
           /* select * from t_todo where statut = FALSE AND date_rappel <= CURRENT_DATE() AND t_todo.ref_id_folders IN (select ref_id_folders from t_link_group_folders where ref_id_group IN (select ref_id_group from 
t_link_group_users where ref_id_users = 1))*/
            
            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql_event);
            ps.setLong(1, currentUser.getId());
            ResultSet result = ps.executeQuery();
            oEvents.clear();
            while(result.next())
            {
               ModelEvent model = new ModelEvent();
               model.setInformation("Un evenement est arrivé à expiration");
               model.setNameFolder(result.getString("nom"));
               oEvents.add(model);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
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

    public ModelUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ModelUser currentUser) 
    {
        this.currentUser = currentUser;
        // chargement des events
        this.loadEvent();
    }
    
    @FXML
    public void handleFermerApplication(ActionEvent event)
    {
        mainView.getScene().getWindow().hide();
    }

    @Override
    public void handle(WindowEvent event) 
    {
        // suppression du stage à sa fermeture
        oStages.remove((Stage)event.getSource());
    }

    

    
    
}
