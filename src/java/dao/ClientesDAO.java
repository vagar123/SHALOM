package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Clientes;


//Libreria para factura añadir cliente
public class ClientesDAO extends DAO {
    
    //METODO VISTO EN EL VIDEO PARA LA ORDEN DE PAGO
    ResultSet rs;
    
    
    public Clientes buscar(String nomCliente) {
        Clientes c = new Clientes();
        String sql = "select * from clientes where nomCliente="+nomCliente;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                c.setIdCliente(rs.getInt(1));
                c.setNomCliente(rs.getString(2));
                c.setApellCliente(rs.getString(3));
                c.setTelefCliente(rs.getInt(4));
                c.setCorreoCliente(rs.getString(5));
                c.setFechaNacimiento(rs.getDate(6));
                c.setGeneroCliente(rs.getString(7));
            }
        } catch (Exception e) {
        }
        return c;
    }

    public void registrar(Clientes per) throws Exception {
        if (per != null && !per.getNomCliente().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO clientes (idCliente, nomCliente, apellCliente, telefCliente, correoCliente, fechaNacimiento, generoCliente) values(?, ?, ?, ?, ?, ?, ?)");
                ps.setInt(1, per.getIdCliente());
                ps.setString(2, per.getNomCliente());
                ps.setString(3, per.getApellCliente());
                ps.setInt(4, per.getTelefCliente());
                ps.setString(5, per.getCorreoCliente());
                Calendar c = Calendar.getInstance();
                c.setTime(per.getFechaNacimiento());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(6, sqlDate);
                ps.setString(7, per.getGeneroCliente());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Clientes> listar() throws SQLException {
        List<Clientes> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM clientes");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Clientes p = new Clientes();
                p.setIdCliente(rs.getInt("idCliente"));
                p.setNomCliente(rs.getString("nomCliente"));
                p.setApellCliente(rs.getString("apellCliente"));
                p.setTelefCliente(rs.getInt("telefCliente"));
                p.setCorreoCliente(rs.getString("correoCliente"));
                p.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                p.setGeneroCliente(rs.getString("generoCliente"));

                lista.add(p);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Clientes leedId(Clientes per) throws Exception {
        Clientes pers;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM clientes WHERE idCliente = ?");
            ps.setInt(1, per.getIdCliente());
            rs = ps.executeQuery();
            rs.next();
            pers = new Clientes();
            pers.setIdCliente(rs.getInt("idCliente"));
            pers.setNomCliente(rs.getString("nomCliente"));
            pers.setApellCliente(rs.getString("apellCliente"));
            pers.setTelefCliente(rs.getInt("telefCliente"));
            pers.setCorreoCliente(rs.getString("correoCliente"));
            pers.setFechaNacimiento(rs.getDate("fechaNacimiento"));
            pers.setGeneroCliente(rs.getString("generoCliente"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return pers;
    }

    public void actualizar(Clientes per) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE clientes SET nomCliente = ?, apellCliente =?, telefCliente =?, correoCliente =?, fechaNacimiento =?, generoCliente =? WHERE idCliente = ?");
            ps.setString(1, per.getNomCliente());
            ps.setString(2, per.getApellCliente());
            ps.setInt(3, per.getTelefCliente());
            ps.setString(4, per.getCorreoCliente());
            Calendar c = Calendar.getInstance();
            c.setTime(per.getFechaNacimiento());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(5, sqlDate);
            ps.setString(6, per.getGeneroCliente());
            ps.setInt(7, per.getIdCliente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Clientes per) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM clientes WHERE idCliente = ?");
            ps.setInt(1, per.getIdCliente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
