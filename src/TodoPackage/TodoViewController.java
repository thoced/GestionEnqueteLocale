/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TodoPackage;

import ApostillesPackage.*;
import UtilsPackage.DateCell;
import DocumentsPackage.*;
import EntityPackage.*;
import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelApostille;
import ModelPackage.ModelDocument;
import ModelPackage.ModelDossier;
import ModelPackage.ModelPersonne;
import ModelPackage.ModelTodo;
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
public class TodoViewController  implements Initializable,IController,ListChangeListener,Predicate<ModelTodo>,ChangeListener<LocalDate>{

    @FXML
    private TableView tableTodos;
    @FXML
    private TableColumn<ModelTodo,String> columnIndex;
    @FXML
    private TableColumn<ModelTodo,String> columnLibelle;
    @FXML
    private TableColumn<ModelTodo,LocalDate> columnDateCreation;
    @FXML
    private TableColumn<ModelTodo,LocalDate> columnDateRappel;
    @FXML
    private TableColumn<ModelTodo,Boolean> columnStatut;
  
    @FXML
    private ComboBox comboFiltre;
    @FXML
    private TextField libelleField;
    @FXML
    private TextArea contenuField;
    @FXML
    private DatePicker dateCreation;
    @FXML
    private DatePicker dateRappel;
   // @FXML
   // private DatePicker dateOutField;
    @FXML
    private Label statutLabel;
    
    @FXML
    private Button buttonModif;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDel;
    
    
    
    // initi comboQualite
    private ObservableList<String> oFiltre = FXCollections.observableArrayList();
         
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
            columnDateCreation.setCellValueFactory(cellData->cellData.getValue().dateCreationProperty());
            columnDateRappel.setCellValueFactory(cellData->cellData.getValue().dateRappelProperty());
            columnStatut.setCellValueFactory(cellData->cellData.getValue().statutProperty());
            // factory
            columnStatut.setCellFactory(p->new TodoPackage.StatutCell());
           /* columnDateIn.setCellFactory(p-> new DateCell());
            columnDateOut.setCellFactory(p-> new DateCell());*/
            
            
      
            // listener sur le changement du dateOut
          //  dateInField.valueProperty().addListener(this);
           // dateOutField.valueProperty().addListener(this);
        
        // filtre
        /* FilteredList<ModelApostille> filter = new FilteredList<>(oApostilles,p->true);
         filter.setPredicate(this);
         tableApostilles.setItems(filter);*/
        
        // lors du changement
        tableTodos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelTodo>() 
        {
            @Override
            public void changed(ObservableValue<? extends ModelTodo> observable, ModelTodo oldValue, ModelTodo newValue) 
            {
              
                
                libelleField.setText(newValue.getLibelle());
                contenuField.setText(newValue.getContenu());
                dateCreation.setValue(newValue.getDateCreation());
                dateRappel.setValue(newValue.getDateRappel());
                 enable();
                dateCreation.setDisable(true);
            }
        });
       
        // lors de la selection d'une date
        dateRappel.valueProperty().addListener(new ChangeListener<LocalDate>() 
        {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) 
            {
                if(dateCreation.getValue() != null)
                {
                    if(newValue.isBefore(dateCreation.getValue()))
                    {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erreur dans les dates");
                        alert.setContentText("La date de rappel ne peut être inférieur à la date de création");
                        alert.showAndWait();
                        
                        dateRappel.setValue(oldValue);
                    }
                    
                   
                           
                }
            }
        });
        
        
        
    }
    
    private void disable()
    {
            
            libelleField.setDisable(true);
            contenuField.setDisable(true);
            dateCreation.setDisable(true);
            dateRappel.setDisable(true);
            //dateOutField.setDisable(true);
            buttonModif.setDisable(true);
            buttonAdd.setDisable(true);
            buttonDel.setDisable(true);
    }
    
    private void enable()
    {
             libelleField.setDisable(false);
            contenuField.setDisable(false);
            dateCreation.setDisable(true);
            dateRappel.setDisable(false);
            //dateOutField.setDisable(true);
            buttonModif.setDisable(false);
            buttonAdd.setDisable(false);
            buttonDel.setDisable(false);
    }
    
    @FXML
    public void handleFiltre(ActionEvent event)
    {
        // index à 1
      this.index = 1;
      FilteredList<ModelTodo> filter = new FilteredList<>(this.currentDossier.getoTodos(),p->true);
      filter.setPredicate(this);
      tableTodos.setItems(filter);
     
    }
    
    @FXML
    public void handleOnUpdate()
    {
        buttonModif.setDisable(false);
    }
    
    @FXML
    public void handleNew(ActionEvent event)
    {
        libelleField.clear();
        contenuField.clear();
        dateCreation.setValue(LocalDate.now());
        dateRappel.setValue(null);
      
        //dateOutField.setValue(null);
        
                
       enable();
       
    }
    
    @FXML
    public void handleAdd(ActionEvent event)
    {
        ModelTodo model = new ModelTodo();
        model.setLibelle(libelleField.getText());
        model.setContenu(contenuField.getText());
        model.setDateCreation(dateCreation.getValue());
        model.setDateRappel(dateRappel.getValue());
        //model.setDateOut(dateOutField.getValue());
        model.setIndex(this.currentDossier.getoTodos().size() + 1);
        this.currentDossier.getoTodos().add(model);
        
        
      
        libelleField.clear();
        contenuField.clear();
        dateCreation.setValue(null);
        dateRappel.setValue(null);
      
        //dateOutField.setValue(null);
        
        disable();
        
    }
    
    @FXML
    public void handleDelete()
    {
        ModelTodo model = (ModelTodo) tableTodos.getSelectionModel().getSelectedItem();
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
                this.currentDossier.getoTodos().remove(model);
            }

        }

         disable();
    }
    
    @FXML
    public void handleModif()
    {
        ModelTodo model = (ModelTodo) tableTodos.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            try {
                model.setLibelle(libelleField.getText());
                model.setContenu(contenuField.getText());
                model.setDateCreation(dateCreation.getValue());
                model.setDateRappel(dateRappel.getValue());
               // model.setDateOut(dateOutField.getValue());
                
                String sql = "update t_todo "
                        + "set libelle = ?,"
                        + "contenu = ?,"
                        + "date_rappel = ?,"
                        + "statut = ? where id = ?";
                        
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setString(1, model.getLibelle());
                ps.setString(2, model.getContenu());
                if(model.getDateRappel() != null)
                    ps.setDate(3, java.sql.Date.valueOf(model.getDateRappel()));
                else
                    ps.setDate(3, null);
                
                ps.setBoolean(4, model.isStatut());
                ps.setLong(5, model.getId());
                ps.executeUpdate();
                ps.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(TodoViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
          
            
            
        }
        
         disable();
    }
    
    @FXML
    public void handleSelect()
    {
       
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
                        ModelTodo model = (ModelTodo) o;
                        PreparedStatement ps = null;
                        try {
                            // ajout
                            String sql = "insert into t_todo (libelle,contenu,date_creation,date_rappel,statut,ref_id_folders) values "
                                    + "(?,?,?,?,?,?)";
                            ps = ConnectionSQL.getCon().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, model.getLibelle());
                            ps.setString(2, model.getContenu());
                          
                            if(model.getDateCreation()!= null)   // pour forcer à null lorsqu'aucune date n'est encore enregistrée
                                ps.setDate(3, Date.valueOf(model.getDateCreation()));
                            else
                                ps.setDate(3, null);
                            
                            if(model.getDateRappel() != null)
                                ps.setDate(4, Date.valueOf(model.getDateRappel()));
                            else
                                ps.setDate(4, null);
                            
                            ps.setBoolean(5, model.isStatut());
                            
                            ps.setLong(6, this.currentDossier.getId());
                            ps.execute();
                            ResultSet r = ps.getGeneratedKeys();
                            r.first();
                            model.setId(r.getLong(1));
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(TodoViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally 
                        {
                            try {
                                ps.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(TodoViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                }
                
                if(c.wasRemoved())
                {
                    for(Object o : c.getRemoved())
                    {
                        try {
                            ModelTodo model = (ModelTodo) o;
                            String sql = "delete from t_todo where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, model.getId());
                            ps.execute();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(TodoViewController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        this.currentDossier.getoTodos().removeListener(this);

        // ajout des events
        this.currentDossier.getoTodos().addListener(this);
        
         FilteredList<ModelTodo> filter = new FilteredList<>(this.currentDossier.getoTodos(),p->true);
         filter.setPredicate(this);
         tableTodos.setItems(filter);
    }

    
    

    @Override
    public boolean test(ModelTodo t)
    {
        //methode de test pour le filtre à statut
        /*String fil = (String) comboFiltre.getValue();
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
        }*/
        
       return true;
               
    }

    // test de la date de fermeture
    @Override
    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue)
    {
      /* if(observable == dateOutField.valueProperty() && newValue.isBefore(dateInField.getValue()))
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
            }*/
    }

}
