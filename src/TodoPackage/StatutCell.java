/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TodoPackage;

import ApostillesPackage.*;
import ModelPackage.ConnectionSQL;
import ModelPackage.ModelApostille;
import ModelPackage.ModelTodo;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Thonon
 */
public class StatutCell extends TableCell implements ChangeListener<Boolean>, EventHandler
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
                button = new Button("A faire");
                button.setStyle("-fx-background-color:lightgreen");
            }
            else
            {
                button = new Button("Terminé");
                button.setStyle("-fx-background-color:orange");
            }
            button.addEventHandler(ActionEvent.ACTION, this);
            this.setGraphic(button);
        }
    }

    public StatutCell() 
    {
        
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) 
    { 
        
        
    }

    @Override
    public void handle(Event event)
    {
       // récupération du model en cours
            ModelTodo model = (ModelTodo) this.getTableRow().getItem();

                    try {
                       // ModelApostille model = (ModelApostille) this.getTableRow().getItem();
                        model.setStatut(!model.isStatut());
                        
                        //
                        //enregistrement
                        String sql = "update t_todo set statut = ? where id = ?";
                        PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                        ps.setBoolean(1, model.isStatut());
                        ps.setLong(2, model.getId());
                        ps.execute();
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(StatutCell.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            // refresh de la tablevieuw
            this.getTableView().refresh();
    }
    
}
