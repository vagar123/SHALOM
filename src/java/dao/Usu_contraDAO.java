package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Usu_contra;

public class Usu_contraDAO extends DAO {

    public void registrar(Usu_contra usu) throws Exception {
        if (usu != null && !usu.getUsu().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO usu_contra (usu, contra, tipo_usu, usuario) values(?, ?, ?, ?)");              
                ps.setString(1, usu.getUsu());
                ps.setInt(2, usu.getContra());
                ps.setString(3, usu.getTipo_usu());
                ps.setInt(4, usu.getUsuario());                
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Usu_contra> listar() throws SQLException {
        List<Usu_contra> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("select usu_contra.*, usuarios.idUsuario\n"
                    + "from usu_contra inner join usuarios\n"
                    + "on usu_contra.usuario = usuarios.idUsuario;");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Usu_contra i = new Usu_contra();
                i.setUsu(rs.getString("usu"));
                i.setContra(rs.getInt("contra"));
                i.setTipo_usu(rs.getString("tipo_usu"));
                i.setUsuario(rs.getInt("usuario"));
                lista.add(i);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    
    public Usu_contra leedId(Usu_contra usu) throws Exception {
        Usu_contra contra;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM usu_contra WHERE usu = ?");
            ps.setString(1, usu.getUsu());
            rs = ps.executeQuery();
            rs.next();
            contra = new Usu_contra();
            contra.setUsu(rs.getString("usu"));          
            contra.setContra(rs.getInt("contra"));
            contra.setTipo_usu(rs.getString("tipo_usu"));
            contra.setUsuario(rs.getInt("usuario"));
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return contra;
    }
    
    public void actualizar(Usu_contra usu) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE usu_contra SET contra = ?, tipo_usu = ?, usuario = ? WHERE usu = ?");                      
            ps.setInt(1, usu.getContra());
            ps.setString(2, usu.getTipo_usu());
            ps.setInt(3, usu.getUsuario());
            ps.setString(4, usu.getUsu());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
    
    public void eliminar(Usu_contra usu) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM usu_contra WHERE usu = ?");
            ps.setString(1, usu.getUsu());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
