/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NiceTrackPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelDossier;
import ModelPackage.ModelNice;
import ModelPackage.ModelNiceWrapper;
import ModelPackage.ModelNumero;
import ModelPackage.TestWrapper;
import ModelPackage.Voiture;
import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class NiceTrackViewController implements Initializable,IController {

    private ModelDossier currentFolder;
    @FXML
    private ComboBox comboNumeros;
    @FXML
    private TableView tableNices;
    @FXML
    private TableColumn<ModelNice,Long> columnEventId;
    @FXML
    private TableColumn<ModelNice,String> columnStartDate;
    @FXML
    private TableColumn<ModelNice,String> columnStartTime;
    @FXML
    private TableColumn<ModelNice,String> columnEndDate;
    @FXML
    private TableColumn<ModelNice,String> columnEndTime;
    @FXML
    private TableColumn<ModelNice,String> columnEventType;
    @FXML
    private TableColumn<ModelNice,String> columnNumCaller;
    @FXML
    private TableColumn<ModelNice,String> columnNumCalled;
    @FXML
    private TableColumn<ModelNice,String> columCategorie;
    @FXML
    private TableColumn<ModelNice,String> columSens;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        columnEventId.setCellValueFactory(cellData->cellData.getValue().eventIdProperty());
        columnStartDate.setCellValueFactory(cellData->cellData.getValue().startDateProperty());
        columnStartTime.setCellValueFactory(cellData->cellData.getValue().startTimeProperty());
        columnEndDate.setCellValueFactory(cellData->cellData.getValue().endDateProperty());
        columnEndTime.setCellValueFactory(cellData->cellData.getValue().endTimeProperty());
        columnEventType.setCellValueFactory(cellData->cellData.getValue().eventTypeProperty());
        columnNumCaller.setCellValueFactory(cellData->cellData.getValue().numCallerProperty());
        columnNumCalled.setCellValueFactory(cellData->cellData.getValue().numCalledProperty());
        columCategorie.setCellValueFactory(cellData->cellData.getValue().categorieProperty());
        columSens.setCellValueFactory(cellData->cellData.getValue().sensProperty());
    }  
    
    @FXML
    public void handleImport(ActionEvent event)
    {
        FileChooser choose = new FileChooser();
        File file = choose.showOpenDialog(comboNumeros.getScene().getWindow());
        if(file != null)
        {
            try {
                JAXBContext context = JAXBContext
                        .newInstance(ModelNiceWrapper.class);
                Unmarshaller um = context.createUnmarshaller();
                // lecture
               ModelNiceWrapper wrapper =  (ModelNiceWrapper) um.unmarshal(file);
               this.currentFolder.setNiceWrapper(wrapper);
               // création d'une observablelist nécessaire pour la tableNices
               ObservableList<ModelNice> oo = FXCollections.observableArrayList();
               oo.addAll(this.currentFolder.getNiceWrapper().getoNices());

               tableNices.setItems(oo);
            
                
            } catch (JAXBException ex) {
                Logger.getLogger(NiceTrackViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
          
        }
        
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
