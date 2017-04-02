/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NiceTrackPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class NiceTrackViewController implements Initializable,IController {

    private ModelDossier currentFolder;
    @FXML
    private ComboBox comboNumeros;
   
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    
    }  

    @Override
    public void setModelDossier(ModelDossier currentDossier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelDossier getModelDossier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadModel(ModelDossier currentDossier) 
    {
        this.currentFolder = currentDossier;
         // set numéros
 
        comboNumeros.setItems(this.currentFolder.getoNumeros());
    }
    
}
