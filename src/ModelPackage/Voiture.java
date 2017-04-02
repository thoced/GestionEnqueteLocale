/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Thonon
 */
public class Voiture 
{
    private String nom;

    @XmlElement(name="Nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
}
