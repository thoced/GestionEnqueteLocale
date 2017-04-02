/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelDossier;
import ModelPackage.ModelNumero;
import ModelPackage.ModelPersonne;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class NumeroViewController implements Initializable,IController,ListChangeListener {

    @FXML
    private TableView tableNumeros;
    @FXML
    private TableColumn<ModelNumero,String> columnType;
     @FXML
    private TableColumn<ModelNumero,String> columnNumero;
    @FXML
    private TableColumn<ModelNumero,String> columnNationalite;
    @FXML
    private TableColumn<ModelNumero,String> columnOwner;
   
    @FXML
    private ComboBox comboType;
    @FXML
    private TextField numeroField;
    @FXML
    private TextField nationaliteField;
    @FXML
    private TextField ownerField;
       
    
    @FXML
    private Button buttonModif;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDel;
    
    // initi comboQualite
    private ObservableList<String> oType = FXCollections.observableArrayList();
    // list de Personne
    private ObservableList<ModelNumero> oNumeros = FXCollections.observableArrayList();
    
    private ModelDossier currentDossier;
     
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // ajout des event

            // initi
            oType.add("Téléphone");
            oType.add("Gsm");
            oType.add("Email");
            oType.add("Adresse web");
            comboType.setItems(oType);
            comboType.setValue(oType.get(0));
           
            // setitem
            //tableNumeros.setItems(oNumeros);
            // factory
            columnType.setCellValueFactory(cellData->cellData.getValue().typeProperty());
            columnNumero.setCellValueFactory(cellData->cellData.getValue().numeroProperty());
            columnNationalite.setCellValueFactory(cellData->cellData.getValue().nationaliteProperty());
            columnOwner.setCellValueFactory(cellData->cellData.getValue().ownerProperty());
            
            
           
    }   
    
    private void disable()
    {
            comboType.setDisable(true);
            numeroField.setDisable(true);
            nationaliteField.setDisable(true);
            ownerField.setDisable(true);
            buttonModif.setDisable(true);
            buttonAdd.setDisable(true);
            buttonDel.setDisable(true);
    }
    
    private void enable()
    {
            comboType.setDisable(false);
            numeroField.setDisable(false);
            nationaliteField.setDisable(false);
            ownerField.setDisable(false);
            buttonModif.setDisable(false);
            buttonAdd.setDisable(false);
            buttonDel.setDisable(false);
    }
    
    @FXML
    public void handleOnUpdate()
    {
        buttonModif.setDisable(false);
    }
    
    @FXML
    public void handleNew(ActionEvent event)
    {
        comboType.setValue(oType.get(0));
        numeroField.clear();
        nationaliteField.clear();
        ownerField.clear();
        enable();
       
    }
    
    @FXML
    public void handleAdd(ActionEvent event)
    {
        ModelNumero model = new ModelNumero();
        model.setType((String)comboType.getSelectionModel().getSelectedItem());
        model.setNumero(numeroField.getText());
        model.setNationalite(nationaliteField.getText());
        model.setOwner(ownerField.getText());
        this.currentDossier.getoNumeros().add(model);
        
        
        numeroField.clear();
        nationaliteField.clear();
        comboType.setValue(oType.get(0));
        ownerField.clear();
        
        disable();
        
    }
    
    @FXML
    public void handleDelete()
    {
        ModelNumero model = (ModelNumero) tableNumeros.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setContentText("Etes-vous sûr de vouloir supprimer cet élément ?");
            ButtonType bOui = new ButtonType("Oui");
            ButtonType bNon = new ButtonType("Non");
            alert.getButtonTypes().setAll(bNon,bOui);
            Optional<ButtonType> ret = alert.showAndWait();
            if(ret.get() == bOui)
            {
                this.currentDossier.getoNumeros().remove(model);
            }
            
        }
        
       
         
         disable();
    }
    
    @FXML
    public void handleModif()
    {
        ModelNumero model = (ModelNumero) tableNumeros.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            try {
                model.setType((String)comboType.getValue());
                model.setNumero(numeroField.getText());
                model.setNationalite(nationaliteField.getText());
                model.setOwner(ownerField.getText());
                
                String sql = "update t_numero set type = ?, numero = ?,nationalite = ?,owner = ? where id = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setString(1, model.getType());
                ps.setString(2, model.getNumero());
                ps.setString(3, model.getNationalite());
                ps.setString(4, model.getOwner());
                ps.setLong(5, model.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(NumeroViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
         disable();
    }
    
    @FXML
    public void handleSelect()
    {
        ModelNumero model = (ModelNumero) tableNumeros.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            
            comboType.setValue(model.getType());
            numeroField.setText(model.getNumero());
            nationaliteField.setText(model.getNationalite());
            ownerField.setText(model.getOwner());
                     
            enable();
            
        }
        else
            disable();
    }

    @Override
    public void onChanged(Change c) 
    {
        if(c != null)
        {
            while(c.next())
            {
                if(c.wasAdded())
                {
                    for(Object o : c.getAddedSubList())
                    {
                        ModelNumero model = (ModelNumero) o;
                        PreparedStatement ps = null;
                        try {
                            // ajout
                            String sql = "insert into t_numero (type,numero,nationalite,owner,ref_id_folders) values "
                                    + "(?,?,?,?,?)";
                            ps = ConnectionSQL.getCon().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, model.getType());
                            ps.setString(2, model.getNumero());
                            ps.setString(3, model.getNationalite());
                            ps.setString(4, model.getOwner());
                            ps.setLong(5, this.currentDossier.getId());
                            ps.execute();
                            ResultSet r = ps.getGeneratedKeys();
                            r.first();
                            model.setId(r.getLong(1));
                        } catch (SQLException ex) {
                            Logger.getLogger(NumeroViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally 
                        {
                            try {
                                ps.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(NumeroViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                }
                
                if(c.wasRemoved())
                {
                    for(Object o : c.getRemoved())
                    {
                        try {
                            ModelNumero model = (ModelNumero) o;
                            String sql = "delete from t_numero where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, model.getId());
                            ps.execute();
                        } catch (SQLException ex) {
                            Logger.getLogger(NumeroViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }
        }
    }

    @Override
    public void setModelDossier(ModelDossier currentDossier) 
    {
       
    }

    @Override
    public ModelDossier getModelDossier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadModel(ModelDossier currentDossier)
    {
        this.currentDossier = currentDossier;
        
        this.currentDossier.getoNumeros().removeListener(this);
       
        // ajout des events
        this.currentDossier.getoNumeros().addListener(this);
        
       this.tableNumeros.setItems(this.currentDossier.getoNumeros());
    }
     
}
