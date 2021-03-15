package dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public void conectar() throws SQLException{
        try{
        Driver drv = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(drv);

        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shalom?user=root&password=&useSSL=false");
        }catch(SQLException e){
            throw e;
        }
    }
    
    public void cerrar() throws SQLException{
        try{
        if(conn != null)
            if(!conn.isClosed())
                conn.close();
        }catch(SQLException e){
            throw e;
        }            
    }
}
