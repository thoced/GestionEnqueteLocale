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
                button = new Button("En cours");
                button.setStyle("-fx-background-color:lightgreen");
            }
            else
            {
                button = new Button("Cloturé");
                button.setStyle("-fx-background-color:red");
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
            ModelApostille model = (ModelApostille) this.getTableRow().getItem();
            
            if(!model.isStatut()) // si le statut est toujours en cours
            {
                try {
                    //
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ApostillesPackage/DateOutView.fxml"));
                    AnchorPane pane = loader.load();
                    DateOutViewController controller = loader.getController();
                    Scene scene = new Scene(pane);
                    Stage stage = new Stage();
                    stage.setTitle("Statut de l'apostille");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.showAndWait();

                    // si une date a été choisie, modification du statut
                    if(controller.getDateOut() != null)
                    {

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
            else
            {
                    // l'utilisateur clic a nouveau sur un bouton déja en statut cloturé
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Changement de statut");
                alert.setContentText("Etes-vous sûr de vouloir modifier le statut en apostille en cours ?");
                ButtonType bOui = new ButtonType("Oui");
                ButtonType bNon = new ButtonType("Non");
                alert.getButtonTypes().setAll(bNon,bOui);
                Optional<ButtonType> ret =  alert.showAndWait();
                if(ret.get() == bOui)
                {
                    try {
                       // ModelApostille model = (ModelApostille) this.getTableRow().getItem();
                        model.setStatut(!model.isStatut());
                        model.setDateOut(null);
                        //
                        //enregistrement
                        String sql = "update t_apostille set statut = ?, date_OUT = NULL where id = ?";
                        PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                        ps.setBoolean(1, model.isStatut());
                        ps.setLong(2, model.getId());
                        ps.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(StatutCell.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }
    
}
