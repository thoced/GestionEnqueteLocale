/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import AnnexesPackage.AnnexeViewController;
import ApostillesPackage.ApostilleViewController;
import DocumentsPackage.DocumentViewController;
import EntityPackage.NumeroViewController;
import EntityPackage.PersonneViewController;
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
    // Personne
    private ObservableList<ModelPersonne> oPersonnes;
    // Document
    private ObservableList<ModelDocument> oDocuments;
    // Annexe
    private ObservableList<ModelAnnexe> oAnnexes;
    // Apostilles
    private ObservableList<ModelApostille> oApostilles;
    // NiceTrack
    private ModelNiceWrapper niceWrapper = null;

    public ModelDossier() 
    {
        oNumeros = FXCollections.observableArrayList();
        oPersonnes = FXCollections.observableArrayList();
        oDocuments = FXCollections.observableArrayList();
        oAnnexes = FXCollections.observableArrayList();
        oApostilles = FXCollections.observableArrayList();
        
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
         
         // chargement des personnes
         try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_personne where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, this.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oPersonnes.clear();
            while(result.next())
            {
                ModelPersonne model = new ModelPersonne();
                model.setId(result.getLong("id"));
                model.setNom(result.getString("nom"));
                model.setPrenom(result.getString("prenom"));
                model.setAdresse(result.getString("adresse"));
                model.setDateNaissance(result.getDate("date_naissance").toLocalDate());
                model.setQualite(result.getString("qualite"));
                // add
                oPersonnes.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonneViewController.class.getName()).log(Level.SEVERE, null, ex);
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
          
          // chargement des annexes
           try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_annexe where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, this.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oAnnexes.clear();
            int index = 1;
            while(result.next())
            {
                ModelAnnexe model = new ModelAnnexe();
                model.setId(result.getLong("id"));
                model.setLibelle(result.getString("libelle"));
                model.setCommentaire(result.getString("commentaire"));
                model.setRaw(result.getBlob("raw"));
                model.setIndex(index);
                index++;
                // add
                oAnnexes.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnnexeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
           // chargement des apostilles
            try {
            PreparedStatement ps = null;
            // chargement de la liste des personnes
            String sql = "select * from t_apostille where ref_id_folders = ?";
            ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setLong(1, this.getId());
            ResultSet result = ps.executeQuery();
            // clear de oPersonnes
            oApostilles.clear();
            int index = 1;
            while(result.next())
            {
                ModelApostille model = new ModelApostille();
                model.setId(result.getLong("id"));
                model.setAutorite(result.getString("autorite"));
                model.setLibelle(result.getString("libelle"));
                model.setContenu(result.getString("contenu"));
                model.setStatut(result.getBoolean("statut"));
                try
                {
                     model.setDateIn(result.getDate("date_IN").toLocalDate());
                }catch(NullPointerException npe)
                {
                    
                }
                try
                {
                    model.setDateOut(result.getDate("date_OUT").toLocalDate());
                }
                catch(NullPointerException npe)
                {
                    
                }
                model.setIndex(index);
                index++;
                // add
                oApostilles.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApostilleViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ModelNiceWrapper getNiceWrapper() {
        return niceWrapper;
    }

    public void setNiceWrapper(ModelNiceWrapper niceWrapper) {
        this.niceWrapper = niceWrapper;
    }

    
    
    public ObservableList<ModelApostille> getoApostilles() {
        return oApostilles;
    }
    
    
    public ObservableList<ModelAnnexe> getoAnnexes() {
        return oAnnexes;
    }
    
    
    public ObservableList<ModelDocument> getoDocuments() {
        return oDocuments;
    }

    public ObservableList<ModelNumero> getoNumeros() {
        return oNumeros;
    }

    public ObservableList<ModelPersonne> getoPersonnes() {
        return oPersonnes;
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
