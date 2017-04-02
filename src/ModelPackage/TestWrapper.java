/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thonon
 */
@XmlRootElement(name="Export")
public class TestWrapper 
{
    private List<Voiture> listVoiture;

    @XmlElement(name="Voiture")
    public List<Voiture> getListVoiture() {
        return listVoiture;
    }

    public void setListVoiture(List<Voiture> listVoiture) {
        this.listVoiture = listVoiture;
    }
    
    
}
