/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPackage;

import ModelPackage.ConnectionSQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thonon
 */
public class CheckLogin 
{

    public CheckLogin(String login,String password) throws NoLoginException, OkLoginException 
    {
        try {
            // Check de login
            String sql = "select * from t_users where login = ? AND password = ?";
            PreparedStatement ps = ConnectionSQL.getCon().prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet result = ps.executeQuery();
           
            
                // placement au premier
                result.first();
                OkLoginException ok = new OkLoginException();
                ok.getModelUser().setId(result.getLong("id"));
                ok.getModelUser().setLogin(result.getString("login"));
                ok.getModelUser().setNom(result.getString("nom"));
                ok.getModelUser().setPrenom(result.getString("prenom"));
                ok.getModelUser().setPassword(result.getString("password"));
                throw ok;
                
            
        } catch (SQLException ex)
        {
            Logger.getLogger(CheckLogin.class.getName()).log(Level.SEVERE, null, ex);
            // mauvais login
            throw new NoLoginException();
        }
    }
    
    
    
}
