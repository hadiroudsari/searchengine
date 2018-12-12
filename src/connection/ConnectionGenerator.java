/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import cofiguration.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author STS
 */
public class ConnectionGenerator {

    private final static String url = Configuration.getConfig().getUrl();                             //"jdbc:postgresql://localhost:5432/searchdb";
    private final static String user =Configuration.getConfig().getUser();                           //"postgres";
    private final static String password =Configuration.getConfig().getPassword();                  //"1234";
    private static Logger myLog=Logger.getLogger(ConnectionGenerator.class.getName());
    
   public synchronized static Connection getConnection(){
       Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            myLog.log(Level.INFO, "Connected to the PostgreSQL server successfully.");
        } catch (SQLException ex) {
            myLog.log(Level.SEVERE, ex.toString());
        }

        return conn;
   }
}
