/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApostillesPackage;

import DocumentsPackage.*;
import EntityPackage.*;
import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelApostille;
import ModelPackage.ModelDocument;
import ModelPackage.ModelDossier;
import ModelPackage.ModelPersonne;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;


/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class ApostilleViewController  implements Initializable,IController,ListChangeListener,Predicate<ModelApostille>,ChangeListener<LocalDate>{

    @FXML
    private TableView tableApostilles;
    @FXML
    private TableColumn<ModelApostille,String> columnIndex;
    @FXML
    private TableColumn<ModelApostille,String> columnAutorite;
     @FXML
    private TableColumn<ModelApostille,String> columnLibelle;
    @FXML
    private TableColumn<ModelApostille,LocalDate> columnDateIn;
    @FXML
    private TableColumn<ModelApostille,LocalDate> columnDateOut;
    @FXML
    private TableColumn<ModelApostille,Boolean> columnStatut;
  
    @FXML
    private ComboBox comboFiltre;
    @FXML
    private TextField autoriteField;
    @FXML
    private TextField libelleField;
    @FXML
    private TextArea contenuField;
    @FXML
    private DatePicker dateInField;
    @FXML
    private DatePicker dateOutField;
    @FXML
    private Label statutLabel;
    
    @FXML
    private Button buttonModif;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDel;
    
    
    
    // initi comboQualite
    private ObservableList<String> oStatut = FXCollections.observableArrayList();
    // list de Personne
    private ObservableList<ModelApostille> oApostilles = FXCollections.observableArrayList();
      
    private ModelDossier currentDossier;
    // index
    private int index = 1;
   
     
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // disable les field
        disable();
          
            // filtre
            comboFiltre.getItems().add("Tous");
            comboFiltre.getItems().add("En cours");
            comboFiltre.getItems().add("Cloturé");
            comboFiltre.setValue("Tous");
           
            // setitem
           // tableDocuments.setItems(oDocuments);
            // factory
            columnIndex.setCellValueFactory(cellData->cellData.getValue().indexProperty().asString());
            columnLibelle.setCellValueFactory(cellData->cellData.getValue().libelleProperty());
            columnAutorite.setCellValueFactory(cellData->cellData.getValue().autoriteProperty());
            columnDateIn.setCellValueFactory(cellData->cellData.getValue().dateInProperty());
            columnDateOut.setCellValueFactory(cellData->cellData.getValue().dateOutProperty());
            columnStatut.setCellValueFactory(cellData->cellData.getValue().statutProperty());
            columnStatut.setCellFactory(p->new StatutCell());
            
      
            // listener sur le changement du dateOut
            dateInField.valueProperty().addListener(this);
            dateOutField.valueProperty().addListener(this);
        
        // filtre
         FilteredList<ModelApostille> filter = new FilteredList<>(oApostilles,p->true);
         filter.setPredicate(this);
         tableApostilles.setItems(filter);
       
    }
    
    private void disable()
    {
            autoriteField.setDisable(true);
            libelleField.setDisable(true);
            contenuField.setDisable(true);
            dateInField.setDisable(true);
            dateOutField.setDisable(true);
            buttonModif.setDisable(true);
            buttonAdd.setDisable(true);
            buttonDel.setDisable(true);
    }
    
    private void enable()
    {
            autoriteField.setDisable(false);
            libelleField.setDisable(false);
            contenuField.setDisable(false);
            dateInField.setDisable(false);
            dateOutField.setDisable(false);
            buttonModif.setDisable(false);
            buttonAdd.setDisable(false);
            buttonDel.setDisable(false);
    }
    
    @FXML
    public void handleFiltre(ActionEvent event)
    {
        // index à 1
      this.index = 1;
      FilteredList<ModelApostille> filter = new FilteredList<>(oApostilles,p->true);
      filter.setPredicate(this);
      tableApostilles.setItems(filter);
     
    }
    
    @FXML
    public void handleOnUpdate()
    {
        buttonModif.setDisable(false);
    }
    
    @FXML
    public void handleNew(ActionEvent event)
    {
        autoriteField.clear();
        libelleField.clear();
        contenuField.clear();
        dateInField.setValue(null);
        dateOutField.setValue(null);
        
                
       enable();
       
    }
    
    @FXML
    public void handleAdd(ActionEvent event)
    {
        ModelApostille model = new ModelApostille();
        model.setAutorite(autoriteField.getText());
        model.setLibelle(libelleField.getText());
        model.setContenu(contenuField.getText());
        model.setDateIn(dateInField.getValue());
        model.setDateOut(dateOutField.getValue());
        model.setIndex(oApostilles.size() + 1);
        oApostilles.add(model);
        
        
        autoriteField.clear();
        libelleField.clear();
        contenuField.clear();
        dateInField.setValue(null);
        dateOutField.setValue(null);
        
        disable();
        
    }
    
    @FXML
    public void handleDelete()
    {
        ModelApostille model = (ModelApostille) tableApostilles.getSelectionModel().getSelectedItem();
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
                oApostilles.remove(model);
            }

        }

         disable();
    }
    
    @FXML
    public void handleModif()
    {
        ModelApostille model = (ModelApostille) tableApostilles.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            try {
                model.setAutorite(autoriteField.getText());
                model.setLibelle(libelleField.getText());
                model.setContenu(contenuField.getText());
                model.setDateIn(dateInField.getValue());
                model.setDateOut(dateOutField.getValue());
                
                String sql = "update t_apostille set autorite = ?, libelle = ?, contenu = ?, date_IN = ?, date_OUT = ?, statut = ? where id = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setString(1, model.getAutorite());
                ps.setString(2, model.getLibelle());
                ps.setString(3, model.getContenu());
                if(model.getDateIn() != null)
                    ps.setDate(4, Date.valueOf(model.getDateIn()));
                else
                     ps.setDate(4,null);
                
                if(model.getDateOut() != null)
                    ps.setDate(5, Date.valueOf(model.getDateOut()));
                else
                     ps.setDate(5, null);
                
                ps.setBoolean(6, model.isStatut());
                ps.setLong(7, model.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ApostilleViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
         disable();
    }
    
    @FXML
    public void handleSelect()
    {
       ModelApostille model = (ModelApostille) tableApostilles.getSelectionModel().getSelectedItem();
        if(model != null)
        {
        autoriteField.setText(model.getAutorite());
        libelleField.setText(model.getLibelle());
        contenuField.setText(model.getContenu());
        dateInField.setValue(model.getDateIn());
        dateOutField.setValue(model.getDateOut());
            
            
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
                        ModelApostille model = (ModelApostille) o;
                        PreparedStatement ps = null;
                        try {
                            // ajout
                            String sql = "insert into t_apostille (autorite,libelle,contenu,date_IN,date_OUT,statut,ref_id_folders) values "
                                    + "(?,?,?,?,?,?,?)";
                            ps = ConnectionSQL.getCon().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, model.getAutorite());
                            ps.setString(2, model.getLibelle());
                            ps.setString(3, model.getContenu());
                            if(model.getDateIn() != null)   // pour forcer à null lorsqu'aucune date n'est encore enregistrée
                                ps.setDate(4, Date.valueOf(model.getDateIn()));
                            else
                                ps.setDate(4, null);
                            if(model.getDateOut() != null)
                                ps.setDate(5, Date.valueOf(model.getDateOut()));
                            else
                                ps.setDate(5, null);
                            
                            ps.setBoolean(6, model.isStatut());
                            ps.setLong(7, this.currentDossier.getId());
                            ps.execute();
                            ResultSet r = ps.getGeneratedKeys();
                            r.first();
                            model.setId(r.getLong(1));
                        } catch (SQLException ex) {
                            Logger.getLogger(ApostilleViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally 
                        {
                            try {
                                ps.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(ApostilleViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                }
                
                if(c.wasRemoved())
                {
                    for(Object o : c.getRemoved())
                    {
                        try {
                            ModelApostille model = (ModelApostille) o;
                            String sql = "delete from t_apostille where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, model.getId());
                            ps.execute();
                        } catch (SQLException ex) {
                            Logger.getLogger(ApostilleViewController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        oApostilles.removeListener(this);
        try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_apostille where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, currentDossier.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oApostilles.clear();
            int index = 1;
            while(result.next())
            {
                ModelApostille model = new ModelApostille();
                model.setId(result.getLong("id"));
                model.setAutorite(result.getString("autorite"));
                model.setLibelle(result.getString("libelle"));
                model.setContenu(result.getString("contenu"));
                model.setStatut(result.getBoolean("statut"));
                try
                {
                     model.setDateIn(result.getDate("date_IN").toLocalDate());
                }catch(NullPointerException npe)
                {
                    
                }
                try
                {
                    model.setDateOut(result.getDate("date_OUT").toLocalDate());
                }
                catch(NullPointerException npe)
                {
                    
                }
                model.setIndex(index);
                index++;
                // add
                oApostilles.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApostilleViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // ajout des events
        oApostilles.addListener(this);
    }

    
    

    @Override
    public boolean test(ModelApostille t)
    {
        //methode de test pour le filtre à statut
        String fil = (String) comboFiltre.getValue();
        if(fil.equals("Tous"))
        {
            return true;
        }
        
        if(fil.equals("En cours"))
        {
            if(!t.isStatut())
                return true;
        }
        
        if(fil.equals("Cloturé"))
        {
            if(t.isStatut())
                return true;
        }
        
       return false;
               
    }

    // test de la date de fermeture
    @Override
    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue)
    {
       if(observable == dateOutField.valueProperty() && newValue.isBefore(dateInField.getValue()))
       {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur dans la date de clôture !!!");
            alert.setContentText("La date de clôture de l'apostille ne peut être inférieur à la date d'ouverture");
            alert.showAndWait();
            dateOutField.setValue(dateInField.getValue());
       }
       else
           if(observable == dateInField.valueProperty() && newValue.isAfter(dateOutField.getValue()))
            {
                 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Erreur dans la date de d'ouverture !!!");
                 alert.setContentText("La date d'ouverture de l'apostille ne peut être supérieur à la date de clôture");
                 alert.showAndWait();
                 dateInField.setValue(dateOutField.getValue());
            }
    }

}
