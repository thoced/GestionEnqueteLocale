/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPackage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Thonon
 */
public class PersonneViewController implements Initializable {

    @FXML
    private ComboBox comboQualite;
   
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // initi comboQualite
        ObservableList<String> oQualite = FXCollections.observableArrayList();
        oQualite.add("Victime");
        oQualite.add("Suspect");
        oQualite.add("Témoin");
        oQualite.add("Autre");
        comboQualite.setItems(oQualite);
        comboQualite.setValue(oQualite.get(0));
    }    
    
}
