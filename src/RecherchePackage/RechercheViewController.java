/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RecherchePackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelUser;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private TableColumn columnContenu;
    @FXML
    private TableColumn columnDossier;
    @FXML
    private TableColumn columnDocument;
    
    private ModelUser user;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }
    
    @FXML
    public void handleRecherche(ActionEvent event)
    {
       
        try 
        {
            if(rechercheField.getText().isEmpty())
                return;
            
            String recherche = "%" + rechercheField.getText().trim() + "%";
            
            String sql = "select * from t_document inner join t_folders on t_document.ref_id_folders = t_folders.id where contenu LIKE ?";
            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setString(1, recherche);
            ResultSet result = ps.executeQuery();
            while(result.next())
            {
                System.out.println(result.getString("contenu") + " " + result.getString("nom"));
            }
            
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(RechercheViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void load(ModelUser user)
    {
        this.user = user;
    }
    
}
