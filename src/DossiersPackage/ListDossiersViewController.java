/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DossiersPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelDossier;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class ListDossiersViewController implements Initializable,IController {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TableView tableDossiers;
    @FXML
    private TableColumn<ModelDossier,String> columnOwner;
    @FXML
    private TableColumn<ModelDossier,String> columnDossier;
    
    // data
    private ObservableList<ModelDossier> oListDossiers;
    private ModelDossier currentDossier;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // instance de oListDossiers
        oListDossiers = FXCollections.observableArrayList();
        oListDossiers.clear();
        
       
        
        // attachement du tableDossier avec le oListDossiers
        columnDossier.setCellValueFactory(cellData->cellData.getValue().nomDossierProperty());
        columnOwner.setCellValueFactory(cellData->cellData.getValue().nomOwnerProperty());
        tableDossiers.setItems(oListDossiers);
    }    
    
    @FXML
    public void handleCancel(ActionEvent event)
    {
        tableDossiers.getScene().getWindow().hide();
    }
    
    @FXML
    public void handleAccept(ActionEvent event)
    {
        if( tableDossiers.getSelectionModel().getSelectedItem() != null)
            this.currentDossier = (ModelDossier) tableDossiers.getSelectionModel().getSelectedItem();
        
        // Preparation des donnée
        this.currentDossier.prepareData();
        
        System.out.println(this.currentDossier.getNomDossier());
        // hide
        tableDossiers.getScene().getWindow().hide();
    }

    @Override
    public void setModelDossier(ModelDossier currentDossier) 
    {
        this.currentDossier = currentDossier;
    }

    @Override
    public ModelDossier getModelDossier() 
    {
        return this.currentDossier;
    }

    @Override
    public void loadModel(ModelDossier currentDossier) 
    {
      
    }
    
    public void load(ModelUser user)
    {
         try {
            // chargement des dossiers
            String sql = "select * from t_folders inner join t_link_group_folders on t_folders.id = t_link_group_folders.ref_id_folders "
                    + "where t_link_group_folders.ref_id_group like ("
                    + "select DISTINCT t_group.id from t_group inner join t_link_group_users on t_group.id = t_link_group_users.ref_id_group "
                    + "where t_link_group_users.ref_id_users = (select t_users.id from t_users where login = ?)) AND t_folders.visible = TRUE";
            
            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setString(1, user.getLogin());
            ResultSet result = ps.executeQuery();
            while(result.next())
            {
                ModelDossier model = new ModelDossier();
                model.setId(result.getLong("id"));
                model.setNomDossier(result.getString("nom"));
                String sql_user = "select nom,prenom from t_users where id = ?";
                PreparedStatement ps_user = ConnectionSQL.getCon().prepareStatement(sql_user);
                ps_user.setLong(1, result.getLong("owner"));
                ResultSet r = ps_user.executeQuery();
                r.first();
                model.setNomOwner(r.getString("nom") + " " + r.getString("prenom"));
                oListDossiers.add(model);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListDossiersViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
