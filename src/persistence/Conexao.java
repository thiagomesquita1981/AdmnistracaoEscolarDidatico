/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sala308b
 */
public abstract class Conexao {
    
    private Connection conexao;
    
    public Connection conecta(){
               
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            String dsn = "jdbc:mysql://localhost/escola?useTimezone=true&serverTimezone=UTC"; 
            if (null == conexao)
                conexao = DriverManager.getConnection(dsn, "root", "123456");
   
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
            return conexao;
    }
    
}
