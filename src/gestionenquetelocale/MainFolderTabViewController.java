/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionenquetelocale;

import ModelPackage.IController;
import ModelPackage.ModelDossier;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class MainFolderTabViewController implements Initializable {

   @FXML
   private Tab documentsTab;
   @FXML
   private Tab annexesTab;
   @FXML
   private Tab apostillesTab;
   
   private ModelDossier currentDossier;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }    
    
    public void load(ModelDossier folder) throws IOException
    {
        currentDossier = folder;
        // chargement des tab
        
        // Documents
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DocumentsPackage/DocumentView.fxml"));
        BorderPane bp = loader.load();
        IController controller = loader.getController();
        controller.loadModel(currentDossier);
        ((BorderPane)documentsTab.getContent()).setCenter(bp);
        
        // Annexes
        loader = new FXMLLoader(this.getClass().getResource("/AnnexesPackage/AnnexeView.fxml"));
        bp = loader.load();
        controller = loader.getController();
        controller.loadModel(currentDossier);
        ((BorderPane)annexesTab.getContent()).setCenter(bp);
        
        // Apostilles
        loader = new FXMLLoader(this.getClass().getResource("/ApostillesPackage/ApostilleView.fxml"));
        bp = loader.load();
        controller = loader.getController();
        controller.loadModel(currentDossier);
        ((BorderPane)apostillesTab.getContent()).setCenter(bp);
    }
    
    
}
