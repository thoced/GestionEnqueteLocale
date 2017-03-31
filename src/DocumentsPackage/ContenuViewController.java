/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DocumentsPackage;

import ModelPackage.IController;
import ModelPackage.ModelDossier;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;


/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class ContenuViewController implements Initializable {

    @FXML
    private TextArea editorField;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
 
    }    

    public void setContenu(String contenu)
    {
        editorField.setText(contenu);
    }
    
    public String getContenu()
    {
        return editorField.getText();
    }
    
}
