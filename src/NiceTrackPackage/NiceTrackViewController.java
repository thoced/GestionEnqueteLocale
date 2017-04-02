/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NiceTrackPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelDossier;
import ModelPackage.ModelNumero;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class NiceTrackViewController implements Initializable {

    private ModelDossier currentFolder;
    
    private ObservableList<ModelNumero> oNumeros;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        oNumeros = FXCollections.observableArrayList();
    }  
    
    public void load(ModelDossier folder)
    {
        currentFolder = folder;
        try {

            // chargement des numéros de gsm
            String sql = "select * from t_numero where ref_id_folders = ?"; 
            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, folder.getId());
            ResultSet result = ps.executeQuery();
            while(result.next())
            {
                ModelNumero model = new ModelNumero();
                model.setId(result.getLong("id"));
                model.setNumero(result.getString("numero"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(NiceTrackViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
