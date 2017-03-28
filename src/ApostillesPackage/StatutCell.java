/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApostillesPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.ModelApostille;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Thonon
 */
public class StatutCell extends TableCell implements ChangeListener<Boolean>
{
   
    @Override
    protected void updateItem(Object item, boolean empty) 
    {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        
        this.setText(null);
        this.setGraphic(null);

        if(item != null)
        {
            Button button = null;
            
            if(((boolean)item) == false)
            {
                button = new Button("En cours");
                button.setStyle("-fx-background-color:lightgreen");
            }
            else
            {
                button = new Button("Cloturé");
                button.setStyle("-fx-background-color:red");
            }
            button.armedProperty().addListener(this);
            this.setGraphic(button);
        }
    }

    public StatutCell() 
    {
        
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
    { 
        if(newValue == true)
        {
            try {
                
                
                //
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ApostillesPackage/DateOutView.fxml"));
                AnchorPane pane = loader.load();
                DateOutViewController controller = loader.getController();
                Scene scene = new Scene(pane);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                
                // si une date a été choisie, modification du statut
                if(controller.getDateOut() != null)
                {
                    ModelApostille model = (ModelApostille) this.getTableRow().getItem();
                    model.setStatut(!model.isStatut());
                    model.setDateOut(controller.getDateOut());
                    
                    //
                    //enregistrement
                    String sql = "update t_apostille set statut = ?, date_OUT = ? where id = ?";
                    PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                    ps.setBoolean(1, model.isStatut());
                    ps.setDate(2, Date.valueOf(model.getDateOut()));
                    ps.setLong(3, model.getId());
                    ps.execute();
                }
                  
                
            } catch (IOException ex) {
                Logger.getLogger(StatutCell.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(StatutCell.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
