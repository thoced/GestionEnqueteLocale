/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Thonon
 */
public class ModelStatic 
{

    public ModelStatic(ModelDossier folder) 
    {
        oNumeros = FXCollections.observableArrayList();
        try {
            String sNum = "select * from t_numero where ref_id_folder = ?";
            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sNum);
            ps.setLong(1, folder.getId());
            ResultSet result = ps.executeQuery();
            while(result.next())
            {
                ModelNumero numero = new ModelNumero();
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelStatic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public ObservableList<ModelNumero> oNumeros;

    public ObservableList<ModelNumero> getoNumeros() {
        return oNumeros;
    }
    
    
}
