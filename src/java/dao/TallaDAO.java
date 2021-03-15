package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import modelo.Talla;

public class TallaDAO extends DAO{
    
    public void registrar(Talla talla) throws Exception {
        if (talla != null && talla.getIdTalla().isEmpty()) {
            try {
                this.conectar();
                PreparedStatement ps = this.getConn().prepareStatement("INSERT INTO tallas (idTalla) values(?)");
                ps.setString(1, talla.getIdTalla());
                
                ps.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                this.cerrar();
            }
        }
    }

    public List<Talla> listar() throws SQLException {
        List<Talla> lista;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareCall("SELECT * FROM tallas");
            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                Talla l = new Talla();
                l.setIdTalla(rs.getString("idTalla"));

                lista.add(l);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }

    public Talla leedId(Talla talla) throws Exception {
        Talla tallas;
        ResultSet rs;
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("SELECT * FROM tallas WHERE idTalla = ?");
            ps.setString(1, talla.getIdTalla());
            rs = ps.executeQuery();
            rs.next();
            tallas = new Talla();
            tallas.setIdTalla(rs.getString("idTalla"));

            
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }

        return tallas;
    }

    public void actualizar(Talla tallas) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("UPDATE tallas SET idTalla = ? WHERE idTalla = ?");
            ps.setString(1, tallas.getIdTalla());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    public void eliminar(Talla tallas) throws Exception {
        try {
            this.conectar();
            PreparedStatement ps = this.getConn().prepareStatement("DELETE FROM tallas WHERE idTalla = ?");
            ps.setString(1, tallas.getIdTalla());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
