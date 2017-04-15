/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecherchePackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelDossier;
import ModelPackage.ModelRecherche;
import ModelPackage.ModelUser;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class RechercheViewController implements Initializable {

    @FXML
    private TextArea rechercheField;
    @FXML
    private TableView tableResultats;
    @FXML
    private TableColumn<ModelRecherche,Button> columnContenu;
    @FXML
    private TableColumn<ModelRecherche,String> columnDossier;
    @FXML
    private TableColumn<ModelRecherche,String> columnDocument;
    
    private ModelUser user;
    
    private ModelDossier dossierToView = null;
    
    private ObservableList<ModelRecherche> oRecherches;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       // instance
        oRecherches = FXCollections.observableArrayList();
        
        // cellfactorey value
        columnContenu.setCellValueFactory(cellData->cellData.getValue().buttonContenuProperty());
        columnDossier.setCellValueFactory(cellData->cellData.getValue().getDossier().nomDossierProperty());
        columnDocument.setCellValueFactory(cellData->cellData.getValue().titreProperty());
        
        tableResultats.setItems(oRecherches);
    }
    
    @FXML
    public void handleRecherche(ActionEvent event)
    {
        // clear
        oRecherches.clear();
        try 
        {
            if(rechercheField.getText().isEmpty())
                return;

            String recherche = "%" + rechercheField.getText().trim() + "%";
            
            String sql = "select * from t_document inner join t_folders on t_document.ref_id_folders = t_folders.id where t_folders.visible = TRUE AND contenu LIKE ? AND t_folders.id IN "
                    + "(select ref_id_folders from t_link_group_folders where t_link_group_folders.ref_id_group IN ( select t_link_group_users.ref_id_group from t_link_group_users where ref_id_users = ?))";
            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setString(1, recherche);
            ps.setLong(2, user.getId());
            ResultSet result = ps.executeQuery();
            while(result.next())
            {
                ModelRecherche model = new ModelRecherche();
                model.setId(result.getLong("t_document.id"));
                model.setTitre(result.getString("t_document.titre"));
                model.getDossier().setId(result.getLong("t_folders.id"));
                model.getDossier().setNomDossier(result.getString("t_folders.nom"));
                model.setParent(this);
                // ajout
                oRecherches.add(model);
               
            }
            
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(RechercheViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void handleQuitter(ActionEvent event)
    {
        rechercheField.getScene().getWindow().hide();
    }
    
    public void load(ModelUser user)
    {
        this.user = user;
    }

    public ModelDossier getDossierToView()
    {
        return dossierToView;
    }

    public void setDossierToView(ModelDossier dossierToView) 
    {
        this.dossierToView = dossierToView;
    }
    
    
    
   
    
}
