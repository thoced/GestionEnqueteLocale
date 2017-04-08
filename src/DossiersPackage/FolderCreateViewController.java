/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DossiersPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelDossier;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class FolderCreateViewController implements Initializable, EventHandler<MouseEvent> {

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
    
    @FXML
    private Label messageError;
    
    private ObservableList<ModelGroup> oGroups;
    
    private boolean createFolder = false;
    
    
    private ModelDossier dossier;
    
    
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
       
       // reset message error
       nameFolderField.setOnMousePressed(this);
       listGroups.setOnMousePressed(this);
        
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
    
    @FXML
    public void handleCancel()
    {
        createFolder = false;
        // cancel
        nameFolderField.getScene().getWindow().hide();
    }
    
     @FXML
    public void handleConfirmer()
    {
        if(nameFolderField.getText().isEmpty() || listGroups.getSelectionModel().isEmpty())
        {
            messageError.setText("Un nom de dossier est obligatoire et/ou un groupe doit être au minimum sélectionné");
           
        }else
        {
 
        createFolder = true;
        
        // creation du modeldossier
        dossier = new ModelDossier();
        dossier.setNomDossier(nameFolderField.getText());
        dossier.setCommentaire(informationField.getText());
        // ajout des groupes sélectionnés
        dossier.getoGroups().addAll(listGroups.getSelectionModel().getSelectedItems());
        
         // fermeture de la vue
        nameFolderField.getScene().getWindow().hide();
      
        }
       
    }

    public boolean isCreateFolder() {
        return createFolder;
    }

    @Override
    public void handle(MouseEvent event) 
    {
       messageError.setText("");
    }

    public ModelDossier getDossier() {
        return dossier;
    }
    
    
}
