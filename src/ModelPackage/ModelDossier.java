/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import DocumentsPackage.DocumentViewController;
import EntityPackage.NumeroViewController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Thonon
 */
public class ModelDossier extends Model
{

    // nom du dossier
    private final StringProperty nomDossier = new SimpleStringProperty();
    // nom du owner
    private final StringProperty nomOwner = new SimpleStringProperty();
    
    // Numero
    private ObservableList<ModelNumero> oNumeros;
    // Document
     private ObservableList<ModelDocument> oDocuments;

    public ModelDossier() 
    {
        oNumeros = FXCollections.observableArrayList();
        oDocuments = FXCollections.observableArrayList();
    }

    
    
    // préparation de donnée du dossier
    public void prepareData()
    {
        // chargement des numéros
         try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_numero where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, this.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oNumeros.clear();
            while(result.next())
            {
                ModelNumero model = new ModelNumero();
                model.setId(result.getLong("id"));
                model.setType(result.getString("type"));
                model.setNumero(result.getString("numero"));
                model.setNationalite(result.getString("nationalite"));
                model.setOwner(result.getString("owner"));
                // add
                oNumeros.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NumeroViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         // chargement des documents
          try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_document inner join t_type_document on t_document.ref_id_type = t_type_document.id where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, this.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oDocuments.clear();
            int index = 1;
            while(result.next())
            {
                ModelDocument model = new ModelDocument();
                model.setId(result.getLong("id"));
                model.setTitre(result.getString("titre"));
                model.setCommentaire(result.getString("commentaire"));
                model.setDate(result.getDate("date").toLocalDate());
                model.setType(result.getString("type"));
                model.setReference(result.getString("reference"));
                model.setContenu(result.getString("contenu"));
                model.setIndex(index);
                index++;
                // add
                oDocuments.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DocumentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ObservableList<ModelDocument> getoDocuments() {
        return oDocuments;
    }

    public ObservableList<ModelNumero> getoNumeros() {
        return oNumeros;
    }
        
    public String getNomOwner() {
        return nomOwner.get();
    }

    public void setNomOwner(String value) {
        nomOwner.set(value);
    }

    public StringProperty nomOwnerProperty() {
        return nomOwner;
    }
    

    public String getNomDossier() {
        return nomDossier.get();
    }

    public void setNomDossier(String value) {
        nomDossier.set(value);
    }

    public StringProperty nomDossierProperty() {
        return nomDossier;
    }
    // nom du dossier
    
}
