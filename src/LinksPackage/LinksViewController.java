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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class LinksViewController implements Initializable,EventHandler<WindowEvent> {

    @FXML
    private TableView tableAnnexes;
    @FXML
    private TableColumn<ModelAnnexeChecked,String> columnLibelle;
    @FXML
    private TableColumn<ModelAnnexeChecked,Boolean> columnCheck;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonConfirmer;
    

    @FXML
    private AnchorPane anchor;
    
    private ObservableList<ModelAnnexeChecked> oAnnexes = FXCollections.observableArrayList();
    private ObservableList<ModelAnnexeChecked> oAttach = FXCollections.observableArrayList();
    
   
    
    private ModelDocument currentDocument;
    private ModelDossier currentFolder;
    
    // flag close and write
    private boolean closeAndWrite = false;

    public ObservableList<ModelAnnexeChecked> getoAttach() {
        return oAttach;
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       // setitems
      tableAnnexes.setItems(oAnnexes);
      // factoru
      columnLibelle.setCellValueFactory(cellData->cellData.getValue().libelleProperty());
      columnCheck.setCellValueFactory(cellData->cellData.getValue().checkedProperty());
      columnCheck.setCellFactory(r->new CheckCell());
       
    }
    @FXML
    public void handleConfirmer(ActionEvent event) 
    {
        // Suppression 
        String s = "delete from t_link_annexe_document where ref_id_document = ?";
        PreparedStatement ps;
        try 
        {
            ps = ConnectionSQL.getCon().prepareStatement(s);
            ps.setLong(1, currentDocument.getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(LinksViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // confirmation
        for(ModelAnnexeChecked annexe : oAnnexes)
        {
            if(annexe.getChecked())
            {
                try {
                    String sql = "insert into t_link_annexe_document (ref_id_annexe,ref_id_document) values (?,?)";
                    ps = ConnectionSQL.getCon().prepareStatement(sql);
                    ps.setLong(1, annexe.getId());
                    ps.setLong(2, currentDocument.getId());
                    ps.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(LinksViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        // fermeture
        tableAnnexes.getScene().getWindow().hide();
    }
    
    @FXML
    public void handleCancel(ActionEvent event) throws SQLException
    {
      
        tableAnnexes.getScene().getWindow().hide();
    }
   
    public void load(ModelDocument model,ModelDossier folder)
    {
        currentDocument = model;
        currentFolder = folder;
        
        if(currentDocument != null && currentFolder != null)
        {
            try {
                // chargemnet des annexes lié
                String s = "select id,libelle from t_annexe inner join t_link_annexe_document on t_annexe.id = t_link_annexe_document.ref_id_annexe where t_link_annexe_document.ref_id_document = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(s);
                ps.setLong(1, currentDocument.getId());
                ResultSet result = ps.executeQuery();
                while(result.next())
                {
                    ModelAnnexeChecked annexe = new ModelAnnexeChecked();
                    annexe.setId(result.getLong("id"));
                    annexe.setLibelle(result.getString("libelle"));
                    oAttach.add(annexe);
                    
                }
               ps.close();
                
                // chargement des liens existants
                s = "select id,libelle from t_annexe where ref_id_folders = ?";
                PreparedStatement ps2 = ConnectionSQL.getCon().prepareStatement(s);
                ps2.setLong(1, currentFolder.getId());
                ResultSet result2 = ps2.executeQuery();
                while(result2.next())
                {
                    ModelAnnexeChecked annexe = new ModelAnnexeChecked();
                    annexe.setId(result2.getLong("id"));
                    annexe.setLibelle(result2.getString("libelle"));
                    annexe.setChecked(false);
                    for(ModelAnnexeChecked a : oAttach)
                    {
                        if(a.getId() == annexe.getId())
                        {
                            annexe.setChecked(true);
                            break;
                        }
                    }
                    
                    
                    oAnnexes.add(annexe);
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
       
        
    }
            
            
    
    
}
