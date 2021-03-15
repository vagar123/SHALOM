package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import modelo.Proveedores;

public class ProveedoresDAO extends DAO {

    public void registrar(Proveedores pro) throws Exception {
        if (pro != null && !pro.getNomProveedor().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO proveedores (idProveedor, nomProveedor, telefProveedor, correoProveedor, direccProveedor, fechaEntrada) values(?, ?, ?, ?, ?, ?)");
                ps.setInt(1, pro.getIdProveedor());
                ps.setString(2, pro.getNomProveedor());
                ps.setInt(3, pro.getTelefProveedor());
                ps.setString(4, pro.getCorreoProveedor());
                ps.setString(5, pro.getDireccProveedor());
                Calendar c = Calendar.getInstance();
                c.setTime(pro.getFechaEntrada());
                java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
                ps.setDate(6, sqlDate);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Proveedores> listar() throws SQLException {
        List<Proveedores> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM proveedores");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Proveedores p = new Proveedores();
                p.setIdProveedor(rs.getInt("idProveedor"));
                p.setNomProveedor(rs.getString("nomProveedor"));
                p.setTelefProveedor(rs.getInt("telefProveedor"));
                p.setCorreoProveedor(rs.getString("correoProveedor"));
                p.setDireccProveedor(rs.getString("direccProveedor"));
                p.setFechaEntrada(rs.getDate("fechaEntrada"));
              
                lista.add(p);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Proveedores leedId(Proveedores pro) throws Exception {
        Proveedores prov;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM proveedores WHERE idProveedor = ?");
            ps.setInt(1, pro.getIdProveedor());
            rs = ps.executeQuery();
            rs.next();
            prov = new Proveedores();
            prov.setIdProveedor(rs.getInt("idProveedor"));
            prov.setNomProveedor(rs.getString("nomProveedor"));
            prov.setTelefProveedor(rs.getInt("telefProveedor"));
            prov.setCorreoProveedor(rs.getString("correoProveedor"));
            prov.setDireccProveedor(rs.getString("direccProveedor"));
            prov.setFechaEntrada(rs.getDate("fechaEntrada"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return prov;
    }

    public void actualizar(Proveedores pro) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE proveedores SET nomProveedor = ?, telefProveedor = ?, correoProveedor = ?, direccProveedor = ?, fechaEntrada = ?  WHERE idProveedor = ?");
            ps.setString(1, pro.getNomProveedor());
            ps.setInt(2, pro.getTelefProveedor());
            ps.setString(3, pro.getCorreoProveedor());
            ps.setString(4, pro.getDireccProveedor());
            Calendar c = Calendar.getInstance();
            c.setTime(pro.getFechaEntrada());
            java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
            ps.setDate(5, sqlDate);
            ps.setInt(6, pro.getIdProveedor());
            ps.executeUpdate();
            
            
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Proveedores pro) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM proveedores WHERE idProveedor = ?");
            ps.setInt(1, pro.getIdProveedor());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
