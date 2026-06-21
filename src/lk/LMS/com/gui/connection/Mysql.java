
package lk.LMS.com.gui.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {
    private static final String DATABASE= "inventorymanagmentsystem";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static Connection connection;
    
    
    public static Connection getconnection(){
        try {
            if(connection == null){
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE, USERNAME, PASSWORD);
            }
        } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
        }
    
    return connection;
    }
    
    public static ResultSet excute(String query) throws SQLException{
    Statement smt = getconnection().createStatement();
    
    if(query.toUpperCase().startsWith("SELECT")){
    
    return smt.executeQuery(query);
    }else{
    smt.executeUpdate(query);
    
    
    return null;
    
    }
    
    }
}
