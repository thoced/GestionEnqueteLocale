/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thonon
 */
@XmlRootElement(name="Export")
public class ModelNiceWrapper 
{

    private List<ModelNice> oNices;

    @XmlElement(name="Event") 
    public List<ModelNice> getoNices() {
        return oNices;
    }

    public void setoNices(List<ModelNice> oNices) {
        this.oNices = oNices;
    }
    
}
