package dao;

import control.SenderData;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Clientes;

public class CorreoDAO {
    
    SenderData sender = new SenderData();
    
     public List<Clientes> listar() {

        List<Clientes> listaCtes = null;
        ResultSet rs;

        try {
            Driver drv = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(drv);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shalom?user=root&password=&useSSL=false");

            PreparedStatement ps = conn.prepareCall("SELECT * FROM clientes");
            rs = ps.executeQuery();

            listaCtes = new ArrayList();

            while (rs.next()) {
                Clientes c = new Clientes();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNomCliente(rs.getString("nomCliente"));
                c.setApellCliente(rs.getString("apellCliente"));
                c.setTelefCliente(rs.getInt("telefCliente"));
                c.setCorreoCliente(rs.getString("correoCliente"));
                c.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                c.setGeneroCliente(rs.getString("generoCliente"));
                listaCtes.add(c);
            }
        } catch (SQLException e) {
        } 

        return listaCtes;
    } 
}
