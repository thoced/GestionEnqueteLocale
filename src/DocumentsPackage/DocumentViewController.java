/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentsPackage;

import EntityPackage.*;
import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
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
public class DocumentViewController implements Initializable,IController,ListChangeListener,Predicate<ModelDocument>{

    @FXML
    private TableView tableDocuments;
    @FXML
    private TableColumn<ModelDocument,String> columnIndex;
    @FXML
    private TableColumn<ModelDocument,String> columnType;
     @FXML
    private TableColumn<ModelDocument,String> columnTitre;
    @FXML
    private TableColumn<ModelDocument,LocalDate> columnDate;
    @FXML
    private TableColumn<ModelDocument,String> columnReference;
    @FXML
    private ComboBox comboType;
    @FXML
    private ComboBox comboFiltre;
    @FXML
    private TextField titreField;
    @FXML
    private TextArea commentaireField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField referenceField;
    
    
    @FXML
    private Button buttonModif;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDel;
    
    // initi comboQualite
    private ObservableList<String> oType = FXCollections.observableArrayList();
    // list de Personne
    private ObservableList<ModelDocument> oDocuments = FXCollections.observableArrayList();
      
    private ModelDossier currentDossier;
    // index
    private int index = 1;
   
     
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // disable les field
        disable();
        
        try {
            // ajout des event
            
            // initi
            String sql = "select * from t_type_document";
            Statement st = ConnectionSQL.getCon().createStatement();
            ResultSet result = st.executeQuery(sql);
            while(result.next())
            {
                oType.add(result.getString("type"));
            }
            // type
            comboType.setItems(oType);
            // filtre
            comboFiltre.setItems(oType);
            comboFiltre.getItems().add("Tous");
            comboFiltre.setValue("Tous");
           
            // setitem
           // tableDocuments.setItems(oDocuments);
            // factory
            columnIndex.setCellValueFactory(cellData->cellData.getValue().indexProperty().asString());
            columnType.setCellValueFactory(cellData->cellData.getValue().typeProperty());
            columnTitre.setCellValueFactory(cellData->cellData.getValue().titreProperty());
            columnDate.setCellValueFactory(cellData->cellData.getValue().dateProperty());
            columnReference.setCellValueFactory(cellData->cellData.getValue().referenceProperty());
        } catch (SQLException ex) {
            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // filtre
         FilteredList<ModelDocument> filter = new FilteredList<>(oDocuments,p->true);
         filter.setPredicate(this);
         tableDocuments.setItems(filter);
       
    }
    
    private void disable()
    {
            comboType.setDisable(true);
            titreField.setDisable(true);
            commentaireField.setDisable(true);
            dateField.setDisable(true);
            referenceField.setDisable(true);
            buttonModif.setDisable(true);
            buttonAdd.setDisable(true);
            buttonDel.setDisable(true);
    }
    
    private void enable()
    {
            comboType.setDisable(false);
            titreField.setDisable(false);
            commentaireField.setDisable(false);
            dateField.setDisable(false);
            referenceField.setDisable(false);
            buttonModif.setDisable(false);
            buttonAdd.setDisable(false);
            buttonDel.setDisable(false);
    }
    
    @FXML
    public void handleFiltre(ActionEvent event)
    {
        // index à 1
      this.index = 1;
      FilteredList<ModelDocument> filter = new FilteredList<>(oDocuments,p->true);
      filter.setPredicate(this);
      tableDocuments.setItems(filter);
     
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
       titreField.clear();
       commentaireField.clear();
       dateField.setValue(null);
       referenceField.clear();
           
       enable();
       
    }
    
    @FXML
    public void handleAdd(ActionEvent event)
    {
        ModelDocument model = new ModelDocument();
        model.setTitre(titreField.getText());
        model.setCommentaire(commentaireField.getText());
        model.setDate(dateField.getValue());
        model.setType((String)comboType.getSelectionModel().getSelectedItem());
        model.setReference(referenceField.getText());
        model.setIndex(oDocuments.size() + 1);
        oDocuments.add(model);
        
        
       comboType.setValue(oType.get(0));
       titreField.clear();
       commentaireField.clear();
       dateField.setValue(null);
       referenceField.clear();
        
        disable();
        
    }
    
    @FXML
    public void handleDelete()
    {
        ModelDocument model = (ModelDocument) tableDocuments.getSelectionModel().getSelectedItem();
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
                oDocuments.remove(model);
            }

        }

         disable();
    }
    
    @FXML
    public void handleModif()
    {
        ModelDocument model = (ModelDocument) tableDocuments.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            try {
                model.setTitre(titreField.getText());
                model.setCommentaire(commentaireField.getText());
                model.setDate(dateField.getValue());
                model.setType((String)comboType.getSelectionModel().getSelectedItem());
                model.setReference(referenceField.getText());
                
                String sql = "update t_document set ref_id_type = (select t_type_document.id from t_type_document where t_type_document.type = ?), titre = ?, commentaire = ?, date = ?,referrence = ?  where id = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setString(1, model.getType());
                ps.setString(2, model.getTitre());
                ps.setString(3, model.getCommentaire());
                ps.setDate(4, Date.valueOf(model.getDate()));
                ps.setString(5, model.getReference());
                ps.setLong(6, model.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
         disable();
    }
    
    @FXML
    public void handleSelect()
    {
        ModelDocument model = (ModelDocument) tableDocuments.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            comboType.setValue(model.getType());
            titreField.setText(model.getTitre());
            commentaireField.setText(model.getCommentaire());
            dateField.setValue(model.getDate());
            referenceField.setText(model.getReference());
            
            
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
                        ModelDocument model = (ModelDocument) o;
                        PreparedStatement ps = null;
                        try {
                            // ajout
                            String sql = "insert into t_document (ref_id_type,titre,commentaire,date,reference,ref_id_folders) values "
                                    + "((select id from t_type_document where type = ?),?,?,?,?,?)";
                            ps = ConnectionSQL.getCon().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, model.getType());
                            ps.setString(2, model.getTitre());
                            ps.setString(3, model.getCommentaire());
                            ps.setDate(4, Date.valueOf(model.getDate()));
                            ps.setString(5, model.getReference());
                            ps.setLong(6, this.currentDossier.getId());
                            ps.execute();
                            ResultSet r = ps.getGeneratedKeys();
                            r.first();
                            model.setId(r.getLong(1));
                        } catch (SQLException ex) {
                            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally 
                        {
                            try {
                                ps.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                }
                
                if(c.wasRemoved())
                {
                    for(Object o : c.getRemoved())
                    {
                        try {
                            ModelDocument model = (ModelDocument) o;
                            String sql = "delete from t_document where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, model.getId());
                            ps.execute();
                        } catch (SQLException ex) {
                            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        oDocuments.removeListener(this);
        try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_document inner join t_type_document on t_document.ref_id_type = t_type_document.id where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, currentDossier.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oDocuments.clear();
            int index = 1;
            while(result.next())
            {
                ModelDocument model = new ModelDocument();
                model.setId(result.getLong("id"));
                model.setTitre(result.getString("titre"));
                model.setCommentaire(result.getString("commentaire"));
                model.setDate(result.getDate("date").toLocalDate());
                model.setType(result.getString("type"));
                model.setReference(result.getString("reference"));
                model.setIndex(index);
                index++;
                // add
                oDocuments.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // ajout des events
        oDocuments.addListener(this);
    }

    
    

    @Override
    public boolean test(ModelDocument t)
    {
        //methode de test pour le filtre à type
        
        String compare = (String)comboFiltre.getSelectionModel().getSelectedItem();
        
        if(t.getType().equals(compare) || compare.equals("Tous"))
         {
             t.setIndex(this.index);
             this.index++;
             return true;       
         }
        else
            return false;
               
    }

    

    
   
}