/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NiceTrackPackage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author Thonon
 */
public class NiceTrackViewImportController extends NiceTrackViewController implements EventHandler<ActionEvent>
{
    private boolean isImport = false;
    
    @FXML
    private Button bCancel;
    @FXML
    private Button bImporter;
    
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
        
        
        
        // import
        bImporter.setOnAction(this);
        bCancel.setOnAction(this);
        
    }

    public boolean isIsImport() {
        return isImport;
    }

    @Override
    public void handle(ActionEvent event) 
    {
       if(event.getSource() == bImporter)
       {
           isImport = true;
       }
       
       if(event.getSource() == bCancel)
       {
           isImport = false;
       }
       
       bCancel.getScene().getWindow().hide();
    }
    
    

    
    
}
