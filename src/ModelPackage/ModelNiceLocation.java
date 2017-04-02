/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Thonon
 */
public class ModelNiceLocation 
{

    private final ObjectProperty<String> time = new SimpleObjectProperty<>();
    private final ObjectProperty<String> locationGeo = new SimpleObjectProperty<>();
    private final ObjectProperty<String> locationAdresse = new SimpleObjectProperty<>();

    
    @XmlElement(name="Location-GlobalCellId-Address")
    public String getLocationAdresse() {
        return locationAdresse.get();
    }

    public void setLocationAdresse(String value) {
        locationAdresse.set(value);
    }

    public ObjectProperty locationAdresseProperty() {
        return locationAdresse;
    }
    
    @XmlElement(name="Location-Geo")
    public String getLocationGeo() {
        return locationGeo.get();
    }

    public void setLocationGeo(String value) {
        locationGeo.set(value);
    }

    public ObjectProperty locationGeoProperty() {
        return locationGeo;
    }
    
    @XmlElement(name="Time") 
    public String getTime() {
        return time.get();
    }

    public void setTime(String value) {
        time.set(value);
    }

    public ObjectProperty timeProperty() {
        return time;
    }
    
}
