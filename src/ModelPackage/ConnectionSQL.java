/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Thonon
 */
public class ConnectionSQL 
{
    private static Connection con;
    
    private static String url = "jdbc:mysql://localhost:3306/db_gel";
    private static String user = "root";
    private static String password = "19868tt5425";
    
    public ConnectionSQL() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException
    {
      // chargement du fichier de configuration
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "managertconfig" + System.getProperty("file.separator") + "managert.ini");
        properties.load(input);
        url = properties.getProperty("url_database");
 
    }
    
    public static void Connect() throws SQLException, ClassNotFoundException
    {
      // For Mysql
       Class.forName("com.mysql.jdbc.Driver");
        // For hsqldb
        //Class.forName("org.hsqldb.jdbcDriver");
        // For SqlLite
        //Class.forName("org.sqlite.JDBC");
       con = DriverManager.getConnection(url, user, password);
        
    }
    
    public static Connection getCon() {
        return con;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        ConnectionSQL.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConnectionSQL.password = password;
    }

   
    
    
    
}
