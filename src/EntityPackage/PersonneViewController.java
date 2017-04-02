/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelDossier;
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
public class PersonneViewController implements Initializable,IController,ListChangeListener {

    @FXML
    private TableView tablePersonnes;
    @FXML
    private TableColumn<ModelPersonne,String> columnNom;
     @FXML
    private TableColumn<ModelPersonne,String> columnPrenom;
    @FXML
    private TableColumn<ModelPersonne,String> columnAdresse;
    @FXML
    private TableColumn<ModelPersonne,LocalDate> columnDateNaissance;
    @FXML
    private TableColumn<ModelPersonne,String> columnQualite;
    @FXML
    private ComboBox comboQualite;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField adresseField;
    @FXML
    private DatePicker dateField;
    
    
    @FXML
    private Button buttonModif;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDel;
    
    // initi comboQualite
    private ObservableList<String> oQualite = FXCollections.observableArrayList();
    // list de Personne
    private ObservableList<ModelPersonne> oPersonnes = FXCollections.observableArrayList();
    
    private ModelDossier currentDossier;
     
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // ajout des event
            disable();

            // initi
            oQualite.add("Victime");
            oQualite.add("Suspect");
            oQualite.add("Témoin");
            oQualite.add("Autre");
            comboQualite.setItems(oQualite);
            comboQualite.setValue(oQualite.get(0));
           
            // setitem
            tablePersonnes.setItems(oPersonnes);
            // factory
            columnNom.setCellValueFactory(cellData->cellData.getValue().nomProperty());
            columnPrenom.setCellValueFactory(cellData->cellData.getValue().prenomProperty());
            columnAdresse.setCellValueFactory(cellData->cellData.getValue().adresseProperty());
            columnDateNaissance.setCellValueFactory(cellData->cellData.getValue().dateNaissanceProperty());
            columnQualite.setCellValueFactory(cellData->cellData.getValue().qualiteProperty());
            
           
    }   
    
    private void disable()
    {
            nomField.setDisable(true);
            prenomField.setDisable(true);
            adresseField.setDisable(true);
            dateField.setDisable(true);
            buttonModif.setDisable(true);
            buttonAdd.setDisable(true);
            buttonDel.setDisable(true);
    }
    
    private void enable()
    {
            nomField.setDisable(false);
            prenomField.setDisable(false);
            adresseField.setDisable(false);
            dateField.setDisable(false);
            comboQualite.setDisable(false);
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
        nomField.clear();
        prenomField.clear();
        adresseField.clear();
        dateField.setValue(null);
        comboQualite.setValue(oQualite.get(0));
        enable();
       
    }
    
    @FXML
    public void handleAdd(ActionEvent event)
    {
        ModelPersonne model = new ModelPersonne();
        model.setNom(nomField.getText());
        model.setPrenom(prenomField.getText());
        model.setAdresse(adresseField.getText());
        model.setDateNaissance(dateField.getValue());
        model.setQualite((String)comboQualite.getSelectionModel().getSelectedItem());
        oPersonnes.add(model);
        
        
        nomField.clear();
        prenomField.clear();
        adresseField.clear();
        dateField.setValue(null);
        comboQualite.setValue(oQualite.get(0));
        
        disable();
        
    }
    
    @FXML
    public void handleDelete()
    {
        ModelPersonne model = (ModelPersonne) tablePersonnes.getSelectionModel().getSelectedItem();
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
                oPersonnes.remove(model);
            }
            
        }
        
       
         
         disable();
    }
    
    @FXML
    public void handleModif()
    {
        ModelPersonne model = (ModelPersonne) tablePersonnes.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            try {
                model.setNom(nomField.getText());
                model.setPrenom(prenomField.getText());
                model.setAdresse(adresseField.getText());
                model.setDateNaissance(dateField.getValue());
                model.setQualite((String)comboQualite.getValue());
                
                String sql = "update t_personne set nom = ?, prenom = ?,adresse = ?,date_naissance = ?,qualite = ? where id = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setString(1, model.getNom());
                ps.setString(2, model.getPrenom());
                ps.setString(3, model.getAdresse());
                ps.setDate(4, Date.valueOf(model.getDateNaissance()));
                ps.setString(5, model.getQualite());
                ps.setLong(6, model.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(PersonneViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
         disable();
    }
    
    @FXML
    public void handleSelect()
    {
        ModelPersonne model = (ModelPersonne) tablePersonnes.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            
            nomField.setText(model.getNom());
            prenomField.setText(model.getPrenom());
            adresseField.setText(model.getAdresse());
            dateField.setValue(model.getDateNaissance());
            comboQualite.setValue(model.getQualite());
            
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
                        ModelPersonne model = (ModelPersonne) o;
                        PreparedStatement ps = null;
                        try {
                            // ajout
                            String sql = "insert into t_personne (nom,prenom,adresse,date_naissance,qualite,ref_id_folders) values "
                                    + "(?,?,?,?,?,?)";
                            ps = ConnectionSQL.getCon().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, model.getNom());
                            ps.setString(2, model.getPrenom());
                            ps.setString(3, model.getAdresse());
                            ps.setDate(4, Date.valueOf(model.getDateNaissance()));
                            ps.setString(5, model.getQualite());
                            ps.setLong(6, this.currentDossier.getId());
                            ps.execute();
                            ResultSet r = ps.getGeneratedKeys();
                            r.first();
                            model.setId(r.getLong(1));
                        } catch (SQLException ex) {
                            Logger.getLogger(PersonneViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally 
                        {
                            try {
                                ps.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(PersonneViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                }
                
                if(c.wasRemoved())
                {
                    for(Object o : c.getRemoved())
                    {
                        try {
                            ModelPersonne model = (ModelPersonne) o;
                            String sql = "delete from t_personne where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, model.getId());
                            ps.execute();
                        } catch (SQLException ex) {
                            Logger.getLogger(PersonneViewController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        oPersonnes.removeListener(this);
        try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_personne where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, currentDossier.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oPersonnes.clear();
            while(result.next())
            {
                ModelPersonne model = new ModelPersonne();
                model.setId(result.getLong("id"));
                model.setNom(result.getString("nom"));
                model.setPrenom(result.getString("prenom"));
                model.setAdresse(result.getString("adresse"));
                model.setDateNaissance(result.getDate("date_naissance").toLocalDate());
                model.setQualite(result.getString("qualite"));
                // add
                oPersonnes.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonneViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // ajout des events
        oPersonnes.addListener(this);
    }
     
}
