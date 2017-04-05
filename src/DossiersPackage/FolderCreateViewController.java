/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DossiersPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelGroup;
import ModelPackage.ModelUser;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class FolderCreateViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField nameFolderField;
    
    @FXML
    private TextArea informationField;
    
    @FXML
    private ListView listGroups;
    
    @FXML
    private TextField ownerField;
    
    private ObservableList<ModelGroup> oGroups;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        listGroups.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listGroups.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ObservableList<ModelGroup>>() 
        {
            @Override
            public void changed(ObservableValue<? extends ObservableList<ModelGroup>> observable, ObservableList<ModelGroup> oldValue, ObservableList<ModelGroup> newValue)
            {
               
            }
        });
        
       oGroups = FXCollections.observableArrayList();
       listGroups.setItems(oGroups);
        
    }    
    
    public void load(ModelUser user) throws SQLException
    {
        // owner
        ownerField.setText(user.getLogin());
        
        // chargement des groupes
        String sql = "select * from t_group";
        Statement ps = ConnectionSQL.getCon().createStatement();
        ResultSet result = ps.executeQuery(sql);
        while(result.next())
        {
            ModelGroup model = new ModelGroup();
            model.setId(result.getLong("id"));
            model.setGroupName(result.getString("group_name"));
            oGroups.add(model);
        }
        
    }
    
    
}
