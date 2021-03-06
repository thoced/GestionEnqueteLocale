/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NiceTrackPackage;

import ModelPackage.ConnectionSQL;
import ModelPackage.IController;
import ModelPackage.ModelDossier;
import ModelPackage.ModelNice;
import ModelPackage.ModelNiceWrapper;
import ModelPackage.ModelNumero;
import ModelPackage.TestWrapper;
import ModelPackage.Voiture;
import UtilsPackage.MakeRapport;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.text.DateFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class NiceTrackViewController implements Initializable,IController {

    protected ModelDossier currentFolder;
    @FXML
    protected ComboBox comboNumeros;
    @FXML
    protected TableView tableNices;
    @FXML
    protected TableColumn<ModelNice,Long> columnEventId;
    @FXML
    protected TableColumn<ModelNice,String> columnStartDate;
    @FXML
    protected TableColumn<ModelNice,String> columnStartTime;
    @FXML
    protected TableColumn<ModelNice,String> columnEndDate;
    @FXML
    protected TableColumn<ModelNice,String> columnEndTime;
    @FXML
    protected TableColumn<ModelNice,String> columnEventType;
    @FXML
    protected TableColumn<ModelNice,String> columnNumCaller;
    @FXML
    protected TableColumn<ModelNice,String> columnNumCalled;
    @FXML
    protected TableColumn<ModelNice,String> columCategorie;
    @FXML
    protected TableColumn<ModelNice,String> columSens;
    @FXML
    protected TextField eventIdField;
    @FXML
    protected TextField typeField;
    
    @FXML
    private TextArea contentField;
    @FXML
    private TextArea synopsisField;
    @FXML
    protected Label labelNbRecords;
    @FXML
    protected ComboBox comboRelevant;
    @FXML
    protected Button buttonRechercher;
    @FXML
    protected Button buttonRapport;
    
    @FXML
    protected DatePicker pickerDateBasse;
    
    @FXML
    protected DatePicker pickerDateHaute;
    
    
    private ModelNumero numeroSearch;
    
    private String request;
    
    private LocalDate dateBasse,dateHaute;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        columnEventId.setCellValueFactory(cellData->cellData.getValue().eventIdProperty());
        columnStartDate.setCellValueFactory(cellData->cellData.getValue().startDateProperty());
        columnStartTime.setCellValueFactory(cellData->cellData.getValue().startTimeProperty());
        columnEndDate.setCellValueFactory(cellData->cellData.getValue().endDateProperty());
        columnEndTime.setCellValueFactory(cellData->cellData.getValue().endTimeProperty());
        columnEventType.setCellValueFactory(cellData->cellData.getValue().eventTypeProperty());
        columnNumCaller.setCellValueFactory(cellData->cellData.getValue().numCallerProperty());
        columnNumCalled.setCellValueFactory(cellData->cellData.getValue().numCalledProperty());
        columCategorie.setCellValueFactory(cellData->cellData.getValue().categorieProperty());
        columSens.setCellValueFactory(cellData->cellData.getValue().sensProperty());
        
        // initialisation du comboRelevant
        ObservableList oRelevant = FXCollections.observableArrayList();
        oRelevant.add("All");
        oRelevant.add("Relevant");
        oRelevant.add("Non relevant");
        comboRelevant.setItems(oRelevant);
       
        
        // creation du rapport
        buttonRapport.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event)
            {
               MakeRapport rapport = new MakeRapport();
               FileChooser fc = new FileChooser();
               ExtensionFilter filter = new ExtensionFilter("Document Word","*.docx");
               fc.setSelectedExtensionFilter(filter);
               File file = fc.showSaveDialog(NiceTrackViewController.this.buttonRapport.getScene().getWindow());
               rapport.createNewDocument(file,NiceTrackViewController.this.tableNices.getItems());

            }
        });
        
        // listener du bouton rechercher
        buttonRechercher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
                boolean relevant = false;
                // récupération des dates
                dateBasse = pickerDateBasse.getValue();
                if(dateBasse == null)
                    dateBasse = LocalDate.of(1990, Month.JANUARY, 1);
                dateHaute = pickerDateHaute.getValue();
                if(dateHaute == null)
                    dateHaute = LocalDate.of(2999, Month.DECEMBER, 31);

               // request = "select * from t_nicetrack where ref_id_numero = ? AND ref_id_folders = ? AND date_start  BETWEEN ? AND ? AND categorie = 'Relevant'";
                NiceTrackViewController.this.searchNice();
            }
        });
        
        // addlistener
        tableNices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelNice>() 
        {
            @Override
            public void changed(ObservableValue<? extends ModelNice> observable, ModelNice oldValue, ModelNice newValue) 
            {
                contentField.clear();
                synopsisField.clear();
                eventIdField.clear();
                typeField.clear();
              if(newValue != null)
                {
                    contentField.setText(newValue.getSmsContent());
                    synopsisField.setText(newValue.getSynopsis());
                    eventIdField.setText(newValue.getEventId().toString());
                    typeField.setText(newValue.getEventType());
                }
                    
            }
        });
        // combo
        comboNumeros.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ModelNumero>() 
        {
            @Override
            public void changed(ObservableValue<? extends ModelNumero> observable, ModelNumero oldValue, ModelNumero newValue) 
            {
                // serach
                numeroSearch = newValue;
            }
        });
        
        // combo relevant
        comboRelevant.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                
                switch(newValue)
                {
                    case "All": request = "select * from t_nicetrack where ref_id_numero = ? AND ref_id_folders = ? AND date_start  BETWEEN ? AND ?";
                    break;
                    
                    case "Relevant": request = "select * from t_nicetrack where ref_id_numero = ? AND ref_id_folders = ? AND date_start  BETWEEN ? AND ? AND categorie = 'Relevant'";
                    break;
                    
                    case "Non relevant": request = "select * from t_nicetrack where ref_id_numero = ? AND ref_id_folders = ? AND date_start  BETWEEN ? AND ? AND categorie = 'Non relevant'";
                    break;
                }
               
               
            }
        }); 
        
        // selection de base en all
         comboRelevant.getSelectionModel().selectFirst();
        
    }  
    
    private void searchNice()
    {
        if(numeroSearch == null)
            return;
        
        PreparedStatement ps = null;
        try {
                    // chargement
                   // String st = "select * from t_nicetrack where ref_id_numero = ? AND ref_id_folders = ?";
                    ps = ConnectionSQL.getCon().prepareStatement(request);
                    ps.setLong(1, numeroSearch.getId());
                    ps.setLong(2, NiceTrackViewController.this.currentFolder.getId());
                    ps.setDate(3, java.sql.Date.valueOf(dateBasse));
                    ps.setDate(4, java.sql.Date.valueOf(dateHaute));
                    ResultSet result = ps.executeQuery();
                    ObservableList<ModelNice> oNices = FXCollections.observableArrayList();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    while(result.next())
                    {
                        ModelNice model = new ModelNice();
                        model.setId(result.getLong("id"));
                        model.setEventId(result.getLong("event_id"));
                        model.setStartDate(result.getDate("date_start").toLocalDate().format(formatter));
                        try
                         {   
                             model.setEndDate(result.getDate("date_end").toLocalDate().format(formatter));
                         }catch(NullPointerException npe){}
                        try
                        {
                             model.setEndTime(result.getTime("time_end").toString());
                        }catch(NullPointerException npe){}
                        
                        model.setStartTime(result.getTime("time_start").toString());
                        model.setNumCalled(result.getString("num_called"));
                        model.setNumCaller(result.getString("num_caller"));
                        model.setEventType(result.getString("event_type"));
                        model.setCategorie(result.getString("categorie"));
                        model.setSens(result.getString("sens"));
                        model.setSmsContent(result.getString("content"));
                        model.setSynopsis(result.getString("synopsis"));
                        oNices.add(model);
                        
                    }
                    tableNices.setItems(oNices);
                } catch (SQLException ex) {
                    Logger.getLogger(NiceTrackViewController.class.getName()).log(Level.SEVERE, null, ex);
                 
                }
                  finally
              {
                  try {
                      ps.close();
                      } catch (SQLException ex) {
                Logger.getLogger(NiceTrackViewController.class.getName()).log(Level.SEVERE, null, ex);
               }
}
                
                // modification du label du nombre de records
                labelNbRecords.setText("Nombre de records: " + tableNices.getItems().size());
    }
    
    @FXML
    public void handleImport(ActionEvent event) throws ParseException
    {
        FileChooser choose = new FileChooser();
        File file = choose.showOpenDialog(comboNumeros.getScene().getWindow());
        if(file != null)
        {
            // creation du marshaller
            try {
                JAXBContext context = JAXBContext
                        .newInstance(ModelNiceWrapper.class);
                Unmarshaller um = context.createUnmarshaller();
                // lecture
               ModelNiceWrapper wrapper =  (ModelNiceWrapper) um.unmarshal(file);
               // placement du model dans le currentFolder
               this.currentFolder.setNiceWrapper(wrapper);
               // création d'une observablelist nécessaire pour la tableNices
               ObservableList<ModelNice> oo = FXCollections.observableArrayList();
               oo.addAll(this.currentFolder.getNiceWrapper().getoNices());
               // set du item
               // ouverture de la vue d'import
               FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/NiceTrackPackage/NiceTrackImportView.fxml"));
               BorderPane pane = loader.load();
               NiceTrackViewImportController controller = loader.getController();
               Scene scene = new Scene(pane);
               Stage stage = new Stage();
               stage.setScene(scene);
               controller.getTableNices().setItems(oo);
               stage.showAndWait();
               // 
               if(controller.isIsImport())
               {
                    String str = "insert into t_nicetrack (event_id,"
                                + "date_start,"
                                + "time_start,"
                                + "date_end,"
                                + "time_end,"
                                + "num_caller,"
                                + "num_called,"
                                + "categorie,"
                                + "sens,"
                                + "content,"
                                + "synopsis,"
                                + "event_type,"
                                + "ref_id_folders,"
                                + "ref_id_numero) "
                                + "value (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(str);
                   
                   for(Object obj : controller.getTableNices().getItems())
                   {
                       ModelNice model = (ModelNice) obj;
                        // l'import est accepté
                       
                        ps.setLong(1, model.getEventId());
                        // conversation date
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        Date parsed = format.parse(model.getStartDate());
                        ps.setDate(2, new java.sql.Date(parsed.getTime()));
                        // conversation time
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalTime t = LocalTime.parse(model.getStartTime(),dtf);
                        
                        ps.setTime(3,  java.sql.Time.valueOf(t));
                        // conversion
                        try
                        {
                        parsed = format.parse(model.getEndDate());
                        ps.setDate(4,new java.sql.Date(parsed.getTime()));
                        }catch(NullPointerException npe)
                        {
                            ps.setDate(4,null);
                        }
                        // conversation time
               
                        t = LocalTime.parse(model.getEndTime(),dtf);
                        ps.setTime(5,  java.sql.Time.valueOf(t));
                        //
                        ps.setString(6, model.getNumCaller());
                        ps.setString(7, model.getNumCalled());
                        ps.setString(8, model.getCategorie());
                        ps.setString(9,model.getSens());
                        ps.setString(10, model.getSmsContent());
                        ps.setString(11, model.getSynopsis());
                        ps.setString(12, model.getEventType());
                        ps.setLong(13, this.currentFolder.getId());
                        ps.setLong(14, ((ModelNumero)comboNumeros.getSelectionModel().getSelectedItem()).getId());
                        ps.execute();
                   }
                   //fermeture
                   ps.close();
                   
                   // ajout des models
                   this.tableNices.getItems().addAll(controller.getTableNices().getItems());
               }
               
            
                
            } catch (JAXBException ex) {
                Logger.getLogger(NiceTrackViewController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NiceTrackViewController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(NiceTrackViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    @Override
    public void setModelDossier(ModelDossier currentDossier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModelDossier getModelDossier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadModel(ModelDossier currentDossier) 
    {
        this.currentFolder = currentDossier;
         // set numéros
        comboNumeros.setItems(this.currentFolder.getoNumeros());

    }

    public TableView getTableNices() {
        return tableNices;
    }

    
    

    
}
