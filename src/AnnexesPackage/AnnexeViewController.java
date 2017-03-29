/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnnexesPackage;

import DocumentsPackage.*;
import EntityPackage.*;
import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelAnnexe;
import ModelPackage.ModelDocument;
import ModelPackage.ModelDossier;
import ModelPackage.ModelPersonne;
import UtilsPackage.DateCell;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javax.sql.rowset.serial.SerialBlob;



/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class AnnexeViewController implements Initializable,IController,ListChangeListener{

    @FXML
    private TableView tableAnnexes;
    @FXML
    private TableColumn<ModelAnnexe,String> columnIndex;
    @FXML
    private TableColumn<ModelAnnexe,String> columnlibelle;
    @FXML
    private TableColumn<ModelAnnexe,Blob> columnVoiAnnexe; 
    @FXML
    private TextField libelleField;
    @FXML
    private TextArea commentaireField;
    @FXML
    private Button buttonAssocierAnnexe;
    
    @FXML
    private Button buttonModif;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDel;
    
    // Blob
    private Blob raw = null;
    
    // list de Personne
    private ObservableList<ModelAnnexe> oAnnexes = FXCollections.observableArrayList();
      
    private ModelDossier currentDossier;
    // index
    private int index = 1;
   
     
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // disable les field
        disable();
        
       
            // ajout des event
            
            // initi
           
           
            // setitem
           // tableDocuments.setItems(oDocuments);
            // factory
            columnIndex.setCellValueFactory(cellData->cellData.getValue().indexProperty().asString());
            columnlibelle.setCellValueFactory(cellData->cellData.getValue().libelleProperty());
            columnVoiAnnexe.setCellValueFactory(cellData->cellData.getValue().rawProperty());
            // cellfactory
            columnVoiAnnexe.setCellFactory(p->new RawCell());
            tableAnnexes.setItems(oAnnexes);
           
    }
    
    private void disable()
    {
            libelleField.setDisable(true);
            commentaireField.setDisable(true);
            buttonAssocierAnnexe.setDisable(true);
            buttonModif.setDisable(true);
            buttonAdd.setDisable(true);
            buttonDel.setDisable(true);
    }
    
    private void enable()
    {
            libelleField.setDisable(false);
            commentaireField.setDisable(false);
            buttonAssocierAnnexe.setDisable(false);
            buttonModif.setDisable(false);
            buttonAdd.setDisable(false);
            buttonDel.setDisable(false);
    }
    
    @FXML
    public void handleAttachFichier()
    {
       FileChooser dialog = new FileChooser();
       dialog.setTitle("Sélection du fichier à attacher");
       File file = dialog.showOpenDialog(libelleField.getScene().getWindow());
       if(file != null)
       {
           try 
           {
               FileInputStream stream = new FileInputStream(file);
               byte[] b;
               b = new byte[stream.available()];
               stream.read(b); 
               raw = new SerialBlob(b);
               
           } catch (FileNotFoundException ex) {
               Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
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
            commentaireField.clear();
          
       enable();
       
    }
    
    @FXML
    public void handleAdd(ActionEvent event)
    {
        if(raw == null)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de fichier");
            alert.setContentText("Aucun fichier n'est associé, veuillez importer un fichier avant d'ajouter l'annexe");
            alert.showAndWait();
            return;
        }
        
        ModelAnnexe model = new ModelAnnexe();
        model.setLibelle(libelleField.getText());
        model.setCommentaire(commentaireField.getText());
        model.setRaw(this.raw);
        model.setIndex(oAnnexes.size() + 1);
        oAnnexes.add(model);

       libelleField.clear();
       commentaireField.clear();
        
        disable();
        
    }
    
    @FXML
    public void handleDelete()
    {
        ModelAnnexe model = (ModelAnnexe) tableAnnexes.getSelectionModel().getSelectedItem();
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
                oAnnexes.remove(model);
            }

        }

         disable();
    }
    
    @FXML
    public void handleModif()
    {
        // Message d'information indiquant qu'il n'est possible de modifier le fichier attaché (raw)
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Modification");
        alert.setContentText("Pour information, il n'est pas possible de modifier le fichier attaché, si vous désirez modifier le fichier, il est nécessaire"
                + " supprimer l'annexe et en ajouter une nouvelle");
        alert.showAndWait();
        
        ModelAnnexe model = (ModelAnnexe) tableAnnexes.getSelectionModel().getSelectedItem();
        if(model != null)
        {
            try {
                model.setLibelle(libelleField.getText());
                model.setCommentaire(commentaireField.getText());
              
                String sql = "update t_annexe set libelle = ?, commentaire = ? where id = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setString(1, model.getLibelle());
                ps.setString(2, model.getCommentaire());
                ps.setLong(3, model.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
         disable();
    }
    
    @FXML
    public void handleSelect()
    {
        ModelAnnexe model = (ModelAnnexe) tableAnnexes.getSelectionModel().getSelectedItem();
        if(model != null)
        {
           
            libelleField.setText(model.getLibelle());
            commentaireField.setText(model.getCommentaire());
            enable();
            // disable du bouton attacher car pas de modification possible du fichier
            buttonAssocierAnnexe.setDisable(true);
            buttonAdd.setDisable(true);
            
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
                        ModelAnnexe model = (ModelAnnexe) o;
                        PreparedStatement ps = null;
                        try {
                            // ajout
                            String sql = "insert into t_annexe (libelle,commentaire,raw,ref_id_folders) values "
                                    + "(?,?,?,?)";
                            ps = ConnectionSQL.getCon().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, model.getLibelle());
                            ps.setString(2, model.getCommentaire());
                            ps.setBlob(3, model.getRaw());
                            ps.setLong(4, this.currentDossier.getId());
                            ps.execute();
                            ResultSet r = ps.getGeneratedKeys();
                            r.first();
                            model.setId(r.getLong(1));
                        } catch (SQLException ex) {
                            Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally 
                        {
                            try {
                                ps.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                }
                
                if(c.wasRemoved())
                {
                    for(Object o : c.getRemoved())
                    {
                        try {
                            ModelAnnexe model = (ModelAnnexe) o;
                            String sql = "delete from t_annexe where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                            ps.setLong(1, model.getId());
                            ps.execute();
                        } catch (SQLException ex) {
                            Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        oAnnexes.removeListener(this);
        try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_annexe where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, currentDossier.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oAnnexes.clear();
            int index = 1;
            while(result.next())
            {
                ModelAnnexe model = new ModelAnnexe();
                model.setId(result.getLong("id"));
                model.setLibelle(result.getString("libelle"));
                model.setCommentaire(result.getString("commentaire"));
                model.setRaw(result.getBlob("raw"));
                model.setIndex(index);
                index++;
                // add
                oAnnexes.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // ajout des events
        oAnnexes.addListener(this);
    }
  
}
