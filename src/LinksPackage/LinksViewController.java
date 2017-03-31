/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinksPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelAnnexe;
import ModelPackage.ModelDocument;
import ModelPackage.ModelDossier;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class LinksViewController implements Initializable,EventHandler<WindowEvent> {

    @FXML
    private ListView listAnnexes;
    
    @FXML
    private ListView listAttach;
    
    @FXML
    private AnchorPane anchor;
    
    private ObservableList<ModelAnnexe> oAnnexes = FXCollections.observableArrayList();
    private ObservableList<ModelAnnexe> oAttach = FXCollections.observableArrayList();
    private ModelDocument currentDocument;
    private ModelDossier currentFolder;
    
    // flag close and write
    private boolean closeAndWrite = false;

    public ObservableList<ModelAnnexe> getoAttach() {
        return oAttach;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       listAnnexes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       listAttach.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       listAnnexes.setItems(oAnnexes);
       listAttach.setItems(oAttach);
     
       
       
    }
    
    @FXML
    public void handleAdd(ActionEvent event)
    {
        ObservableList<ModelAnnexe> oModels = (ObservableList) listAnnexes.getSelectionModel().getSelectedItems();
        if(oModels != null)
        {
           oAttach.addAll(oModels);
           oAnnexes.removeAll(oModels);
           
        }
    }
    
    @FXML
    public void handleSub(ActionEvent event)
    {
        ObservableList<ModelAnnexe> oModels = (ObservableList) listAttach.getSelectionModel().getSelectedItems();
        if(oModels != null)
        {
           oAnnexes.addAll(oModels);
           oAttach.removeAll(oModels);
           
        }
    }
    
   
    public void load(ModelDocument model,ModelDossier folder)
    {
        currentDocument = model;
        currentFolder = folder;
        
        if(currentDocument != null && currentFolder != null)
        {
            try {
                // chargemnet des annexes
                String sql = "select id,libelle from t_annexe where ref_id_folders = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setLong(1, currentFolder.getId());
                ResultSet result = ps.executeQuery();
                while(result.next())
                {
                    ModelAnnexe annexe = new ModelAnnexe();
                    annexe.setId(result.getLong("id"));
                    annexe.setLibelle(result.getString("libelle"));
                    oAnnexes.add(annexe);
                    
                }
               ps.close();
                
                // chargement des liens existants
                String s = "select id,libelle from t_annexe inner join t_link_annexe_document on t_annexe.id = t_link_annexe_document.ref_id_annexe where t_link_annexe_document.ref_id_document = ?";
                PreparedStatement ps2 = ConnectionSQL.getCon().prepareStatement(s);
                ps2.setLong(1, currentFolder.getId());
                ResultSet result2 = ps2.executeQuery();
                while(result2.next())
                {
                    ModelAnnexe annexe = new ModelAnnexe();
                    annexe.setId(result2.getLong("id"));
                    annexe.setLibelle(result2.getString("libelle"));
                    oAttach.add(annexe);
                }
                
                ps2.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(LinksViewController.class.getName()).log(Level.SEVERE, null, ex);
             
                
            }
            
            
        }
    }

    @Override
    public void handle(WindowEvent event) 
    {
       // sauvegarde
        if(event.getSource().getClass() == Button.class)
        {
            for(ModelAnnexe annexe : oAttach)
            {
                try {
                    String sql = "insert into t_link_annexe_document (ref_id_annexe,ref_id_document) values (?,?)";
                    PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                    ps.setLong(1,annexe.getId());
                    ps.setLong(2, currentDocument.getId());
                    ps.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(LinksViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
            
            
    
    
}
