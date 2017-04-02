/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentsPackage;

import EntityPackage.*;
import LinksPackage.LinksViewController;
import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelAnnexe;
import ModelPackage.ModelDocument;
import ModelPackage.ModelDossier;
import ModelPackage.ModelPersonne;
import UtilsPackage.DateCell;
import UtilsPackage.ViewPdfController;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.swing.event.ChangeEvent;


/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class DocumentViewController implements Initializable,IController,ListChangeListener,Predicate<ModelDocument>,EventHandler<WindowEvent>,ChangeListener<ModelAnnexe>{

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
    
    // liste des annexes attachées
    @FXML
    private ListView listAnnexes;
    
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
    // list annexes attachées
    private ObservableList<ModelAnnexe> oAnnexes = FXCollections.observableArrayList();
      
    private ModelDossier currentDossier;
    private ModelDocument currentDocument;
    
    // contenu controller
    private ContenuViewController controllerContenu;
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
            
            // factory
            columnDate.setCellFactory(p->new DateCell());
            // set item de la liste des annexes attachées
            listAnnexes.setItems(oAnnexes);
            listAnnexes.setOnMouseClicked((mouseEvent) -> {
               
                        if(mouseEvent.getClickCount() < 2)
                            return;

                        ModelAnnexe model = (ModelAnnexe) listAnnexes.getSelectionModel().getSelectedItem();
                        if(model != null)
                            {
                             try {
                            // on récupère la raw pour l'afficher, pour ce faire, on relance une requete pour le récupérer
                            String sql_raw  = "select raw from t_annexe where id = ?";
                            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql_raw);
                            ps.setLong(1, model.getId());
                            ResultSet result_raw = ps.executeQuery();
                            result_raw.first();
                            model.setRaw(result_raw.getBlob("raw"));
                            // lancement de l'affiche du PDf
                            if(model.getRaw() != null)
                            {
                                ViewPdfController vpc = new ViewPdfController(model.getLibelle(),model.getRaw());
                                vpc.ShowPDFDocument();
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            }

                         });
            
           
         
            
        } catch (SQLException ex) {
            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
       
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
    public void handleContenu(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/EntityPackage/EntityBaseView.fxml"));
            BorderPane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setMinWidth(960);
            stage.initOwner(tableDocuments.getScene().getWindow());
            stage.initStyle(StageStyle.UTILITY);
            // stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Gestion du contenu du document");
            // Personne
            FXMLLoader loaderPersonne = new FXMLLoader(this.getClass().getResource("/DocumentsPackage/ContenuView.fxml"));
            AnchorPane panePersonne = loaderPersonne.load();
            controllerContenu = loaderPersonne.getController();
            controllerContenu.setContenu(((ModelDocument)tableDocuments.getSelectionModel().getSelectedItem()).getContenu());
           // controller.loadModel(currentDossier);
            pane.setCenter(panePersonne);
            
            stage.setResizable(false);
            stage.setScene(scene);
            // ajout d'un listener de fermeture pour pouvoir enregistré le contenu inséré
            stage.setOnHidden(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleAttach(ActionEvent event) throws IOException 
    {
       
        FXMLLoader loaderLinks = new FXMLLoader(this.getClass().getResource("/LinksPackage/LinksView.fxml"));
        AnchorPane panePersonne = loaderLinks.load();
        LinksViewController controller = loaderLinks.getController();
        controller.load(currentDocument, currentDossier);
        Scene scene = new Scene(panePersonne);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
       
       
    }
    
   
    
    @FXML
    public void handleFiltre(ActionEvent event)
    {
        // index à 1
      this.index = 1;
      FilteredList<ModelDocument> filter = new FilteredList<>(this.currentDossier.getoDocuments(),p->true);
      filter.setPredicate(this);
      tableDocuments.setItems(filter);
     
    }
    
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
       // init controller contenu
       controllerContenu = null;
           
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
        if(controllerContenu != null && controllerContenu.getContenu() != null)
         model.setContenu(controllerContenu.getContenu());
        this.currentDossier.getoDocuments().add(model);
        
        
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
                this.currentDossier.getoDocuments().remove(model);
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
                
                String sql = "update t_document set ref_id_type = (select t_type_document.id from t_type_document where t_type_document.type = ?), titre = ?, commentaire = ?, date = ?, reference = ?, contenu = ?  where id = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setString(1, model.getType());
                ps.setString(2, model.getTitre());
                ps.setString(3, model.getCommentaire());
                ps.setDate(4, Date.valueOf(model.getDate()));
                ps.setString(5, model.getReference());
                ps.setString(6, model.getContenu());
                ps.setLong(7, model.getId());
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
            currentDocument = model;
            try {
                comboType.setValue(model.getType());
                titreField.setText(model.getTitre());
                commentaireField.setText(model.getCommentaire());
                dateField.setValue(model.getDate());
                referenceField.setText(model.getReference());
                controllerContenu = null;
                enable();
                // clear du listAnnexes
                oAnnexes.clear();
                // recherche des annexes attachées
                String sql = "select id,libelle from t_annexe inner join t_link_annexe_document on t_annexe.id = t_link_annexe_document.ref_id_annexe where t_link_annexe_document.ref_id_document = ?";
                PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
                ps.setLong(1, model.getId());
                ResultSet result = ps.executeQuery();
 
                while(result.next())
                {
                    ModelAnnexe annexe = new ModelAnnexe();
                    annexe.setId(result.getLong("id"));
                    annexe.setLibelle(result.getString("libelle"));
                    oAnnexes.add(annexe);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                            String sql = "insert into t_document (ref_id_type,titre,commentaire,date,reference,contenu,ref_id_folders) values "
                                    + "((select id from t_type_document where type = ?),?,?,?,?,?,?)";
                            ps = ConnectionSQL.getCon().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, model.getType());
                            ps.setString(2, model.getTitre());
                            ps.setString(3, model.getCommentaire());
                            ps.setDate(4, Date.valueOf(model.getDate()));
                            ps.setString(5, model.getReference());
                            ps.setString(6, model.getContenu());
                            ps.setLong(7, this.currentDossier.getId());
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
        
        this.currentDossier.getoDocuments().removeListener(this);
        
        this.tableDocuments.setItems(this.currentDossier.getoDocuments());
   
        // ajout des events
        this.currentDossier.getoDocuments().addListener(this);
        
         // filtre
         FilteredList<ModelDocument> filter = new FilteredList<>(this.currentDossier.getoDocuments(),p->true);
         filter.setPredicate(this);
         tableDocuments.setItems(filter);
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

   
  
    @Override
    public void changed(ObservableValue<? extends ModelAnnexe> observable, ModelAnnexe oldValue, ModelAnnexe newValue) 
    {
            
            if(newValue != null && oldValue == newValue)
            {
               
            }
    }

    @Override
    public void handle(WindowEvent event) 
    {

        if(event.getSource().getClass() == Stage.class)
        {
            // si fermeture du stage du contenu, il y a enregistrement
            ModelDocument model = (ModelDocument) tableDocuments.getSelectionModel().getSelectedItem();
            if(model != null)
            {
                if(controllerContenu != null && controllerContenu.getContenu() != null)
                     model.setContenu(controllerContenu.getContenu());
            }
            
            
        }
        
      
    }



   

    

    
   
}
